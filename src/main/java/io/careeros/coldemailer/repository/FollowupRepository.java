package io.careeros.coldemailer.repository;

import io.careeros.coldemailer.entity.Followup;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowupRepository extends JpaRepository<Followup, UUID> {}
