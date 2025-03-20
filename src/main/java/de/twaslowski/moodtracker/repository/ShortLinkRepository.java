package de.twaslowski.moodtracker.repository;

import de.twaslowski.moodtracker.domain.entity.ShortLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortLinkRepository extends JpaRepository<ShortLink, String> {

}
