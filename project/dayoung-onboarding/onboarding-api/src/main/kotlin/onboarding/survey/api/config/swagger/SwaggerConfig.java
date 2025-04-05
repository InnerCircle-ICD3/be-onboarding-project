package onboarding.survey.api.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description : Swagger 설정 클래스
 */
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openApi() {
        Info info = new Info()
                .title("Onboarding API")
                .description("Dayoung Onboarding API")
                .version("1.0.0");

        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}
