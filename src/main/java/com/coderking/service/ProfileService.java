package com.coderking.service;

import com.coderking.model.Profile;
import com.coderking.repository.ProfileRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor @Getter @Setter
public class ProfileService {

    @Autowired
    private final ProfileRepository repo;

    public Profile getProfile(Long userId){
        return repo.findById(userId).orElse(null);
    }

    public Profile updateProfile(Profile profile) {
        Profile existing = repo.findById(profile.getUserId())
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        existing.setBio(profile.getBio());
        existing.setFullName(profile.getFullName());
        existing.setProfileImage(profile.getProfileImage());
        existing.setSkills(profile.getSkills());
        existing.setBadges(profile.getBadges());
        existing.setTotalContests(profile.getTotalContests());
        existing.setXp(profile.getXp());
        return repo.save(existing);
    }
}
