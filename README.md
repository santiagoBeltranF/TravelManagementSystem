# Travel Management System

![Travel Management System](https://img.shields.io/badge/Project-Travel%20Management%20System-brightgreen)

## Descripción
Travel Management System es una aplicación de microservicios diseñada para gestionar todos los aspectos relacionados con los viajes. Permite a los usuarios buscar y reservar vuelos, alojamientos y servicios adicionales de manera sencilla y eficiente. Cada componente del sistema está especializado en una función específica, garantizando una experiencia fluida y escalable.

### Funcionalidades
- ✈️ **Gestión de Usuarios**: Registro, inicio de sesión y administración de perfiles de usuario.
- 🛏️ **Reservas**: Los usuarios pueden crear, modificar y cancelar reservas de vuelos y alojamiento.
- 📅 **Consulta de Vuelos**: Acceso a información en tiempo real sobre vuelos disponibles y precios.
- 🌍 **Servicios de Alojamiento**: Búsqueda y reserva de opciones de alojamiento en diversas ubicaciones.
- 🔒 **Autenticación Segura**: Integración con Keycloak para una gestión de identidad robusta y segura.
- 📩 **Notificaciones**: Actualizaciones y alertas sobre cambios en reservas y promociones.

## Tecnologías
- **Java** ![Java](https://img.shields.io/badge/Java-ED8B00?style=flat&logo=java&logoColor=white)
- **Spring Boot** ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=flat&logo=spring&logoColor=white)
- **PostgreSQL** ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-336791?style=flat&logo=postgresql&logoColor=white)
- **Docker** ![Docker](https://img.shields.io/badge/Docker-2496ED?style=flat&logo=docker&logoColor=white)
- **Eureka** ![Eureka](https://img.shields.io/badge/Eureka-FFA500?style=flat&logo=java&logoColor=white)
- **RabbitMQ** ![RabbitMQ](https://img.shields.io/badge/RabbitMQ-FF6600?style=flat&logo=rabbitmq&logoColor=white)
- **Keycloak** ![Keycloak](https://img.shields.io/badge/Keycloak-FF0000?style=flat&logo=keycloak&logoColor=white)

## Estructura de Servicios
- 👤 **user-service**: Maneja la creación y gestión de cuentas de usuario.
- 🛫 **reservation-service**: Se encarga de las reservas de vuelos y alojamiento.
- ✈️ **airline-service**: Proporciona información sobre aerolíneas y vuelos.
- 🏨 **hosting-service**: Administra los servicios de alojamiento disponibles.
- 🌐 **gateway-service**: Actúa como un punto de entrada unificado para las solicitudes API.
- 🔑 **authentication-service**: Maneja la autenticación y autorización de usuarios.

## Configuración
1. Clona el repositorio:
   ```bash
   git clone https://github.com/santiagoBeltranF/TravelManagementSystem.git
