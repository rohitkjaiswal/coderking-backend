package com.coderking.controller;

import com.coderking.model.Contest;
import com.coderking.model.Role;
import com.coderking.model.User;
import com.coderking.repository.ContestRepository;
import com.coderking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/contests")
@RequiredArgsConstructor
public class ContestController {

    @Autowired
    private final ContestRepository contestRepository;
    @Autowired
    private final UserRepository userRepository;

    // ✅ Create a new contest (only ADMIN or ORGANIZER)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('ORGANIZER')")
    public ResponseEntity<Contest> createContest(@RequestBody Contest contest, Authentication auth) {
        // Get current user from authentication
        User user = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() == Role.ADMIN || user.getRole() == Role.ORGANIZER || user.getRole()==Role.USER) {
            Contest saved = contestRepository.save(contest);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    // ✅ Get all contests
    @GetMapping
    public ResponseEntity<List<Contest>> list() {
        List<Contest> contests = contestRepository.findAll();
        return ResponseEntity.ok(contests);
    }

    // ✅ Get contest by ID
    @GetMapping("/{id}")
    public ResponseEntity<Contest> get(@PathVariable Long id) {
        return contestRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Update contest
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ORGANIZER')")
    public ResponseEntity<Contest> update(@PathVariable Long id, @RequestBody Contest updatedContest) {
        return contestRepository.findById(id)
                .map(existing -> {
                    existing.setTitle(updatedContest.getTitle());
                    existing.setDescription(updatedContest.getDescription());
                    existing.setStatus(updatedContest.getStatus());
                    existing.setStartsAt(updatedContest.getStartsAt());
                    existing.setEndsAt(updatedContest.getEndsAt());
                    existing.setRewards(updatedContest.getRewards());
                    Contest saved = contestRepository.save(existing);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Delete contest
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ORGANIZER')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (contestRepository.existsById(id)) {
            contestRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
