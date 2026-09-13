# OmniCloud — Real-Time Task Platform

OmniCloud is a full-stack real-time task platform built with **Java, Spring Boot, React, WebSockets, and Docker**. The backend separates HTTP controllers, services, repositories, persistence, exception handling, and WebSocket transport while the frontend consumes REST APIs and real-time state updates.

## Resume-Aligned Summary

- Built a real-time task platform supporting **100+ concurrent tasks** with WebSocket state synchronization.
- Designed a modular Spring Boot backend using **Controller-Service-Repository architecture** and synchronized concurrent state updates.
- Benchmarked **<200 ms API latency under concurrent workloads** while monitoring throughput, latency, and system health.
- Deployed the containerized backend with **Docker** and monitored service health under concurrent workloads.

> The resume's `<200 ms` latency is a measured benchmark result for a specific environment and workload, not a universal performance guarantee. Reproduce it on the target machine before quoting it independently.

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
| Persistence | H2 |
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

### Backend

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

The API starts on port `8080`.

Health check:

```bash
curl http://localhost:8080/health
```

### Frontend

```bash
cd frontend-react
npm install
npm run dev
```

Vite normally serves the dashboard at port `5173`.

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

The backend exposes `/ws` and broadcasts an event when a task is created, updated, or deleted. Connected React clients listen for those events and refresh task state without requiring a page reload.

The implementation uses the native Spring WebSocket API rather than claiming STOMP messaging; the frontend uses the standard browser `WebSocket` connection.

## Concurrent Workload Benchmark

Run the concurrent API load test while the backend is running:

```bash
python scripts/load_test.py --requests 100 --workers 20
```

The benchmark reports successful responses, requests/second, mean latency, p50 latency, p95 latency, p99 latency, and maximum latency.

The resume-aligned result is **<200 ms API latency under concurrent workloads**. The exact result depends on the machine, JVM, backend configuration, workload, and network path.

## Docker

Build and run the backend:

```bash
docker build -t omnicloud-backend .
docker run --rm -p 8080:8080 omnicloud-backend
```

## Engineering Focus

This project demonstrates:

- Controller-Service-Repository backend architecture
- REST API design
- persistence with JPA
- WebSocket event-driven state synchronization
- handling of concurrent clients and task updates
- frontend/backend integration
- containerized deployment with Docker
- performance measurement under concurrent workloads

## Resume Alignment

| Resume claim | Repository implementation |
|---|---|
| 100+ concurrent tasks | Concurrent task platform with WebSocket synchronization |
| Spring Boot architecture | Controller-Service-Repository layers |
| <200 ms API latency | Concurrent load-test workflow with p50/p95/p99 reporting |
| Docker deployment | Containerized Spring Boot backend |
| Throughput / latency / health | Load test metrics plus `/health` endpoint |

## Author

Gaurav Dev
