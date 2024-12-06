package vn.hoctienganh.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import vn.hoctienganh.entity.User;
import vn.hoctienganh.models.LoginRequest;
import vn.hoctienganh.models.Response;
import vn.hoctienganh.services.IAuthService;

@Controller
public class AuthController {

	@Autowired
	private IAuthService authService;

	@GetMapping("/login")
	public String login(HttpSession session) {
		var result = (Response) session.getAttribute("loginResult");
		if (result != null) {
			var user = (User) result.getData();
			if (user != null) {
				if (user.isAdmin())
					return "redirect:/admin/home";
				else
					return "redirect:/user/study";
			}
		}
		return "auth/login";
	}

	@PostMapping("/login")
	public String login(@RequestParam String username, @RequestParam String password, Model model,
			HttpSession session) {
		var result = authService.login(new LoginRequest(username, password));

		if (result.getCode() == 200) {
			User user = (User) result.getData();
			session.setAttribute("loggedInUser", user);

			if (!user.getIsVerified()) {
				authService.sendOtp(user.getEmail());
				return "redirect:/confirm-email?email=" + user.getEmail();
			}

			// Kiểm tra vai trò của người dùng và chuyển hướng tới trang tương ứng
			if (user.isAdmin()) {
				return "redirect:/admin/home";
			} else {
				return "redirect:/user/home";
			}
		} else {
			// Hiển thị thông báo lỗi từ service lên giao diện
			model.addAttribute("error", result.getMessage());
			return "auth/login";
		}
	}

	@GetMapping("/register")
	public String registerPage() {
		return "auth/register";
	}

	@PostMapping("/register")
	public String registerUser(@RequestParam String username, @RequestParam String password,
			@RequestParam String confirmPassword, @RequestParam String fullName, @RequestParam String email,
			@RequestParam String address, @RequestParam String phoneNumber, Model model) {

		if (!password.equals(confirmPassword)) {
			model.addAttribute("confirmPasswordError", "Mật khẩu nhập lại của bạn không khớp");
			return "auth/register";
		}

		User user = new User();
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(password);
		user.setFullName(fullName);
		user.setAddress(address);
		user.setPhoneNumber(phoneNumber);
		user.setIsAdmin(false);

		Response result = authService.register(user);
		
		if (result.getCode() != 200) {
			model.addAttribute("error", result.getMessage());
			return "auth/register";
		} else {
			model.addAttribute("email", email);
			return "auth/confirm-email";
		}
	}

	@GetMapping("/confirm-email")
	public String confirmEmailPage(@RequestParam String email, Model model) {
		model.addAttribute("email", email);
		return "auth/confirm-email";
	}

	@PostMapping("/confirm-email")
	public String verifyOtp(@RequestParam String email, @RequestParam String otp, Model model) {
		Response result = authService.verifyOtpForEmailConfirmation(email, otp);

		if (result.getCode() == 401) {
			model.addAttribute("error", result.getMessage());
			return "auth/register";
		}
		if (result.getCode() != 200) {
			model.addAttribute("email", email);
			model.addAttribute("error", result.getMessage());
			return "auth/confirm-email";
		} else {
			model.addAttribute("message", result.getMessage());
			return "redirect:/login";
		}
	}

	@GetMapping("/forgot-password")
	public String forgotPasswordPage() {
		return "auth/forgot-password";
	}

	@PostMapping("/forgot-password")
	public String requestPasswordReset(@RequestParam String email, Model model, HttpSession session) {
		var result = authService.requestPasswordReset(email);
		if (result.getCode() != 200) {
			model.addAttribute("error", result.getMessage());
			return "/auth/forgot-password";
		}
		session.setAttribute("email", email);
		return "redirect:/verify-otp";
	}

	// Trang xác thực OTP
	@GetMapping("/verify-otp")
	public String verifyOtpPage(Model model) {
		return "/auth/verify-otp";
	}

	@PostMapping("/verify-otp")
	public String verifyOtpForReset(@RequestParam String otp, Model model, HttpSession session) {
		String email = (String) session.getAttribute("email");
		Response result = authService.verifyOtpForPasswordReset(email, otp);

		if (result.getCode() == 401) {
			model.addAttribute("error", result.getMessage());
			return "auth/forgot-password";
		}

		if (result.getCode() == 402) {
			model.addAttribute("email", email);
			model.addAttribute("error", result.getMessage());
			return "auth/verify-otp";
		}
		System.out.println(email);

		model.addAttribute("message", result.getMessage());
		return "redirect:/change-password";
	}

	@GetMapping("/change-password")
	public String changePasswordPage(Model model) {
		return "auth/change-password";
	}

	@PostMapping("/change-password")
	public String changePasswordReset(@RequestParam String email, @RequestParam String password,
			@RequestParam String confirmPassword, Model model) {
		Response result = authService.resetPassword(email, password, confirmPassword);
		if (result.getCode() != 200) {
			model.addAttribute("error", result.getMessage());
			model.addAttribute("email", email);
			return "auth/change-password";
		}
		return "auth/login";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}
	
	@PostMapping("/resend-otp")
	public String resendOtp(@RequestParam String email) {
		authService.sendOtp(email);
	    return "redirect:/confirm-email?email=" + email;
	}
}
