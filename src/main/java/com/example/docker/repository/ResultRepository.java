package com.example.docker.repository;

import com.example.docker.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface ResultRepository extends JpaRepository<Result , Long> {

    public Boolean existsResultByDate(LocalDate localDate);

    public Result findResultByDate(LocalDate localDate);
}
