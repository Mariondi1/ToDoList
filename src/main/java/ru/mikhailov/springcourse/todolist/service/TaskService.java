package ru.mikhailov.springcourse.todolist.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mikhailov.springcourse.todolist.models.Task;
import ru.mikhailov.springcourse.todolist.models.User;
import ru.mikhailov.springcourse.todolist.repository.TaskRepository;

import java.util.List;


public interface TaskService {
    public List<Task> findAllTasks();

    Task addTask(Long userId, Task task);
    Task findTask(String taskName);
    Task updateTask(Task task);
    void deleteTask(String taskName);

}
