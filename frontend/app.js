const API_BASE = "https://omnicloud.onrender.com";
const WS_URL = "wss://omnicloud.onrender.com/ws/tasks";

const list = document.getElementById("taskList");
const emptyState = document.getElementById("emptyState");

// ---------------- LOAD TASKS ----------------
async function loadTasks() {
    const res = await fetch(`${API_BASE}/tasks`);
    const tasks = await res.json();

    list.innerHTML = "";

    tasks.forEach(addTaskToUI);
    toggleEmpty(tasks);
}

// ---------------- CREATE ----------------
async function createTask() {
    const title = document.getElementById("title").value;
    const description = document.getElementById("description").value;

    if (!title) return;

    await fetch(`${API_BASE}/tasks`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ title, description })
    });

    document.getElementById("title").value = "";
    document.getElementById("description").value = "";
}

// ---------------- DELETE ----------------
async function deleteTask(id) {
    await fetch(`${API_BASE}/tasks/${id}`, {
        method: "DELETE"
    });

    loadTasks();
}

// ---------------- UI ----------------
function addTaskToUI(task) {
    const item = document.createElement("li");

    item.innerHTML = `
    <div>
      <strong>${task.title}</strong><br/>
      <small>${task.description || ""}</small>
    </div>
    <button class="delete-btn" onclick="deleteTask(${task.id})">Delete</button>
  `;

    list.appendChild(item);
}

function toggleEmpty(tasks) {
    emptyState.style.display = tasks.length === 0 ? "block" : "none";
}

// ---------------- WEBSOCKET ----------------
const socket = new WebSocket(WS_URL);

socket.onopen = () => {
    console.log("WebSocket connected");
};

socket.onmessage = (event) => {
    try {
        const data = JSON.parse(event.data);

        if (data.type === "CREATE") {
            addTaskToUI(data);
            emptyState.style.display = "none";
        }
    } catch (e) {
        console.log("Invalid WS message");
    }
};

// ---------------- INIT ----------------
loadTasks();