package com.yourcompany.workforcemgmt.controller;


import com.yourcompany.workforcemgmt.dto.*;
import com.yourcompany.workforcemgmt.model.Priority;
import com.yourcompany.workforcemgmt.model.Response;
import com.yourcompany.workforcemgmt.service.TaskManagementService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
    @RequestMapping("/task-mgmt")
    public class TaskManagementController {


        private final TaskManagementService taskManagementService;


        public TaskManagementController(TaskManagementService taskManagementService) {
            this.taskManagementService = taskManagementService;
        }

        @GetMapping("/{id}")
        public Response<TaskManagementDto> getTaskById(@PathVariable Long id) {
            return new Response<>(taskManagementService.findTaskById(id));
        }


        @PostMapping("/create")
        public Response<List<TaskManagementDto>> createTasks(@RequestBody TaskCreateRequest request) {
            return new Response<>(taskManagementService.createTasks(request));
        }


        @PostMapping("/update")
        public Response<List<TaskManagementDto>> updateTasks(@RequestBody UpdateTaskRequest request) {
            return new Response<>(taskManagementService.updateTasks(request));
        }


        @PostMapping("/assign-by-ref")
        public Response<String> assignByReference(@RequestBody AssignByReferenceRequest request) {
            return new Response<>(taskManagementService.assignByReference(request));
        }


        @PostMapping("/fetch-by-date/v2")
        public Response<List<TaskManagementDto>> fetchByDate(@RequestBody TaskFetchByDateRequest request) {
            return new Response<>(taskManagementService.fetchTasksByDate(request));
        }


    //new
    @PutMapping("/{id}/priority")
    public Response<TaskManagementDto> updateTaskPriority(@PathVariable Long id, @RequestParam Priority priority) {
        return new Response<>(taskManagementService.updateTaskPriority(id, priority));
    }

    @GetMapping("/priority/{priority}")
    public Response<List<TaskManagementDto>> fetchTasksByPriority(@PathVariable Priority priority) {
        return new Response<>(taskManagementService.fetchTasksByPriority(priority));
    }


    // new
    @PostMapping("/comment")
    public Response<TaskManagementDto> addComment(@RequestBody AddCommentRequest request) {
        return new Response<>(taskManagementService.addComment(request));
    }

    }


