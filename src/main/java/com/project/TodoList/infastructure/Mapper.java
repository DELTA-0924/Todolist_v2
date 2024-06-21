package com.project.TodoList.infastructure;

import com.project.TodoList.infastructure.enums.ExceptionsCode;
import com.project.TodoList.infastructure.enums.Status;
import com.project.TodoList.infastructure.exception.MainException;
import com.project.TodoList.models.contract.TaskCreateRequest;
import com.project.TodoList.models.contract.TaskResponce;

import com.project.TodoList.models.contract.TaskUpdateRequest;
import com.project.TodoList.models.dto.TaskDto;
import com.project.TodoList.models.entities.TaskEntity;
import org.springframework.http.HttpStatus;

import static com.project.TodoList.infastructure.enums.ExceptionsCode.*;
import static com.project.TodoList.infastructure.enums.Status.inactive;

public class Mapper {



    public static TaskEntity fromContactToEntity(TaskCreateRequest taskContact){
        TaskEntity task=TaskEntity.builder()
                .title(taskContact.getTitle().orElseThrow(()->new MainException(TITLE_NULL, HttpStatus.BAD_REQUEST)))
                .content(taskContact.getContent().orElseThrow(()->new MainException(CONTENT_NULL,HttpStatus.BAD_REQUEST)))
                .status(inactive.toString())
                .build();
        return task;
    }
    public static TaskEntity fromContactToEntity(TaskUpdateRequest taskContact){
        TaskEntity task=new TaskEntity();
        task.setId(taskContact.getId());
        taskContact.getTitle().ifPresent(task::setTitle);
        taskContact.getContent().ifPresent(task::setContent);
        taskContact.getStatus().ifPresent(task::setStatus);
        return task;
    }
    public static TaskResponce fromEntityToContact(TaskEntity taskEntity) {
        TaskResponce task = new TaskResponce(taskEntity.id,
                                             taskEntity.title,
                                             taskEntity.content,
                                             taskEntity.status);
        return task;
    }
    //from Entity model to Dto model
    public static TaskDto fromEntityToDto(TaskEntity taskEntity) {
        TaskDto task = TaskDto.builder().
                id(taskEntity.getId()).
                title(taskEntity.getTitle()).
                content(taskEntity.getContent()).
                status(Status.valueOf(taskEntity.getStatus())).
                build();
        return task;
    }


    //from Dto model to entity model
    public static TaskEntity fromDtoToEntity(TaskDto taskDto) {
        TaskEntity task = new TaskEntity(taskDto.id, taskDto.title, taskDto.content, taskDto.status.toString());
        return task;
    }
    //from contact model to dto model
    public static TaskDto fromContactToDto(TaskCreateRequest taskContact){
        TaskDto task=TaskDto.builder()
                .title(taskContact.getTitle().orElseThrow(()->new MainException(TITLE_NULL,HttpStatus.BAD_REQUEST)))
                .content(taskContact.getContent().orElseThrow(()->new MainException(CONTENT_NULL,HttpStatus.BAD_REQUEST)))
                .build();
        return task;
    }

//    public static TaskEntity fromDomainToEntity(TaskDomain taskDomain) {
//        TaskEntity task = new TaskEntity(
//                taskDomain.getId(),
//                taskDomain.getTitle(),
//                taskDomain.getContent(),
//                taskDomain.getStatus().toString());
//        return task;
//    }

    //    //From Domain  model to Dto model
//    public static TaskDto fromDomainToDto(TaskDomain taskDomain) {
//        TaskDto task = TaskDto.builder().
//                title(taskDomain.getTitle()).
//                content(taskDomain.getContent()).
//                status(taskDomain.getStatus()).
//                build();
//        return task;
//    }
}
