package com.VetApp.PurrgentCare.dtos;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PetResponse {
    private Integer id;
    private String name;
    private String type;
    private Integer age;
    private String gender;
    //TODO how should Account be passed without creating a loop?
}
