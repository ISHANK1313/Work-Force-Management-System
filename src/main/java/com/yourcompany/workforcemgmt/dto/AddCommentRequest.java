package com.yourcompany.workforcemgmt.dto;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

// new
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AddCommentRequest {
    private Long taskId;
    private String comment;
    private Long userId;
}
