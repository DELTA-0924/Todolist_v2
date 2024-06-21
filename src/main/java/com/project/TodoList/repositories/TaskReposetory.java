package com.project.TodoList.repositories;

import com.project.TodoList.infastructure.enums.Status;
import com.project.TodoList.models.entities.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface TaskReposetory extends JpaRepository<TaskEntity, Long> {
//    @Transactional
//    @Modifying
//    @Query("update TaskEntity t set t.status=:status where t.id=:Id")
//    void changeStatusById(@Param("Id") Long Id,@Param("status") String status);
}
