package ru.mikhailov.springcourse.todolist.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.mikhailov.springcourse.todolist.DTO.TaskDTO;
import ru.mikhailov.springcourse.todolist.DTO.UserDTO;
import ru.mikhailov.springcourse.todolist.models.TaskStatus;
import ru.mikhailov.springcourse.todolist.service.TaskService;
import ru.mikhailov.springcourse.todolist.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class ToDoController {
    private final TaskService taskService;
    private final UserService userService;

    // Помещаем получение login в отдельный метод для повторного использования
    private String getLogin(Authentication authentication) {
        return authentication.getName();
    }

    @PostMapping("/users/register")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.createUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping("/tasks")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<TaskDTO>> findAllTasks(Authentication authentication) {
        String login = getLogin(authentication);  // Получаем login
        List<TaskDTO> tasks = taskService.findTasksByLogin(login);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/tasks/filter")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<TaskDTO>> filterTasksByStatus(@RequestParam TaskStatus status, Authentication authentication) {
        String login = getLogin(authentication);  // Получаем login
        List<TaskDTO> tasks = taskService.findTasksByLoginAndStatus(login, status);
        return ResponseEntity.ok(tasks);
    }

    @PostMapping("/tasks")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<TaskDTO> addTask(@RequestBody TaskDTO taskDTO, Authentication authentication) {
        String login = getLogin(authentication);  // Получаем login
        TaskDTO createdTask = taskService.addTask(taskDTO, login);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    @GetMapping("/tasks/{taskId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<TaskDTO> findTask(@PathVariable Long taskId, Authentication authentication) {
        String login = getLogin(authentication);  // Получаем login
        TaskDTO task = taskService.findTaskByLogin(taskId, login);
        return ResponseEntity.ok(task);
    }

    @PutMapping("/tasks/{taskId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long taskId, @RequestBody TaskDTO taskDTO, Authentication authentication) {
        String login = getLogin(authentication);  // Получаем login
        TaskDTO updatedTask = taskService.updateTask(taskId, taskDTO, login);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/tasks/{taskId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId, Authentication authentication) {
        String login = getLogin(authentication);  // Получаем login
        taskService.deleteTask(taskId, login);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tasks/sort/by-date")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<TaskDTO>> sortTasksByDate(Authentication authentication) {
        String login = getLogin(authentication);  // Получаем login
        List<TaskDTO> sortedTasks = taskService.findAllTasksSortedByDate(login);
        return ResponseEntity.ok(sortedTasks);
    }

    @GetMapping("/tasks/sort/by-status")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<TaskDTO>> sortTasksByStatus(Authentication authentication) {
        String login = getLogin(authentication);  // Получаем login
        List<TaskDTO> sortedTasks = taskService.findAllTasksSortedByStatus(login);
        return ResponseEntity.ok(sortedTasks);
    }
}

