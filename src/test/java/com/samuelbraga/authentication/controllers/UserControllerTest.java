package com.samuelbraga.authentication.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import com.samuelbraga.authentication.dtos.user.CreateUserDTO;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @Test
  public void shouldBeCreatedAnUser() throws Exception {
    CreateUserDTO createUserDTO = CreateUserDTO
      .builder()
      .email("foo.bar@example.com")
      .username("foobar")
      .name("Foo Bar")
      .password("123456")
      .profileId(2L)
      .build();

    Gson gson = new Gson();
    String content = gson.toJson(createUserDTO);

    MvcResult result =
      this.mockMvc.perform(
          post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(content)
        )
        .andExpect(status().isCreated())
        .andReturn();

    Assert.assertNotNull(result);
  }

  @Test
  @WithMockUser(authorities = "ADMIN")
  public void shouldBeListUsers() throws Exception {
    MvcResult result =
      this.mockMvc.perform(
          get("/users").contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andReturn();

    Assert.assertNotNull(result);
  }
}
