package com.example.docker.repository;

import com.example.docker.entity.Story;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface StoryRepository extends JpaRepository<Story , Long> {

    public Boolean existsStoryByDate(LocalDate date);
}
