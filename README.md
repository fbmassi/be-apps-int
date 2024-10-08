# E-Commerce Web Application - Backend

![](Imagenes/LogoUADE.svg)

Este proyecto corresponde a la primera entrega del Trabajo Práctico Obligatorio (TPO) de la materia **Aplicaciones Interactivas** del segundo cuatrimestre de 2024, desarrollado en el marco de la carrera Ingeniería Informática de la UADE. Se trata del desarrollo del backend de una aplicación de e-commerce, implementada utilizando **Java Spring Boot**. La aplicación tiene como objetivo cubrir los requerimientos académicos solicitados en la cursada, por lo que no responde a una necesidad comercial real, sino que se enmarca en un contexto académico.

## Integrantes
- Álvarez Tomás
- Gluck Matías
- Massi Franco
- Medina Tobias

## Fecha de Presentación
Martes 24/9/2024

## Tecnologías utilizadas

El stack tecnológico empleado para el backend incluye:

- **Java Spring Boot**: Framework para el desarrollo de la API REST, utilizando una arquitectura basada en microservicios.
- **JPA (Java Persistence API)**: Se utiliza para el mapeo objeto-relacional y la interacción con la base de datos relacional.
- **CRUD Repository**: Para facilitar las operaciones de creación, lectura, actualización y eliminación (Create, Read, Update, Delete) en la base de datos.
- **JWT (JSON Web Token)**: Implementado para gestionar la autenticación y autorización de usuarios de manera segura, permitiendo proteger las rutas de acceso a la API.
- **MySQL**: Base de datos relacional utilizada para almacenar la información estructurada de productos, usuarios y compras.

**Repositorio Frontend:** https://github.com/17sTomy/fe-apps-int

## Requerimientos

La aplicación cumple con los siguientes casos de uso:

### Gestión de Usuarios:
- **Registro de usuarios**: Permite registrar usuarios solicitando nombre de usuario, email, contraseña, fecha de nacimiento, nombre y apellido.
- **Login**: Los usuarios pueden autenticarse con su email o nombre de usuario y contraseña, obteniendo un JWT que les permite operar en el sistema.

### Catálogo de Productos:
- **Visualización de productos**: Los usuarios autenticados pueden acceder a la home de la plataforma, donde se muestra un listado de productos destacados, productos por categoría, y productos recientemente vistos por el usuario.
- **Detalle de productos**: Los usuarios pueden ver una descripción detallada de cada producto con imagen ampliada, precio, stock disponible e información adicional. Si el producto está sin stock, se notificará al usuario y no podrá añadirlo al carrito. También pueden añadir productos a su lista de favoritos.

### Carrito de Compras:
- **Gestión del carrito**: Los usuarios pueden añadir productos al carrito, vaciarlo o eliminarlos. Al realizar el checkout, se calculará el costo total de los productos y se validará que haya stock disponible. Si no hay stock, se notificará al usuario.
- **Descuento de stock**: Tras un checkout exitoso, se descontará el stock correspondiente a los productos comprados.

### Gestión de Productos:
- **Publicación de productos**: Un usuario administrador puede dar de alta un producto nuevo adjuntando fotos, descripción y la categoría a la que pertenece.
- **Gestión del stock**: Los productos creados pueden gestionarse modificando su stock o eliminándolos.

### Mi Perfil:
- **Sección Mi Perfil**: Los usuarios pueden acceder a la sección "Mi Perfil" para ver su información personal y los checkouts realizados con la fecha de las transacciones exitosas.

## Casos de uso adicionales implementados:
Además de los requerimientos solicitados, se implementaron las siguientes funcionalidades extra:

- **Motor de Recomendación de Productos**: Basado en el género, década y director de productos (películas), así como en los productos favoritos del usuario.
- **Admin Requests**: Un módulo adicional donde los administradores pueden gestionar solicitudes específicas.
- **Sección de Reviews**: Los usuarios pueden dejar reseñas y calificar los productos comprados.

## Instalación y uso

1. Clonar este repositorio.
2. Configurar las credenciales de la base de datos en el archivo `application.properties`.
3. Ejecutar la aplicación mediante el comando:
    ```
    mvn spring-boot:run
    ```
4. Acceder a la API a través de `http://localhost:8080/`.

## Swagger
Se puede acceder mediante el siguiente link: http://localhost:8080/swagger-ui/index.html

## Contribuciones
Este proyecto fue realizado en el marco de la materia **Aplicaciones Interactivas** como parte de la carrera de **Ingeniería en Informática**.
