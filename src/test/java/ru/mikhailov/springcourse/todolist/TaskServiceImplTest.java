package ru.mikhailov.springcourse.todolist;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.mikhailov.springcourse.todolist.DTO.TaskDTO;
import ru.mikhailov.springcourse.todolist.Impl.TaskServiceImpl;
import ru.mikhailov.springcourse.todolist.Mappers.TaskMapper;
import ru.mikhailov.springcourse.todolist.models.Role;
import ru.mikhailov.springcourse.todolist.models.Task;
import ru.mikhailov.springcourse.todolist.models.TaskStatus;
import ru.mikhailov.springcourse.todolist.models.User;
import ru.mikhailov.springcourse.todolist.repository.TaskRepository;
import ru.mikhailov.springcourse.todolist.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {

    private static final Logger logger = LoggerFactory.getLogger(TaskServiceImplTest.class);

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskServiceImpl taskService;

    private User user;
    private List<Task> tasks;
    private List<TaskDTO> taskDTOs;


    @BeforeEach
    void setUp() {
        // Устанавливаем тестового пользователя в SecurityContext
        Authentication authentication = new UsernamePasswordAuthenticationToken("testUser", null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Логируем текущего пользователя
        logger.info("Test User in SecurityContext: {}", SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @Test
    void shouldReturnAllTasksForCurrentUser() {
        // Given
        String currentUsername = "testUser";
        User user = new User();  // Создаем фиктивного пользователя
        user.setLogin("testUser");

        // Передаем параметры в правильном порядке
        List<Task> tasks = List.of(
                new Task(1L, user, TaskStatus.TODO, null, "Task 1", "Desc"),
                new Task(2L, user, TaskStatus.IN_PROGRESS, null, "Task 2", "Desc")
        );

        // Логируем список задач
        logger.info("Tasks to be returned: ");
        tasks.forEach(task -> logger.info("Task ID: {}, Name: {}", task.getId(), task.getTaskName()));

        List<TaskDTO> taskDTOs = List.of(
                new TaskDTO(1L, TaskStatus.TODO, null, "Task 1", "testUser"),
                new TaskDTO(2L, TaskStatus.IN_PROGRESS, null, "Task 2", "testUser")
        );

        when(taskRepository.findByUser_Login(currentUsername)).thenReturn(tasks);

        // Логируем вызов мока репозитория
        logger.info("Mocked taskRepository.findByUser_Login returns: {}", tasks.size());
        tasks.forEach(task -> logger.info("Mocked Task ID: {}, Name: {}", task.getId(), task.getTaskName()));

        when(taskMapper.toTaskDTO(any(Task.class))).thenAnswer(invocation -> {
            Task task = invocation.getArgument(0);
            // Логируем преобразование задачи в DTO
            logger.info("Mapping Task ID: {} to DTO with Task Name: {}", task.getId(), task.getTaskName());
            return new TaskDTO(task.getId(), task.getStatus(), task.getTaskDate(), task.getTaskName(), task.getUser().getLogin());
        });

        // When
        List<TaskDTO> result = taskService.findAllTasks();

        // Логируем результат
        logger.info("Resulting TaskDTO list size: {}", result.size());
        result.forEach(dto -> logger.info("TaskDTO ID: {}, Name: {}", dto.getId(), dto.getTaskName()));

        // Then
        assertEquals(2, result.size());
        assertEquals("Task 1", result.get(0).getTaskName());
        verify(taskRepository, times(1)).findByUser_Login(currentUsername);
    }
    @Test
    void shouldAddTaskForCurrentUser() {
        // Устанавливаем пользователя в mock
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setLogin("testUser");
        mockUser.setPassword("password");
        mockUser.setRoles(Set.of(Role.ROLE_USER));

        TaskDTO newTask = new TaskDTO(null, TaskStatus.TODO, LocalDate.now(), "New Task", "testUser");

        // Логируем, чтобы проверить мок
        System.out.println("User before mock: " + mockUser);

        // Given
        when(userRepository.findByLogin(anyString())).thenReturn(Optional.of(mockUser));

        when(taskMapper.toTask(any(TaskDTO.class))).thenAnswer(invocation -> {
            TaskDTO dto = invocation.getArgument(0);
            return new Task(null, mockUser, dto.getStatus(), dto.getTaskDate(), dto.getTaskName(), "Desc");
        });

        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> {
            Task savedTask = invocation.getArgument(0);
            savedTask.setId(1L);
            return savedTask;
        });

        when(taskMapper.toTaskDTO(any(Task.class))).thenAnswer(invocation -> {
            Task task = invocation.getArgument(0);
            return new TaskDTO(task.getId(), task.getStatus(), task.getTaskDate(), task.getTaskName(), task.getUser().getLogin());
        });

        // When
        TaskDTO result = taskService.addTask(newTask);

        // Then
        assertNotNull(result);
        assertEquals("New Task", result.getTaskName());
        assertEquals("testUser", result.getUserLogin());

        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void shouldUpdateTaskForCurrentUser() {
        // Создаём пользователя
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setLogin("testUser");
        mockUser.setPassword("password");
        mockUser.setRoles(Set.of(Role.ROLE_USER));

        // Создаём существующую задачу
        Task existingTask = new Task(1L, mockUser, TaskStatus.TODO, LocalDate.now(), "Old Task", "Old Desc");

        // Данные для обновления
        TaskDTO updatedTaskDTO = new TaskDTO(1L, TaskStatus.IN_PROGRESS, LocalDate.now().plusDays(1), "Updated Task", "testUser");


        // Mock taskRepository.findById()
        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));

        // Mock taskRepository.save()
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> {
            Task savedTask = invocation.getArgument(0);
            return savedTask;
        });

        // Mock taskMapper.toTaskDTO()
        when(taskMapper.toTaskDTO(any(Task.class))).thenAnswer(invocation -> {
            Task task = invocation.getArgument(0);
            return new TaskDTO(task.getId(), task.getStatus(), task.getTaskDate(), task.getTaskName(), task.getUser().getLogin());
        });

        // When
        TaskDTO result = taskService.updateTask(updatedTaskDTO);

        // Then
        assertNotNull(result);
        assertEquals("Updated Task", result.getTaskName());
        assertEquals(TaskStatus.IN_PROGRESS, result.getStatus());
        assertEquals("testUser", result.getUserLogin());

        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void shouldDeleteTaskForCurrentUser() {
        // Создаем пользователя
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setLogin("testUser");

        // Создаем задачу и назначаем пользователя
        Task mockTask = new Task();
        mockTask.setId(1L);
        mockTask.setTaskName("Test Task");
        mockTask.setStatus(TaskStatus.TODO);
        mockTask.setUser(mockUser); // ✅ Назначаем пользователя

        // Мокируем SecurityContext
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testUser");
        SecurityContextHolder.setContext(securityContext);

        // Мокируем репозиторий
        when(taskRepository.findById(1L)).thenReturn(Optional.of(mockTask));

        // Вызываем метод
        taskService.deleteTask(1L);

        // Проверяем, что delete() был вызван один раз
        verify(taskRepository, times(1)).delete(mockTask);
    }
}

