package io.careeros.careeros.controller;


import io.careeros.careeros.dto.Request.NaukriResumeHeadline;
import io.careeros.careeros.model.NaukriProfileDetail;
import io.careeros.careeros.service.NaukriService;
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
