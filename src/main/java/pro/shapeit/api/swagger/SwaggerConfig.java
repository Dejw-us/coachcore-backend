package pro.shapeit.api.swagger;

import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import pro.shapeit.api.common.exception.ResourceNotFoundException;

@Configuration
public class SwaggerConfig {
  @Qualifier("requestMappingHandlerMapping")
  @Autowired
  private RequestMappingHandlerMapping mappings;

  @Bean
  public OpenApiCustomizer openApiCustomizer() {
    return api -> {
      api.getComponents()
          .addSchemas("MessageDto", new Schema<>()
              .type("object")
              .addProperty("message", new Schema<>().type("string"))
          );

      api.getPaths().forEach((path, item) -> {
        item.readOperations().forEach(operation -> {
          var responses = operation.getResponses();

          if (canThrowResourceNotFound(operation.getOperationId())) {
            responses.addApiResponse("404", new ApiResponse()
                .description("This response will be send, if there is no specific resource stored in the database")
                .content(new Content().addMediaType(
                    "application/json",
                    new MediaType().schema(new Schema<>().$ref("#/components/schemas/MessageDto"))))
            );
          }
        });
      });
    };
  }

  private boolean canThrowResourceNotFound(String operationId) {
    for (var handlerMethod : mappings.getHandlerMethods().values()) {
      var method = handlerMethod.getMethod();

      if (method.getName().equals(operationId)) {
        for (var type : method.getExceptionTypes()) {
          if (type.getName().equals(ResourceNotFoundException.class.getName())) {
            return true;
          }
        }
      }
    }
    return false;
  }
}
