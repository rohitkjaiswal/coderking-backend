package com.coderking.controller;

import com.coderking.model.Profile;
import com.coderking.repository.ProfileRepository;
import com.coderking.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {

    @Autowired
    private final ProfileService service;


    @GetMapping("/{userId}")
    public ResponseEntity<Profile> get(@PathVariable String userId){
        Profile profile = service.getProfile(Long.parseLong(userId));
        return profile != null ? ResponseEntity.ok(profile) : ResponseEntity.notFound().build();
    }


    @PutMapping("/update")
    public ResponseEntity<Profile> update(@RequestBody Profile p){
        Profile updated = service.updateProfile(p);
        return ResponseEntity.ok(updated);
    }



}
