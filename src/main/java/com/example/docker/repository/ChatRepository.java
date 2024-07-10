package com.example.docker.repository;

import com.example.docker.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat , Long> {

    public List<Chat> findChatsByUserIdAndSuspectNumberOrderByIdAsc(Long userId, Long suspectNumber);
}
