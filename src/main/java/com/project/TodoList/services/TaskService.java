package com.project.TodoList.services;
import com.project.TodoList.infastructure.Mapper;
import com.project.TodoList.infastructure.enums.ExceptionsCode;
import com.project.TodoList.infastructure.enums.Status;
import com.project.TodoList.infastructure.exception.MainException;
import com.project.TodoList.models.contract.Response;
import com.project.TodoList.models.contract.TaskCreateRequest;
import com.project.TodoList.models.contract.TaskResponce;

import com.project.TodoList.models.contract.TaskUpdateRequest;
import com.project.TodoList.services.validators.TaskValidator;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import com.project.TodoList.models.dto.TaskDto;
import com.project.TodoList.models.entities.TaskEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;//package services;
import org.springframework.stereotype.Service;
import com.project.TodoList.repositories.TaskReposetory;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.*;
import java.util.stream.Collectors;

import static com.project.TodoList.infastructure.enums.ExceptionsCode.ID_FIELD_NULL;
import static com.project.TodoList.infastructure.enums.ExceptionsCode.OBJECT_NOT_FOUND;


@Service
@AllArgsConstructor
public class TaskService {
    private TaskReposetory taskReposetory;
    public ResponseEntity<TaskEntity> CreateTask(  TaskEntity taskEntity) {
        TaskValidator.TaskTitleValid().apply(taskEntity);
        TaskValidator.TaskContentValid().apply(taskEntity);
        taskReposetory.save(taskEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskEntity);
    }
    public ResponseEntity<List<TaskEntity>> GetAllTasks() {
        var taskEntities = taskReposetory.findAll();
        if(taskEntities.isEmpty()) {
            throw new MainException(OBJECT_NOT_FOUND,HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.status(HttpStatus.OK).body(taskEntities);
    }
    public ResponseEntity<TaskEntity> UpdateTask(TaskEntity taskRequest) {
        Long id =Optional.ofNullable(taskRequest.getId()).orElseThrow(()->new MainException(ID_FIELD_NULL,HttpStatus.BAD_REQUEST));
        TaskEntity taskEntity = taskReposetory.findById(id).orElseThrow(()->new MainException(OBJECT_NOT_FOUND,HttpStatus.NOT_FOUND));
            //var task= Mapper.fromContactToEntity(request);
        Optional.ofNullable(taskRequest.getStatus()).ifPresent(t->{
                taskEntity.setStatus(t);
                TaskValidator.TaskStatusValid().apply(taskEntity);
            });
        Optional.ofNullable(taskRequest.getTitle()).ifPresent(t->{
                taskEntity.setTitle(t);
                TaskValidator.TaskTitleValid().apply(taskEntity);
            });
        Optional.ofNullable(taskRequest.getContent()).ifPresent(t->{
                taskEntity.setContent(t);
                TaskValidator.TaskContentValid().apply(taskEntity);
            });
        taskReposetory.save(taskEntity);
        return ResponseEntity.status(HttpStatus.OK).body(taskEntity);
    }
    public void DeleteTask(Long id) {
        if(!taskReposetory.existsById(id)) {
            throw new MainException(OBJECT_NOT_FOUND,HttpStatus.NOT_FOUND);
        }
        taskReposetory.deleteById(id);
    }
    public ResponseEntity<TaskEntity> GetTaskById(Long id) {
         var task=taskReposetory.findById(id).orElseThrow(()->new MainException(OBJECT_NOT_FOUND,HttpStatus.NOT_FOUND));
         return ResponseEntity.status(HttpStatus.OK).body(task);
    }

}
