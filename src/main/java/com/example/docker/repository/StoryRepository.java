package com.example.docker.repository;

import com.example.docker.entity.Story;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoryRepository extends JpaRepository<Story , Long> {
}
