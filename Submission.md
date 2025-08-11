<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width,initial-scale=1" />
  <title>Work Force Management Assignment — README</title>
  <style>
    :root{
      --bg:#0f1724; --card:#0b1220; --muted:#94a3b8; --accent:#60a5fa;
      --glass: rgba(255,255,255,0.03);
    }
    html,body{height:100%; margin:0; font-family:Inter,ui-sans-serif,system-ui,Segoe UI,Roboto,"Helvetica Neue",Arial; background:linear-gradient(180deg,#071025 0%, #041020 100%); color:#e6eef8;}
    .container{max-width:1100px; margin:28px auto; padding:24px;}
    header{display:flex;gap:16px;align-items:center;margin-bottom:18px}
    .logo{width:56px;height:56px;border-radius:8px;background:linear-gradient(135deg,var(--accent),#7dd3fc);display:flex;align-items:center;justify-content:center;color:#042; font-weight:700}
    h1{margin:0;font-size:1.6rem}
    p.lead{color:var(--muted);margin:6px 0 0}
    nav{margin:18px 0}
    nav a{color:var(--accent); text-decoration:none; margin-right:12px; font-size:0.95rem}
    .card{background:var(--glass); border:1px solid rgba(255,255,255,0.04); padding:18px; border-radius:12px; box-shadow: 0 6px 18px rgba(2,6,23,0.6);}
    .grid{display:grid;grid-template-columns: 1fr 320px; gap:18px; align-items:start}
    @media (max-width:900px){ .grid{grid-template-columns:1fr} .toc{order:2} }
    .toc{padding:12px; background:linear-gradient(180deg,#07122633,#07122611); border-radius:8px}
    .toc h3{margin:0 0 8px}
    .toc a{display:block;color:var(--muted); margin:6px 0; text-decoration:none}
    section{margin-bottom:18px}
    h2{color:#dff1ff; margin:0 0 8px}
    ul{color:var(--muted); margin:8px 0 12px}
    pre{background:#010714; border-radius:8px; padding:12px; overflow:auto; white-space:pre-wrap; word-break:break-word; color:#dfeeff; font-family: ui-monospace, SFMono-Regular, Menlo, Monaco, "Roboto Mono", "Courier New", monospace; font-size:0.92rem; border:1px solid rgba(255,255,255,0.03)}
    .meta{display:flex;gap:12px;flex-wrap:wrap;color:var(--muted);font-size:0.95rem}
    .btn{display:inline-flex;align-items:center;gap:8px;padding:8px 10px;border-radius:8px;background:linear-gradient(90deg,var(--accent),#7dd3fc);color:#042;border:none;cursor:pointer;font-weight:600}
    .small{font-size:0.9rem;color:var(--muted)}
    .footer{margin-top:18px;color:var(--muted); font-size:0.88rem}
    .copy-btn{float:right;margin-top:-6px;background:transparent;border:1px solid rgba(255,255,255,0.04);color:var(--muted);padding:6px 8px;border-radius:6px;cursor:pointer}
    .code-header{display:flex;justify-content:space-between;align-items:center;margin-bottom:6px}
    .link{color:var(--accent); text-decoration:underline}
    .badge{display:inline-block;padding:6px 8px;border-radius:8px;background:rgba(255,255,255,0.02);border:1px solid rgba(255,255,255,0.03); color:var(--muted); font-weight:600}
  </style>
</head>
<body>
  <div class="container">
    <header>
      <div class="logo">WFM</div>
      <div>
        <h1>Work Force Management Assignment</h1>
        <p class="lead">A task management microservice — task re-assignment, priority management, smart daily views, comments and activity tracking.</p>
      </div>
    </header>

    <nav>
      <a href="#features">Features</a>
      <a href="#bugfixes">Bug Fixes</a>
      <a href="#new-features">New Features</a>
      <a href="#getting-started">Getting Started</a>
      <a href="#api-testing">API Testing</a>
      <a href="#license">License</a>
      <a href="https://github.com/ISHANK1313/WorkForceManagementAssignment/tree/master" target="_blank" class="btn">GitHub Repo</a>
    </nav>

    <div class="grid">
      <main class="card">
        <!-- Features -->
        <section id="features">
          <h2>Features</h2>
          <ul>
            <li><strong>Task Re-assignment</strong> — prevents duplicate tasks for the same reference by cancelling old ones when reassigning.</li>
            <li><strong>Cancelled Tasks Filtering</strong> — excludes cancelled tasks from date views for a cleaner result set.</li>
            <li><strong>Smart Daily Task View</strong> — shows tasks within a date range and active tasks that started earlier but still require attention.</li>
            <li><strong>Task Priority Management</strong> — supports updating and filtering by priority: HIGH, MEDIUM, LOW.</li>
            <li><strong>Comments & Activity Tracking</strong> — add comments and view full activity history for a task.</li>
          </ul>
        </section>

        <!-- Bug fixes -->
        <section id="bugfixes">
          <h2>Bug Fixes</h2>

          <h3>Bug 1 — Task Re-assignment Creates Duplicates</h3>
          <p class="small">Issue:</p>
          <p class="small" style="color:var(--muted)">Multiple tasks of the same type could be assigned to different staff for the same reference ID.</p>
          <p class="small">Resolution:</p>
          <p class="small" style="color:var(--muted)">When reassigning by reference, the service now cancels existing tasks of the same type for that reference and creates/keeps only a single ASSIGNED task.</p>

          <div class="code-header"><div class="small">Testing steps</div></div>
          <pre><code># View all tasks
GET /task-mgmt

# Reassign task by reference
POST /task-mgmt/assign-by-ref
{
  "reference_id": 201,
  "reference_type": "ENTITY",
  "assignee_id": 4
}

# Verify: GET /task-mgmt
# Only one task should be ASSIGNED (to assignee 4); previous duplicates should be CANCELLED
          </code></pre>

          <h3>Bug 2 — Cancelled Tasks Clutter the View</h3>
          <p class="small">Issue:</p>
          <p class="small" style="color:var(--muted)">Cancelled tasks were included in date-filtered results when fetching tasks by date and assignee list.</p>
          <p class="small">Resolution:</p>
          <p class="small" style="color:var(--muted)">Cancelled tasks are now filtered out of the date-range fetch endpoint to give users a focused, relevant task view.</p>

          <div class="code-header"><div class="small">Testing steps</div></div>
          <pre><code>POST /task-mgmt/fetch-by-date/v2
{
  "start_date": 1640995200000,
  "end_date": 1672531200000,
  "assignee_ids": [1, 2, 3]
}

# Verify: CANCELLED tasks no longer appear in the results
          </code></pre>
        </section>

        <!-- New features -->
        <section id="new-features">
          <h2>New Features</h2>

          <h3>Feature 1 — Smart Daily Task View</h3>
          <p class="small">Behavior:</p>
          <ul>
            <li>Includes tasks that started within the requested date range.</li>
            <li>Also includes tasks that started before the range but remain active (incomplete) so they are not missed.</li>
          </ul>
          <div class="code-header"><div class="small">Testing</div></div>
          <pre><code>POST /task-mgmt/fetch-by-date/v2
{
  "start_date": 1640995200000,
  "end_date": 1672531200000,
  "assignee_ids": [1, 2]
}
# Verify results include tasks created in range and still-active tasks that started earlier.
          </code></pre>

          <h3>Feature 2 — Task Priority Management</h3>
          <p class="small">Behavior:</p>
          <ul>
            <li>Update a task's priority using a dedicated endpoint.</li>
            <li>Fetch tasks filtered by priority value.</li>
          </ul>
          <div class="code-header"><div class="small">Testing</div></div>
          <pre><code># Update priority to HIGH
PUT /task-mgmt/{id}/priority?priority=HIGH

# Fetch HIGH priority tasks
GET /task-mgmt/priority/HIGH
          </code></pre>

          <h3>Feature 3 — Comments & Activity Tracking</h3>
          <p class="small">Behavior:</p>
          <ul>
            <li>Add comments to tasks and record activity events (create, assign, comment, update).</li>
            <li>Task details endpoint returns comments + activity history.</li>
          </ul>
          <div class="code-header"><div class="small">Testing</div></div>
          <pre><code># Add a comment
POST /task-mgmt/comment
{
  "task_id": 1,
  "comment": "Working on this now",
  "user_id": 1
}

# View task with comments & history
GET /task-mgmt/1
# Verify: response includes Comments and Activity History sections
          </code></pre>
        </section>

        <!-- Getting started -->
        <section id="getting-started">
          <h2>Getting Started</h2>
          <p class="small">Prerequisites</p>
          <ul>
            <li>Java 11+</li>
            <li>Spring Boot (project uses Spring ecosystem)</li>
            <li>Maven (or your preferred build tool)</li>
            <li>Configured database (set connection in <code>application.properties</code>)</li>
          </ul>

          <div class="code-header"><div class="small">Install & Run</div></div>
          <pre><code># Clone
git clone https://github.com/ISHANK1313/WorkForceManagementAssignment.git
cd WorkForceManagementAssignment

# Build
mvn clean install

# Run
mvn spring-boot:run
          </code></pre>

          <p class="small">Notes:</p>
          <ul>
            <li>Seed sample data before running functional tests (optional fixtures/seed scripts).</li>
            <li>Use Postman or cURL to call the API endpoints shown above.</li>
          </ul>
        </section>

        <!-- API testing -->
        <section id="api-testing">
          <h2>API Testing</h2>
          <p class="small">Use Postman or cURL. Example sequence for verifying Bug 1 (duplicates):</p>
          <pre><code>1. GET /task-mgmt                 -> observe duplicates for reference_id: 201
2. POST /task-mgmt/assign-by-ref
   { "reference_id": 201, "reference_type": "ENTITY", "assignee_id": 4 }
3. GET /task-mgmt                 -> verify only one ASSIGNED; rest CANCELLED
          </code></pre>

          <p class="small">Date range fetch example:</p>
          <pre><code>POST /task-mgmt/fetch-by-date/v2
{
  "start_date": 1640995200000,
  "end_date": 1672531200000,
  "assignee_ids": [1, 2, 3]
}
# Confirm CANCELLED tasks are filtered after the fix.
          </code></pre>
        </section>

        <section id="license">
          <h2>License</h2>
          <p class="small">This project is provided under the <strong>MIT License</strong>. See the repository for license text.</p>
        </section>

        <div class="footer">
          <div class="meta">
            <div class="badge">Repository</div>
            <div><a class="link" href="https://github.com/ISHANK1313/WorkForceManagementAssignment/tree/master" target="_blank">github.com/ISHANK1313/WorkForceManagementAssignment</a></div>
          </div>
        </div>
      </main>

      <aside class="toc card">
        <h3>Table of contents</h3>
        <a href="#features">Features</a>
        <a href="#bugfixes">Bug Fixes</a>
        <a href="#new-features">New Features</a>
        <a href="#getting-started">Getting Started</a>
        <a href="#api-testing">API Testing</a>
        <a href="#license">License</a>

        <hr style="border:none;margin:12px 0;border-top:1px solid rgba(255,255,255,0.03)">

        <div style="font-size:0.95rem;color:var(--muted)">
          <strong>Quick snippets</strong>
          <div style="margin-top:8px">
            <div class="small">View tasks</div>
            <pre style="margin:6px 0 0;padding:8px;font-size:0.85rem">GET /task-mgmt</pre>

            <div class="small" style="margin-top:8px">Assign-by-ref</div>
            <pre style="margin:6px 0 0;padding:8px;font-size:0.85rem">POST /task-mgmt/assign-by-ref</pre>

            <div class="small" style="margin-top:8px">Fetch by date</div>
            <pre style="margin:6px 0 0;padding:8px;font-size:0.85rem">POST /task-mgmt/fetch-by-date/v2</pre>
          </div>
        </div>

        <hr style="border:none;margin:12px 0;border-top:1px solid rgba(255,255,255,0.03)">

        <div style="font-size:0.9rem;color:var(--muted)">
          <strong>Tips</strong>
          <ul>
            <li>Seed data for consistent test results.</li>
            <li>Use the date range in milliseconds (epoch) as shown in examples.</li>
            <li>Check activity history to verify comment/assignment events.</li>
          </ul>
        </div>
      </aside>
    </div>
  </div>

  <script>
    // Copy code block helper when user clicks a pre element
    document.querySelectorAll('pre').forEach(pre => {
      const btn = document.createElement('button');
      btn.textContent = 'Copy';
      btn.className = 'copy-btn';
      btn.title = 'Copy code to clipboard';
      btn.addEventListener('click', async () => {
        try {
          await navigator.clipboard.writeText(pre.innerText.trim());
          btn.textContent = 'Copied!';
          setTimeout(() => btn.textContent = 'Copy', 1300);
        } catch (e) {
          btn.textContent = 'Failed';
          setTimeout(() => btn.textContent = 'Copy', 1300);
        }
      });
      pre.style.position = 'relative';
      pre.parentNode.insertBefore(btn, pre);
    });
  </script>
</body>
</html>
