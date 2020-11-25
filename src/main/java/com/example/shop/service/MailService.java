package com.example.shop.service;

public interface MailService {
    Boolean send(String toEmail, String subject, String text);
}
