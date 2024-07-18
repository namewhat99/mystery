package com.example.docker.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String nickName;

    private Integer usedChance;

    private LocalDate date;

    public void increaseUsedChance(){
        this.usedChance += 1;
    }

    @PrePersist
    protected void onCreate() {
        this.date = LocalDate.now();
    }
}
