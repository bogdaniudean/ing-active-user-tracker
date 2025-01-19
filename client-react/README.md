# Client: Active Users Tracker UI

---

## Tech Stack
- **React** with **TypeScript**
- **MUI** for UI components
- **Vite** as the build tool
- **Axios** for API communication
- **Server-Sent Events (SSE)** for real-time updates
- **Docker** containerized deployment

---

## Features
- Displays live active user count
- Provides a login/logout interface
- Environment-based API configuration

---

## Configuration

Adjust environment-specific settings in 
- [.env.loc](./.env.loc) for local builds
- [.env.prod](./.env.prod) for production builds


| **Variable**         | **Purpose**                            | **Example Value**           | **Explanation**                                                                                       |
|-----------------------|----------------------------------------|-----------------------------|-------------------------------------------------------------------------------------------------------|
| `VITE_API_ENDPOINT`   | Configure the backend API endpoint    | `http://localhost:8080`     | Specifies the base URL of the backend API for the client to communicate with.                        |

---

## Local Development

### Install Dependencies
- Install **Node.js** (18+).

### Run Locally
```bash
  npm install && npm run dev
```

Client available at: http://localhost:5173

## Build and Run with Docker

### Build the Docker Image

```bash
    docker build -t client-react .
```

### Run the Container

```bash
  docker run -p 3000:80 client-react
```

Client available at: http://localhost:3000
