package com.samuelbraga.authentication.exceptions.handler;

import com.samuelbraga.authentication.exceptions.BaseException;
import com.samuelbraga.authentication.exceptions.handler.dtos.BaseExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BaseExceptionHandler {

  @ExceptionHandler(BaseException.class)
  public ResponseEntity<BaseExceptionDTO> handle(BaseException baseException) {
    BaseExceptionDTO baseExceptionDTO = new BaseExceptionDTO(
      baseException.getMessage()
    );
    return new ResponseEntity<>(baseExceptionDTO, HttpStatus.BAD_REQUEST);
  }
}
