# ReviewService
Der ReviewService ist eine Spring Boot Anwendung, die Rezensionen für Rezepte verwaltet.

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=THI-CND_review_service&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=THI-CND_review_service)

---

## Architektur

![reviewservice-architecture.png](docs/reviewservice-architecture.png?)

---

## Getting Started
### Dependencies
Das Projekt benötigt die folgenden Abhängigkeiten, um lokal gestartet zu werden:
- Java 17 JDK
- Maven 3+
- Docker
- Docker Compose

### Start
- Projekt lokal bauen:
    ```bash
    mvn clean install
    ```
- Gebautes Projekt lokal starten:
    ```bash
    java -jar target/reviewservice-<version>.jar
    ```
#### Docker
Das Projekt kann auch lokal per Docker gestartet werden.\
Dazu muss im Projektordner der folgende Befehl ausgeführt werden:
```bash
docker compose up
```
Neben dem Service wird eine Postgres Datenbank und eine RabbitMQ Message Queue gestartet.
Erreichbar ist der Service anschließend unter Port 8080 (REST) und 9090 (gRPC).

### Profile
#### Dev
Im `dev`-Profil wird die Datenbank bei jedem Start geleert.

#### Test
Das `test`-Profil wird zum Ausführen der Tests verwendet.\
Es wird eine lokale H2-Datenbank gestartet, die für die Tests verwendet wird.

---

### Environment Variables
- `DB_URL`: JDBC-URL der Datenbank
- `DB_USER`: Benutzername für die Datenbank
- `DB_PASSWORD`: Passwort für die Datenbank
- `RABBIT_HOST`: Hostname des RabbitMQ Brokers
- `RABBIT_PORT`: Port des RabbitMQ Brokers
- `RABBIT_USER`: Benutzername für RabbitMQ
- `RABBIT_PASSWORD`: Passwort für RabbitMQ
- `RABBIT_EXCHANGE`: Exchange Name für Veröffentlichung der Events in RabbitMQ
- `USER_SERVICE_ADDRESS`: Adresse des User-Service

---

## APIs
### REST
#### GET /api/v1/reviews
Gibt alle Rezensionen zurück.

**Request Parameter:**
- recipeId: Filtert die Rezensionen nach der Rezept-ID (optional).

**Response:**
- Status: `200 OK`
    ```json
    [
      {
        "id": 1,
        "recipeId": "123e4567-e89b-12d3-a456-426614174000",
        "author": "user1",
        "rating": 4.5,
        "comment": "Sehr lecker!"
      },
      ...
    ]
    ```

#### GET /api/v1/reviews/{id}
Gibt die Rezension mit der ID {id} zurück.

**Response:**
- Status: `200 OK`
    ```json
    {
      "id": 1,
      "recipeId": "123e4567-e89b-12d3-a456-426614174000",
      "author": "user1",
      "rating": 4.5,
      "comment": "Sehr lecker!"
    }
    ```
- Status: `404 Not Found`

#### POST /api/v1/reviews
Erstellt eine neue Rezension.

**Request Body:**

```json
{
  "recipeId": "123e4567-e89b-12d3-a456-426614174000",
  "author": "user1",
  "rating": 4.5,
  "comment": "Sehr lecker!"
}
```
**Response:**
- Status: `201 Created`
    ```json
    {
      "id": 1,
      "recipeId": "123e4567-e89b-12d3-a456-426614174000",
      "author": "user1",
      "rating": 4.5,
      "comment": "Sehr lecker!"
    }
    ```

#### PUT /api/v1/reviews/{id}
Aktualisiert die Rezension mit der ID {id}.

**Request Body:**

```json
{
  "recipeId": "123e4567-e89b-12d3-a456-426614174000",
  "author": "user1",
  "rating": 4.5,
  "comment": "Sehr lecker!"
}
```

**Response:**
- Status: `200 OK`
    ```json
    {
      "id": 1,
      "recipeId": "123e4567-e89b-12d3-a456-426614174000",
      "author": "user1",
      "rating": 4.5,
      "comment": "Sehr lecker!"
    }
    ```
- Status: `404 Not Found`

#### DELETE /api/v1/reviews/{id}
Löscht die Rezension mit der ID {id}.

**Response:**
- Status: `204 No Content`
- Status: `404 Not Found`

### gRPC
```protobuf
syntax = "proto3";

package de.thi.cnd.reviewservice;

option java_multiple_files = true;
option java_package = "de.thi.cnd.review";
option java_outer_classname = "ReviewProto";

service ReviewService {
  rpc CreateReview(CreateReviewRequest) returns (ReviewResponse);
  rpc GetReviews(Empty) returns (ReviewsResponse);
  rpc GetReview(ReviewIdRequest) returns (ReviewResponse);
  rpc UpdateReview(UpdateReviewRequest) returns (ReviewResponse);
  rpc DeleteReview(ReviewIdRequest) returns (Empty);
}

message Empty {
}

message ReviewsResponse {
  repeated ReviewResponse reviews = 1;
}

message ReviewResponse {
  int64 id = 1;
  string recipeId = 2;
  string author = 3;
  float rating = 4;
  string comment = 5;
}

message ReviewIdRequest {
  int64 id = 1;
}

message CreateReviewRequest {
  string recipeId = 1;
  string author = 2;
  float rating = 3;
  string comment = 4;
}

message UpdateReviewRequest {
  int64 id = 1;
  string recipeId = 2;
  string author = 3;
  float rating = 4;
  string comment = 5;
}
```

---

## Events
### reviews.created

**Payload:**
```json
{
  "id": 1,
  "recipeId": "123e4567-e89b-12d3-a456-426614174000",
  "author": "user1",
  "rating": 4.5,
  "comment": "Sehr lecker!"
}
```
