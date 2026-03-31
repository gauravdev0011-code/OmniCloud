# OmniCloud — Distributed Real-Time Collaboration Platform

OmniCloud is a reactive Java backend designed for real-time multi-user collaboration systems similar to Google Docs or Notion backends.

## 🚀 Features

- Reactive REST APIs using Spring WebFlux
- Real-time task updates via Server-Sent Events
- Multi-user collaboration architecture
- CRDT-based conflict resolution foundation
- PostgreSQL reactive database (R2DBC)
- Docker-ready deployment
- Modular microservice-ready structure

## 🧱 Tech Stack

- Java 17
- Spring Boot + WebFlux
- PostgreSQL (R2DBC)
- Reactive Streams (Flux / Mono)
- Docker
- GitHub CI-ready structure

## 📡 Architecture

Client → REST API → Reactive Service Layer → Database  
↘ Real-Time Event Stream

## ▶ Run Locally

```bash
./mvnw spring-boot:run