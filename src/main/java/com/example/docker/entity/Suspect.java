package com.example.docker.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Suspect {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private Integer age;
    private String gender;
    private String occupation;
    private String trait;
}
