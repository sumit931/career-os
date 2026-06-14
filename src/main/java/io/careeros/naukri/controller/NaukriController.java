package io.careeros.naukri.controller;


import io.careeros.naukri.dto.Request.NaukriResumeHeadline;
import io.careeros.naukri.model.NaukriProfileDetail;
import io.careeros.naukri.service.NaukriService;
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
