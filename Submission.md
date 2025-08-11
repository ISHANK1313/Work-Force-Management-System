Work Force Management Assignment
This project is a Workforce Management System that provides APIs for task management, prioritization, re-assignment, smart task views, and activity tracking. It also includes bug fixes and feature enhancements with detailed test cases.

GitHub Repository: WorkForceManagementAssignment

📌 Features
Task Re-assignment
Prevents duplicate tasks when reassigning by reference ID.

Cancelled Tasks Filtering
Removes cancelled tasks from task views for a cleaner interface.

Smart Daily Task View
Displays tasks within a date range and active tasks that started earlier.

Task Priority Management
Allows updating and filtering tasks by priority (e.g., HIGH, MEDIUM, LOW).

Comments & Activity Tracking
Enables adding comments to tasks and viewing complete activity history.

🐛 Bug Fixes
Bug 1: Task Re-assignment Creates Duplicates
Before:
Two tasks with the same type could be assigned to different staff for the same reference ID.

Fix:
When reassigning, the system cancels old tasks and keeps only one active task.

Testing:

http
Copy
Edit
# View all tasks
GET /task-mgmt

# Reassign task
POST /task-mgmt/assign-by-ref
{
  "reference_id": 201,
  "reference_type": "ENTITY",
  "assignee_id": 4
}

# Verify only one task is ASSIGNED, others are CANCELLED
GET /task-mgmt
Bug 2: Cancelled Tasks Clutter the View
Before:
Cancelled tasks appeared in the date-filtered task view.

Fix:
Cancelled tasks are now excluded from the results.

Testing:

http
Copy
Edit
# Fetch tasks in a date range
POST /task-mgmt/fetch-by-date/v2
{
  "start_date": 1640995200000,
  "end_date": 1672531200000,
  "assignee_ids": [1, 2, 3]
}

# Verify CANCELLED tasks no longer appear
✨ New Features
Feature 1: Smart Daily Task View
Shows:

Tasks that start within the date range.

Tasks started before the range but still active.

Testing:

http
Copy
Edit
POST /task-mgmt/fetch-by-date/v2
{
  "start_date": 1640995200000,
  "end_date": 1672531200000,
  "assignee_ids": [1, 2]
}
Feature 2: Task Priority Management
Testing:

http
Copy
Edit
# Update priority
PUT /task-mgmt/{id}/priority?priority=HIGH

# Fetch by priority
GET /task-mgmt/priority/HIGH
Feature 3: Comments & Activity Tracking
Testing:

http
Copy
Edit
# Add a comment
POST /task-mgmt/comment
{
  "task_id": 1,
  "comment": "Working on this now",
  "user_id": 1
}

# View task with history
GET /task-mgmt/1
🚀 Getting Started
Prerequisites
Java 11+

Spring Boot

Maven

Database configured in application.properties

Installation
bash
Copy
Edit
git clone https://github.com/ISHANK1313/WorkForceManagementAssignment.git
cd WorkForceManagementAssignment
mvn clean install
Running the Application
bash
Copy
Edit
mvn spring-boot:run
📮 API Testing
Use Postman or cURL to test the endpoints.
Ensure the database is seeded with sample data before testing.

📜 License
This project is licensed under the MIT License.
