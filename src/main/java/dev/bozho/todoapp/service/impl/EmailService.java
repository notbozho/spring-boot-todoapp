package dev.bozho.todoapp.service.impl;

import dev.bozho.todoapp.service.IEmailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.util.Map;

@Service
@AllArgsConstructor
public class EmailService implements IEmailService {

    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class.getName());

    private final JavaMailSender javaMailSender;

    private final Configuration freemarkerConfig;

    @Override
    @Async
    public void send(String to, String subject, String content) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
        try {
            helper.setText(content, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setFrom("hello@bozho.com");
            javaMailSender.send(message);
        } catch (MessagingException e) {
            LOGGER.error("Failed to send email", e);
            throw new IllegalStateException("Failed to send email");
        }
    }

    @Override
    public void sendEmailConfirmation(String to, String token) {
        Map<String, Object> model = Map.of("url", "http://localhost:8080/api/v1/user/confirm?token=" + token, "user", to.split("@")[0]);

        String content = mergeTemplateIntoString("email.confirm.ftl", model);

        this.send(to, "Please confirm your email", content);
    }

    private String mergeTemplateIntoString(String templateLocation, Map<String, Object> model) {
        try {
            Template template = freemarkerConfig.getTemplate(templateLocation);
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        } catch (IOException | TemplateException e) {
            throw new RuntimeException(e);
        }
    }

}
