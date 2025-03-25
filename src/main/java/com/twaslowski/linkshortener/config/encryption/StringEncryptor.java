package com.twaslowski.linkshortener.config.encryption;

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
  public String convertToDatabaseColumn(String attribute) {
    if (attribute == null) {
      return null;
    }

    String salt = KeyGenerators.string().generateKey();
    var encryptor = Encryptors.text(encryptionKey, salt);
    String encryptedValue = encryptor.encrypt(attribute);

    return new EncryptedString(encryptedValue, salt).toString();
  }

  @Override
  public String convertToEntityAttribute(String dbData) {
    if (dbData == null || dbData.isBlank()) {
      return null;
    }

    EncryptedString encryptedString = EncryptedString.fromString(dbData);
    var decryptor = Encryptors.text(encryptionKey, encryptedString.getSalt());

    return decryptor.decrypt(encryptedString.getEncryptedValue());
  }
}
