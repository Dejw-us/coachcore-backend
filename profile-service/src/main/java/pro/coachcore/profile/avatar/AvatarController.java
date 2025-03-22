package pro.coachcore.profile.avatar;

import org.apache.coyote.BadRequestException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/avatars")
public class AvatarController {
  private final AvatarService avatarService;

  @PostMapping
  ResponseEntity<?> postAvatar(@RequestParam MultipartFile avatar) throws BadRequestException {
    avatarService.setAvatar(avatar);
    return ResponseEntity.ok("Avatar has been set");
  }

  @GetMapping("/{userId}")
  ResponseEntity<?> getAvatar(@PathVariable String userId) {
    return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG)
        .body(avatarService.getAvatar(userId));
  }
}
