# F1 API REST - Fórmula 1

API REST completa para consultar datos de Fórmula 1: Pilotos, Escuderías, Grandes Premios, Modelos de Coches, Temporadas y Participaciones.

---

## 🚀 Inicio Rápido

### Requisitos
- Java 21+
- Maven 3.8+
- MySQL 8.0+ (para conexión a BD real)

### Ejecutar Localmente

```bash
# Clonar y navegar al proyecto
git clone <tu-repo>
cd f1

# Compilar
mvn clean package

# Ejecutar
java -jar target/f1-0.0.1-SNAPSHOT.jar
```

La API estará disponible en: **http://localhost:8080**

---

## 📡 API REST - Endpoints

### Base URL
```
http://localhost:8080/f1
```

### 1. **Pilotos** (`/f1/pilotos`)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/f1/pilotos` | Obtiene todos los pilotos |
| GET | `/f1/pilotos/{numero}` | Obtiene piloto por número |
| GET | `/f1/pilotos/nacionalidad/{nacionalidad}` | Filtra por nacionalidad |
| GET | `/f1/pilotos/apellido/{apellido}` | Busca por apellido |

**Ejemplo:**
```bash
curl http://localhost:8080/f1/pilotos
curl http://localhost:8080/f1/pilotos/1
curl http://localhost:8080/f1/pilotos/nacionalidad/Británica
```

**Respuesta:**
```json
{
  "numero": 1,
  "nombre": "Lando",
  "apellido": "Norris",
  "nacionalidad": "Británica",
  "fechaNacimiento": "1999-11-13",
  "podios": 42,
  "victorias": 11,
  "campeonatos": 1,
  "poles": 15
}
```

---

### 2. **Escuderías** (`/f1/escuderias`)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/f1/escuderias` | Obtiene todas las escuderías |
| GET | `/f1/escuderias/{id}` | Obtiene escudería por ID |
| GET | `/f1/escuderias/nombre/{nombre}` | Busca por nombre |

**Ejemplo:**
```bash
curl http://localhost:8080/f1/escuderias
curl http://localhost:8080/f1/escuderias/1
curl http://localhost:8080/f1/escuderias/nombre/McLaren
```

**Respuesta:**
```json
{
  "idEscuderia": 1,
  "nombre": "McLaren",
  "pais": "Reino Unido",
  "directorEquipo": "Andrea Stella",
  "fechaFundacion": "1966-01-01",
  "campeonatos": 9,
  "victorias": 13,
  "podios": 195,
  "color": "FF8000",
  "pilotos": [...]
}
```

---

### 3. **Grandes Premios** (`/f1/granpremios`)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/f1/granpremios` | Obtiene todos los GPs |
| GET | `/f1/granpremios/{id}` | Obtiene GP por ID |
| GET | `/f1/granpremios/nombre/{nombre}` | Busca por nombre |
| GET | `/f1/granpremios/ubicacion/{ubicacion}` | Filtra por ubicación |

**Ejemplo:**
```bash
curl http://localhost:8080/f1/granpremios
curl http://localhost:8080/f1/granpremios/1
curl http://localhost:8080/f1/granpremios/ubicacion/Bahréin
```

**Respuesta:**
```json
{
  "idGp": 1,
  "nombre": "Bahrain Grand Prix",
  "ubicacion": "Bahréin",
  "longitud": 5.412,
  "vueltas": 57,
  "tiempoPromedio": "01:31:12",
  "fechaPrimeraCarrera": "2004-01-01",
  "participaciones": [...]
}
```

---

### 4. **Modelos de Coches** (`/f1/modelos`)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/f1/modelos` | Obtiene todos los modelos |
| GET | `/f1/modelos/{id}` | Obtiene modelo por ID |
| GET | `/f1/modelos/nombre/{nombre}` | Busca por nombre |
| GET | `/f1/modelos/motor/{motor}` | Filtra por motor |

**Ejemplo:**
```bash
curl http://localhost:8080/f1/modelos
curl http://localhost:8080/f1/modelos/1
curl http://localhost:8080/f1/modelos/motor/Mercedes
```

**Respuesta:**
```json
{
  "idModelo": 1,
  "nombre": "McLaren MCL38",
  "motor": "Mercedes",
  "potencia": 900,
  "peso": 340,
  "velocidadMaxima": 798
}
```

