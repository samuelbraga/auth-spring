package com.samuelbraga.authentication.exceptions.handler.dtos;

import lombok.Data;

@Data
public class ErrorBodyValidationDTO {
  private String field;
  private String message;

  public ErrorBodyValidationDTO(String field, String message) {
    this.field = field;
    this.message = message;
  }
}
