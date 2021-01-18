package com.samuelbraga.authentication.services.impl;

import com.samuelbraga.authentication.configs.hash.Hash;
import com.samuelbraga.authentication.configs.mapper.Mapper;
import com.samuelbraga.authentication.dtos.user.CreateUserDTO;
import com.samuelbraga.authentication.dtos.user.UserDTO;
import com.samuelbraga.authentication.exceptions.BaseException;
import com.samuelbraga.authentication.models.Profile;
import com.samuelbraga.authentication.models.User;
import com.samuelbraga.authentication.repositories.UserRepository;
import com.samuelbraga.authentication.services.ProfileService;
import com.samuelbraga.authentication.services.UserService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final ProfileService profileService;
  private final Mapper mapper;
  private final Hash hash;

  @Autowired
  public UserServiceImpl(
    UserRepository userRepository,
    ProfileService profileService,
    Mapper mapper,
    Hash hash
  ) {
    this.userRepository = userRepository;
    this.profileService = profileService;
    this.hash = hash;
    this.mapper = mapper;
  }

  @Override
  public User findUserById(Long id) {
    return this.userRepository.findById(id)
      .orElseThrow(() -> new BaseException("User email not found"));
  }

  @Override
  public User findUserByEmail(String email) {
    return this.userRepository.findByEmail(email)
      .orElseThrow(() -> new BaseException("User email not found"));
  }

  @Override
  public boolean existsEmail(String email) {
    return this.userRepository.findByEmail(email).isPresent();
  }

  @Override
  public User findUserByUsername(String username) {
    return this.userRepository.findByUsername(username)
      .orElseThrow(() -> new BaseException("Username not found"));
  }

  @Override
  public boolean existsUsername(String username) {
    return this.userRepository.findByEmail(username).isPresent();
  }

  @Override
  public List<UserDTO> list() {
    List<User> users = this.userRepository.findAll();
    return users
      .stream()
      .map(user -> this.mapper.modelMapper().map(user, UserDTO.class))
      .collect(Collectors.toList());
  }

  @Override
  public UserDTO createUser(CreateUserDTO createUserDTO) {
    if (this.existsEmail(createUserDTO.getEmail())) {
      throw new BaseException("User email already exists");
    }

    if (this.existsUsername(createUserDTO.getUsername())) {
      throw new BaseException("Username already exists");
    }

    List<Profile> profiles = this.getProfile(createUserDTO.getProfileId());

    String hashedPassword = this.hashPassword(createUserDTO.getPassword());
    createUserDTO.setPassword(hashedPassword);

    User user = new User(createUserDTO);
    user.setProfiles(profiles);

    this.userRepository.save(user);

    return this.mapper.modelMapper().map(user, UserDTO.class);
  }

  private String hashPassword(String password) {
    return this.hash.hash(password);
  }

  private List<Profile> getProfile(Long id) {
    Profile profile = this.profileService.findProfileById(id);
    return new ArrayList<>(
      Collections.singletonList(profile)
    );
  }
}
