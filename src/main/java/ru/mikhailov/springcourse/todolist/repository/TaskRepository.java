package ru.mikhailov.springcourse.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mikhailov.springcourse.todolist.models.Task;
import ru.mikhailov.springcourse.todolist.models.TaskStatus;

import java.util.List;


@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUser_Login(String login);
    List<Task> findByUser_LoginAndStatus(String login, TaskStatus status);
    List<Task> findByUser_LoginAndStatusOrderByTaskDateAsc(String login, TaskStatus status);
    List<Task> findByUser_LoginOrderByTaskDateAsc(String login);
    List<Task> findByUser_LoginOrderByStatusAsc(String login);
}



