package com.samuelbraga.authentication.services;

import com.samuelbraga.authentication.dtos.authenticate.CredentialsDTO;
import com.samuelbraga.authentication.dtos.authenticate.TokenDTO;

public interface AuthenticateService {
  TokenDTO authenticate(CredentialsDTO credentialsDTO);
}
