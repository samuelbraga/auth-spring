package com.samuelbraga.authentication.services;

import com.samuelbraga.authentication.dtos.user.CreateUserDTO;
import com.samuelbraga.authentication.dtos.user.UserDTO;
import com.samuelbraga.authentication.models.User;
import java.util.List;

public interface UserService {
  User findUserById(Long id);
  User findUserByEmail(String email);
  boolean existsEmail(String email);
  User findUserByUsername(String username);
  boolean existsUsername(String username);
  UserDTO createUser(CreateUserDTO createUserDTO);
  List<UserDTO> list();
}
