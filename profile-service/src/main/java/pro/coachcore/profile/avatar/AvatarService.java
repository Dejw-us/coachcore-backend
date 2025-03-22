package pro.coachcore.profile.avatar;

import org.apache.coyote.BadRequestException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import lombok.RequiredArgsConstructor;
import pro.coachcore.profile.s3.S3Service;

@Service
@RequiredArgsConstructor
public class AvatarService {
  private static final String AVATAR_PREFIX = "avatar_";

  private final S3Service s3Service;

  public void setAvatar(MultipartFile avatar) throws BadRequestException {
    if (!(SecurityContextHolder.getContext()
        .getAuthentication() instanceof JwtAuthenticationToken auth)) {
      throw new BadRequestException("To set avatar you need to be authenticated using jwt");
      // TODO add custom exception
    }
    var userId = (String) auth.getToken().getClaim("id");
    s3Service.uploadFile(avatar, AVATAR_PREFIX.concat(userId));
  }

  public byte[] getAvatar(String userId) {
    var avatarBytes = s3Service.downloadFile(AVATAR_PREFIX.concat(userId));
    return avatarBytes;
  }
}
