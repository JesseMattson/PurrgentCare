package com.VetApp.PurrgentCare.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Builder
@Getter
@Setter
public class AccountRequest {
    private Boolean active;
    private Date dateCreated;
    
}
