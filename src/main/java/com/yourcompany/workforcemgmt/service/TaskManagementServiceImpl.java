package com.yourcompany.workforcemgmt.service;


import com.yourcompany.workforcemgmt.dto.*;
import com.yourcompany.workforcemgmt.exception.ResourceNotFoundException;
import com.yourcompany.workforcemgmt.mapper.ITaskManagementMapper;
import com.yourcompany.workforcemgmt.model.*;
import com.yourcompany.workforcemgmt.repository.TaskRepository;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskManagementServiceImpl implements TaskManagementService{



        private final TaskRepository taskRepository;
        private final ITaskManagementMapper taskMapper;


        public TaskManagementServiceImpl(TaskRepository taskRepository, ITaskManagementMapper taskMapper) {
            this.taskRepository = taskRepository;
            this.taskMapper = taskMapper;
        }


    /*
    //old
    @Override
    public TaskManagementDto findTaskById(Long id) {
        TaskManagement task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
        return taskMapper.modelToDto(task);
    }


    @Override
    public List<TaskManagementDto> createTasks(TaskCreateRequest createRequest) {
        List<TaskManagement> createdTasks = new ArrayList<>();
        for (TaskCreateRequest.RequestItem item : createRequest.getRequests()) {
            TaskManagement newTask = new TaskManagement();
            newTask.setReferenceId(item.getReferenceId());
            newTask.setReferenceType(item.getReferenceType());
            newTask.setTask(item.getTask());
            newTask.setAssigneeId(item.getAssigneeId());
            newTask.setPriority(item.getPriority());
            newTask.setTaskDeadlineTime(item.getTaskDeadlineTime());
            newTask.setStatus(TaskStatus.ASSIGNED);
            newTask.setDescription("New task created.");
            createdTasks.add(taskRepository.save(newTask));
        }
        return taskMapper.modelListToDtoList(createdTasks);
    }

*/


        @Override
        public List<TaskManagementDto> updateTasks(UpdateTaskRequest updateRequest) {
            List<TaskManagement> updatedTasks = new ArrayList<>();
            for (UpdateTaskRequest.RequestItem item : updateRequest.getRequests()) {
                TaskManagement task = taskRepository.findById(item.getTaskId())
                        .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + item.getTaskId()));


                if (item.getTaskStatus() != null) {
                    task.setStatus(item.getTaskStatus());
                }
                if (item.getDescription() != null) {
                    task.setDescription(item.getDescription());
                }
                updatedTasks.add(taskRepository.save(task));
            }
            return taskMapper.modelListToDtoList(updatedTasks);
        }


    @Override
    public String assignByReference(AssignByReferenceRequest request) {
        List<Task> applicableTasks = Task.getTasksByReferenceType(request.getReferenceType());
        List<TaskManagement> existingTasks = taskRepository.findByReferenceIdAndReferenceType(request.getReferenceId(), request.getReferenceType());


        for (Task taskType : applicableTasks) {
            List<TaskManagement> tasksOfType = existingTasks.stream()
                    .filter(t -> t.getTask() == taskType && t.getStatus() != TaskStatus.COMPLETED)
                    .collect(Collectors.toList());


            // BUG #1 is here. It should assign one and cancel the rest.
            // Instead, it reassigns ALL of them.
           /*
           //old
            if (!tasksOfType.isEmpty()) {
                for (TaskManagement taskToUpdate : tasksOfType) {
                    taskToUpdate.setAssigneeId(request.getAssigneeId());
                    taskRepository.save(taskToUpdate);
                }
            } else {
                // Create a new task if none exist
                TaskManagement newTask = new TaskManagement();
                newTask.setReferenceId(request.getReferenceId());
                newTask.setReferenceType(request.getReferenceType());
                newTask.setTask(taskType);
                newTask.setAssigneeId(request.getAssigneeId());
                newTask.setStatus(TaskStatus.ASSIGNED);
                taskRepository.save(newTask);
            }
        }
            */



        // new
        // FIX for Bug #1: Cancel existing tasks and create a new one
            if (!tasksOfType.isEmpty()) {
            // Create a new task with the new assignee
            TaskManagement newTask = new TaskManagement();
            newTask.setReferenceId(request.getReferenceId());
            newTask.setReferenceType(request.getReferenceType());
            newTask.setTask(taskType);
            newTask.setAssigneeId(request.getAssigneeId());
            newTask.setStatus(TaskStatus.ASSIGNED);
            newTask.setDescription("Reassigned task");
            taskRepository.save(newTask);

            // Cancel all existing tasks
            for (TaskManagement taskToCancel : tasksOfType) {
                taskToCancel.setStatus(TaskStatus.CANCELLED);
                taskRepository.save(taskToCancel);
            }
        } else {
            // Create a new task if none exist
            TaskManagement newTask = new TaskManagement();
            newTask.setReferenceId(request.getReferenceId());
            newTask.setReferenceType(request.getReferenceType());
            newTask.setTask(taskType);
            newTask.setAssigneeId(request.getAssigneeId());
            newTask.setStatus(TaskStatus.ASSIGNED);
            newTask.setDescription("New task created");
            taskRepository.save(newTask);
        }
    }

        return "Tasks assigned successfully for reference " + request.getReferenceId();
}


    /*
    // old
    @Override
    public List<TaskManagementDto> fetchTasksByDate(TaskFetchByDateRequest request) {
        List<TaskManagement> tasks = taskRepository.findByAssigneeIdIn(request.getAssigneeIds());


        // BUG #2 is here. It should filter out CANCELLED tasks but doesn't.
        List<TaskManagement> filteredTasks = tasks.stream()
                .filter(task -> {

                    // This logic is incomplete for the assignment.
                    // It should check against startDate and endDate.
                    // For now, it just returns all tasks for the assignees.
                    return true;
                })
                .collect(Collectors.toList());




        return taskMapper.modelListToDtoList(filteredTasks);
    }
     */


