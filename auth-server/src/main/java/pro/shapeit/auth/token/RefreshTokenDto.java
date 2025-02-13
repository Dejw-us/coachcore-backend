package pro.shapeit.auth.token;

import jakarta.servlet.http.Cookie;

public record RefreshTokenDto(
    String refreshToken
) {
  public static RefreshTokenDto fromCookie(Cookie cookie) {
    return new RefreshTokenDto(cookie.getValue());
  }
}
