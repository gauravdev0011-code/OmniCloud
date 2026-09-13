# OmniCloud — Real-Time Task Platform

OmniCloud is a full-stack task-management platform built around a Spring Boot REST API, JPA persistence, a React frontend, and WebSocket-based state synchronization.

The project is intentionally structured as a small production-style system rather than a single-page CRUD demo: the backend separates controllers, services, repositories, models, exception handling, and WebSocket transport, while the frontend consumes the REST API and refreshes state when real-time events arrive.

## Architecture

```text
React + Vite
     |
     | HTTP/JSON
     v
Spring Boot REST API ----> JPA/H2
     |
     | WebSocket events
     v
TaskWebSocketHandler
```

### Backend layers

- **Controller:** HTTP endpoints under `/api/tasks`
- **Service:** task lifecycle and persistence orchestration
- **Repository:** Spring Data JPA persistence
- **WebSocket handler:** broadcasts task changes to connected clients
- **Health endpoint:** `/health` for service checks

### Frontend

- React + Vite
- Framer Motion
- REST API integration
- Native browser WebSocket client

## Features

- Create, update, complete, and delete tasks
- Persistent task state through Spring Data JPA
- Real-time task-change notifications over WebSockets
- Live task statistics in the React dashboard
- Health endpoint for service monitoring
- Dockerized Spring Boot backend
- Concurrent API load-test utility reporting throughput and p50/p95/p99 latency

## Tech Stack

| Layer | Technology |
|---|---|
| Backend | Java 17, Spring Boot 3.2, Spring Web, Spring Data JPA |
| Persistence | H2 (development/runtime default) |
| Real-time | Spring WebSocket |
| Frontend | React, Vite, Framer Motion |
| Build | Maven, npm |
| Deployment | Docker |
| Testing/benchmarking | Spring Boot Test, Python standard-library load test |

## Repository Layout

```text
OmniCloud/
├── backend/
│   ├── pom.xml
│   └── src/main/java/com/omnicloud/
│       ├── controllers/
│       ├── exceptions/
│       ├── models/
│       ├── repository/
│       ├── services/
│       └── websocket/
├── frontend-react/
│   ├── package.json
│   └── src/
├── scripts/
│   └── load_test.py
├── Dockerfile
└── README.md
```

## Run Locally

### 1. Backend

```bash
cd backend
./mvnw clean test package
java -jar target/backend-1.0.0.jar
```

Windows PowerShell:

```powershell
cd backend
.\mvnw.cmd clean test package
java -jar target\backend-1.0.0.jar
```

The API starts on `http://localhost:8080`.

Health check:

```bash
curl http://localhost:8080/health
```

### 2. Frontend

In another terminal:

```bash
cd frontend-react
npm install
npm run dev
```

Vite normally serves the dashboard at `http://localhost:5173`.

The frontend connects to:

```text
REST:      http://localhost:8080/api/tasks
WebSocket: ws://localhost:8080/ws
```

## API

| Method | Endpoint | Purpose |
|---|---|---|
| GET | `/api/tasks` | List tasks |
| POST | `/api/tasks` | Create a task |
| PUT | `/api/tasks/{id}` | Update a task |
| DELETE | `/api/tasks/{id}` | Delete a task |
| GET | `/health` | Service health |

## WebSocket Synchronization

The backend exposes a WebSocket endpoint at `/ws`. When a task is created, updated, or deleted, the service broadcasts an event to connected clients. The React application listens for those events and refreshes its task state without requiring a page reload.

This is deliberately implemented with the native Spring WebSocket API rather than claiming STOMP messaging; the current frontend uses a standard browser `WebSocket` connection.

## Performance Benchmark

Run the concurrent API load test while the backend is running:

```bash
python scripts/load_test.py --requests 100 --workers 20
```

The benchmark reports:

- successful responses
- requests/second
- mean latency
- p50 latency
- p95 latency
- p99 latency
- maximum latency

Performance numbers are machine- and workload-dependent. The resume's reported `<200 ms` latency should therefore be understood as a measured benchmark result for a specific environment, not as a universal guarantee.

## Docker

Build and run the backend:

```bash
docker build -t omnicloud-backend .
docker run --rm -p 8080:8080 omnicloud-backend
```

The container exposes port `8080`.

## Engineering Focus

The project demonstrates:

- layered Spring Boot backend design
- REST API design
- persistence with JPA
- event-driven state synchronization
- concurrent-client handling
- frontend/backend integration
- containerized deployment
- performance measurement rather than unverified performance claims

## Author

Gaurav Dev
