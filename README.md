# Best Travel API (spring-travel)

API REST para gestión de viajes: vuelos, hoteles, tours, reservas, tickets y reportes. Desarrollada con **Spring Boot 4** y **Java 21**.

## Tecnologías

- **Spring Boot 4.0.1** – Web MVC, Data JPA, Validation, Mail, Cache
- **PostgreSQL** – Datos transaccionales (vuelos, hoteles, clientes, reservas, tickets, tours)
- **MongoDB** – Usuarios de la aplicación y roles (autenticación)
- **Redis** – Caché (Redisson)
- **OAuth2** – Authorization Server + Resource Server (JWT)
- **SpringDoc OpenAPI** – Documentación Swagger
- **Apache POI** – Exportación de reportes Excel
- **MapStruct** – Mapeo DTO/entidades
- **Lombok** – Reducción de boilerplate

## Requisitos

- JDK 21
- Maven 3.9+
- Docker y Docker Compose (para bases de datos)

## Variables de entorno

Crea un archivo `.env` o configura estas variables antes de ejecutar:

| Variable       | Descripción              | Ejemplo   |
|----------------|--------------------------|-----------|
| `USER_DB`      | Usuario PostgreSQL/Mongo | `jsaire`  |
| `PASSWORD_DB`  | Contraseña BD            | `test123` |
| `USER_EMAIL`   | Usuario SMTP (ej. Gmail)  | tu@email  |
| `PASSWORD_EMAIL` | Contraseña/App password  | ***       |
| `API-KEY`      | API key para divisas     | (apilayer)|

## Ejecución con Docker

Levanta PostgreSQL, Redis y MongoDB:

```bash
docker compose up -d
```

La base `best_travel` y los datos iniciales se crean con los scripts en `sql/`. Los usuarios de la app se cargan en MongoDB desde `sql/mongo/init-mongo.js`.

## Ejecución de la aplicación

```bash
# Con Maven
./mvnw spring-boot:run

# O compilando y ejecutando el JAR
./mvnw clean package
java -jar target/spring-travel-0.0.1-SNAPSHOT.jar
```

Asegúrate de tener definidas las variables de entorno (`USER_DB`, `PASSWORD_DB`, etc.).

## Base URL y documentación

- **Context path:** `/spring-travel`
- **Prefijo API:** `/api/v1`
- **Base URL completa:** `http://localhost:8080/spring-travel/api/v1`

Endpoints principales:

| Recurso     | Path base   | Acceso (ejemplo)      |
|------------|-------------|------------------------|
| Vuelos     | `/fly`      | Público                |
| Hoteles    | `/hotel`    | Público                |
| Tours      | `/tour`     | Autenticado            |
| Tickets    | `/ticket`   | Autenticado            |
| Reservas   | `/reservation` | Autenticado        |
| Usuarios   | `/user`     | Rol admin              |
| Reportes   | `/report`   | Rol admin              |

- **Swagger UI:** `http://localhost:8080/spring-travel/swagger-ui.html`
- **OpenAPI JSON:** `http://localhost:8080/spring-travel/v3/api-docs`

## Seguridad (OAuth2)

La API usa OAuth2 con servidor de autorización embebido y JWT. La configuración del cliente está en `configs/client_security.properties`. Para acceder a recursos protegidos:

1. Obtener token (Authorization Code o Client Credentials) contra el authorization server.
2. Enviar el JWT en el header: `Authorization: Bearer <token>`.

Los recursos `/fly/**` y `/hotel/**` son públicos; el resto requiere autenticación o rol según la configuración en `SecurityConfig`.

## Estructura del proyecto

```
src/main/java/pe/jsaire/springtravel/
├── configs/          # OpenAPI, propiedades, Redis, WebClient
├── controllers/      # REST (Fly, Hotel, Tour, Ticket, Reservation, User, Report)
├── mappers/          # MapStruct DTO ↔ Entity
├── models/
│   ├── dto/          # request / response
│   └── entity/       # JPA (PostgreSQL) y documents (MongoDB)
├── repositories/     # JPA y MongoDB
├── securities/       # SecurityConfig, OAuth2, JWT
├── services/         # Lógica de negocio e implementaciones
└── utils/            # Constantes, fechas, excepciones, enums
```

## Tests

```bash
./mvnw test
```

## Licencia

Uso educativo / proyecto personal.
