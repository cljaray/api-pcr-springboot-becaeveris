package cl.grupo4.baseDeDatosPCR;

import static springfox.documentation.builders.PathSelectors.regex;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("API REST - Clase1")
				.description("API REST Ejemplo Clase 1 ")
                .version("0.1")
				.build();
	}

	@Bean
	public Docket newApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.paths(regex("/pcr.*"))
				.build();
	}

}