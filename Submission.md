# Work Force Management Assignment

A task management microservice with features for task re-assignment, priority management, smart daily views, comments, and activity tracking.

**GitHub Repository:** [WorkForceManagementAssignment](https://github.com/ISHANK1313/WorkForceManagementAssignment)

---

## 📌 Features

- **Task Re-assignment** – Prevents duplicate tasks when reassigning by reference ID.
- **Cancelled Tasks Filtering** – Removes cancelled tasks from task views for a cleaner interface.
- **Smart Daily Task View** – Displays tasks within a date range and active tasks that started earlier.
- **Task Priority Management** – Update and filter tasks by priority (HIGH, MEDIUM, LOW).
- **Comments & Activity Tracking** – Add comments to tasks and view full activity history.

---

## 🐛 Bug Fixes

### **Bug 1: Task Re-assignment Creates Duplicates**
**Before:**  
Two tasks with the same type could be assigned to different staff for the same reference ID.

**Fix:**  
When reassigning, old tasks are cancelled and only one task remains active.

**Testing:**
```http
# View all tasks
GET /task-mgmt

# Reassign task
POST /task-mgmt/assign-by-ref
{
  "reference_id": 201,
  "reference_type": "ENTITY",
  "assignee_id": 4
}

Bug 2: Cancelled Tasks Clutter the View
Before:
Cancelled tasks appeared in date-filtered task views.

Fix:
Cancelled tasks are now excluded from results.

Testing:


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

Tasks started within the date range.

Active tasks started before the range but still ongoing.

Testing:

POST /task-mgmt/fetch-by-date/v2
{
  "start_date": 1640995200000,
  "end_date": 1672531200000,
  "assignee_ids": [1, 2]
}
Feature 2: Task Priority Management
Testing:

# Update priority
PUT /task-mgmt/{id}/priority?priority=HIGH

# Fetch by priority
GET /task-mgmt/priority/HIGH
Feature 3: Comments & Activity Tracking
Testing:

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
git clone https://github.com/ISHANK1313/WorkForceManagementAssignment.git
cd WorkForceManagementAssignment
mvn clean install


Running the Application
mvn spring-boot:run

📮 API Testing
Use Postman or cURL to test endpoints.
Ensure the database has sample data before testing.

📜 License
This project is licensed under the MIT License.


# Verify only one task is ASSIGNED (to assignee 4), others are CANCELLED
GET /task-mgmt
