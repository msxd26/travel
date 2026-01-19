package pe.jsaire.springtravel.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    private final Path templatePath = Paths.get("src/main/resources/email/email_template.html");


    public void sendEmail(String to, String name, String product) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        String htmlContent = this.readHTMLTemplate(name, product);

        try {
            mimeMessage.setFrom(new InternetAddress("sairelp26@gmail.com"));
            mimeMessage.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(to));
            mimeMessage.setContent(htmlContent, "text/html; charset=utf-8");
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error(e.getMessage());
        }
    }

    private String readHTMLTemplate(String name, String product) {
        try (var line = Files.lines(templatePath)) {
            var html = line.collect(Collectors.joining());
            return html.replace("{name}", name).replace("{product}", product);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
