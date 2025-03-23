package com.twaslowski.linkshortener.repository;

import com.twaslowski.linkshortener.domain.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

  Optional<User> findByEmailHash(String emailHash);
}
