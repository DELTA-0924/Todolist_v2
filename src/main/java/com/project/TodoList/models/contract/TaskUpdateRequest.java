package com.project.TodoList.models.contract;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

public class TaskUpdateRequest {
    @NotNull
    @Schema(    description = "Индефиктор задачи",required = true)
    private Long id;
    @Schema(    description = "Заголовок задачи")
    private String title;
    @Schema(    description = "содержимое  задачи")
    private String content;
    @Schema(    description = "статус  задачи (по умолчанию inactive)" )
    private String status;
    public Optional<String> getTitle(){
        return Optional.ofNullable(title);
    }
    public Optional<String> getContent(){
        return Optional.ofNullable(content);
    }
    public Optional<String> getStatus(){
        return Optional.ofNullable(status);
    }
    public Long getId(){
        return id;
    }
}
