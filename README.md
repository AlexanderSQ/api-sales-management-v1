# api-sales-management-v1

API REST de gestión de ventas desarrollada con Quarkus, Hibernate Panache, Kafka y PostgreSQL - NTT Data Peru.

## Tecnologías utilizadas

- Java 25
- Quarkus 3.34.3
- Hibernate ORM + Panache (Patrón Repository)
- PostgreSQL 15
- Apache Kafka
- Docker y Docker Compose
- SmallRye OpenAPI / Swagger UI

## Proceso de Negocio

El sistema gestiona el ciclo de ventas:

1. Registro de Clientes
2. Registro de Productos con control de stock
3. Creación de Ventas con sus Detalles
4. Publicación de eventos Kafka al confirmar una venta
5. Consumo de eventos Kafka para procesamiento asíncrono
6. Integración con API externa Platzi para autenticación

## Estructura del Proyecto

src/main/java/com/nttdata/peru/apps/
- client: Clientes REST para APIs externas
- dto: Data Transfer Objects
- entities: Entidades JPA
- repositories: Patrón Repository con Panache
- resources: Endpoints REST
- services: Lógica de negocio

## Endpoints disponibles

### Clientes
- GET /api/clientes
- GET /api/clientes/{id}
- GET /api/clientes/dni/{dni}
- POST /api/clientes
- PUT /api/clientes/{id}
- DELETE /api/clientes/{id}

### Productos
- GET /api/productos
- GET /api/productos/{id}
- GET /api/productos/buscar?nombre=
- GET /api/productos/con-stock
- POST /api/productos
- PUT /api/productos/{id}
- DELETE /api/productos/{id}

### Ventas
- GET /api/ventas
- GET /api/ventas/{id}
- GET /api/ventas/cliente/{clienteId}
- POST /api/ventas
- PUT /api/ventas/{id}/estado?estado=

### Perfil API Externa
- POST /api/perfil/login
- GET /api/perfil/me

## Cómo ejecutar el proyecto

### Requisitos
- Docker Desktop instalado

### Pasos

1. Clonar el repositorio:
   git clone https://github.com/AlexanderSQ/api-sales-management-v1.git

2. Ingresar a la carpeta:
   cd api-sales-management-v1

3. Levantar los servicios:
   docker compose up

4. Acceder al Swagger UI:
   http://localhost:8080/q/swagger-ui

5. Verificar el Health Check:
   http://localhost:8080/q/health

## Kafka

Los eventos de ventas se publican automáticamente al topic sales-topic cuando se crea una venta. El consumer los procesa de forma asíncrona.

## Autor

Alexander Santos - NTT Data Peru