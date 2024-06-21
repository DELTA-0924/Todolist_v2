package com.project.TodoList.TestingService;

import com.project.TodoList.controllers.TaskController;
import com.project.TodoList.infastructure.Mapper;
import com.project.TodoList.infastructure.enums.ExceptionsCode;
import com.project.TodoList.infastructure.exception.MainException;
import com.project.TodoList.models.contract.TaskCreateRequest;
import com.project.TodoList.models.contract.TaskResponce;
import com.project.TodoList.models.entities.TaskEntity;
import com.project.TodoList.repositories.TaskReposetory;
import com.project.TodoList.services.TaskService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.project.TodoList.infastructure.enums.ExceptionsCode.OBJECT_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {
    @Mock
    private TaskReposetory taskRepository;

    @InjectMocks
    private TaskService taskService;
    private static TaskEntity taskEntity;
    private static TaskEntity updatedTask;
    private static List<TaskEntity> expectedEntity;
    @BeforeAll
    static void initExpectedData() {
        taskEntity = new TaskEntity(1L, "Task", "Content", "inactive");
        updatedTask = new TaskEntity(1L, "New Task", "New Content", "active");

        expectedEntity = new ArrayList<TaskEntity>() {{
            add(new TaskEntity(1L, "First Task", "Content of the first task", "inactive"));
            add(new TaskEntity(2L, "Second Task", "Content of the second task", "closed"));
        }};
    }

    @Test
    void TaskCreate_TitleTooLong_MainException() {
        TaskEntity taskRequest = new TaskEntity(1L, "Lorem Lorem Lorem Lorem Lorem Lorem Lorem",
                "content", "inactive");
        Assertions.assertThrows(MainException.class, () -> taskService.CreateTask(taskRequest));
    }

    @Test
    void TaskCreate_ContentTooLong_MainException() {
        TaskEntity taskRequest = new TaskEntity(1L, "sddd",
                "content content content content content content content content", "inactive");
        Assertions.assertThrows(MainException.class, () -> taskService.CreateTask(taskRequest));
    }

    @Test
    void GetAllTasks_DataMatchExpected() {
        when(taskRepository.findAll()).thenReturn(expectedEntity);

        var actualResponse = taskService.GetAllTasks();
        var actual = actualResponse.getBody();
        assertEquals(expectedEntity, actual);
    }

    @Test
    void TaskCreate_DataMatchExpected() {
        doReturn(taskEntity).when(taskRepository).save(any(TaskEntity.class));

        var savedTask = taskService.CreateTask(taskEntity).getBody();

        assertEquals(taskEntity, savedTask);

        verify(taskRepository).save(taskEntity);
    }

    @Test
    void TaskUpdate_DataMatchExpected() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(taskEntity));
        when(taskRepository.save(any(TaskEntity.class))).thenReturn(updatedTask);

        var savedTask = taskService.UpdateTask(updatedTask).getBody();
        assertEquals(updatedTask, savedTask);
        verify(taskRepository).findById(1L);
        verify(taskRepository).save(any(TaskEntity.class));
    }

    @Test
    void TaskUpdate_STATUS_ILLEGAL_ARGUMENT() {
        TaskEntity updatedTask = new TaskEntity(1L, "New Task", "New Content", "unclosed");
        when(taskRepository.findById(1L)).thenReturn(Optional.of(taskEntity));
        Assertions.assertThrows(MainException.class, () -> taskService.UpdateTask(updatedTask));
    }

    @Test
    void TaskGetbyId_DataMatchExpected() {
        when(taskRepository.findById(1L)).thenThrow(new MainException(OBJECT_NOT_FOUND,HttpStatus.NOT_FOUND));
        var taskEntity = taskService.GetTaskById(1L).getBody();
        assertEquals(taskEntity, taskEntity);
    }
    @Test
    void TaskUpdate_ObjectNotFound(){
        when(taskRepository.findById(1L)).thenThrow(new MainException(OBJECT_NOT_FOUND, HttpStatus.NOT_FOUND));
        Assertions.assertThrows(MainException.class, () -> taskService.UpdateTask(updatedTask));
    }

}
