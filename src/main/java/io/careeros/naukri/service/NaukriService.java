package io.careeros.naukri.service;

import io.careeros.naukri.dto.Request.CreateNaukriRequest;
import io.careeros.naukri.dto.Request.UpdateNaukriRequest;
import io.careeros.naukri.exception.ProfileNotFoundException;
import io.careeros.naukri.model.NaukriProfileDetail;
import io.careeros.naukri.repository.NaukriProfileUpdateRepository;
import io.careeros.naukri.scheduler.NaukriUpdateScheduler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NaukriService {

    private final NaukriProfileUpdateRepository naukriProfileUpdateRepository;
    private final NaukriUpdateScheduler naukriUpdateScheduler;

    public NaukriProfileDetail createSubscription(CreateNaukriRequest request) {
        NaukriProfileDetail naukriProfileDetail = new NaukriProfileDetail();
        naukriProfileDetail.setHeadline1(request.getHeadline1());
        naukriProfileDetail.setHeadline2(request.getHeadline2());
        naukriProfileDetail.setPassword(request.getPassword());
        naukriProfileDetail.setEmail(request.getEmail());
        naukriProfileDetail.setChangeHeadline(true);
        naukriProfileUpdateRepository.save(naukriProfileDetail);
        return naukriProfileDetail;
    }

    public List<NaukriProfileDetail> getAllSubscription(){
        return naukriProfileUpdateRepository.findAll();
    }

    public void deleteSubscription(Long id) {
        if (!naukriProfileUpdateRepository.existsById(id)) {
            throw new ProfileNotFoundException(id);
        }
        naukriProfileUpdateRepository.deleteById(id);
    }

    public NaukriProfileDetail updateSubscriptionDetail(Long id, UpdateNaukriRequest request){
        NaukriProfileDetail naukriProfileDetail = naukriProfileUpdateRepository.findById(id).orElseThrow(() -> new ProfileNotFoundException(id));

        if(request.getHeadline1() != null){
            naukriProfileDetail.setHeadline1(request.getHeadline1());
        }

        if(request.getHeadline2() != null){
            naukriProfileDetail.setHeadline2(request.getHeadline2());
        }

        naukriProfileUpdateRepository.save(naukriProfileDetail);
        return naukriProfileDetail;
    }

    public void triggerUpdateFlow(){
        naukriUpdateScheduler.checkProfile();
    }

}
