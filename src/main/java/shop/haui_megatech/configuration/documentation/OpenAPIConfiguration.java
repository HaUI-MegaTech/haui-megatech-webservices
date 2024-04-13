package shop.haui_megatech.configuration.documentation;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;


@Configuration
@EnableWebMvc
public class OpenAPIConfiguration {
    @Value("${haui-megatech.openapi.dev-url}")
    private String devUrl;

    @Value("${haui-megatech.openapi.prod-url}")
    private String prodUrl;

    @Bean
    public OpenAPI customOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in Development environment");

        Server prodServer = new Server();
        prodServer.setUrl(prodUrl);
        prodServer.setDescription("Server URL in Production environment");

        Contact contact = new Contact();
        contact.setEmail("contact@haui-megatech.shop");
        contact.setName("HaUI MegaTech");
        contact.setUrl("http://haui-megatech.shop/contact");

        Info info = new Info().title("HaUI MegaTech API Documentation")
                              .version("1.0")
                              .contact(contact)
                              .description("This document exposes endpoint APIs to manage HaUI MegaTech system");

        return new OpenAPI().info(info)
                            .servers(List.of(devServer, prodServer));
    }

}
