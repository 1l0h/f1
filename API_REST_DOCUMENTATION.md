# API REST F1 - Documentación

## Descripción
API REST completa para consultar datos de Fórmula 1: Pilotos, Escuderías, Grandes Premios, Modelos de Coches, Temporadas y Participaciones.

## Configuración

### Variables de Entorno (application.properties)
```properties
spring.application.name=f1
server.port=8080

# Base de datos MySQL
db.url=jdbc:mysql://localhost:3306/f1
db.usuario=admin_f1
db.contrasena=Admin123!

# Logging
logging.level.org.springframework.web=INFO
logging.level.org.palomafp.f1=DEBUG
```

## Endpoints

### Pilotos (`/api/pilotos`)

#### Obtener todos los pilotos
```
GET /api/pilotos
```
**Respuesta:** Array de todos los pilotos en la BD

#### Obtener piloto por número
```
GET /api/pilotos/{numero}
```
**Ejemplo:** `GET /api/pilotos/1`

#### Obtener pilotos por nacionalidad
```
GET /api/pilotos/nacionalidad/{nacionalidad}
```
**Ejemplo:** `GET /api/pilotos/nacionalidad/España`

#### Obtener pilotos por apellido
```
GET /api/pilotos/apellido/{apellido}
```
**Ejemplo:** `GET /api/pilotos/apellido/Hamilton`

---

### Modelos de Coches (`/api/modelos`)

#### Obtener todos los modelos
```
GET /api/modelos
```

#### Obtener modelo por ID
```
GET /api/modelos/{id}
```
**Ejemplo:** `GET /api/modelos/1`

#### Obtener modelos por nombre
```
GET /api/modelos/nombre/{nombre}
```
**Ejemplo:** `GET /api/modelos/nombre/F1-2024`

#### Obtener modelos por motor
```
GET /api/modelos/motor/{motor}
```
**Ejemplo:** `GET /api/modelos/motor/V6`

---

### Escuderías (`/api/escuderias`)

#### Obtener todas las escuderías
```
GET /api/escuderias
```
*Incluye pilotos y modelo de coche asociado*

#### Obtener escudería por ID
```
GET /api/escuderias/{id}
```
**Ejemplo:** `GET /api/escuderias/1`

#### Obtener escudería por nombre
```
GET /api/escuderias/nombre/{nombre}
```
**Ejemplo:** `GET /api/escuderias/nombre/Mercedes`

---

### Grandes Premios (`/api/granpremios`)

#### Obtener todos los GPs
```
GET /api/granpremios
```
*Incluye participaciones ordenadas por posición*

#### Obtener GP por ID
```
GET /api/granpremios/{id}
```
**Ejemplo:** `GET /api/granpremios/1`

#### Obtener GP por nombre
```
GET /api/granpremios/nombre/{nombre}
```
**Ejemplo:** `GET /api/granpremios/nombre/Gran Premio de Mónaco`

#### Obtener GPs por ubicación
```
GET /api/granpremios/ubicacion/{ubicacion}
```
**Ejemplo:** `GET /api/granpremios/ubicacion/Mónaco`

---

### Temporadas (`/api/temporadas`)

#### Obtener todas las temporadas
```
GET /api/temporadas
```
*Incluye carreras, escuderías y piloto ganador*

#### Obtener temporada por año
```
GET /api/temporadas/{anio}
```
**Ejemplo:** `GET /api/temporadas/2024`

---

### Participaciones (`/api/participaciones`)

#### Obtener todas las participaciones
```
GET /api/participaciones
```

#### Obtener participaciones de un piloto
```
GET /api/participaciones/piloto/{numero}
```
**Ejemplo:** `GET /api/participaciones/piloto/1`

#### Obtener participaciones de un GP
```
GET /api/participaciones/granpremio/{id}
```
**Ejemplo:** `GET /api/participaciones/granpremio/1`

---

## Respuesta de Error

```json
{
  "status": 500,
  "message": "Internal Server Error"
}
```

## Códigos HTTP

- `200 OK` - Petición exitosa
- `201 Created` - Recurso creado
- `404 Not Found` - Recurso no encontrado
- `500 Internal Server Error` - Error en el servidor

## CORS

La API tiene CORS habilitado para todas las rutas, permitiendo peticiones desde cualquier origen.

## Ejemplo de Uso (cURL)

```bash
# Obtener todos los pilotos
curl -X GET http://localhost:8080/api/pilotos

# Obtener piloto específico
curl -X GET http://localhost:8080/api/pilotos/1

# Obtener escuderías
curl -X GET http://localhost:8080/api/escuderias

# Obtener GPs por ubicación
curl -X GET http://localhost:8080/api/granpremios/ubicacion/España
```

## Construcción y Despliegue

### Compilar
```bash
mvn clean package
```

### Ejecutar
```bash
java -jar target/f1-0.0.1-SNAPSHOT.jar
```

### Con propiedades externas
```bash
java -jar target/f1-0.0.1-SNAPSHOT.jar \
  --db.url=jdbc:mysql://tu-host:3306/f1 \
  --db.usuario=tu_usuario \
  --db.contrasena=tu_contrasena \
  --server.port=8080
```

## Dependencias

- Spring Boot 4.0.6
- MySQL Connector 8.0.33
- Java 21
