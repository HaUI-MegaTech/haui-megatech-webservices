package shop.haui_megatech.job;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import shop.haui_megatech.domain.dto.MailDTO;

import java.io.File;
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

    public void sendOrderPdf(String email, String path) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(FROM);
            helper.setTo(email);
            helper.setSubject("Invoice");
            helper.setText("Your Invoice");

            // Add PDF as attachment
            //String pdfPath = "D:\\3_Mon_hoc_tren_Truong\\31_Thuc_tap_Chuyen_nganh\\Code\\31-05\\haui-megatech-webservices\\src\\main\\resources\\pdf\\Order1720240602_225759.pdf"; // Ensure the path is correct
            FileSystemResource file = new FileSystemResource(path);
            helper.addAttachment("Invoice.pdf", file);

            // Send email in a new thread
            new Thread(() -> mailSender.send(message)).start();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
