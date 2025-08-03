package com.yourcompany.workforcemgmt.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.yourcompany.workforcemgmt.model.*;
import lombok.Data;

import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TaskManagementDto {
    private Long id;
    private Long referenceId;
    private ReferenceType referenceType;
    private Task task;
    private String description;
    private TaskStatus status;
    private Long assigneeId;
    private Long taskDeadlineTime;
    private Priority priority;

    // new
    private List<TaskComment> comments;
    private List<TaskActivity> activityHistory;
}

