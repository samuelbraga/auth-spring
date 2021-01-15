package com.samuelbraga.authentication.controllers;

import com.samuelbraga.authentication.dtos.user.CreateUserDTO;
import com.samuelbraga.authentication.dtos.user.UserDTO;
import com.samuelbraga.authentication.services.UserService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public ResponseEntity<List<UserDTO>> list(HttpServletRequest request) {
    List<UserDTO> userDTOS = this.userService.list();
    return new ResponseEntity<>(userDTOS, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<UserDTO> create(
    @RequestBody @Valid CreateUserDTO createUserDTO
  ) {
    UserDTO userDTO = this.userService.createUser(createUserDTO);
    return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
  }
}
