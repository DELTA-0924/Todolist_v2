package com.project.TodoList.models.dto;

import com.project.TodoList.infastructure.enums.Status;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

import java.util.UUID;
@Builder
@Data
@Setter
public class TaskDto {
    public Long id;
    public String title;
    public String content;
    public Status status;
}
