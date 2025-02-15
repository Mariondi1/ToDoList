package ru.mikhailov.springcourse.todolist.service;


import ru.mikhailov.springcourse.todolist.DTO.TaskDTO;
import ru.mikhailov.springcourse.todolist.models.TaskStatus;


import java.util.List;


public interface TaskService {
    List<TaskDTO> findTasksByLogin(String login); // Найти все задачи пользователя
    TaskDTO addTask(TaskDTO taskDTO, String login); // Добавить задачу пользователю
    TaskDTO findTaskByLogin(Long taskId, String login); // Найти задачу по ID и пользователю
    TaskDTO updateTask(Long taskId, TaskDTO taskDTO, String login); // Обновить задачу
    void deleteTask(Long taskId, String login); // Удалить задачу пользователя
    List<TaskDTO> findTasksByLoginAndStatus(String login, TaskStatus status); // Фильтр по статусу
    List<TaskDTO> findAllTasksSortedByDate(String login); // Сортировка по дате
    List<TaskDTO> findAllTasksSortedByStatus(String login); // Сортировка по статусу
}

