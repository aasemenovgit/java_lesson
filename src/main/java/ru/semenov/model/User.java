package ru.semenov.model;

import jakarta.persistence.*;
import lombok.Getter;


import java.util.List;

@Entity
@Table(name = "Users")
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Getter
    @Column(name = "username")
    private String username;

    @Getter
    @Column(name = "fio")
    private String fio;

    @OneToMany(mappedBy = "user_id")
    private List<Login> logins;

    public User() {
    }

    public User(String username, String fio) {
        this.username = username;
        this.fio = fio;
    }
}

