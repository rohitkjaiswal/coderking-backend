package com.coderking.controller;

import com.coderking.model.Contest;
import com.coderking.model.Participant;
import com.coderking.model.Profile;
import com.coderking.model.User;
import com.coderking.repository.ContestRepository;
import com.coderking.repository.ParticipantRepository;
import com.coderking.repository.ProfileRepository;
import com.coderking.repository.UserRepository;
import com.coderking.security.JwtUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Getter @Setter
@RequestMapping("/api/user/contest")
@RequiredArgsConstructor
public class ParticipantController {

    private final ParticipantRepository participantRepository;
    private final ContestRepository contestRepository;
    private final UserRepository userRepository;
    private  final ProfileRepository profileRepository;

    private JwtUtil jwtUtil;
    // ✅ Join a contest
    @PostMapping("/{contestId}/join")
    public ResponseEntity<Participant> joinContest(
            @PathVariable Long contestId,
            @AuthenticationPrincipal Jwt jwt) {

        Long userId = jwt.getClaim("userId");
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Contest contest = contestRepository.findById(contestId)
                .orElseThrow(() -> new RuntimeException("Contest not found"));

        if (participantRepository.existsByContestAndUser(contest, user)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        Participant participant = new Participant();
        participant.setContest(contest);
        participant.setUser(user);
        participant.setScore(0);
        participant.setRank(null);



        Participant saved = participantRepository.save(participant);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // ✅ Get all participants of a contest
    @GetMapping("/{contestId}/participants")
    public ResponseEntity<List<Participant>> getParticipants(@PathVariable Long contestId) {
        Contest contest = contestRepository.findById(contestId)
                .orElseThrow(() -> new RuntimeException("Contest not found"));

        List<Participant> participants = participantRepository.findByContest(contest);
        return ResponseEntity.ok(participants);
    }

    // ✅ Leaderboard (sorted by score, then rank)
    @GetMapping("/{contestId}/leaderboard")
    public ResponseEntity<List<Participant>> getLeaderboard(@PathVariable Long contestId) {
        Contest contest = contestRepository.findById(contestId)
                .orElseThrow(() -> new RuntimeException("Contest not found"));

        List<Participant> leaderboard = participantRepository.findByContestIdOrderByScoreDesc(contest.getId());
        return ResponseEntity.ok(leaderboard);
    }
}
