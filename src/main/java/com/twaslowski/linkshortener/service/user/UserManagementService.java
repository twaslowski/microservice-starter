package com.twaslowski.linkshortener.service.user;

import com.twaslowski.linkshortener.domain.entity.User;
import com.twaslowski.linkshortener.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserManagementService {

  private final UserRepository userRepository;

  public User getUser(String uuid) {
    return userRepository.findById(uuid)
        .orElseThrow(() -> new UsernameNotFoundException(uuid));
  }
}
