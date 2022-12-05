package com.example.Knights.Domain.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class Middleware {
    @Autowired
    private JavaMailSender emailSender;
    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@baeldung.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
    @ExceptionHandler({RestException.class})
    public ResponseEntity<BaseResponse> handleException(RestException e) {

        BaseResponse response = new BaseResponse(e.getMessage());
        sendSimpleMessage("okorniichuk03@gmail.com","Error",response.getMessage());
        return new ResponseEntity<>(response, e.getStatusCode());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<BaseResponse> handleException2(Exception e) {
        BaseResponse response = new BaseResponse(e.getMessage());
        sendSimpleMessage("okorniichuk03@gmail.com","Error",response.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
