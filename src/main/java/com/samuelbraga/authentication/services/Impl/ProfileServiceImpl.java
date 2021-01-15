package com.samuelbraga.authentication.services.Impl;

import com.samuelbraga.authentication.exceptions.BaseException;
import com.samuelbraga.authentication.models.Profile;
import com.samuelbraga.authentication.repositories.ProfileRepository;
import com.samuelbraga.authentication.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {
  private final ProfileRepository profileRepository;

  @Autowired
  public ProfileServiceImpl(ProfileRepository profileRepository) {
    this.profileRepository = profileRepository;
  }

  @Override
  public Profile findProfileById(Long id) {
    return this.profileRepository.findById(id)
      .orElseThrow(() -> new BaseException("Profile not found"));
  }
}
