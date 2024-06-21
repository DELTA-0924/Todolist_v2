package com.project.TodoList.abstractions;

import com.project.TodoList.models.dto.TaskDto;

import java.util.List;

public interface ITaskService {
    String CreateTask(TaskDto taskDto);
    String UpdateTask(TaskDto taskDto);
    void DeleteTask(Long Id);
    String ChangeStatusTask(String status,String Id);
    String DeleteAllTasks();
    List<TaskDto> getAllTasks();
}
