package pro.coachcore.image.profile;

import java.net.MalformedURLException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import lombok.RequiredArgsConstructor;
import pro.coachcore.dto.MessageDto;

@RestController
@RequestMapping("/v1/profile-pictures")
@RequiredArgsConstructor
public class ProfilePictureController {
  private final ProfilePictureService profilePictureService;

  @GetMapping("/{userId}")
  ResponseEntity<Resource> getProfilePicture(@PathVariable String userId)
      throws MalformedURLException {
    var imagePath = profilePictureService.getProfilePicture(userId);
    var imageResource = new UrlResource(imagePath.toUri());

    return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(imageResource);
  }

  @PostMapping
  ResponseEntity<MessageDto> postProfilePicture(@RequestParam MultipartFile file,
      @AuthenticationPrincipal Jwt jwt) {
    var userId = (String) jwt.getClaims().get("id");
    var saved = profilePictureService.saveProfilePicture(userId, file);

    if (saved) {
      return ResponseEntity.status(HttpStatus.CREATED)
          .body(new MessageDto("Saved profile picture"));
    }
    return ResponseEntity.internalServerError()
        .body(new MessageDto("Failed to save profile picture"));
  }
}
