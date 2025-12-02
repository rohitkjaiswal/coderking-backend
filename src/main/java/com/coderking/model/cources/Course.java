package com.coderking.model.cources;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cid;

    private String name;
    private String creator;

    @Column(length = 3000)
    private String details;



    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Playlist> playlists = new ArrayList<>();

    // add playlist helper method
    public void addPlaylist(Playlist p) {
        playlists.add(p);
        p.setCourse(this);
    }

    public void removePlaylist(Playlist p) {
        playlists.remove(p);
        p.setCourse(null);
    }

    // getters & setters
}
