package com.twaslowski.linkshortener.service.user;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailHashingService {

  /**
   * Provides computationally quick hashes for email lookups.
   * Todo: Should use a salt as well.
   */

  private static final String HMAC_ALGORITHM = "HmacSHA256";

  @Value("${link-shortener.security.hashing.secret-key}")
  private String secretKey;

  public String hashEmail(String email) {
    try {
      String normalizedEmail = normalizeEmail(email);

      Mac mac = Mac.getInstance(HMAC_ALGORITHM);
      SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), HMAC_ALGORITHM);
      mac.init(secretKeySpec);
      byte[] hashBytes = mac.doFinal(normalizedEmail.getBytes(StandardCharsets.UTF_8));

      return Base64.getUrlEncoder().withoutPadding().encodeToString(hashBytes);
    } catch (Exception e) {
      throw new RuntimeException("Error hashing email", e);
    }
  }

  private String normalizeEmail(String email) {
    if (email == null) {
      throw new IllegalArgumentException("Email cannot be null");
    }
    return email.trim().toLowerCase();
  }
}
