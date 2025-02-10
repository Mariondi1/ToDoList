package ru.mikhailov.springcourse.todolist.Impl;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.mikhailov.springcourse.todolist.models.Task;
import ru.mikhailov.springcourse.todolist.repository.TaskRepository;
import ru.mikhailov.springcourse.todolist.service.TaskService;

import java.util.List;

@Service
@AllArgsConstructor
@Primary
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    @Override
    public List<Task> findAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Task addTask(Long userId, Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Task findTask(String taskName) {
        return taskRepository.findByTaskName(taskName);
    }

    @Override
    public Task updateTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public void deleteTask(String taskName) {
        taskRepository.deleteByTaskName(taskName);
    }
}
