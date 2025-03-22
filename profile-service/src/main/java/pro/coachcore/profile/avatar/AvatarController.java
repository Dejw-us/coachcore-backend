package pro.coachcore.profile.avatar;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import pro.coachcore.profile.s3.S3Service;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/avatars")
public class AvatarController {
  private final S3Service s3Service;

  @PostMapping
  ResponseEntity<?> postAvatar() {
    s3Service.uploadFile("/home/json/notes/calc.txt", "notes");
    return ResponseEntity.ok("test");
  }
}
