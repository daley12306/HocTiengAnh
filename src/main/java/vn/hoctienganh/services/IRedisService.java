package vn.hoctienganh.services;

public interface IRedisService {

	void saveOtp(String email, String otp, long timeout);

	String getOtp(String email);

	void deleteOtp(String email);
}
