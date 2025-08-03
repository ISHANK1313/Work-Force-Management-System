Bug Fixes Testing
Bug 1: Task Re-assignment Creates Duplicates
Before Testing:

Use Postman to send a GET request to /task-mgmt to view all tasks
Note that reference ID 201 has two tasks with the same task type assigned to different staff (IDs 2 and 3)

After Testing:

Send a POST request to /task-mgmt/assign-by-ref with body:

text{
  "reference_id": 201,
  "reference_type": "ENTITY",
  "assignee_id": 4
}

Send GET request to /task-mgmt again
Verify only one task is ASSIGNED (to assignee 4) and the others are CANCELLED

Bug Fixes Testing
Bug 1: Task Re-assignment Creates Duplicates
Before Testing:

Use Postman to send a GET request to /task-mgmt to view all tasks
Note that reference ID 201 has two tasks with the same task type assigned to different staff (IDs 2 and 3)

After Testing:

Send a POST request to /task-mgmt/assign-by-ref with body:

text{
  "reference_id": 201,
  "reference_type": "ENTITY",
  "assignee_id": 4
}

Send GET request to /task-mgmt again
Verify only one task is ASSIGNED (to assignee 4) and the others are CANCELLED

Bug 2: Cancelled Tasks Clutter the View
Before Testing:

Send a POST request to /task-mgmt/fetch-by-date/v2 with body:

text{
  "start_date": 1640995200000,
  "end_date": 1672531200000,
  "assignee_ids": [1, 2, 3]
}

Note that CANCELLED tasks appear in results

After Testing:

Send same request again
Verify CANCELLED tasks no longer appear in results

Bug Fix #2. The issue was that cancelled tasks cluttered the task view. In this request, 
I'm fetching tasks for specific assignees. Before our fix, notice cancelled tasks appear in the results. 
After implementing the fix, you can see the cancelled tasks are filtered out, giving users a cleaner view of relevant tasks
New Features Testing
Feature 1: Smart Daily Task View
Testing:

Create a few tasks with different deadline times
Send a POST request to /task-mgmt/fetch-by-date/v2 with body:

text{
  "start_date": 1640995200000, 
  "end_date": 1672531200000,
  "assignee_ids": [1, 2]
}

Verify results include:

   * Tasks that started within the date range
   * Tasks that started before the range but are still active
   For Feature #1, I've implemented a smart daily task view. This feature shows all relevant tasks that need attention, not just those created on a specific day. 
   I'll create several tasks with different start dates, then fetch tasks for a specific date range. 
   Notice the results include both tasks that started within our selected date range AND active tasks that started before the range but still need attention. 
   This gives users a complete view of their responsibilities.
Feature 2: Task Priority Management
Testing:

Update a task's priority:

   * Send PUT request to /task-mgmt/{id}/priority?priority=HIGH
   * Verify priority is updated in response

Fetch tasks by priority:

   * Send GET request to /task-mgmt/priority/HIGH
   * Verify only HIGH priority tasks are returned
Feature #2 adds task prioritization. Let's first update this task's priority to HIGH using the new endpoint. 
You can see the priority is successfully updated in the response. Now, I'll fetch all HIGH priority tasks using our new priority filter endpoint. 
This returns only tasks with HIGH priority, helping managers and staff focus on the most critical items first.
Testing:

Add a comment to a task:

Send POST request to /task-mgmt/comment with body:
{
  "task_id": 1,
  "comment": "Working on this now",
  "user_id": 1
}
Verify comment is added and activity is logged
View task details with history:

Send GET request to /task-mgmt/1
Verify response includes:
Comments section with your added comment
Activity history showing task creation and comment addition
Finally, Feature #3 implements task comments and activity tracking
