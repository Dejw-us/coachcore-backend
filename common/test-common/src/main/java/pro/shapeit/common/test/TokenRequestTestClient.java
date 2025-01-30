package pro.shapeit.common.test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

public class TokenRequestTestClient {
  public String obtainAccessToken() {
    return obtainAccessToken("http://localhost:9000/oauth2/token", "shapeit", "secret");
  }

  public String obtainAccessToken(String tokenUrl, String clientId, String clientSecret) {
    var body = "grant_type=client_credentials";
    try (var client = HttpClient.newHttpClient()) {
      var credentials = clientId + ":" + clientSecret;
      var request = HttpRequest.newBuilder()
          .uri(URI.create(tokenUrl))
          .header("Content-Type", "application/x-www-form-urlencoded")
          .header("Authorization", "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes()))
          .POST(HttpRequest.BodyPublishers.ofString(body))
          .build();
      var response = client.send(request, HttpResponse.BodyHandlers.ofString());

      if (response.statusCode() != 200) {
        throw new IOException("Request failed with status: " + response.statusCode() + ". body: " + response.body());
      }

      return extractAccessToken(response.body());
    } catch (IOException | InterruptedException exception) {
      return null;
    }
  }

  private String extractAccessToken(String jsonResponse) {
    String accessTokenPrefix = "\"access_token\":\"";
    int startIndex = jsonResponse.indexOf(accessTokenPrefix) + accessTokenPrefix.length();
    int endIndex = jsonResponse.indexOf("\"", startIndex);
    return jsonResponse.substring(startIndex, endIndex);
  }
}
