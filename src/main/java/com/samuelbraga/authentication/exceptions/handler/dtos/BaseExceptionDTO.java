package com.samuelbraga.authentication.exceptions.handler.dtos;

import lombok.Data;

@Data
public class BaseExceptionDTO {
  private final String message;

  public BaseExceptionDTO(String message) {
    this.message = message;
  }
}
