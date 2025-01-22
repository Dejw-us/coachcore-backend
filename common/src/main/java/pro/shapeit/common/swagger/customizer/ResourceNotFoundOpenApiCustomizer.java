package pro.shapeit.common.swagger.customizer;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import pro.shapeit.common.exception.ResourceNotFoundException;

@RequiredArgsConstructor
public class ResourceNotFoundOpenApiCustomizer implements OpenApiCustomizer {
  private final RequestMappingHandlerMapping mappings;

  @Override
  public void customise(OpenAPI api) {
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
