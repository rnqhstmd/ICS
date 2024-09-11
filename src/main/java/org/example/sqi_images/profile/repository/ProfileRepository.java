package org.example.sqi_images.profile.repository;

import org.example.sqi_images.profile.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
