package io.sumitnegi.careeros.controller;


import io.sumitnegi.careeros.dto.Request.NaukriResumeHeadline;
import io.sumitnegi.careeros.model.NaukriProfileDetail;
import io.sumitnegi.careeros.service.NaukriService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/naukri")
public class NaukriController {

    private final NaukriService naukriService;

    @PostMapping
    public NaukriProfileDetail profileDetails(@RequestBody NaukriResumeHeadline naukriResumeHeadline){
        return naukriService.createSubscription(naukriResumeHeadline);
    }

//    @GetMapping
//    public List<>
}
