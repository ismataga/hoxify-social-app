package com.hoxify.hoxify_new.email;

import com.hoxify.hoxify_new.configration.HoxifyProperties;
import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailService {
    JavaMailSenderImpl mailSender;
    @Autowired
    HoxifyProperties hoxifyProperties;

    @Autowired
    MessageSource messageSource;

    String activationEmail = """
            <html>
                <body>
                    <h1>${title}</h1>
                    <a href="${url}">${clickHere}</a>
                </body>
            </html>
            """;


    @PostConstruct
    public void initialize() {

        mailSender = new JavaMailSenderImpl();
        mailSender.setHost(hoxifyProperties.getEmail().host());
        mailSender.setPort(hoxifyProperties.getEmail().port());
        mailSender.setUsername(hoxifyProperties.getEmail().username());
        mailSender.setPassword(hoxifyProperties.getEmail().password());

        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.smtp.starttls.enable", "true");
    }

    public void sendActivationEmail(String email, String activationToken) {
        var activationUrl = hoxifyProperties.getClient().host() + "/acticvation/" + activationToken;
        var title = messageSource.getMessage("hoaxify.mail.user.created.title", null, LocaleContextHolder.getLocale());
        var clickHere = messageSource.getMessage("hoaxify.mail.click.here", null, LocaleContextHolder.getLocale());

        var mailBody = activationEmail
                .replace("${url}", activationUrl)
                .replace("${title}", title)
                .replace("${clickHere}", clickHere);


        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");

        try {
            message.setFrom(hoxifyProperties.getEmail().from());
            message.setTo(email);
            message.setSubject(title);
            message.setText(mailBody);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        this.mailSender.send(mimeMessage);
    }

}
