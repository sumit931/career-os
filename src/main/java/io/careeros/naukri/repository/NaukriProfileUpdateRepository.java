package io.careeros.naukri.repository;

import io.careeros.naukri.model.NaukriProfileDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NaukriProfileUpdateRepository extends JpaRepository<NaukriProfileDetail,Long> {

}
