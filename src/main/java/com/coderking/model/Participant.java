package com.coderking.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "participants")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Participant {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "participant_rank")
    private Integer rank;
    private Integer score;

    //Relationships
    @ManyToOne
    @JoinColumn(name = "contest_id",nullable = false)
    private Contest contest;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;
}
