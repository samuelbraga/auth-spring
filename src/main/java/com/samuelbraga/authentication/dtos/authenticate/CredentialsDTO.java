package com.samuelbraga.authentication.dtos.authenticate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CredentialsDTO {
  @NotNull
  @NotEmpty
  private String username;

  @NotNull
  @NotEmpty
  @Length
  private String password;
}
