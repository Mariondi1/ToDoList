package ru.mikhailov.springcourse.todolist.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.mikhailov.springcourse.todolist.DTO.TaskDTO;
import ru.mikhailov.springcourse.todolist.models.Task;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    @Mapping(source = "user.login", target = "userLogin")
    TaskDTO toTaskDTO(Task task);

    @Mapping(source = "userLogin", target = "user.login")
    Task toTask(TaskDTO taskDTO);
}

