package shop.haui_megatech.job;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AutoMailSender {
    private final JavaMailSender mailSender;

    private final String FROM = "hauimegatech.bot@gmail.com";

    public void sendResetPasswordMail(String email, String newPassword) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom(FROM);
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("Reset password");
        simpleMailMessage.setText("Your new password is: " + newPassword);

        new Thread(() -> mailSender.send(simpleMailMessage)).start();
    }
}
