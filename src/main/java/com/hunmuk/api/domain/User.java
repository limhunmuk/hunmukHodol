package com.hunmuk.api.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

    private LocalDateTime createdAt;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Session> sessions = new ArrayList<>();

    @Builder
    public User(Long id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.createdAt = LocalDateTime.now();
    }

    public Session addSession() {
        // uuid 생성
        Session session = Session.builder()
                .user(this)
                .build();
        sessions.add(session);

        return session;
    }
}
