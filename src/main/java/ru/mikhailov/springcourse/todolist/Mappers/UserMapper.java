package ru.mikhailov.springcourse.todolist.Mappers;

import org.mapstruct.Mapper;
import ru.mikhailov.springcourse.todolist.DTO.UserDTO;
import ru.mikhailov.springcourse.todolist.models.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toUserDTO(User user);
    User toUser(UserDTO userDTO);
}
