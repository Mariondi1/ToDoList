package ru.mikhailov.springcourse.todolist.models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
@Builder
@NoArgsConstructor
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

        public User(Long id, List<Task> tasks, String login, String password, Set<Role> roles) {
                this.id = id;
                this.tasks = tasks;
                this.login = login;
                this.password = password;
                this.roles = roles;
        }

}
