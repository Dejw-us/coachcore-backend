package pro.coachcore.oauth2.server.user;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pro.coachcore.dto.MessageDto;
import pro.coachcore.exception.ResourceNotFoundException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UserController {
  private final UserService userService;
  private final UserMapper userMapper;

  @GetMapping("/public/{identifier}")
  ResponseEntity<PublicUserDto> getPublicAppUser(@PathVariable String identifier,
      @RequestParam(defaultValue = "false") Boolean byUsername)
      throws ResourceNotFoundException {
    var user = userService.getUser(identifier, byUsername);
    var publicUserDto = userMapper.mapToPublic(user);

    return ResponseEntity.ok(publicUserDto);
  }

  @GetMapping("/me")
  ResponseEntity<PublicUserDto> getCurrentUser(@AuthenticationPrincipal Jwt jwt) {
    var userId = (String) jwt.getClaim("id");
    var user = userService.getUserByLocalId(userId);
    var publicUserDto = userMapper.mapToPublic(user);

    return ResponseEntity.ok(publicUserDto);
  }

  @PatchMapping("/me")
  ResponseEntity<MessageDto> updateUser(@RequestBody UpdateUserDto body,
      @AuthenticationPrincipal Jwt jwt) {
    var userId = (String) jwt.getClaim("id");
    var user = userService.getUserByLocalId(userId);
    userService.updateUser(user, body);
    return ResponseEntity.ok(new MessageDto("User has been updated"));
  }
}
