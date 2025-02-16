package ru.mikhailov.springcourse.todolist.Impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mikhailov.springcourse.todolist.DTO.TaskDTO;
import ru.mikhailov.springcourse.todolist.Mappers.TaskMapper;
import ru.mikhailov.springcourse.todolist.models.Task;
import ru.mikhailov.springcourse.todolist.models.TaskStatus;
import ru.mikhailov.springcourse.todolist.models.User;
import ru.mikhailov.springcourse.todolist.repository.TaskRepository;
import ru.mikhailov.springcourse.todolist.repository.UserRepository;
import ru.mikhailov.springcourse.todolist.service.TaskService;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Primary
@Transactional
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<TaskDTO> findTasksByLogin(String login) {
        User user = getUserByLogin(login);
        return taskRepository.findByUser(user).stream()
                .map(taskMapper::toTaskDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TaskDTO addTask(TaskDTO taskDTO, String login) {
        if (taskDTO == null || login == null) {
            throw new IllegalArgumentException("taskDTO или login не могут быть null");
        }

        User user = getUserByLogin(login);
        Task task = taskMapper.toTask(taskDTO);
        task.setUser(user);

        // Проверяем дедлайн: если он не передан, ставим +1 день, если в прошлом — выбрасываем исключение
        if (taskDTO.getTaskDate() == null) {
            task.setTaskDate(LocalDate.now().plusDays(1));
        } else {
            if (taskDTO.getTaskDate().isBefore(LocalDate.now())) {
                throw new IllegalArgumentException("Нельзя установить дедлайн в прошлом");
            }
            task.setTaskDate(taskDTO.getTaskDate());
        }

        Task savedTask = taskRepository.save(task);
        return taskMapper.toTaskDTO(savedTask);
    }

    @Override
    public TaskDTO findTaskByLogin(Long taskId, String login) {
        User user = getUserByLogin(login);
        Task task = taskRepository.findByIdAndUser(taskId, user)
                .orElseThrow(() -> new EntityNotFoundException("Задача не найдена или не принадлежит пользователю"));
        return taskMapper.toTaskDTO(task);
    }

    @Override
    public TaskDTO updateTask(Long taskId, TaskDTO taskDTO, String login) {
        if (taskDTO == null || login == null) {
            throw new IllegalArgumentException("taskDTO или login не могут быть null");
        }

        User user = getUserByLogin(login);
        Task task = taskRepository.findByIdAndUser(taskId, user)
                .orElseThrow(() -> new EntityNotFoundException("Задача не найдена или не принадлежит пользователю"));

        task.setTaskName(taskDTO.getTaskName());
        task.setDescription(taskDTO.getDescription());
        task.setTaskDate(taskDTO.getTaskDate());
        task.setStatus(taskDTO.getStatus());

        Task updatedTask = taskRepository.save(task);
        return taskMapper.toTaskDTO(updatedTask);
    }

    @Override
    public void deleteTask(Long taskId, String login) {
        User user = getUserByLogin(login);
        Task task = taskRepository.findByIdAndUser(taskId, user)
                .orElseThrow(() -> new EntityNotFoundException("Задача не найдена или не принадлежит пользователю"));
        taskRepository.delete(task);
    }

    @Override
    public List<TaskDTO> findTasksByLoginAndStatus(String login, TaskStatus status) {
        User user = getUserByLogin(login);
        return taskRepository.findByUserAndStatus(user, status).stream()
                .map(taskMapper::toTaskDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> findAllTasksSortedByDate(String login) {
        User user = getUserByLogin(login);
        return taskRepository.findByUserOrderByTaskDateAsc(user).stream()
                .map(taskMapper::toTaskDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> findAllTasksSortedByStatus(String login) {
        User user = getUserByLogin(login);
        return taskRepository.findByUserOrderByStatus(user).stream()
                .map(taskMapper::toTaskDTO)
                .collect(Collectors.toList());
    }

    private User getUserByLogin(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }
}





