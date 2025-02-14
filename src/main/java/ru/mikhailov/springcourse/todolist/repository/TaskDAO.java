//package ru.mikhailov.springcourse.todolist.repository;
//
//import org.springframework.stereotype.Repository;
//import ru.mikhailov.springcourse.todolist.models.Task;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.IntStream;
//
//@Repository
//public class TaskDAO {
//    private final List<Task> TASKS = new ArrayList<>();
//
//    public List<Task> findAllTasks() {
//        return TASKS;
//    }
//
//
//    public Task saveTask(Task task) {
//        TASKS.add(task);
//        return task;
//    }
//
//
//    public Task findTask(String taskName) {
//        return TASKS.stream().filter(task -> task.getTaskName()
//                        .equals(taskName))
//                .findFirst()
//                .orElse(null);
//    }
//
//
//    public Task updateTask(Task task) {
//        var taskIndex= IntStream.range(0, TASKS.size())
//                .filter(index -> TASKS.get(index).getTaskName().equals(task.getTaskName()))
//                .findFirst()
//                .orElse(-1);
//        if (taskIndex > -1) {
//            TASKS.set(taskIndex, task);
//            return task;
//        }
//        return null;
//    }
//
//
//    public void deleteTask(String taskName) {
//        var task= findTask(taskName);
//        if (task != null) {
//            TASKS.remove(task);
//        }
//
//    }
//}

