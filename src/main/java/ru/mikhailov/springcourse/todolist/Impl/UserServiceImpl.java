package ru.mikhailov.springcourse.todolist.Impl;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.mikhailov.springcourse.todolist.DTO.UserDTO;
import ru.mikhailov.springcourse.todolist.Mappers.UserMapper;
import ru.mikhailov.springcourse.todolist.models.Role;
import ru.mikhailov.springcourse.todolist.models.User;
import ru.mikhailov.springcourse.todolist.repository.UserRepository;
import ru.mikhailov.springcourse.todolist.service.UserService;

import java.util.Collections;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // Для шифрования пароля
    private final UserMapper userMapper;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        if (userRepository.existsByLogin(userDTO.getLogin())) {
            throw new IllegalArgumentException("Пользователь с таким именем уже существует");
        }

        User user = userMapper.toUser(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword())); // Шифруем пароль перед сохранением
        user.setRoles(Collections.singleton(Role.ROLE_USER)); // Назначаем роль по умолчанию
        User savedUser = userRepository.save(user);

        return userMapper.toUserDTO(savedUser);
    }

    @Override
    public UserDTO getCurrentUser(String login) {
        User user = findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
        return userMapper.toUserDTO(user);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return userRepository.findByLogin(login);
    }
}