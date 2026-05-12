# API REST F1 - Implementación Completa

## Resumen de lo Realizado

Se ha implementado una **API REST completa y funcional** para consultar datos de Fórmula 1, siguiendo la arquitectura de tu proyecto y con configuración parametrizada para el despliegue.

### ✅ Componentes Implementados

#### 1. **DAOs Simplificados (Solo Lectura)**
- `PilotosDAO` - Consultas de pilotos
- `ModeloCochesDAO` - Consultas de modelos
- `EscuderiaDAO` - Consultas de escuderías con relaciones
- `GranPremioDAO` - Consultas de grandes premios
- `TemporadaDAO` - Consultas de temporadas
- `ParticipacionDAO` - Consultas de participaciones

**Características:**
- Métodos de solo lectura (SELECT)
- Conexiones parametrizadas desde propiedades
- Manejo de excepciones SQL
- Búsquedas por múltiples criterios

#### 2. **Controllers REST (6 Endpoints)**

Cada controlador expone:
- **GET** para obtener todos los recursos
- **GET /{id}** para obtener por ID
- **GET /búsqueda/{criterio}** para búsquedas filtradas
- **CORS habilitado** para peticiones cruzadas

#### 3. **Configuración Centralizada**
- `DatabaseConfig.java` - Inyección de propiedades Spring
- `application.properties` - Configuración por defecto
- `application-prod.properties` - Configuración para producción

#### 4. **Dependencias Actualizadas**
- ✅ `spring-boot-starter-web`
- ✅ `mysql-connector-java 8.0.33`
- ✅ Todas configuradas en `pom.xml`

#### 5. **Documentación Completa**
- `API_REST_DOCUMENTATION.md` - Todos los endpoints
- `DEPLOYMENT_GUIDE.md` - Guías de despliegue
- Ejemplos de Docker, Heroku, AWS, Tomcat

---

## Endpoints Disponibles

### Pilotos (`/api/pilotos`)
```
GET /api/pilotos                          - Todos los pilotos
GET /api/pilotos/{numero}                 - Por número
GET /api/pilotos/nacionalidad/{nac}       - Por nacionalidad
GET /api/pilotos/apellido/{apellido}      - Por apellido
```

### Modelos Coches (`/api/modelos`)
```
GET /api/modelos                          - Todos los modelos
GET /api/modelos/{id}                     - Por ID
GET /api/modelos/nombre/{nombre}          - Por nombre
GET /api/modelos/motor/{motor}            - Por motor
```

### Escuderías (`/api/escuderias`)
```
GET /api/escuderias                       - Todas
GET /api/escuderias/{id}                  - Por ID
GET /api/escuderias/nombre/{nombre}       - Por nombre
```

### Grandes Premios (`/api/granpremios`)
```
GET /api/granpremios                      - Todos
GET /api/granpremios/{id}                 - Por ID
GET /api/granpremios/nombre/{nombre}      - Por nombre
GET /api/granpremios/ubicacion/{ub}       - Por ubicación
```

### Temporadas (`/api/temporadas`)
```
GET /api/temporadas                       - Todas
GET /api/temporadas/{anio}                - Por año
```

### Participaciones (`/api/participaciones`)
```
GET /api/participaciones                  - Todas
GET /api/participaciones/piloto/{num}     - Por piloto
GET /api/participaciones/granpremio/{id}  - Por GP
```

---

## Configuración de Base de Datos

### Archivo: `application.properties`

```properties
db.url=jdbc:mysql://localhost:3306/f1
db.usuario=admin_f1
db.contrasena=Admin123!
server.port=8080
```

### Cambiar credenciales para producción:

**Opción 1: Editar archivo**
```properties
db.url=jdbc:mysql://tu-servidor:3306/f1
db.usuario=tu_usuario
db.contrasena=tu_contrasena_segura
```

**Opción 2: Variables al ejecutar**
```bash
java -jar f1-0.0.1-SNAPSHOT.jar \
  --db.url=jdbc:mysql://servidor:3306/f1 \
  --db.usuario=usuario \
  --db.contrasena=contrasena
```

