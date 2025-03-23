package com.twaslowski.linkshortener.service.user;

import com.twaslowski.linkshortener.domain.entity.User;
import com.twaslowski.linkshortener.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserManagementService {

  private final UserRepository userRepository;
  private final AuthenticationManager authenticationManager;

  public User getUser(String uuid) {
    return userRepository.findById(uuid)
        .orElseThrow(() -> new UsernameNotFoundException(uuid));
  }

  public User authenticate(String email, String password) {
    var authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            email,
            password
        )
    );
    return (User) authentication.getPrincipal();
  }
}
