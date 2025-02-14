//package ru.mikhailov.springcourse.todolist.Impl;
//
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Service;
//import ru.mikhailov.springcourse.todolist.DTO.TaskDTO;
//import ru.mikhailov.springcourse.todolist.Mappers.TaskMapper;
//import ru.mikhailov.springcourse.todolist.models.Task;
//import ru.mikhailov.springcourse.todolist.repository.TaskDAO;
//import ru.mikhailov.springcourse.todolist.repository.TaskRepository;
//import ru.mikhailov.springcourse.todolist.service.TaskService;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@AllArgsConstructor
//public class InMemoryTaskServiceImpl implements TaskService {
//    private final TaskDAO taskRepository;
//    private final TaskMapper taskMapper;
//
//
//    @Override
//    public List<TaskDTO> findAllTasks() {
//        return taskRepository.findAllTasks()
//                .stream()
//                .map(taskMapper::toTaskDTO)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public TaskDTO addTask(TaskDTO taskDTO) {
//        Task task = taskMapper.toTask(taskDTO);
//        return taskMapper.toTaskDTO(taskRepository.saveTask(task));
//    }
//
//    @Override
//    public TaskDTO findTask(String taskName) {
//        Task task = taskRepository.findTask(taskName);
//        return task != null ? taskMapper.toTaskDTO(task) : null;
//    }
//
//    @Override
//    public TaskDTO updateTask(TaskDTO taskDTO) {
//        Task task = taskMapper.toTask(taskDTO);
//        return taskMapper.toTaskDTO(taskRepository.updateTask(task));
//    }
//
//    @Override
//    public void deleteTask(String taskName) {
//        taskRepository.deleteTask(taskName);
//    }
//}
