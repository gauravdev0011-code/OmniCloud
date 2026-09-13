# OmniCloud — Real-Time Task Platform

OmniCloud is a full-stack real-time task platform built with **Java, Spring Boot, React, WebSockets, and Docker**. The backend separates HTTP controllers, services, repositories, persistence, exception handling, and WebSocket transport while the frontend consumes REST APIs and real-time state updates.

[![CI](https://github.com/gauravdev0011-code/OmniCloud/actions/workflows/ci.yml/badge.svg)](https://github.com/gauravdev0011-code/OmniCloud/actions/workflows/ci.yml)

## What it demonstrates

- **Backend architecture:** Controller-Service-Repository separation with Spring Boot and Spring Data JPA.
- **Real-time systems:** task changes are broadcast to connected clients over WebSockets.
- **Concurrency:** the platform is exercised with concurrent API workloads and reports latency percentiles and throughput.
- **Deployment:** the Spring Boot backend is packaged and run as a Docker container.
- **Observability:** a health endpoint plus benchmark metrics expose service behavior under load.

## Resume-Aligned Results

| Metric | Result |
|---|---:|
| Concurrent task workload | **100+ tasks** |
| API latency | **<200 ms** under the measured concurrent workload |
| Real-time transport | **WebSocket** |
| Backend architecture | **Controller → Service → Repository** |
| Deployment | **Docker** |

> The latency figure is a measured result for a specific environment and workload, not a universal performance guarantee. Reproduce the benchmark on the target machine before quoting it independently.

## Architecture

```text
React + Vite
     |
     | HTTP/JSON
     v
Spring Boot REST API ----> Spring Data JPA ----> H2
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
│   ├── mvnw
│   ├── mvnw.cmd
│   └── .mvn/wrapper/
│       └── maven-wrapper.properties
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
bash ./mvnw clean test package
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
npm ci
npm run lint
npm run build
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
| POST | `/api/tasks` | Create tasks |
| PUT | `/api/tasks/{id}` | Update a task |
| DELETE | `/api/tasks/{id}` | Delete a task |
| GET | `/health` | Service health |

## WebSocket Synchronization

The backend exposes `/ws` and broadcasts an event when a task is created, updated, or deleted. Connected React clients listen for those events and refresh task state without requiring a page reload.

The implementation uses the native Spring WebSocket API; the frontend uses the standard browser `WebSocket` connection.

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

## CI

GitHub Actions validates the Java backend and React frontend on every push and pull request.

## Author

Gaurav Dev
