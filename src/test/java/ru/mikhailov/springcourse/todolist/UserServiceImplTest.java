package ru.mikhailov.springcourse.todolist;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.mikhailov.springcourse.todolist.DTO.UserDTO;
import ru.mikhailov.springcourse.todolist.Impl.UserServiceImpl;
import ru.mikhailov.springcourse.todolist.Mappers.UserMapper;
import ru.mikhailov.springcourse.todolist.models.User;
import ru.mikhailov.springcourse.todolist.repository.UserRepository;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setLogin("testUser");
        userDTO.setPassword("password");

        User user = new User();
        user.setLogin("testUser");
        user.setPassword("encodedPassword");

        when(userRepository.existsByLogin("testUser")).thenReturn(false);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userMapper.toUser(userDTO)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toUserDTO(user)).thenReturn(userDTO);

        UserDTO result = userService.createUser(userDTO);

        assertNotNull(result);
        assertEquals("testUser", result.getLogin());
    }

    @Test
    void createUserWithExistingLogin() {
        UserDTO userDTO = new UserDTO();
        userDTO.setLogin("testUser");

        when(userRepository.existsByLogin("testUser")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> userService.createUser(userDTO));
    }

    @Test
    void getCurrentUser() {
        User user = new User();
        user.setLogin("testUser");

        UserDTO userDTO = new UserDTO();
        userDTO.setLogin("testUser");

        when(userRepository.findByLogin("testUser")).thenReturn(Optional.of(user));
        when(userMapper.toUserDTO(user)).thenReturn(userDTO);

        UserDTO result = userService.getCurrentUser("testUser");

        assertNotNull(result);
        assertEquals("testUser", result.getLogin());
    }

    @Test
    void getCurrentUserNotFound() {
        when(userRepository.findByLogin("testUser")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.getCurrentUser("testUser"));
    }

    @Test
    void findByLogin() {
        User user = new User();
        user.setLogin("testUser");

        when(userRepository.findByLogin("testUser")).thenReturn(Optional.of(user));

        Optional<User> result = userService.findByLogin("testUser");

        assertTrue(result.isPresent());
        assertEquals("testUser", result.get().getLogin());
    }
}
