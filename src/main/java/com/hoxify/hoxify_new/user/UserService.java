package com.hoxify.hoxify_new.user;

import com.hoxify.hoxify_new.exception.NotUniqueEmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Properties;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void save(User user) {
        try {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            user.setActivationToken(UUID.randomUUID().toString());
            sendActivationEmail(user);
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new NotUniqueEmailException();
        }
    }

    private void sendActivationEmail(User user) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("ismataga1@gmail.com");
        simpleMailMessage.setTo(user.getEmail());
        simpleMailMessage.setSubject("Account Activation");
        simpleMailMessage.setText("http://localhost:5173/acticvation/"+user.getActivationToken());
        getSimpleMailSender().send(simpleMailMessage);
    }
    public JavaMailSender getSimpleMailSender() {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.ethereal.email");
        mailSender.setPort(587);
        mailSender.setUsername("sherwood.mayer53@ethereal.email");
        mailSender.setPassword("NRGz9KMeAqx6M8zNAr");

        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.smtp.startttls.enable",true);
        return mailSender;
    }
}
