package com.twaslowski.linkshortener.port;

import com.twaslowski.linkshortener.domain.dto.UserDTO;
import com.twaslowski.linkshortener.domain.entity.User;
import com.twaslowski.linkshortener.service.user.JwtService;
import com.twaslowski.linkshortener.service.user.UserManagementService;
import com.twaslowski.linkshortener.service.user.UserRegistrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
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
    HttpHeaders headers = headersWithJwt(jwtToken);
    return ResponseEntity.ok().headers(headers).build();
  }

  @GetMapping("/me")
  public ResponseEntity<User> getUser(@AuthenticationPrincipal User user) {
    return ResponseEntity.ok(user);
  }

  @PostMapping("/logout")
  public ResponseEntity<?> logout() {
    HttpHeaders headers = headersWithJwt("");
    return ResponseEntity.ok().headers(headers).build();
  }

  private HttpHeaders headersWithJwt(String jwt) {
    ResponseCookie responseCookie = responseCookieFrom(jwt);
    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.SET_COOKIE, responseCookie.toString());
    return headers;
  }

  private ResponseCookie responseCookieFrom(String jwt) {
    return ResponseCookie.from("Authorization", jwt)
        .sameSite("None")
        .secure(true)
        .httpOnly(true)
        .path("/")
        .maxAge(86400)
        .build();
  }
}
