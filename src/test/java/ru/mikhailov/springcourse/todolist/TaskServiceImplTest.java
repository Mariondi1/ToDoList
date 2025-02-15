package ru.mikhailov.springcourse.todolist;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.mikhailov.springcourse.todolist.DTO.TaskDTO;
import ru.mikhailov.springcourse.todolist.Mappers.TaskMapper;
import ru.mikhailov.springcourse.todolist.models.Task;
import ru.mikhailov.springcourse.todolist.models.TaskStatus;
import ru.mikhailov.springcourse.todolist.models.User;
import ru.mikhailov.springcourse.todolist.repository.TaskRepository;
import ru.mikhailov.springcourse.todolist.repository.UserRepository;
import ru.mikhailov.springcourse.todolist.Impl.TaskServiceImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskServiceImpl taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findTasksByLogin() {
        User user = new User();
        user.setLogin("testUser");

        Task task = new Task();
        task.setUser(user);

        TaskDTO taskDTO = new TaskDTO();

        when(userRepository.findByLogin("testUser")).thenReturn(Optional.of(user));
        when(taskRepository.findByUser(user)).thenReturn(Collections.singletonList(task));
        when(taskMapper.toTaskDTO(task)).thenReturn(taskDTO);

        List<TaskDTO> result = taskService.findTasksByLogin("testUser");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(taskDTO, result.get(0));
    }

    @Test
    void addTask() {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTaskName("Test Task");

        User user = new User();
        user.setLogin("testUser");

        Task task = new Task();
        task.setTaskName("Test Task");

        when(userRepository.findByLogin("testUser")).thenReturn(Optional.of(user));
        when(taskMapper.toTask(taskDTO)).thenReturn(task);
        when(taskRepository.save(task)).thenReturn(task);
        when(taskMapper.toTaskDTO(task)).thenReturn(taskDTO);

        TaskDTO result = taskService.addTask(taskDTO, "testUser");

        assertNotNull(result);
        assertEquals("Test Task", result.getTaskName());
    }

    @Test
    void findTaskByLogin() {
        User user = new User();
        user.setLogin("testUser");

        Task task = new Task();
        task.setId(1L);
        task.setUser(user);

        TaskDTO taskDTO = new TaskDTO();

        when(userRepository.findByLogin("testUser")).thenReturn(Optional.of(user));
        when(taskRepository.findByIdAndUser(1L, user)).thenReturn(Optional.of(task));
        when(taskMapper.toTaskDTO(task)).thenReturn(taskDTO);

        TaskDTO result = taskService.findTaskByLogin(1L, "testUser");

        assertNotNull(result);
        assertEquals(taskDTO, result);
    }

    @Test
    void updateTask() {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTaskName("Updated Task");

        User user = new User();
        user.setLogin("testUser");

        Task task = new Task();
        task.setId(1L);
        task.setUser(user);

        when(userRepository.findByLogin("testUser")).thenReturn(Optional.of(user));
        when(taskRepository.findByIdAndUser(1L, user)).thenReturn(Optional.of(task));
        when(taskRepository.save(task)).thenReturn(task);
        when(taskMapper.toTaskDTO(task)).thenReturn(taskDTO);

        TaskDTO result = taskService.updateTask(1L, taskDTO, "testUser");

        assertNotNull(result);
        assertEquals("Updated Task", result.getTaskName());
    }

    @Test
    void deleteTask() {
        User user = new User();
        user.setLogin("testUser");

        Task task = new Task();
        task.setId(1L);
        task.setUser(user);

        when(userRepository.findByLogin("testUser")).thenReturn(Optional.of(user));
        when(taskRepository.findByIdAndUser(1L, user)).thenReturn(Optional.of(task));

        taskService.deleteTask(1L, "testUser");

        verify(taskRepository, times(1)).delete(task);
    }

    @Test
    void findTasksByLoginAndStatus() {
        User user = new User();
        user.setLogin("testUser");

        Task task = new Task();
        task.setUser(user);
        task.setStatus(TaskStatus.DONE);

        TaskDTO taskDTO = new TaskDTO();

        when(userRepository.findByLogin("testUser")).thenReturn(Optional.of(user));
        when(taskRepository.findByUserAndStatus(user, TaskStatus.DONE)).thenReturn(Collections.singletonList(task));
        when(taskMapper.toTaskDTO(task)).thenReturn(taskDTO);

        List<TaskDTO> result = taskService.findTasksByLoginAndStatus("testUser", TaskStatus.DONE);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(taskDTO, result.get(0));
    }

    @Test
    void findAllTasksSortedByDate() {
        User user = new User();
        user.setLogin("testUser");

        Task task = new Task();
        task.setUser(user);

        TaskDTO taskDTO = new TaskDTO();

        when(userRepository.findByLogin("testUser")).thenReturn(Optional.of(user));
        when(taskRepository.findByUserOrderByTaskDateAsc(user)).thenReturn(Collections.singletonList(task));
        when(taskMapper.toTaskDTO(task)).thenReturn(taskDTO);

        List<TaskDTO> result = taskService.findAllTasksSortedByDate("testUser");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(taskDTO, result.get(0));
    }

    @Test
    void findAllTasksSortedByStatus() {
        User user = new User();
        user.setLogin("testUser");

        Task task = new Task();
        task.setUser(user);

        TaskDTO taskDTO = new TaskDTO();

        when(userRepository.findByLogin("testUser")).thenReturn(Optional.of(user));
        when(taskRepository.findByUserOrderByStatus(user)).thenReturn(Collections.singletonList(task));
        when(taskMapper.toTaskDTO(task)).thenReturn(taskDTO);

        List<TaskDTO> result = taskService.findAllTasksSortedByStatus("testUser");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(taskDTO, result.get(0));
    }
}


