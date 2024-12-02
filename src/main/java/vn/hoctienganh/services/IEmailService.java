package vn.hoctienganh.services;

public interface IEmailService {
    void sendEmail(String to, String subject, String content);
}
