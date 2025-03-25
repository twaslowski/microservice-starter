package com.twaslowski.linkshortener.config.encryption;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EncryptedString {

  private String encryptedValue;
  private String salt;

  @Override
  public String toString() {
    return encryptedValue + ":" + salt;
  }

  public static EncryptedString fromString(String value) {
    String[] parts = value.split(":");
    if (parts.length != 2) {
      throw new IllegalArgumentException("Malformed encrypted data");
    }
    return new EncryptedString(parts[0], parts[1]);
  }
}

