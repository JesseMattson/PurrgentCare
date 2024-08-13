package com.VetApp.PurrgentCare.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PetRequest {
    private String name;
    private String type;
    private Integer age;
    private String gender;
}
