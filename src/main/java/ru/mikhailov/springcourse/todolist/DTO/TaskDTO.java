package ru.mikhailov.springcourse.todolist.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mikhailov.springcourse.todolist.models.TaskStatus;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDTO {
        private Long id;
        private TaskStatus status;
        private LocalDate taskDate;
        private String taskName;
        private String description;
        private String  userLogin; // Ссылка на пользователя
    }

