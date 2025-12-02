package com.coderking.repository;

import com.coderking.model.Contest;
import com.coderking.model.Participant;
import com.coderking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    boolean existsByContestAndUser(Contest contest, User user);
    List<Participant> findByContest(Contest contest);
    List<Participant> findByContestIdOrderByScoreDesc(Long contestId);
}
