import { useEffect, useState } from "react";
import "./index.css";
import { motion } from "framer-motion";

const API = "http://localhost:8080/api/tasks";

export default function App() {
    const [tasks, setTasks] = useState([]);
    const [text, setText] = useState("");

    useEffect(() => {
        fetchTasks();

        const socket = new WebSocket("ws://localhost:8080/ws");

        socket.onmessage = () => {
            fetchTasks();
        };

        return () => socket.close();
    }, []);

    const fetchTasks = async () => {
        const res = await fetch(API);
        const data = await res.json();
        setTasks(data);
    };

    const addTask = async () => {
        if (!text) return;

        await fetch(API, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ title: text }),
        });

        setText("");
        fetchTasks();
    };

    const toggle = async (task) => {
        await fetch(`${API}/${task.id}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                ...task,
                completed: !task.completed,
            }),
        });

        fetchTasks();
    };

    const remove = async (id) => {
        await fetch(`${API}/${id}`, { method: "DELETE" });
        fetchTasks();
    };

    const completed = tasks.filter(t => t.completed).length;

    return (
        <div className="app">
            {/* BACKGROUND IMAGE */}
            <div className="bg"></div>

            {/* SIDEBAR */}
            <div className="sidebar">
                <h2>OmniCloud</h2>
                <p>01 Dashboard</p>
                <p>02 Tasks</p>
                <p>03 Analytics</p>
                <p>04 Team</p>
            </div>

            {/* MAIN */}
            <div className="main">

                <h1 className="title">
                    OmniCloud<span>.</span>
                </h1>

                <p className="subtitle">
                    Organize your tasks. Maximize your potential.
                </p>

                {/* INPUT */}
                <div className="input-box">
                    <input
                        value={text}
                        onChange={(e) => setText(e.target.value)}
                        placeholder="What needs to be done?"
                    />
                    <button onClick={addTask}>Add Task +</button>
                </div>

                {/* STATS */}
                <div className="cards">
                    <motion.div whileHover={{ scale: 1.05 }} className="card">
                        <h2>{tasks.length}</h2>
                        <p>Tasks</p>
                    </motion.div>

                    <motion.div whileHover={{ scale: 1.05 }} className="card">
                        <h2>{completed}</h2>
                        <p>Completed</p>
                    </motion.div>

                    <motion.div whileHover={{ scale: 1.05 }} className="card">
                        <h2>{tasks.length - completed}</h2>
                        <p>Pending</p>
                    </motion.div>
                </div>

                {/* TASK LIST */}
                <div className="task-list">
                    {tasks.map((t) => (
                        <motion.div
                            key={t.id}
                            className="task"
                            initial={{ opacity: 0, y: 20 }}
                            animate={{ opacity: 1, y: 0 }}
                        >
              <span
                  className={t.completed ? "done" : ""}
                  onClick={() => toggle(t)}
              >
                {t.title}
              </span>

                            <button onClick={() => remove(t.id)}>✕</button>
                        </motion.div>
                    ))}
                </div>
            </div>
        </div>
    );
}