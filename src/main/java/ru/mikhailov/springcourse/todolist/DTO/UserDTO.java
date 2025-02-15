package ru.mikhailov.springcourse.todolist.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mikhailov.springcourse.todolist.models.Role;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String login;
    private String password;
    private Set<Role> roles;
}
