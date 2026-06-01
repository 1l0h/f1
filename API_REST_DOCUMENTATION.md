# API REST F1 - Documentación Completa

Documentación técnica de todos los endpoints disponibles en la API REST de Fórmula 1.

---

## 🌐 Base URL

```
http://localhost:8080/f1
```

En producción (EC2): Reemplaza `localhost` con la IP o DNS de tu instancia EC2.

---

## 📚 Endpoints por Recurso

### 1. PILOTOS (`/f1/pilotos`)

#### 1.1 Obtener todos los pilotos
```
GET /f1/pilotos
```
**Descripción:** Devuelve lista completa de todos los pilotos en la BD.

**Ejemplo:**
```bash
curl -X GET http://localhost:8080/f1/pilotos
```

**Respuesta (200 OK):**
```json
[
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
]
```

---

#### 1.2 Obtener piloto por número
```
GET /f1/pilotos/{numero}
```
**Parámetros:**
- `numero` (path): Número de piloto (int)

**Ejemplo:**
```bash
curl -X GET http://localhost:8080/f1/pilotos/1
```

---

#### 1.3 Obtener pilotos por nacionalidad
```
GET /f1/pilotos/nacionalidad/{nacionalidad}
```

**Ejemplo:**
```bash
curl -X GET http://localhost:8080/f1/pilotos/nacionalidad/Británica
```

---

#### 1.4 Obtener pilotos por apellido
```
GET /f1/pilotos/apellido/{apellido}
```

**Ejemplo:**
```bash
curl -X GET http://localhost:8080/f1/pilotos/apellido/Hamilton
```

---

### 2. ESCUDERÍAS (`/f1/escuderias`)

#### 2.1 Obtener todas las escuderías
```
GET /f1/escuderias
```

**Ejemplo:**
```bash
curl -X GET http://localhost:8080/f1/escuderias
```

---

#### 2.2 Obtener escudería por ID
```
GET /f1/escuderias/{id}
```

**Ejemplo:**
```bash
curl -X GET http://localhost:8080/f1/escuderias/1
```

---

#### 2.3 Obtener escudería por nombre
```
GET /f1/escuderias/nombre/{nombre}
```

**Ejemplo:**
```bash
curl -X GET http://localhost:8080/f1/escuderias/nombre/McLaren
```

---

### 3. GRANDES PREMIOS (`/f1/granpremios`)

#### 3.1 Obtener todos los GPs
```
GET /f1/granpremios
```

**Ejemplo:**
```bash
curl -X GET http://localhost:8080/f1/granpremios
```

---

#### 3.2 Obtener GP por ID
```
GET /f1/granpremios/{id}
```

**Ejemplo:**
```bash
curl -X GET http://localhost:8080/f1/granpremios/1
```

---

#### 3.3 Obtener GP por nombre
```
GET /f1/granpremios/nombre/{nombre}
```

**Ejemplo:**
```bash
curl -X GET "http://localhost:8080/f1/granpremios/nombre/Bahrain%20Grand%20Prix"
```

---

#### 3.4 Obtener GPs por ubicación
```
GET /f1/granpremios/ubicacion/{ubicacion}
```

**Ejemplo:**
```bash
curl -X GET http://localhost:8080/f1/granpremios/ubicacion/Bahréin
```

---

### 4. MODELOS DE COCHES (`/f1/modelos`)

#### 4.1 Obtener todos los modelos
```
GET /f1/modelos
```

---

#### 4.2 Obtener modelo por ID
```
GET /f1/modelos/{id}
```

**Ejemplo:**
```bash
curl -X GET http://localhost:8080/f1/modelos/1
```

---

#### 4.3 Obtener modelos por nombre
```
GET /f1/modelos/nombre/{nombre}
```

**Ejemplo:**
```bash
curl -X GET http://localhost:8080/f1/modelos/nombre/McLaren
```

---

#### 4.4 Obtener modelos por motor
```
GET /f1/modelos/motor/{motor}
```

**Ejemplo:**
```bash
curl -X GET http://localhost:8080/f1/modelos/motor/Mercedes
```

---

### 5. PARTICIPACIONES (`/f1/participaciones`)

#### 5.1 Obtener todas las participaciones
```
GET /f1/participaciones
```

---

#### 5.2 Obtener participaciones de un piloto
```
GET /f1/participaciones/piloto/{numero}
```

**Ejemplo:**
```bash
curl -X GET http://localhost:8080/f1/participaciones/piloto/1
```

---

#### 5.3 Obtener participaciones en un GP
```
GET /f1/participaciones/granpremio/{id}
```

**Ejemplo:**
```bash
curl -X GET http://localhost:8080/f1/participaciones/granpremio/1
```

---

### 6. TEMPORADAS (`/f1/temporadas`)

#### 6.1 Obtener todas las temporadas
```
GET /f1/temporadas
```

---

#### 6.2 Obtener temporada por año
```
GET /f1/temporadas/{anio}
```

**Ejemplo:**
```bash
curl -X GET http://localhost:8080/f1/temporadas/2026
```

---

## 🎯 Códigos de Respuesta HTTP

| Código | Significado |
|--------|------------|
| 200 | OK - Solicitud exitosa |
| 404 | Not Found - Recurso no encontrado |
| 500 | Internal Server Error - Error del servidor |

---

## 🔄 CORS

Todas las respuestas incluyen CORS habilitado:
```
Access-Control-Allow-Origin: *
Content-Type: application/json
```

---

## 🧪 Ejemplos Completos

### JavaScript (Fetch API)
```javascript
// Obtener todos los pilotos
fetch('http://localhost:8080/f1/pilotos')
  .then(response => response.json())
  .then(data => console.log(data));

// Obtener piloto específico
fetch('http://localhost:8080/f1/pilotos/1')
  .then(response => response.json())
  .then(data => console.log(data));
```

### Python (Requests)
```python
import requests

response = requests.get('http://localhost:8080/f1/pilotos')
print(response.json())
```

### cURL
```bash
# Obtener todos los pilotos
curl http://localhost:8080/f1/pilotos

# Con formato pretty JSON
curl http://localhost:8080/f1/pilotos | jq
```

---

**Para más información sobre configuración y despliegue, consulta [README.md](README.md) y [DATABASE_CONFIGURATION.md](DATABASE_CONFIGURATION.md).**
