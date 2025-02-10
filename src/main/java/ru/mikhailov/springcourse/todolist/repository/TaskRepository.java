package ru.mikhailov.springcourse.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mikhailov.springcourse.todolist.models.Task;
import ru.mikhailov.springcourse.todolist.models.User;

import java.util.List;

@Repository
    public interface TaskRepository extends JpaRepository<Task, Long> {
    Task findByTaskName(String taskName);

    void deleteByTaskName(String taskName);
    }

