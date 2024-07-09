package com.example.docker.exceptionHandler;

import com.example.docker.dto.ResponseDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ResponseDto<Void>> handleInternalServerError(EntityNotFoundException exception) {
        ResponseDto<Void> responseDto = new ResponseDto<>(404 , exception.getMessage());
        return ResponseEntity.status(404).body(responseDto);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseDto<Void>> IllegalArgumentException(IllegalArgumentException exception){
        ResponseDto<Void> responseDto = new ResponseDto<>(400 , exception.getMessage());
        return ResponseEntity.status(400).body(responseDto);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ResponseDto<Void>> IllegalStateException(IllegalArgumentException exception){
        ResponseDto<Void> responseDto = new ResponseDto<>(400 , exception.getMessage());
        return ResponseEntity.status(400).body(responseDto);
    }
}
