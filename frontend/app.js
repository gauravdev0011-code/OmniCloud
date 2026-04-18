const API = "http://localhost:8080/api/tasks";
const list = document.getElementById("tasks");

// LOAD TASKS
async function loadTasks() {
    const res = await fetch(API);
    const data = await res.json();

    list.innerHTML = "";

    data.forEach(t => {
        const li = document.createElement("li");
        li.innerHTML = `
            <strong>${t.title}</strong><br/>
            <small>${t.description}</small>
        `;
        list.appendChild(li);
    });
}

// CREATE TASK
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

// WEBSOCKET
const socket = new WebSocket("ws://localhost:8080/ws/tasks");

socket.onmessage = function () {
    loadTasks();
};

// INIT
loadTasks();