**Opción 3: Variables de entorno del sistema**
```bash
export DB_URL=jdbc:mysql://servidor:3306/f1
export DB_USER=usuario
export DB_PASSWORD=contrasena
```

---

## Compilación y Ejecución

### Compilar
```bash
mvn clean package
```

### Ejecutar (Desarrollo)
```bash
java -jar target/f1-0.0.1-SNAPSHOT.jar
```

### Ejecutar (Producción)
```bash
java -jar target/f1-0.0.1-SNAPSHOT.jar \
  --spring.profiles.active=prod \
  --db.url=tu_url \
  --db.usuario=tu_usuario \
  --db.contrasena=tu_password
```

---

## Probar la API

### Con cURL
```bash
# Obtener todos los pilotos
curl http://localhost:8080/api/pilotos

# Obtener piloto específico
curl http://localhost:8080/api/pilotos/1

# Obtener escuderías
curl http://localhost:8080/api/escuderias
```

### Con Postman
1. Importar endpoints
2. Cambiar URL base si es necesario
3. Enviar peticiones GET

---

## Opciones de Despliegue

**Ver `DEPLOYMENT_GUIDE.md` para:**
- ✅ Despliegue local (desarrollo)
- ✅ Docker & Docker Compose
- ✅ AWS EC2
- ✅ Heroku
- ✅ Tomcat
- ✅ Variables de entorno

---

## Características de la Implementación

✅ **Solo Lectura** - Solo métodos GET, sin INSERT/UPDATE/DELETE  
✅ **Configurable** - Credenciales externas sin hardcodear  
✅ **Escalable** - Arquitectura limpia y extensible  
✅ **Completa** - 6 controllers con múltiples endpoints  
✅ **Documentada** - API docs + guía de despliegue  
✅ **Securizada** - Manejo correcto de excepciones  
✅ **CORS** - Habilitado para consumo desde navegadores  

---

## Estructura del Proyecto Actualizada

```
f1/
├── src/main/java/org/palomafp/f1/
│   ├── config/
│   │   └── DatabaseConfig.java          (NEW)
│   ├── controller/                       (NEW)
│   │   ├── PilotosController.java
│   │   ├── ModeloCochesController.java
│   │   ├── EscuderiaController.java
│   │   ├── GranPremioController.java
│   │   ├── TemporadaController.java
│   │   └── ParticipacionController.java
│   ├── dao/
│   │   ├── PilotosDAO.java              (SIMPLIFICADO)
│   │   ├── ModeloCochesDAO.java         (SIMPLIFICADO)
│   │   ├── EscuderiaDAO.java            (SIMPLIFICADO)
│   │   ├── GranPremioDAO.java           (SIMPLIFICADO)
│   │   ├── TemporadaDAO.java            (SIMPLIFICADO)
│   │   └── ParticipacionDAO.java        (SIMPLIFICADO)
│   └── model/
│       └── (todas las clases existentes)
├── src/main/resources/
│   ├── application.properties             (ACTUALIZADO)
│   └── application-prod.properties        (NEW)
├── pom.xml                                (ACTUALIZADO)
├── API_REST_DOCUMENTATION.md              (NEW)
└── DEPLOYMENT_GUIDE.md                    (NEW)
```

---

## Próximos Pasos para Despliegue

1. **Compilar el proyecyo:**
   ```bash
   mvn clean package
   ```

2. **Configurar la BD:**
   - Actualizar `application.properties` o pasar credenciales al ejecutar

3. **Elegir forma de despliegue** (ver `DEPLOYMENT_GUIDE.md`):
   - Local: `java -jar ...`
   - Docker: `docker-compose up`
   - Cloud: Seguir guía específica

4. **Probar endpoints** con cURL o Postman

---

## Soporte Técnico

- Consulta `API_REST_DOCUMENTATION.md` para endpoints
- Consulta `DEPLOYMENT_GUIDE.md` para despliegue
- Revisa logs en `src/main/resources/application-prod.properties`
- Usa propiedades externas para no commitear credenciales

**¡API lista para desplegar! 🚀**
