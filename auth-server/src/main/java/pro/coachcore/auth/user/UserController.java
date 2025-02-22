package pro.coachcore.auth.user;

import lombok.RequiredArgsConstructor;
import pro.coachcore.exception.ResourceNotFoundException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UserController {
  private final UserService userService;
  private final UserMapper userMapper;

  @GetMapping("/public/{username}")
  ResponseEntity<PublicUserDto> getPublicAppUser(
      @PathVariable String username
  ) throws ResourceNotFoundException {
    var user = userService.findUserByUsername(username);
    var publicUserDto = userMapper.mapToPublic(user);

    return ResponseEntity
        .ok(publicUserDto);
  }
}
