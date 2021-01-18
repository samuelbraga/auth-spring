package com.samuelbraga.authentication.exceptions.handler;

import com.samuelbraga.authentication.exceptions.handler.dtos.ErrorBodyValidationDTO;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BodyValidationExceptionHandler {
  private final MessageSource messageSource;

  @Autowired
  public BodyValidationExceptionHandler(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<List<ErrorBodyValidationDTO>> handle(
    MethodArgumentNotValidException methodArgumentNotValidException
  ) {
    List<FieldError> fieldErrors = methodArgumentNotValidException
      .getBindingResult()
      .getFieldErrors();

    List<ErrorBodyValidationDTO> listErrorBodyValidationDTO = fieldErrors
      .stream()
      .map(
        fieldError -> {
          String message =
            this.messageSource.getMessage(
                fieldError,
                LocaleContextHolder.getLocale()
              );
          return new ErrorBodyValidationDTO(fieldError.getField(), message);
        }
      )
      .collect(Collectors.toList());

    return new ResponseEntity<>(
      listErrorBodyValidationDTO,
      HttpStatus.BAD_REQUEST
    );
  }
}
