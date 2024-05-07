package shop.haui_megatech.annotation;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.haui_megatech.constant.UrlConstant;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RestController
@RequestMapping(UrlConstant.API_V1)
public @interface RestApiV1 {
}
