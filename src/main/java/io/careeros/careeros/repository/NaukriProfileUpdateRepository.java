package io.careeros.careeros.repository;

import io.careeros.careeros.model.NaukriProfileDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NaukriProfileUpdateRepository extends JpaRepository<NaukriProfileDetail,Long> {

}
