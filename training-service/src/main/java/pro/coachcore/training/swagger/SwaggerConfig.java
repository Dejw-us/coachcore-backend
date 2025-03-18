package pro.coachcore.training.swagger;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import pro.coachcore.swagger.customizer.AccessTokenApiCustomizer;
import pro.coachcore.swagger.customizer.ResourceNotFoundOpenApiCustomizer;

@Configuration
public class SwaggerConfig {
  @Bean
  OpenApiCustomizer accessTokenCustomizer() {
    return new AccessTokenApiCustomizer();
  }

  @Bean
  OpenApiCustomizer resourceNotFoundCustomizer(
      @Qualifier("requestMappingHandlerMapping") RequestMappingHandlerMapping mappings) {
    return new ResourceNotFoundOpenApiCustomizer(mappings);
  }
}
