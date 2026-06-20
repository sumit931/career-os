package io.careeros.naukri.dto.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateNaukriRequest {
    @NotBlank
    @Email
    String email;
    @NotBlank
    String password;
    @NotBlank
    String headline1;
    @NotBlank
    String headline2;
}
