package io.careeros.naukri.service;

import io.careeros.naukri.dto.Request.NaukriResumeHeadline;
import io.careeros.naukri.model.NaukriProfileDetail;
import io.careeros.naukri.repository.NaukriProfileUpdateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NaukriService {

    private final NaukriProfileUpdateRepository naukriProfileUpdateRepository;

    public NaukriProfileDetail createSubscription(NaukriResumeHeadline naukriResumeHeadline) {
        NaukriProfileDetail naukriProfileDetail = new NaukriProfileDetail();
        naukriProfileDetail.setHeadline1(naukriResumeHeadline.getHeadline1());
        naukriProfileDetail.setHeadline2(naukriResumeHeadline.getHeadline2());
        naukriProfileDetail.setPassword(naukriResumeHeadline.getPassword());
        naukriProfileDetail.setEmail(naukriResumeHeadline.getEmail());
        naukriProfileDetail.setChangeHeadline(true);
        naukriProfileUpdateRepository.save(naukriProfileDetail);
        return naukriProfileDetail;
    }

    public List<NaukriProfileDetail> getAllSubscription(){
        List<NaukriProfileDetail> allSubscription = naukriProfileUpdateRepository.findAll();
        return allSubscription;
    }

}
