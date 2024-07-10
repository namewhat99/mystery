package com.example.docker.repository;

import com.example.docker.entity.Suspect;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SuspectRepository extends JpaRepository<Suspect , Long> {

    public Integer countSuspectsByDate(LocalDate localDate);

    public Suspect findSuspectByDateAndSuspectName(LocalDate date, String suspectName);

    public List<Suspect> findSuspectsByDate(LocalDate date);
}
