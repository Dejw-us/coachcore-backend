package pro.shapeit.swagger.customizer;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomizer;

public class AccessTokenApiCustomizer implements OpenApiCustomizer {
  @Override
  public void customise(OpenAPI api) {
    api.components(api.getComponents().addSecuritySchemes("bearerAuth", new SecurityScheme()
        .type(SecurityScheme.Type.HTTP)
        .scheme("bearer")
        .bearerFormat("JWT"))
    ).addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
  }
}
