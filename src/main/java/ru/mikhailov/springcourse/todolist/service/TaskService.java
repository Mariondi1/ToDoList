package ru.mikhailov.springcourse.todolist.service;


import ru.mikhailov.springcourse.todolist.DTO.TaskDTO;
import ru.mikhailov.springcourse.todolist.models.TaskStatus;


import java.util.List;


public interface TaskService {
    List<TaskDTO> findAllTasks();
    TaskDTO addTask(TaskDTO taskDTO);
    TaskDTO findTask(Long taskId);
    TaskDTO updateTask(TaskDTO taskDTO);
    void deleteTask(Long taskId);
    List<TaskDTO> findTasksByStatus(TaskStatus status);
    List<TaskDTO> findAllTasksSortedByDate();
    List<TaskDTO> findAllTasksSortedByStatus();
}

