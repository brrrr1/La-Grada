# La Grada - API
![Logo La Grada](https://github.com/user-attachments/assets/92d38f74-acd4-4d58-805c-b2683bb3a525)
## Descripción

La API de *La Grada* proporciona funcionalidades para gestionar eventos deportivos, equipos, usuarios y membresías en una plataforma de entretenimiento inmersivo. Permite la autenticación de usuarios y administradores, la gestión de eventos y equipos, la compra de entradas y la administración de membresías. Los usuarios pueden registrarse, seleccionar un equipo favorito, comprar entradas para eventos deportivos y consultar los próximos eventos de su equipo preferido. Además, pueden editar su información personal y gestionar su cuenta.

## Requisitos

- Docker y Docker Compose instalados en el sistema.
- Configuración adecuada de credenciales de correo en `application-sec.properties`.

## Instalación y Ejecución

Para ejecutar la API, usa el siguiente comando desde la carpeta raíz del proyecto:

```sh
 docker-compose up --build
```

La API se ejecutará en el puerto `8080`.

## Configuración

En el archivo `application-sec.properties`, se deben configurar las credenciales de correo:

```
spring.mail.username=TU_CORREO
spring.mail.password=TU_CONTRASEÑA_DE_APP
```

## Documentación

La documentación de la API está disponible mediante Swagger en:

```
http://localhost:8080/swagger-ui.html
```

## Funcionalidades Principales

### Funcionalidades sin autenticación
- **Registro de usuario** (`POST /auth/register`)
- **Verificación por mail** (`POST /activate/account`)
- **Inicio de sesión** (`POST /auth/login`)
- **Ver los próximos eventos** (`GET /evento/proximos`)
- **Ver eventos por equipo** (`GET /evento/equipo/{nombreEquipo}`)
- **Obtener detalles de un equipo** (`GET /equipo/{equipoId}`)
- **Obtener todos los equipos** (`GET /equipo/`)
- **Comprobar disponibilidad de username** (GET /auth/check-username)
- **Comprobar disponibilidad de email** (GET /auth/check-email)
- **Ver todos los eventos sin filtrar** (GET /evento/todos)
- **Ver próximos eventos cotidianos** (GET /evento/proximos/cotidianos)
- **Ver próximos eventos importantes** (GET /evento/proximos/importantes)
- **Ver próximos eventos finales** (GET /evento/proximos/finales)

### Funcionalidades para usuarios autenticados
- **Refrescar token** (`POST /auth/refresh/token`)
- **Cerrar sesión** (`POST /auth/logout`)
- **Obtener información del usuario** (`GET /me`)
- **Editar información personal** (`PUT /user/edit-info`)
- **Cambiar contraseña** (`PUT /user/edit-password`)
- **Seleccionar equipo favorito** (`POST /user/choose-favorite-team/{equipoId}`)
- **Cambiar equipo favorito** (`POST /user/change-favorite-team/{equipoId}`)
- **Comprar entradas para un evento** (`POST /user/buy-ticket/{eventoId}`)
- **Ver próximos eventos de su equipo favorito** (`GET /user/favorite-team-events`)
- **Ver eventos futuros para los que tiene entrada** (`GET /user/eventos-futuros`)
- **Ver eventos a los que ya ha asistido** (`GET /user/eventos-pasados`)
- **Quitar equipo favorito** (DELETE /user/remove-favorite-team)
- **Ver entradas futuras con QR** (GET /user/entradas/futuras)
- **Ver entradas pasadas con QR** (GET /user/entradas/pasadas)

### Funcionalidades para administradores
- **Crear equipo** (`POST /equipo/`)
- **Obtener todos los equipos** (`GET /equipo/`)
- **Crear evento** (`POST /evento/`)
- **Editar evento** (`PUT /evento/{eventoId}`)
- **Eliminar evento** (`DELETE /evento/{eventoId}`)
- **Buscar usuarios** (`GET /search?search=nombre:...,apellidos:...,correo:...`)
- **Subir archivo** (`POST /upload`)
- **Descargar archivo** (`GET /download/{idFile}`)
- **Actualizar equipo** (PUT /equipo/{id})
- **Obtener lista de todos los usuarios** (GET /admin/users)
- **Desactivar usuario** (POST /admin/users/disable)
- **Habilitar usuario** (POST /admin/users/enable)

## Notas

- Se requiere autenticación con tokens Bearer en la mayoría de los endpoints.
- La API diferencia entre usuarios normales y administradores, proporcionando permisos adecuados a cada tipo de cuenta.


## Figma
El diseño de la aplicación está disponible en el siguiente enlace: [Diseño en Figma](https://www.figma.com/design/0kE1LwhCQsRSM8IFBq5KqO/LA-GRADA---M%C3%93VIL?node-id=0-1&t=ara9SiTSo5zwMQGD-1)

![App](https://github.com/user-attachments/assets/d9f8a0cc-53d7-4ba6-a4d9-dcac41bb2f87)
![App](https://github.com/user-attachments/assets/fb496557-47ea-41b6-9418-63113c7daaad)
![App](https://github.com/user-attachments/assets/79d07642-0f08-415e-882e-79484dceb8cc)

![VIVE EL DEPORTE COMO NUNCA ANTES](https://github.com/user-attachments/assets/e4fea73b-fa87-4994-a83f-c8b6322771ff)


