package dev.bozho.todoapp.service.impl;

import dev.bozho.todoapp.service.IEmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService implements IEmailService {

    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class.getName());

    private final JavaMailSender javaMailSender;

    @Override
    @Async
    public void send(String to, String email) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
        try {
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Confirm your email");
            helper.setFrom("hello@bozho.com");
            javaMailSender.send(message);
        } catch (MessagingException e) {
            LOGGER.error("Failed to send email", e);
            throw new IllegalStateException("Failed to send email");
        }
    }

    public String buildVerificationEmail(String token) {

        return "    <style>"+
                "    @import url('https://fonts.googleapis.com/css2?family=Open+Sans:ital,wght@0,300;0,400;0,500;0,600;0,700;0,800;1,300;1,400;1,500;1,600;1,700;1,800&display=swap');"+
                "      body {"+
                "        font-family: 'Open Sans', sans-serif;"+
                "      }"+
                "      .container {"+
                "        width: 500px;"+
                "        margin: 100px auto;"+
                "        text-align: center;"+
                "        background-color: #fff;"+
                "        padding: 30px 40px 60px 40px;"+
                "        border-radius: 10px;"+
                "        box-shadow: 2px 2px 8px rgba(0, 0, 0, 0.1), -2px -2px 8px rgba(255, 255, 255, 0.3);"+
                "        transition: all 0.3s ease-in-out;"+
                "      }"+
                "      h1 {"+
                "        font-size: 36px;"+
                "        color: #333;"+
                "        margin-bottom: 20px;"+
                "        text-transform: uppercase;"+
                "        letter-spacing: 2px;"+
                "      }"+
                "      p {"+
                "        font-size: 18px;"+
                "        color: #555;"+
                "        margin-bottom: 30px;"+
                "        line-height: 1.5;"+
                "      }"+
                "      .btn {"+
                "        background-color: #ff5a5f;"+
                "        color: #fff;"+
                "        padding: 12px 20px;"+
                "        border-radius: 5px;"+
                "        text-decoration: none;"+
                "        letter-spacing: 2px;"+
                "        transition: all 0.3s ease-in-out;"+
                "      }"+
                "      .container:hover {"+
                "        box-shadow: 4px 4px 12px rgba(0, 0, 0, 0.2), -4px -4px 12px rgba(255, 255, 255, 0.4);"+
                "      }"+
                "      .btn:hover {"+
                "        box-shadow: 4px 4px 12px rgba(0, 0, 0, 0.2), -4px -4px 12px rgba(255, 255, 255, 0.4);"+
                "      }"+
                "    </style>"+
                "<body>"+
                "<div class=\"container\">"+
                "    <h1>Verify your email</h1>"+
                "    <p>Thanks for signing up! Please verify your email by clicking the button below:</p>"+
                // @TODO change localhost:8080 to the actual domain as const in configuration file
                "    <a class=\"btn\" href=\"http://localhost:8080/api/v1/user/confirm?token=" + token + "\">Verify Email</a>"+
                "</div>"+
                "</body>";
    }
}
