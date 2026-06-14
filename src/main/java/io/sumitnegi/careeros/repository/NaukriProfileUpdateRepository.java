package io.sumitnegi.careeros.repository;

import io.sumitnegi.careeros.model.NaukriProfileDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NaukriProfileUpdateRepository extends JpaRepository<NaukriProfileDetail,Long> {

}
