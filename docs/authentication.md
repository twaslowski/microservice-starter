# Authentication

This page outlines the structure of the `User` entity and the authentication process.

The `User` entity is designed for maximum security. The password is hashed and salted using the
`BCryptPasswordEncoder` and the email is hashed and encrypted using Spring's `TextEncryptor`,
making it GDPR-compliant.

## Encryption

Encryption of user's emails is performed using AES-256 and a different, cryptographically random
16-byte salt every time utilizing Spring's
[TextEncryptor](https://docs.spring.io/spring-security/site/docs/5.2.0.RELEASE/reference/html/crypto.html#spring-security-crypto-encryption-text).

An `email` column in the database consists of the encrypted email as well as the random salt,
which is appended to it at the end.

### Email hashing

When hashing a user's password, the outcome is deterministic. What that means is that when you
call `passwordEncoder.encode(userDTO.password())`, the result is always going to be identical.

Because Spring's `TextEncryptor` uses a different IV every time, the result of
`encryptor.encrypt(userDTO.email())` is **not** always identical. 

This leads to the following issue: Consider a user is trying to log in, and you're trying to retrieve
their data:

```java
import com.twaslowski.linkshortener.domain.dto.UserDTO;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public findUser(UserDTO dto) {
  String email = userDTO.email();
  String encryptedEmail = encryptor.encrypt(email);
  return userRepository.findByEmail(encryptedEmail)
      .orElseThrow(() -> new UsernameNotFoundException(email));
}
```

This will not work, because the `encryptedEmail` is going to be different every time due to the
randomized IV.

In Spring 5 and below, this problem
was solved using `Encryptors.queryableText`, but this was [deprecated](https://github.com/spring-projects/spring-security/issues/8980)
in Spring 6 due to being insecure.

The solution to this is generating a deterministic hash of the user's email and using that hash
as a basis for retrieving user data:

```java
public User register(UserDTO userDTO) {
  String hashedEmail = emailHashingService.hashEmail(userDTO.email());
  if (userRepository.findByEmailHash(hashedEmail).isPresent()) {
    throw new UserExistsException(hashedEmail);
  }

  String hashedPassword = passwordEncoder.encode(userDTO.password());
  return userRepository.save(User.builder()
      .emailHash(hashedEmail)
      .password(hashedPassword)
      .email(userDTO.email()).build());
}
```

When the user signs in, their data can be retrieved as follows:

```java
import com.twaslowski.linkshortener.domain.dto.UserDTO;
import com.twaslowski.linkshortener.domain.entity.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public boolean userDetailsCorrect(UserDTO userDTO) {
  String hashedEmail = emailHashingService.hashEmail(userDTO.email());
  User user = userRepository.findByEmailHash(hashedEmail)
      .orElseThrow(() -> new UsernameNotFoundException(userDTO.email()));

  var hashedPassword = passwordEncoder.encode(userDTO.password());
  return user.getPassword().equals(hashedPassword);
}
```
