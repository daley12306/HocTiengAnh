package vn.hoctienganh.services;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import vn.hoctienganh.entity.User;
import vn.hoctienganh.models.LoginRequest;
import vn.hoctienganh.models.Response;
import vn.hoctienganh.repository.UserRepo;

@Service
public class AuthServiceImpl implements IAuthService {

	@Autowired
	private UserRepo userRepository; // Giả sử bạn có một repository để truy vấn người dùng.

	@Autowired
	private PasswordEncoder passwordEncoder; // Bạn sẽ sử dụng một PasswordEncoder (ví dụ: BCrypt).

	@Autowired
	private IRedisService redisService;

	@Autowired
	private EmailService emailService;

	@Override
	public Response login(LoginRequest request) {
        Optional<User> user = userRepository.findByUsername(request.getUsername());

		Response response = new Response();
		if (user.isEmpty()) {
			response.setCode(404);
			response.setMessage("Tài khoản bạn nhập không tồn tại");
			return response;
		}
		
		if (!passwordEncoder.matches(request.getPassword(), user.get().getPassword())) {
			response.setCode(401);
			response.setMessage("Sai mật khẩu");
			return response;
		}
		response.setCode(200);
		response.setMessage("Đăng nhập thành công");
		response.setData(user.get());
		return response;
	}

	@Override
	public Response register(User user) {
		Response response = new Response();
		if (userRepository.existsByUsername(user.getUsername())) {
			response.setCode(404);
			response.setMessage("Tài khoản bạn dùng đã tồn tại");
			return response;
		}

		if (userRepository.existsByEmail(user.getEmail())) {
			response.setCode(403);
			response.setMessage("Email bạn dùng đã tồn tại");
			return response;
		}
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		userRepository.save(user);
		sendOtp(user.getEmail());
		
		response.setCode(200);
		response.setMessage("Đăng ký thành công");
		return response;
	}
	
	@Override
	public Response verifyOtpForEmailConfirmation(String email, String otp) {
	    Response response = new Response();

	    String cachedOtp = redisService.getOtp(email);

	    if (cachedOtp == null) {
	        Optional<User> user = userRepository.findByEmail(email);
	        user.ifPresent(u -> userRepository.delete(u));
	        response.setCode(401);
	        response.setMessage("OTP đã hết hạn, vui lòng đăng ký lại.");
	        return response;
	    }

	    if (!cachedOtp.equals(otp)) {
	        response.setCode(402);
	        response.setMessage("Sai OTP, xin vui lòng nhập lại.");
	        return response;
	    }

	    redisService.deleteOtp(email);
	    var userOpt = userRepository.findByEmail(email);
	    
	    if (userOpt.isPresent()) {
	        User user = userOpt.get();
	        user.setIsVerified(true);
	        userRepository.save(user);
	        response.setCode(200);
	        response.setMessage("Email của bạn đã được xác thực thành công.");
	    } else {
	        response.setCode(404);
	        response.setMessage("Không tìm thấy người dùng với email này.");
	    }

	    return response;
	}


	@Override
	public void sendOtp(String email) {
	    String existingOtp = redisService.getOtp(email);
	    if (existingOtp != null) {
	        redisService.deleteOtp(email);
	    }

	    String otp = generateOtp();

	    redisService.saveOtp(email, otp, 1);

	    String subject = "OTP Verification Code";
	    String content = "Your OTP verification code is: " + otp;
	    emailService.sendEmail(email, subject, content);
	}

	private String generateOtp() {
		Random rand = new Random();
		return String.format("%06d", rand.nextInt(999999));
	}
	
	@Override
	public Response requestPasswordReset(String email) {
		var user = userRepository.findByEmail(email);
		Response response = new Response();
		if (user.isEmpty()) {
			response.setCode(404);
			response.setMessage("Email không tồn tại.");
			return response;
		}
		sendOtp(email);
		response.setCode(200);
		response.setMessage("OTP đã được gửi đến email của bạn.");
		return response;
	}

	@Override
	public Response verifyOtpForPasswordReset(String email, String otp) {
	    Response response = new Response();

	    String cachedOtp = redisService.getOtp(email);

	    if (cachedOtp == null) {
	        response.setCode(401);
	        response.setMessage("OTP đã hết hạn, vui lòng xác thực lại.");
	        return response;
	    }

	    if (!cachedOtp.equals(otp)) {
	        response.setCode(402);
	        response.setMessage("Sai OTP, xin vui lòng nhập lại.");
	        return response;
	    }

	    redisService.deleteOtp(email);

	    response.setCode(200);
	    response.setMessage("Email của bạn đã được xác thực thành công.");
	    return response;
	}

	@Override
	public Response resetPassword(String email, String newPassword, String confirmPassword) {
		Response response = new Response();

		if (!newPassword.equals(confirmPassword)) {
			response.setCode(400);
			response.setMessage("Mật khẩu nhập lại của bạn không khớp");
			return response;
		}

		var userOpt = userRepository.findByEmail(email);
		User user = userOpt.get();

		user.setPassword(passwordEncoder.encode(newPassword));

		userRepository.save(user);

		redisService.deleteOtp(email);

		response.setCode(200);
		response.setMessage("Mật khẩu đã được thay đổi thành công.");

		return response;
	}
}
