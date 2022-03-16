package ru.kpfu.stud.garipov.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class MailConfig {

    private String from = "rizvangaripov@gmail.com";
    private String sender = "Test App";
    private String subject = "Please verify your email";
    private String content = "Dear {name}, Please click the link below for verify your account:" +
            " <a href={url}>VERIFY</a>";

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
