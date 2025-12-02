package com.coderking.model;

import com.coderking.Helpers.CommaSeparatedDeserializer;
import com.coderking.Helpers.StringListConverter;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@Builder @Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Profile {

    @Id
    private Long userId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonBackReference
    private User user;

    private String fullName;
    private String bio;
    private String profileImage;
    private int xp;
    private int totalContests;

    @Convert(converter = StringListConverter.class)
    @JsonDeserialize(using = CommaSeparatedDeserializer.class)
    private List<String> skills;

    @Convert(converter = StringListConverter.class)
    @JsonDeserialize(using = CommaSeparatedDeserializer.class)
    private List<String> badges;



}
