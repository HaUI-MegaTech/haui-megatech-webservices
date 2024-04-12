package shop.haui_megatech.configuration.language;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class MessageConfiguration {

    @Value("${spring.messages.basename}")
    private String baseName;

    @Value("${spring.messages.default-encoding}")
    private String defaultEncoding;

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename(baseName);
        messageSource.setDefaultEncoding(defaultEncoding);
        return messageSource;
    }
}
