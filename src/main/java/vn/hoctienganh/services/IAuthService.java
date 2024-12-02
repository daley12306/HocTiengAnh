package vn.hoctienganh.services;

import vn.hoctienganh.entity.User;
import vn.hoctienganh.models.LoginRequest;
import vn.hoctienganh.models.Response;

public interface IAuthService {

    Response login(LoginRequest request);
    Response register(User user);
    Response requestPasswordReset(String email);
	void sendOtp(String email);
	Response resetPassword(String email, String password, String confirmPassword);
	Response verifyOtpForEmailConfirmation(String email, String otp);
	Response verifyOtpForPasswordReset(String email, String otp);
}
