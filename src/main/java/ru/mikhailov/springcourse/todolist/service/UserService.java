package ru.mikhailov.springcourse.todolist.service;

import ru.mikhailov.springcourse.todolist.DTO.UserDTO;
import ru.mikhailov.springcourse.todolist.models.User;

import java.util.Optional;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);
    UserDTO getCurrentUser(String login); // Получение данных текущего пользователя
    Optional<User> findByLogin(String login); // Поиск пользователя в системе
}
