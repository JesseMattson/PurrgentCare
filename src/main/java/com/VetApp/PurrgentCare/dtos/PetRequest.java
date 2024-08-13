package com.VetApp.PurrgentCare.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PetRequest {
    private String name;
    private String type;
    private Integer age;
    private String gender;
}
