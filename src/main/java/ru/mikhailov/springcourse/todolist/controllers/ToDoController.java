package ru.mikhailov.springcourse.todolist.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.mikhailov.springcourse.todolist.DTO.TaskDTO;
import ru.mikhailov.springcourse.todolist.models.TaskStatus;
import ru.mikhailov.springcourse.todolist.service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@AllArgsConstructor
public class ToDoController {
    private final TaskService taskService;


    @GetMapping
    public List<TaskDTO> findAllTasks(){
        return taskService.findAllTasks();
    }

    @GetMapping("/filter")
    public List<TaskDTO> filterTasksByStatus(@RequestParam TaskStatus status) {
        return taskService.findTasksByStatus(status);
    }

    @PostMapping
    public TaskDTO addTask(@RequestBody TaskDTO taskDTO) {
        return taskService.addTask(taskDTO);
}

    @GetMapping("/{taskId}") // Изменили с taskName на taskId
    public TaskDTO findTask(@PathVariable("taskId") Long taskId) {
        return taskService.findTask(taskId);
    }

    @PutMapping("/update_task/{taskId}") // Теперь передаем taskId
    public TaskDTO updateTask(@PathVariable Long taskId, @RequestBody TaskDTO taskDTO) {
        taskDTO.setId(taskId); // Устанавливаем ID из пути в DTO
        return taskService.updateTask(taskDTO);
    }

    @DeleteMapping("/delete_task/{taskId}")
    public void deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
    }

    @GetMapping("/sort/by-date")
    public List<TaskDTO> sortTasksByDate() {
        return taskService.findAllTasksSortedByDate();
    }

    @GetMapping("/sort/by-status")
    public List<TaskDTO> sortTasksByStatus() {
        return taskService.findAllTasksSortedByStatus();
    }
}
