

# Server: Active Users Tracker API

---

## Tech Stack
- **Kotlin**, **Ktor**
- **Session-based authentication**
- **Server-Sent Events (SSE)** for real-time updates
- **Docker** containerized deployment

---

## Features
- Manages active user sessions
- Provides SSE-based active user count
- Handles session expiration and cleanup

---

## Endpoints
- **POST** `/login`: Authenticate users.
- **POST** `/logout`: Log users out.
- **SSE** `/active-users-stream`: Stream active user updates.

---

## Configuration
Adjust environment-specific settings in [application.yaml](./src/main/resources/application.yaml)


| **Parameter**            | **Purpose**                                           | **Example**  | **Explanation**                                                                                                                        |
|---------------------------|-------------------------------------------------------|--------------|----------------------------------------------------------------------------------------------------------------------------------------|
| `ktor.session.maxAgeMs`   | Configure the validity of a session                  | `1800000`    | Specifies the maximum lifetime of a session in milliseconds. Example: `1800000` means sessions expire after 30 minutes.                |
| `ktor.cleanup.intervalMs` | Configure the interval when the session cleaner runs | `60000`      | Defines how often (in milliseconds) the session cleaner will execute to remove expired sessions. Example: `60000` runs every 1 minute. |


---

## Local Development

### Install Dependencies
- Install **Gradle** (7.6+).

### Run Locally
```bash
  ./gradlew run
```

API available at: [http://localhost:8080](http://localhost:8080).

---

## Build and Run with Docker

### Build the Docker Image
```bash
  docker build -t server-ktor .
```

### Run the Container
```bash
  docker run -p 8080:8080 server-ktor
```
