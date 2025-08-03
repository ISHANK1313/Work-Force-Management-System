package com.yourcompany.workforcemgmt.repository;

import com.yourcompany.workforcemgmt.model.ReferenceType;
import com.yourcompany.workforcemgmt.model.TaskActivity;
import com.yourcompany.workforcemgmt.model.TaskComment;
import com.yourcompany.workforcemgmt.model.TaskManagement;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    Optional<TaskManagement> findById(Long id);
    TaskManagement save(TaskManagement task);
    List<TaskManagement> findAll();
    List<TaskManagement> findByReferenceIdAndReferenceType(Long referenceId, ReferenceType referenceType);
    List<TaskManagement> findByAssigneeIdIn(List<Long> assigneeIds);


    //new
    TaskComment saveComment(TaskComment comment);
    TaskActivity saveActivity(TaskActivity activity);
    List<TaskComment> findCommentsByTaskId(Long taskId);
    List<TaskActivity> findActivitiesByTaskId(Long taskId);

}

