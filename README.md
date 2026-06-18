# CRUD - Sistema de Gestión de Activos TI

Este proyecto es una aplicación web Full-Stack desarrollada como prueba técnica para la gestión de inventario básico de equipos de TI. Está diseñado de manera limpia, escalable y modular, aplicando buenas prácticas de desarrollo de software.

## Stack Tecnológico

- **Frontend:** React (Vite), Axios, Tailwind CSS.
- **Backend:** Java (JDK 17+), Spring Boot 3, Spring Data JPA, Jakarta Validation.
- **Base de Datos:** PostgreSQL.
- **Virtualización:** Docker & Docker Compose.
- **Gestor de Dependencias:** `pnpm`.

---

## Requisitos Previos

Antes de ejecutar el proyecto, asegúrate de tener instalado lo siguiente en tu sistema:

1. **Node.js** (versión 18 o superior) junto con **pnpm**:
   ```bash
   npm install -g pnpm
   ```
2. **Java JDK** (versión 17 o superior) instalado y configurado en tus variables de entorno (`JAVA_HOME`).
3. **Docker Desktop** en ejecución.

---

## Instrucciones de Ejecución

Para iniciar todo el ecosistema (Base de Datos, Backend y Frontend) con un único comando, sigue estos pasos desde la raíz del proyecto:

1. **Instalar dependencias del Frontend:**
   ```bash
   pnpm install:all
   ```
2. **Iniciar todos los servicios:**
   ```bash
   pnpm dev
   ```

### ¿Qué hace este comando?
- Levanta un contenedor de **PostgreSQL** mediante `docker compose up`.
- Compila y arranca el servidor de **Spring Boot** en el puerto `8080` (utilizando el Maven Wrapper incluido, por lo que no requieres tener Maven instalado globalmente).
- Inicia el servidor de desarrollo de **Vite** para el frontend en el puerto `5173`.

Una vez listos los servicios, abre tu navegador e ingresa a:
 **[http://localhost:5173](http://localhost:5173)**

---

## Pruebas de la API (Backend)

Si deseas probar o evaluar los endpoints de la API REST directamente sin utilizar el frontend, tienes dos opciones recomendadas:

### Opción 1: Extensión REST Client (VS Code) - Súper Recomendado
1. Instala la extensión **REST Client** de Huachao Mao en tu VS Code.
2. Abre el archivo `pruebas_api.http` que se encuentra en la raíz del proyecto.
3. Verás un enlace interactivo que dice `Send Request` arriba de cada petición. Haz clic en él para ejecutar la petición y ver la respuesta formateada al instante dentro de tu editor.

### Opción 2: Consola / Terminal (cURL)
Puedes ejecutar comandos cURL en tu terminal mientras el proyecto esté en ejecución. Por ejemplo, para listar los equipos:
```bash
curl -i http://localhost:8080/api/equipos
```
*(Puedes encontrar todos los comandos cURL documentados para probar el CRUD completo en el archivo `pruebas_api.http` o en la documentación técnica).*

---

## Decisiones de Arquitectura y Buenas Prácticas

### Backend (Spring Boot)
- **Diseño en Capas:** Se utiliza el patrón clásico `Controller` -> `Service` -> `Repository` -> `Entity`, garantizando una clara separación de responsabilidades.
- **Validación de Datos:** Se implementan anotaciones de validación (`@NotBlank`, `@Pattern`, etc.) a nivel de entidad para asegurar la integridad de los datos.
- **Integridad de Clave Única:** El backend valida que el número de serie de cada equipo sea único en la base de datos antes de proceder con el registro.
- **Manejador Global de Excepciones:** Implementación de `@RestControllerAdvice` para capturar errores de validación y excepciones de negocio, retornando respuestas limpias y estructuradas en formato JSON al cliente.

### Frontend (React)
- **Componentización:** Formulario (`EquipoForm.jsx`) y tabla de listado (`EquipoList.jsx`) desacoplados.
- **Buscador en Tiempo Real:** Filtrado interactivo por tipo, marca y número de serie a través de estados en React.
- **Control de Flujo de Edición:** Limpieza inteligente del estado al cancelar o eliminar un elemento que se encuentra actualmente en edición.
