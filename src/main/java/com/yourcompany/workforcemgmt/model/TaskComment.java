package com.yourcompany.workforcemgmt.model;

import lombok.Data;

import java.time.Instant;
//new
@Data
public class TaskComment {
    private Long id;
    private Long taskId;
    private String comment;
    private Long userId;
    private Long timestamp;

    public TaskComment() {
        this.timestamp = Instant.now().toEpochMilli();
    }
}
