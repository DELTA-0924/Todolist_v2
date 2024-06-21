package com.project.TodoList.controllers;

import com.project.TodoList.infastructure.Mapper;
import com.project.TodoList.models.contract.Response;
import com.project.TodoList.models.contract.TaskCreateRequest;
import com.project.TodoList.models.contract.TaskResponce;

import com.project.TodoList.models.contract.TaskUpdateRequest;
import com.project.TodoList.repositories.TaskReposetory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.project.TodoList.services.TaskService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Tag(name = "Task endpoints")
public class TaskController {

    private final TaskReposetory taskReposetory;
    private TaskService taskService;
    @Operation(summary = "get task",description = "this endpoint got a  task by id which stay in database ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found")
    })
    @GetMapping("/")
    public ResponseEntity<TaskResponce> getAllTasks(@RequestParam Long id) {
        var taskResponse=taskService.GetTaskById(id);
        return ResponseEntity.ok().body(Mapper.fromEntityToContact(taskResponse.getBody()));
    }
    @Operation(summary = "list of tasks",description = "this endpoint got a list all tasks which stay in database ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
            @ApiResponse(responseCode = "204", description = "List is empty"),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found")
    })
    @GetMapping("/list")
    public ResponseEntity<List<TaskResponce>> taskList(){
        var taskEntitiesResponse=taskService.GetAllTasks();
        var taskEntities=taskEntitiesResponse.getBody();
        List<TaskResponce> tasks = taskEntities.stream().map(
                        Mapper::fromEntityToContact).
                collect(Collectors.toList());
        return ResponseEntity.status(taskEntitiesResponse.getStatusCode()).body(tasks);
    }

    @Operation(summary = "Create task",description = "this endpoint set a new object 'task' to database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "400", description = "Bad client request "),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found")
    })
    @PostMapping(value = "/create")
    public ResponseEntity<TaskResponce> taskCreate(@Valid @RequestBody TaskCreateRequest request) {
        var taskResponse=taskService.CreateTask(Mapper.fromContactToEntity(request));
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(taskResponse.getBody().id)
                .toUri();

        return ResponseEntity.created(location).body(Mapper.fromEntityToContact(taskResponse.getBody()));
    }

    @Operation(summary = "delete task",description = "this endpoint delete a object 'task' from database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found")
    })
    @DeleteMapping("/delete")
    public ResponseEntity<Response>taskDelete(@RequestParam Long id){
        taskService.DeleteTask(id);
        return ResponseEntity.status(HttpStatus.OK).body(new Response("Task deleted",HttpStatus.OK));
    }

    @Operation(summary = "update tasks",description = "this endpoint got a update operation for object 'task' on database ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
            @ApiResponse(responseCode = "400", description = "Bad client request "),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found")
    })
    @PutMapping("/update")
    public ResponseEntity<TaskResponce>taskChangeStatus(@RequestBody TaskUpdateRequest request){
        var taskResponse =taskService.UpdateTask(Mapper.fromContactToEntity(request));
        return ResponseEntity.ok().body(Mapper.fromEntityToContact(taskResponse.getBody()));
    }
}
