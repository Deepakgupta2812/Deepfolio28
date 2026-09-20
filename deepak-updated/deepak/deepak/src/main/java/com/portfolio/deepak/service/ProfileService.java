package com.portfolio.deepak.service;

import com.portfolio.deepak.entity.Profile;
import com.portfolio.deepak.repository.ProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    private static final long PROFILE_ID = 1L;
    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Profile getProfile() {
        return profileRepository.findById(PROFILE_ID).orElseGet(() -> profileRepository.save(new Profile()));
    }

    public boolean hasResume() {
        return profileRepository.existsResumeById(PROFILE_ID);
    }

    public boolean hasImage() {
        return profileRepository.existsImageById(PROFILE_ID);
    }

    public Profile saveProfile(Profile profile) {
        profile.setId(PROFILE_ID);
        return profileRepository.save(profile);
    }
}
