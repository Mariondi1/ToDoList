package ru.mikhailov.springcourse.todolist.Impl;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Override
    public List<TaskDTO> findAllTasks() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return taskRepository.findByUser_Login(username).stream()
                .map(taskMapper::toTaskDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TaskDTO addTask(TaskDTO taskDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        User user = userRepository.findByLogin(currentUsername)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        Task task = taskMapper.toTask(taskDTO);
        task.setUser(user); // Назначаем задачу текущему пользователю

        Task savedTask = taskRepository.save(task);
        return taskMapper.toTaskDTO(savedTask);
    }



    @Override
    public List<TaskDTO> findTasksByStatus(TaskStatus status) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        return taskRepository.findByUser_LoginAndStatusOrderByTaskDateAsc(currentUsername, status)
                .stream()
                .map(taskMapper::toTaskDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> findAllTasksSortedByDate() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return taskRepository.findByUser_LoginOrderByTaskDateAsc(username).stream()
                .map(taskMapper::toTaskDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> findAllTasksSortedByStatus() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return taskRepository.findByUser_LoginOrderByStatusAsc(username).stream()
                .map(taskMapper::toTaskDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TaskDTO findTask(Long taskId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Задача не найдена"));

        if (!task.getUser().getLogin().equals(username)) {
            throw new AccessDeniedException("Вы не можете просматривать чужие задачи!");
        }

        return taskMapper.toTaskDTO(task);
    }



    @Override
    public TaskDTO updateTask(TaskDTO taskDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Task existingTask = taskRepository.findById(taskDTO.getId())
                .orElseThrow(() -> new RuntimeException("Задача не найдена"));

        if (!existingTask.getUser().getLogin().equals(username)) {
            throw new AccessDeniedException("Вы не можете редактировать чужие задачи!");
        }

        existingTask.setTaskName(taskDTO.getTaskName());
        existingTask.setStatus(taskDTO.getStatus());
        existingTask.setTaskDate(taskDTO.getTaskDate());

        Task updatedTask = taskRepository.save(existingTask);
        return taskMapper.toTaskDTO(updatedTask);
    }

    @Override
    public void deleteTask(Long taskId) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Задача не найдена"));

        if (!task.getUser().getLogin().equals(currentUsername)) {
            throw new AccessDeniedException("Вы не можете удалить чужую задачу!");
        }

        taskRepository.delete(task);
    }

}



