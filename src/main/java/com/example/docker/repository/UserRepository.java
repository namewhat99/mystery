package com.example.docker.repository;

import com.example.docker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface UserRepository extends JpaRepository<User , Long> {

    public Boolean existsUserByNickNameAndDate(String nickName, LocalDate date);
}
