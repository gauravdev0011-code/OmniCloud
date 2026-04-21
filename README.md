# 🚀 OmniCloud — Real-Time Task Management Dashboard

OmniCloud is a **full-stack real-time task management system** built with modern technologies.
It features a **production-style dashboard UI**, **Spring Boot backend**, and **WebSocket-powered live updates**.

---

## ✨ Features

* ⚡ Real-time updates using WebSockets (STOMP + SockJS)
* 📊 Interactive dashboard with task statistics
* ✅ Create, update, delete, and toggle tasks
* 🔄 Live sync across multiple browser tabs
* 🎨 Modern glassmorphism UI (React + CSS)
* 📡 REST API + WebSocket hybrid architecture

---

## 🧱 Tech Stack

### Frontend

* React (Vite)
* Framer Motion (animations)
* STOMP.js + SockJS (WebSocket client)
* CSS (Glassmorphism UI)

### Backend

* Java + Spring Boot
* Spring Web
* Spring Data JPA
* Spring WebSocket (STOMP)
* H2 / MySQL (configurable)

---

## 🏗️ Architecture

Client ↔ REST API ↔ Database
    ↘ WebSocket (STOMP) ↙

* REST handles CRUD operations
* WebSocket pushes real-time updates
* Frontend subscribes to `/topic/tasks`

---

## 📂 Project Structure

```
OmniCloud/
│
├── backend/
│   ├── controllers/
│   ├── services/
│   ├── repository/
│   ├── models/
│   ├── websocket/
│   └── OmniCloudApplication.java
│
├── frontend-react/
│   ├── src/
│   │   ├── App.jsx
│   │   ├── index.css
│   │   └── main.jsx
│   └── public/
│       └── bg.jpg
│
└── README.md
```

---

## ⚙️ Setup Instructions

### 🔹 1. Clone Repository

```
git clone https://github.com/YOUR_USERNAME/OmniCloud.git
cd OmniCloud
```

---

### 🔹 2. Run Backend

```
cd backend
mvn clean install
mvn spring-boot:run
```

Server runs at:

```
http://localhost:8080
```

---

### 🔹 3. Run Frontend

```
cd frontend-react
npm install
npm run dev
```

Frontend runs at:

```
http://localhost:5173
```

---

## 🔌 API Endpoints

| Method | Endpoint        | Description   |
| ------ | --------------- | ------------- |
| GET    | /api/tasks      | Get all tasks |
| POST   | /api/tasks      | Create task   |
| PUT    | /api/tasks/{id} | Update task   |
| DELETE | /api/tasks/{id} | Delete task   |

---

## 🔄 WebSocket

### Endpoint:

```
/ws
```

### Subscription:

```
/topic/tasks
```

### Behavior:

* On create/update/delete → backend broadcasts event
* Frontend listens → automatically refreshes UI

---

## 🧠 Key Concepts Demonstrated

* Event-driven architecture
* Real-time communication (WebSockets)
* REST + WebSocket hybrid design
* State synchronization across clients
* Clean backend layering (Controller → Service → Repository)

---

## 📸 UI Preview


---

## 🚀 Future Improvements

* User authentication (JWT)
* Task categories & filters
* Drag & drop task management
* Persistent database (PostgreSQL)
* Deployment (Docker + Cloud)

---

## 🧑‍💻 Author

Gaurav Dev

---

## ⭐ Why This Project Matters

This project demonstrates:

* Full-stack engineering capability
* Real-time systems design
* Production-ready architecture thinking

---

## 📌 License

MIT License