---

### 5. **Participaciones** (`/f1/participaciones`)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/f1/participaciones` | Obtiene todas las participaciones |
| GET | `/f1/participaciones/piloto/{numero}` | Participaciones de un piloto |
| GET | `/f1/participaciones/granpremio/{id}` | Participaciones en un GP |

**Ejemplo:**
```bash
curl http://localhost:8080/f1/participaciones
curl http://localhost:8080/f1/participaciones/piloto/1
curl http://localhost:8080/f1/participaciones/granpremio/1
```

---

### 6. **Temporadas** (`/f1/temporadas`)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/f1/temporadas` | Obtiene todas las temporadas |
| GET | `/f1/temporadas/{anio}` | Obtiene temporada por año |

**Ejemplo:**
```bash
curl http://localhost:8080/f1/temporadas
curl http://localhost:8080/f1/temporadas/2026
```

**Respuesta:**
```json
{
  "anio": 2026,
  "carreras": [...],
  "escuderias": [...],
  "pilotoGanador": {...}
}
```

---

## ⚙️ Configuración

### Variables de Base de Datos

Edita `src/main/resources/application.properties`:

```properties
# Conexión
db.url=jdbc:mysql://localhost:3306/f1
db.usuario=consulta_f1
db.contrasena=Consulta123!

# Puerto
server.port=8080

# Logging
logging.level.org.springframework.web=INFO
logging.level.org.palomafp.f1=DEBUG
```

### Modo Offline (Mock Data)

Si no tienes BD disponible, la aplicación **automáticamente** usa datos mock locales. Los errores de conexión aparecerán en logs pero el servicio funciona.

---

## 🐳 Despliegue

### En EC2 AWS

1. **Compilar JAR:**
   ```bash
   mvn clean package
   ```

2. **Actualizar credenciales en `application.properties`:**
   ```properties
   db.url=jdbc:mysql://[IP_EC2]:3306/f1
   db.usuario=f1_app
   db.contrasena=ProduccionPassword123!
   ```

3. **Ejecutar en EC2:**
   ```bash
   nohup java -jar f1-0.0.1-SNAPSHOT.jar > nohup.out 2>&1 &
   ```

4. **Ver logs:**
   ```bash
   tail -f nohup.out
   ```

### Con GitHub Actions

Push a la rama `main`. El workflow automáticamente:
1. Compila el JAR
2. Lo sube a EC2
3. Reinicia la aplicación

---

## 🗄️ Estructura de Base de Datos

Consulta [DATABASE_CONFIGURATION.md](DATABASE_CONFIGURATION.md) para:
- Scripts SQL para crear tablas
- Estructura de relaciones
- Configuración de usuarios MySQL

---

## 📊 Ejemplos de Uso

### JavaScript/Fetch
```javascript
fetch('http://localhost:8080/f1/pilotos')
  .then(res => res.json())
  .then(data => console.log(data));
```

### Python/Requests
```python
import requests
response = requests.get('http://localhost:8080/f1/pilotos')
print(response.json())
```

### cURL
```bash
curl -X GET http://localhost:8080/f1/pilotos
curl -X GET http://localhost:8080/f1/pilotos/1
```

---

## 🔍 Troubleshooting

| Problema | Solución |
|----------|----------|
| Puerto 8080 en uso | Cambiar en `application.properties`: `server.port=8081` |
| BD no conecta | Verificar credenciales en `application.properties` y que MySQL esté corriendo |
| Errores 404 | Verificar que la URL comience con `/f1` |
| CORS errors | Ya está habilitado con `@CrossOrigin(origins = "*")` |

---

## 📝 Estructura del Proyecto

```
f1/
├── src/main/java/org/palomafp/f1/
│   ├── controller/          # REST endpoints
│   ├── dao/                 # Acceso a datos
│   ├── model/               # Entidades
│   └── config/              # Configuración
├── src/main/resources/
│   ├── application.properties
│   └── application-prod.properties
├── .github/workflows/
│   └── maven.yml            # CI/CD
└── pom.xml                  # Dependencias
```

---

## 📄 Licencia

Proyecto F1 - 2026

