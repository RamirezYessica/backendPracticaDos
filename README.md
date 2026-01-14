# Backend - Carrito de Compras

Este proyecto corresponde al **backend** de una aplicación de carrito de compras simple.
Expone una API REST que permite listar productos, agregar productos a un carrito, modificar cantidades y calcular el total del carrito.

El carrito se maneja **en memoria**, identificado por un `sessionId`, con fines educativos y de demostración.

## Tecnologías utilizadas

- Java 25
- Spring Boot 3
- Spring Web
- Jakarta Validation
- Springdoc OpenAPI (Swagger UI)
- Maven

## Arquitectura

El backend está organizado siguiendo una arquitectura por capas:

- **Controller**: maneja las peticiones HTTP
- **Service**: contiene la lógica de negocio
- **DTO**: objetos de transferencia de datos
- **Model**: modelos internos de la aplicación

Toda la lógica del carrito se encuentra en la capa de servicio.

---

## Funcionalidades

- Listar productos disponibles
- Obtener el detalle de un producto
- Agregar productos al carrito
- Incrementar y decrementar cantidades
- Eliminar un producto del carrito
- Calcular el total del carrito
- Validación de datos de entrada

---

## Endpoints disponibles

### Productos

- `GET /api/products`  
  Devuelve la lista de productos disponibles.

- `GET /api/products/{id}`  
  Devuelve el detalle de un producto por ID.

---

### Carrito

`POST /api/cart/items`  
Agrega un producto al carrito.

`GET /api/cart/{sessionId}`
Devuelve los productos del carrito asociados a la sesión.

`DELETE /api/cart/{sessionId}/items/{productId}`
Quita una unidad del producto del carrito.
Si la cantidad llega a 0, el producto se elimina del carrito.

`DELETE /api/cart/{sessionId}/items/{productId}/all`
Elimina todas las unidades del producto del carrito.

`GET /api/cart/{sessionId}/total`
Devuelve el total del carrito, calculado en el backend.

## Validaciones

Las peticiones para agregar productos al carrito se validan utilizando Jakarta Validation:
- sessionId no puede ser vacío
- productId no puede ser nulo
- quantity debe ser mayor o igual a 1

Si alguna validación falla, la petición es rechazada automáticamente antes de llegar a la lógica de negocio.

## Cómo ejecutar el proyecto
- Clonar el repositorio
- Abrir el proyecto en tu IDE (IntelliJ o Eclipse)
- Asegurarse de tener Java 25 instalado
- Ejecutar la aplicación con:
    mvn spring-boot:run
- La aplicación iniciara en:
    http://localhost:8080


