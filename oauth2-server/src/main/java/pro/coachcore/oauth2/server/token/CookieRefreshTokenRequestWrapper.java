package pro.coachcore.oauth2.server.token;

import static java.lang.String.format;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

public class CookieRefreshTokenRequestWrapper extends HttpServletRequestWrapper {
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  private final CookieRefreshTokenInputStream inputStream;

  public CookieRefreshTokenRequestWrapper(HttpServletRequest request, String refreshToken) throws JsonProcessingException {
    super(request);
    this.inputStream = new CookieRefreshTokenInputStream(refreshToken);
  }

  @Override
  public ServletInputStream getInputStream() {
    return inputStream;
  }

  @Override
  public int getContentLength() {
    return inputStream.getContentLength();
  }

  @Override
  public long getContentLengthLong() {
    return inputStream.getContentLength();
  }

  private static class CookieRefreshTokenInputStream extends ServletInputStream {
    private final ByteArrayInputStream inputStream;
    private final byte[] bodyBytes;

    CookieRefreshTokenInputStream(String refreshToken) throws JsonProcessingException {
      var body = RefreshTokenGrantTypeBody.create(refreshToken);
      this.bodyBytes = body.toByteArray();
      this.inputStream = new ByteArrayInputStream(bodyBytes);
    }

    @Override
    public boolean isFinished() {
      return inputStream.available() == 0;
    }

    @Override
    public boolean isReady() {
      return true;
    }

    @Override
    public void setReadListener(ReadListener listener) {
      throw new UnsupportedOperationException(format("%s doesn't support this method", getClass().getName()));
    }

    @Override
    public int read() throws IOException {
      return inputStream.read();
    }

    public int getContentLength() {
      return bodyBytes.length;
    }

    private record RefreshTokenGrantTypeBody(
      String refresh_token,
      String grant_type
    ) {
      private static RefreshTokenGrantTypeBody create(String refreshToken) {
        return new RefreshTokenGrantTypeBody(refreshToken, "refresh_token");
      }

      private byte[] toByteArray() throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsBytes(this);
      }
    }
  }
}
