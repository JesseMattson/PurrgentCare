package com.VetApp.PurrgentCare.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PetResponse {
    private String name;
    private String type;
    private Integer age;
    private String gender;
    //TODO how should Account be passed without creating a loop?
}