// new
@Override
public List<TaskManagementDto> fetchTasksByDate(TaskFetchByDateRequest request) {
    List<TaskManagement> tasks = taskRepository.findByAssigneeIdIn(request.getAssigneeIds());

    // Filter out CANCELLED tasks and implement smart daily view
    List<TaskManagement> filteredTasks = tasks.stream()
            .filter(task -> task.getStatus() != TaskStatus.CANCELLED) // Bug #2 fix
            .filter(task -> {
                // Feature #1: Smart Daily Task View
                // 1. Active tasks that started within the date range
                boolean startedInRange = task.getTaskDeadlineTime() >= request.getStartDate() &&
                        task.getTaskDeadlineTime() <= request.getEndDate();

                // 2. Active tasks that started before the range but are still open
                boolean startedBeforeAndActive = task.getTaskDeadlineTime() < request.getStartDate() &&
                        task.getStatus() != TaskStatus.COMPLETED;

                return startedInRange || startedBeforeAndActive;
            })
            .collect(Collectors.toList());

    return taskMapper.modelListToDtoList(filteredTasks);
}


// new
@Override
public TaskManagementDto updateTaskPriority(Long taskId, Priority priority) {
    TaskManagement task = taskRepository.findById(taskId)
            .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId));

    task.setPriority(priority);
    taskRepository.save(task);

    return taskMapper.modelToDto(task);
}

@Override
public List<TaskManagementDto> fetchTasksByPriority(Priority priority) {
    List<TaskManagement> tasks = taskRepository.findAll();

    List<TaskManagement> filteredTasks = tasks.stream()
            .filter(task -> task.getPriority() == priority)
            .filter(task -> task.getStatus() != TaskStatus.CANCELLED) // Also filter out cancelled tasks
            .collect(Collectors.toList());

    return taskMapper.modelListToDtoList(filteredTasks);
}



// new
@Override
public TaskManagementDto addComment(AddCommentRequest request) {
    TaskManagement task = taskRepository.findById(request.getTaskId())
            .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + request.getTaskId()));

    // Add comment
    TaskComment comment = new TaskComment();
    comment.setTaskId(request.getTaskId());
    comment.setComment(request.getComment());
    comment.setUserId(request.getUserId());
    taskRepository.saveComment(comment);

    // Add activity log
    TaskActivity activity = new TaskActivity();
    activity.setTaskId(request.getTaskId());
    activity.setAction("User " + request.getUserId() + " added a comment");
    activity.setUserId(request.getUserId());
    taskRepository.saveActivity(activity);

    // Update task dto with comments and activity
    TaskManagementDto taskDto = taskMapper.modelToDto(task);
    taskDto.setComments(taskRepository.findCommentsByTaskId(task.getId()));
    taskDto.setActivityHistory(taskRepository.findActivitiesByTaskId(task.getId()));

    return taskDto;
}

// Modify existing methods to log activities
@Override
public TaskManagementDto findTaskById(Long id) {
    TaskManagement task = taskRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

    // Fetch comments and activity history
    TaskManagementDto taskDto = taskMapper.modelToDto(task);
    taskDto.setComments(taskRepository.findCommentsByTaskId(id));
    taskDto.setActivityHistory(taskRepository.findActivitiesByTaskId(id));

    return taskDto;
}

// Update createTasks to log activity
@Override
public List<TaskManagementDto> createTasks(TaskCreateRequest createRequest) {


    List<TaskManagement> createdTasks = new ArrayList<>();
    for (TaskCreateRequest.RequestItem item : createRequest.getRequests()) {
        TaskManagement newTask = new TaskManagement();
        newTask.setReferenceId(item.getReferenceId());
        newTask.setReferenceType(item.getReferenceType());
        newTask.setTask(item.getTask());
        newTask.setAssigneeId(item.getAssigneeId());
        newTask.setPriority(item.getPriority());
        newTask.setTaskDeadlineTime(item.getTaskDeadlineTime());
        newTask.setStatus(TaskStatus.ASSIGNED);
        newTask.setDescription("New task created.");
        createdTasks.add(taskRepository.save(newTask));
    }
    for (TaskManagement task : createdTasks) {
        TaskActivity activity = new TaskActivity();
        activity.setTaskId(task.getId());
        activity.setAction("Task created");
        activity.setUserId(task.getAssigneeId());
        taskRepository.saveActivity(activity);
    }
    return taskMapper.modelListToDtoList(createdTasks);


}

        }

