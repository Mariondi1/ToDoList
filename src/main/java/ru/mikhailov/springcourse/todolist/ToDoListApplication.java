package ru.mikhailov.springcourse.todolist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

    @SpringBootApplication(scanBasePackages = "ru.mikhailov.springcourse.todolist")
    public class ToDoListApplication {
        public static void main(String[] args) {
            SpringApplication.run(ToDoListApplication.class, args);
        }
    }
