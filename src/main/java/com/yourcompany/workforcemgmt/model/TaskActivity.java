package com.yourcompany.workforcemgmt.model;

import lombok.Data;

import java.time.Instant;
// new
@Data
public class TaskActivity {
    private Long id;
    private Long taskId;
    private String action;
    private Long userId;
    private Long timestamp;

    public TaskActivity() {
        this.timestamp = Instant.now().toEpochMilli();
    }
}
