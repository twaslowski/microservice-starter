package com.twaslowski.linkshortener.service.user;

import com.twaslowski.linkshortener.domain.dto.UserDTO;
import com.twaslowski.linkshortener.domain.entity.User;
import com.twaslowski.linkshortener.domain.exception.UserExistsException;
import com.twaslowski.linkshortener.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRegistrationService {

  private final UserRepository userRepository;
  private final EmailHashingService emailHashingService;
  private final PasswordEncoder passwordEncoder;

  public User register(UserDTO userDTO) {
    var hashedEmail = emailHashingService.hashEmail(userDTO.email());
    if (userRepository.findByEmailHash(hashedEmail).isPresent()) {
      throw new UserExistsException(hashedEmail);
    }

    var hashedPassword = passwordEncoder.encode(userDTO.password());
    var user = User.builder()
        .emailHash(hashedEmail)
        .password(hashedPassword)
        .email(userDTO.email()).build();
    return userRepository.save(user);
  }
}
