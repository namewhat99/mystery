package com.example.docker.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;

import java.time.LocalDate;

@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private Long nickName;

    private Integer usedChance;

    private LocalDate date;

    @PrePersist
    protected void onCreate() {
        this.date = LocalDate.now();
    }
}
