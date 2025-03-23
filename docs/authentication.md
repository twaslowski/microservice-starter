# Authentication

This page outlines the structure of the `User` entity and the authentication process.

### The User entity

**[User entity source code](https://github.com/twaslowski/microservice-starter/blob/main/src/main/java/com/twaslowski/linkshortener/domain/entity/User.java)**.

The `User` entity holds all relevant user data. Holding sensitive personalized data, it
is designed for maximum security. The password is hashed and salted using the
[BCryptPasswordEncoder](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/crypto/bcrypt/BCryptPasswordEncoder.html)
and the email is hashed and encrypted using Spring's [TextEncryptor](https://docs.spring.io/spring-security/site/docs/5.2.0.RELEASE/reference/html/crypto.html#spring-security-crypto-encryption-text) [^1],
making it GDPR-compliant.

In order to be able to quickly retrieve a User by their email address, a `email_hash` exists in
the database as an indexed column. [More details in this section](#email-hashing).

It has the following fields:

- `id`: a UUID is used instead of a sequential id, as UUIDs are harder to guess for attackers.
- `email`: The email of the user, encrypted with AES-256
- `email_hash`: The hash of the plaintext email for data retrieval
- `password`: The password hash
- `created_at`: Timestamp with timezone
- `updated_at`: Timestamp with timezone

[^1] TextEncryptor documentation is outdated (Spring v5.x), though still largely accurate.

## Encryption

**User data encryption code [here](https://github.com/twaslowski/microservice-starter/blob/main/src/main/java/com/twaslowski/linkshortener/repository/StringEncryptor.java)**.

Encryption of user's emails is performed using AES-256 and a different, cryptographically random
16-byte salt every time utilizing Spring's
[TextEncryptor](https://docs.spring.io/spring-security/site/docs/5.2.0.RELEASE/reference/html/crypto.html#spring-security-crypto-encryption-text).

An `email` column in the database consists of the encrypted email as well as the random salt,
which is appended to it at the end. The encryption only takes place at-rest; the data is assumed
to be encrypted by TLS in-transit, and so a JPA Converter is used to encrypt it before being saved
to the database.

### Email hashing

**Email hashing code [here](https://github.com/twaslowski/microservice-starter/blob/main/src/main/java/com/twaslowski/linkshortener/service/user/EmailHashingService.java)**.

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
