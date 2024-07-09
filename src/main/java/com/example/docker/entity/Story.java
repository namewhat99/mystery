package com.example.docker.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Story {

    @Id
    @GeneratedValue
    private Long id;

    private String place;
    private String time;
    private String weather;


}
