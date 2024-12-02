package vn.hoctienganh.services;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisServiceImpl implements IRedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // Lưu OTP với thời gian hết hạn
    @Override
	public void saveOtp(String email, String otp, long timeout) {
        redisTemplate.opsForValue().set(email, otp, timeout, TimeUnit.MINUTES); // Lưu OTP với thời gian hết hạn
    }

    // Lấy OTP từ Redis
    @Override
	public String getOtp(String email) {
        Object otp = redisTemplate.opsForValue().get(email);
        return otp != null ? otp.toString() : null;
    }

    // Xóa OTP
    @Override
	public void deleteOtp(String email) {
        redisTemplate.delete(email);
    }
}
