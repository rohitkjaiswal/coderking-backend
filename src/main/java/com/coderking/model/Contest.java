package com.coderking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "contests")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Contest {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    @Column(length=2000)
    private String description;
    @Column(length=2000)
    private String rules;
    private String status; //e.g Upcoming ,Live ,Completed
    private LocalDateTime startsAt;
    private LocalDateTime endsAt;
    private String rewards;

    //Relationships
    @OneToMany(mappedBy = "contest",cascade =CascadeType.ALL,orphanRemoval = true)
    @JsonIgnore
    private List<Participant> participants=new ArrayList<>();
}
