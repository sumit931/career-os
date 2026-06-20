package io.careeros.coldemailer.repository;

import io.careeros.coldemailer.entity.Campaign;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignRepository extends JpaRepository<Campaign, UUID> {}
