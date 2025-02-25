package pro.coachcore.oauth2.server.user.account;

import lombok.Getter;

@Getter
public class IllegalUserCredentialsException extends IllegalArgumentException {
  private final String field;

  public IllegalUserCredentialsException(String field, String message) {
    super(message);
    this.field = field;
  }
}
