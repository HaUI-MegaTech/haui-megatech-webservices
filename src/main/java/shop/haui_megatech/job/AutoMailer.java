package shop.haui_megatech.job;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import shop.haui_megatech.domain.dto.MailDTO;

import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class AutoMailer {
    private final JavaMailSender       mailSender;
    private final SpringTemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String FROM;

    public void sendResetPasswordMail(String email, String newPassword) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom(FROM);
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("Reset password");
        simpleMailMessage.setText("Your new password is: " + newPassword);

        new Thread(() -> mailSender.send(simpleMailMessage)).start();
    }

    public void sendMailWithHtml(MailDTO mail, String template) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(
                mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name()
        );
        mimeMessageHelper.setFrom(FROM);
        mimeMessageHelper.setTo(mail.to());
        mimeMessageHelper.setSubject(mail.subject());
        Context context = new Context();
        context.setVariables(mail.variables());
        String html = templateEngine.process(template, context);
        mimeMessageHelper.setText(html, true);

        new Thread(() -> mailSender.send(mimeMessage)).start();
    }
}
