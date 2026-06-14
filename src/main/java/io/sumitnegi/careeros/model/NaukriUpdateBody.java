package io.sumitnegi.careeros.model;

import lombok.Data;

@Data
public class NaukriUpdateBody {
    private String headline1;
    private String headline2;
    private String email;
    private String password;
    private Boolean changeHeadline;
}


