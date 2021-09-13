package com.assets_france.api.sender.domain;

import javax.mail.MessagingException;

public interface EmailSender {
    void sendSimpleEmail(String toEmail, String body, String subject);
    void sentEmailWithAttachment(String toEmail, String body, String subject, String attachment) throws MessagingException;
}
