package com.VetApp.PurrgentCare.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity (name = "Doctor")
public class Doctor {


    @Id // Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto Generate Values
    private Integer id;
    @Setter
    private String name;
    @Setter
    private Date dateCreated;
    @Setter
    private Boolean active;


    @Override
    public String toString() {
        return "Doctor [id=" + id + ", name=" + name + " , dateCreated=" + dateCreated + " active=" + active + "]";
    }
}

