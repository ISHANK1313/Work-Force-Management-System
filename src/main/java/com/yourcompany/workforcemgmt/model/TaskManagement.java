package com.yourcompany.workforcemgmt.model;
import lombok.Data;
import com.yourcompany.workforcemgmt.model.ReferenceType;
import com.yourcompany.workforcemgmt.model.Task;
import com.yourcompany.workforcemgmt.model.TaskStatus;
import com.yourcompany.workforcemgmt.model.Priority;

import java.util.ArrayList;
import java.util.List;

@Data
public class TaskManagement {
    private Long id;
    private Long referenceId;
    private ReferenceType referenceType;
    private Task task;
    private String description;
    private TaskStatus status;
    private Long assigneeId; // Simplified from Entity for this assignment
    private Long taskDeadlineTime;
    private Priority priority;

    // new
    private List<TaskComment> comments = new ArrayList<>();
    private List<TaskActivity> activityHistory = new ArrayList<>();
}

