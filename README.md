# Spring Boot y Redis

Este proyecto es una aplicación de ejemplo que utiliza Spring Boot y Redis para almacenar y recuperar datos.

## Ejecución

Para compilar el proyecto, puedes usar el siguiente comando:
```bash
./mvnw clean compile
```

Para ejecutar los tests, puedes usar el siguiente comando:
```bash
./mvnw test
```

Para ejecutar el proyecto, puedes usar el siguiente comando:
```bash
./mvnw spring-boot:run
```

## Configuración de Redis

Para ejecutar Redis, puedes usar Docker con el siguiente comando:
```bash
docker run -d --name redis-server -p 6379:6379 redis
```

## Docker Compose 

Para ejecutar la aplicación y Redis juntos, puedes usar Docker Compose con el siguiente comando:
```bash
docker-compose up -d
```

## Endpoints

La aplicación expone los siguientes endpoints:

- `GET /api/v1/users?page=0&size=10&name=&status=`: Obtiene una lista paginada de usuarios, con opciones de filtrado por nombre y estado.
- `POST /api/v1/users`: Crea un nuevo usuario.
- `GET /api/v1/users/{id}`: Obtiene un usuario por su ID.
- `PUT /api/v1/users/{id}`: Actualiza un usuario existente por su ID.
- `DELETE /api/v1/users/{id}`: Elimina un usuario por su ID.

