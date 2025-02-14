package ru.mikhailov.springcourse.todolist.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;


        @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<Task> tasks = new ArrayList<>();

        @Column(unique = true, nullable = false)
        private String login;
        private String password;
        @ElementCollection(fetch = FetchType.EAGER)
        @Enumerated(EnumType.STRING)
        private Set<Role> roles;

}
