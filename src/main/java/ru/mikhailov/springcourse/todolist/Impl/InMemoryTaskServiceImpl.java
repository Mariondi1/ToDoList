package ru.mikhailov.springcourse.todolist.Impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mikhailov.springcourse.todolist.models.Task;
import ru.mikhailov.springcourse.todolist.repository.TaskDAO;
import ru.mikhailov.springcourse.todolist.repository.TaskRepository;
import ru.mikhailov.springcourse.todolist.service.TaskService;

import java.util.List;

@Service
@AllArgsConstructor
public class InMemoryTaskServiceImpl implements TaskService {
    private final TaskDAO taskRepository;


    @Override
    public List<Task> findAllTasks() {
        return taskRepository.findAllTasks();
    }

    @Override
    public Task addTask(Long userId, Task task) {
        return taskRepository.saveTask(task);
    }

    @Override
    public Task findTask(String taskName) {
        return taskRepository.findTask(taskName);
    }

    @Override
    public Task updateTask(Task task) {
        return taskRepository.updateTask(task);
    }

    @Override
    public void deleteTask(String taskName) {
        taskRepository.deleteTask(taskName);

    }
}
