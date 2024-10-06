package com.example.docker.repository;

import com.example.docker.entity.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface UserRepository extends JpaRepository<User , Long> {

    public Boolean existsUserBySessionIdAndDate(String nickName, LocalDate date);

    public User findUserById(Long id);
}
