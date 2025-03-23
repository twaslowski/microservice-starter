package com.twaslowski.linkshortener.port;

import com.twaslowski.linkshortener.domain.dto.UserDTO;
import com.twaslowski.linkshortener.domain.entity.User;
import com.twaslowski.linkshortener.service.user.JwtService;
import com.twaslowski.linkshortener.service.user.UserManagementService;
import com.twaslowski.linkshortener.service.user.UserRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

  private final UserRegistrationService userRegistrationService;
  private final UserManagementService userManagementService;
  private final JwtService jwtService;

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
    userRegistrationService.register(userDTO);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/login")
  public ResponseEntity<String> authenticate(@RequestBody UserDTO userDTO) {
    User authenticatedUser = userManagementService.authenticate(userDTO.email(), userDTO.password());
    String jwtToken = jwtService.generateToken(authenticatedUser);
    return ResponseEntity.ok(jwtToken);
  }

  @GetMapping("/{id}")
  public ResponseEntity<User> getUser(@PathVariable String id) {
    return ResponseEntity.ok(userManagementService.getUser(id));
  }
}
