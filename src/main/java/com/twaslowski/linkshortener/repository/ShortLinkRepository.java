package com.twaslowski.linkshortener.repository;

import com.twaslowski.linkshortener.domain.entity.ShortLink;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortLinkRepository extends JpaRepository<ShortLink, String> {

  Optional<ShortLink> findShortLinkByToken(String token);
}
