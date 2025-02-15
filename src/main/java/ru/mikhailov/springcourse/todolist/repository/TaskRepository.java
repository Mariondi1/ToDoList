package ru.mikhailov.springcourse.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mikhailov.springcourse.todolist.models.Task;
import ru.mikhailov.springcourse.todolist.models.TaskStatus;
import ru.mikhailov.springcourse.todolist.models.User;

import java.util.List;
import java.util.Optional;


@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUser(User user);
    Optional<Task> findByIdAndUser(Long id, User user);
    List<Task> findByUserAndStatus(User user, TaskStatus status);
    List<Task> findByUserOrderByTaskDateAsc(User user);
    List<Task> findByUserOrderByStatus(User user);
}



