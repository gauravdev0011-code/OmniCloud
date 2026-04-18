# OmniCloud

A real-time task management application built using Java Spring Boot and WebSocket.

---

## Features

* Create, update, and delete tasks
* Real-time updates (no refresh needed)
* Clean UI
* REST API backend

---

## Tech Stack

**Backend**

* Java 17
* Spring Boot
* Spring Web
* Spring Data JPA
* Hibernate
* H2 Database
* Maven

**Frontend**

* HTML
* CSS
* JavaScript

**Real-time**

* WebSocket

---

## How to Run

### Backend

```
cd backend
mvn spring-boot:run
```

Runs on:

```
http://localhost:8080
```

---

### Frontend

Open:

```
frontend/index.html
```

---

## How It Works

* Open frontend in 2 tabs
* Add or edit task in one tab
* Changes appear instantly in other tab

---

## API

* GET /api/tasks
* POST /api/tasks
* PUT /api/tasks/{id}
* DELETE /api/tasks/{id}

---

## WebSocket

```
ws://localhost:8080/ws/tasks
```

---

## Future Improvements

* Authentication (JWT)
* PostgreSQL database
* React frontend

---

## Author

Gaurav Dev
