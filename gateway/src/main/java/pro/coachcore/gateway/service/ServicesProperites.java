package pro.coachcore.gateway.service;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "services")
public record ServicesProperites(
  String oauth2ServerUrl,
  String trainingServiceUrl,
  String newsletterServiceUrl,
  String emailServiceUrl,
  String imagesServiceUrl
) {
}
