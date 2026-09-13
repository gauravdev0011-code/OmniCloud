import { useCallback, useEffect, useState } from "react";
import "./index.css";
import { motion } from "framer-motion";

const API = "http://localhost:8080/api/tasks";
const MotionDiv = motion.div;

export default function App() {
    const [tasks, setTasks] = useState([]);
    const [text, setText] = useState("");

    const fetchTasks = useCallback(async () => {
        const res = await fetch(API);
        if (!res.ok) {
            throw new Error(`Failed to fetch tasks: ${res.status}`);
        }
        const data = await res.json();
        setTasks(data);
    }, []);

    useEffect(() => {
        const initialLoad = setTimeout(() => {
            fetchTasks().catch(console.error);
        }, 0);

        const socket = new WebSocket("ws://localhost:8080/ws");

        socket.onmessage = () => {
            fetchTasks().catch(console.error);
        };

        return () => {
            clearTimeout(initialLoad);
            socket.close();
        };
    }, [fetchTasks]);

    const addTask = async () => {
        if (!text.trim()) return;

        const res = await fetch(API, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ title: text.trim() }),
        });

        if (!res.ok) {
            throw new Error(`Failed to create task: ${res.status}`);
        }

        setText("");
        await fetchTasks();
    };

    const toggle = async (task) => {
        const res = await fetch(`${API}/${task.id}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                ...task,
                completed: !task.completed,
            }),
        });

        if (!res.ok) {
            throw new Error(`Failed to update task: ${res.status}`);
        }

        await fetchTasks();
    };

    const remove = async (id) => {
        const res = await fetch(`${API}/${id}`, { method: "DELETE" });
        if (!res.ok) {
            throw new Error(`Failed to delete task: ${res.status}`);
        }
        await fetchTasks();
    };

    const completed = tasks.filter((task) => task.completed).length;

    return (
        <div className="app">
            <div className="bg"></div>

            <div className="sidebar">
                <h2>OmniCloud</h2>
                <p>01 Dashboard</p>
                <p>02 Tasks</p>
                <p>03 Analytics</p>
                <p>04 Team</p>
            </div>

            <div className="main">
                <h1 className="title">
                    OmniCloud<span>.</span>
                </h1>

                <p className="subtitle">
                    Organize your tasks. Maximize your potential.
                </p>

                <div className="input-box">
                    <input
                        value={text}
                        onChange={(event) => setText(event.target.value)}
                        placeholder="What needs to be done?"
                    />
                    <button onClick={addTask}>Add Task +</button>
                </div>

                <div className="cards">
                    <MotionDiv whileHover={{ scale: 1.05 }} className="card">
                        <h2>{tasks.length}</h2>
                        <p>Tasks</p>
                    </MotionDiv>

                    <MotionDiv whileHover={{ scale: 1.05 }} className="card">
                        <h2>{completed}</h2>
                        <p>Completed</p>
                    </MotionDiv>

                    <MotionDiv whileHover={{ scale: 1.05 }} className="card">
                        <h2>{tasks.length - completed}</h2>
                        <p>Pending</p>
                    </MotionDiv>
                </div>

                <div className="task-list">
                    {tasks.map((task) => (
                        <MotionDiv
                            key={task.id}
                            className="task"
                            initial={{ opacity: 0, y: 20 }}
                            animate={{ opacity: 1, y: 0 }}
                        >
                            <span
                                className={task.completed ? "done" : ""}
                                onClick={() => toggle(task)}
                            >
                                {task.title}
                            </span>

                            <button onClick={() => remove(task.id)}>✕</button>
                        </MotionDiv>
                    ))}
                </div>
            </div>
        </div>
    );
}
