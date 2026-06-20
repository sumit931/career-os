package io.careeros.naukri.controller;

import com.sun.net.httpserver.Authenticator;
import io.careeros.naukri.dto.Request.CreateNaukriRequest;
import io.careeros.naukri.dto.Request.UpdateNaukriRequest;
import io.careeros.naukri.model.NaukriProfileDetail;
import io.careeros.naukri.service.NaukriService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/naukri")
public class NaukriController {

    private final NaukriService naukriService;

    @PostMapping
    public NaukriProfileDetail profileDetails(@Valid @RequestBody CreateNaukriRequest request){
        return naukriService.createSubscription(request);
    }

    @GetMapping
    public List<NaukriProfileDetail> allProfiles(){
        return naukriService.getAllSubscription();
    }

    @PatchMapping("/{id}")
    public NaukriProfileDetail profileDetail(@PathVariable Long id, @RequestBody UpdateNaukriRequest request){
        return naukriService.updateSubscriptionDetail(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long id){
        naukriService.deleteSubscription(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/trigger")
    public void TriggerUpdateFlow(){
        naukriService.triggerUpdateFlow();
    }

}
