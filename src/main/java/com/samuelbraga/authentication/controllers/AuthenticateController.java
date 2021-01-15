package com.samuelbraga.authentication.controllers;

import com.samuelbraga.authentication.dtos.authenticate.CredentialsDTO;
import com.samuelbraga.authentication.dtos.authenticate.TokenDTO;
import com.samuelbraga.authentication.services.AuthenticateService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticateController {
  private final AuthenticateService authenticateService;

  @Autowired
  public AuthenticateController(AuthenticateService authenticateService) {
    this.authenticateService = authenticateService;
  }

  @PostMapping
  public ResponseEntity<TokenDTO> authenticate(
    @RequestBody @Valid CredentialsDTO credentialsDTO
  ) {
    TokenDTO tokenDTO = this.authenticateService.authenticate(credentialsDTO);
    return new ResponseEntity<>(tokenDTO, HttpStatus.CREATED);
  }
}
