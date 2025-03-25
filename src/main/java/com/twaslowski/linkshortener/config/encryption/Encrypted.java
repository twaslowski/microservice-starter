package com.twaslowski.linkshortener.config.encryption;

import jakarta.persistence.Convert;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Convert(converter = StringEncryptor.class)
public @interface Encrypted {

}
