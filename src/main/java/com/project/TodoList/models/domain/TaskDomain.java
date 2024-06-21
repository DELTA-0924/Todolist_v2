package com.project.TodoList.models.domain;

import com.project.TodoList.infastructure.enums.Status;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Builder
@Getter
public class TaskDomain {
    private static final int MAX_TITLE_LENGTH = 30;
    private static final int MAX_CONTENT_LENGTH = 60;
    private String Title;
    private String Content;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private Status Status;

    private TaskDomain(String title, String Content, Long id, Status status) {
        this.Title = title;
        this.Content = Content;
        this.Id = id;
        this.Status = status;
    }

    public static Map<String, Object> create(String title, String Content) {
        Map<String, Object> result = new HashMap<>();
        if (title.length() >= MAX_TITLE_LENGTH || Content.length() >= MAX_CONTENT_LENGTH) {
            result.put("task", null);
            result.put("error", "length  og title or content march");
            return result;
        }
        Status status = com.project.TodoList.infastructure.enums.Status.inactive;
        TaskDomain task = new TaskDomain(title, Content, null, status);
        result.put("task", task);
        result.put("error", null);
        return result;
    }
}
