package ru.mikhailov.springcourse.todolist.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.mikhailov.springcourse.todolist.models.Task;
import ru.mikhailov.springcourse.todolist.models.User;
import ru.mikhailov.springcourse.todolist.service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@AllArgsConstructor
public class ToDoController {
    private final TaskService taskService;




    @GetMapping
    public List<Task> findAllTasks(){
        return taskService.findAllTasks();
    }

    @PostMapping
    public Task addTask(Long userId, Task task) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        task.setUser(user);
        return taskRepository.save(task);
}

    @GetMapping("/{taskName}")
    public Task findTask(@PathVariable("taskName") String taskName) {
        return taskService.findTask(taskName);
    }

    @PutMapping("/update_task")
    public Task updateTask(@RequestBody Task taskName) {
        return taskService.updateTask(taskName);
    }

    @DeleteMapping("/delete_task/{taskName}")
    public void  deleteTask(@PathVariable String taskName) {
        taskService.deleteTask(taskName);
    }
}