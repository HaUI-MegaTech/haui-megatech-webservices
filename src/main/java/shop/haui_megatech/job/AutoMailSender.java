package shop.haui_megatech.job;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AutoMailSender {
    private final JavaMailSender mailSender;

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
}
