package com.project.TodoList.models.contract;

import org.springframework.http.HttpStatus;

public record Response (String message, HttpStatus status){
}
