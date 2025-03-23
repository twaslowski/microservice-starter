package com.twaslowski.linkshortener.repository;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Component;

@Converter
@Component
@RequiredArgsConstructor
public class StringEncryptor implements AttributeConverter<String, String> {

  @Value("${link-shortener.security.encryption.secret-key}")
  private String encryptionKey;

  @Override
  public String convertToDatabaseColumn(String email) {
    String salt = KeyGenerators.string().generateKey();
    var encryptor = Encryptors.text(encryptionKey, salt);
    return "%s:%s".formatted(encryptor.encrypt(email), salt);
  }

  @Override
  public String convertToEntityAttribute(String encryptedData) {
    String[] components = encryptedData.split(":");
    if (components.length != 2) {
      throw new RuntimeException("Stored password appears malformatted");
    }

    String password = components[0];
    String salt = components[1];

    var decryptor = Encryptors.text(encryptionKey, salt);
    return decryptor.decrypt(password);
  }
}
