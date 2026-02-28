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
