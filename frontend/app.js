const API = "http://localhost:8080/api/tasks";

const list = document.getElementById("tasks");
const loading = document.getElementById("loading");
const empty = document.getElementById("empty");

// LOAD TASKS
async function loadTasks() {
    loading.style.display = "block";
    empty.style.display = "none";

    const res = await fetch(API);
    const data = await res.json();

    list.innerHTML = "";

    loading.style.display = "none";

    if (data.length === 0) {
        empty.style.display = "block";
        return;
    }

    data.forEach(t => renderTask(t));
}

// RENDER TASK
function renderTask(t) {
    const li = document.createElement("li");

    li.innerHTML = `
        <strong>${t.title}</strong><br/>
        <small>${t.description}</small>
        <div class="actions">
            <button onclick="editTask(${t.id}, '${t.title}', '${t.description}')">Edit</button>
            <button onclick="deleteTask(${t.id})">Delete</button>
        </div>
    `;

    list.appendChild(li);
}

// CREATE
async function createTask() {
    const title = document.getElementById("title").value;
    const description = document.getElementById("desc").value;

    if (!title) return;

    await fetch(API, {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({title, description})
    });

    document.getElementById("title").value = "";
    document.getElementById("desc").value = "";

    loadTasks();
}

// DELETE
async function deleteTask(id) {
    await fetch(`${API}/${id}`, { method: "DELETE" });
    loadTasks();
}

// EDIT
async function editTask(id, title, description) {
    const newTitle = prompt("Edit title:", title);
    const newDesc = prompt("Edit description:", description);

    if (!newTitle) return;

    await fetch(`${API}/${id}`, {
        method: "PUT",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({title: newTitle, description: newDesc})
    });

    loadTasks();
}

// WEBSOCKET
const socket = new WebSocket("ws://localhost:8080/ws/tasks");

socket.onmessage = function (event) {
    const data = JSON.parse(event.data);

    if (data.type === "CREATE") {
        loadTasks();
    }
};

// INIT
loadTasks();