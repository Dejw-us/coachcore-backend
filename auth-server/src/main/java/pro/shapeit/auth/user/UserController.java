package pro.shapeit.auth.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.shapeit.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
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
