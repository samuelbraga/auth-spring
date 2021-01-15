package com.samuelbraga.authentication.services;

import com.samuelbraga.authentication.models.Profile;

public interface ProfileService {
  Profile findProfileById(Long id);
}
