package vn.hoctienganh.services;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements IEmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String content) {
        MimeMessage message = mailSender.createMimeMessage();
        try{
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(new InternetAddress("yt.thangla.2004@gmail.com", "Electric Shop"));
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("An error occurred while sending email", e);
        }
    }
    
    public void sendOtpEmail(String to, String otp) {
        String subject = "Your OTP for Registration";
        String content = "<p>Your OTP for registration is: <strong>" + otp + "</strong></p>";
        sendEmail(to, subject, content);  // Gọi phương thức sendEmail với nội dung OTP
    }
}
