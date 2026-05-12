# Configuración de Despliegue - F1 API REST

## Configuración de Base de Datos

Edita el archivo `src/main/resources/application.properties` con tus credenciales:

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

## Opciones de Despliegue

### 1. Despliegue Local (Desarrollo)

```bash
# Compilar
mvn clean package

# Ejecutar
java -jar target/f1-0.0.1-SNAPSHOT.jar
```

La aplicación estará disponible en: `http://localhost:8080`

### 2. Despliegue con Variables de Entorno (Producción)

```bash
java -jar f1-0.0.1-SNAPSHOT.jar \
  --db.url=jdbc:mysql://mysql-host:3306/f1 \
  --db.usuario=admin_f1 \
  --db.contrasena=tu_contrasena_segura \
  --server.port=8080
```

### 3. Despliegue en Docker

#### Crear archivo `Dockerfile`

```dockerfile
FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/f1-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar", \
  "--db.url=${DB_URL}", \
  "--db.usuario=${DB_USER}", \
  "--db.contrasena=${DB_PASSWORD}", \
  "--server.port=8080"]
```

#### Crear archivo `docker-compose.yml`

```yaml
version: '3.8'

services:
  mysql:
    image: mysql:8.0
    container_name: f1_mysql
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: f1
    ports:
      - "3306:3306"
    volumes:
      - ./script/script.sql:/docker-entrypoint-initdb.d/init.sql
    networks:
      - f1_network

  api:
    build: .
    container_name: f1_api
    environment:
      DB_URL: jdbc:mysql://mysql:3306/f1
      DB_USER: admin_f1
      DB_PASSWORD: Admin123!
    ports:
      - "8080:8080"
    depends_on:
      - mysql
    networks:
      - f1_network

networks:
  f1_network:
    driver: bridge
```

#### Ejecutar con Docker Compose

```bash
docker-compose up
```

### 4. Despliegue en Heroku

#### Crear archivo `Procfile`

```
web: java -Dserver.port=$PORT -Ddb.url=$DB_URL -Ddb.usuario=$DB_USER -Ddb.contrasena=$DB_PASSWORD -jar target/f1-0.0.1-SNAPSHOT.jar
```

#### Configurar variables de entorno en Heroku

```bash
heroku config:set DB_URL=jdbc:mysql://tu-host:3306/f1
heroku config:set DB_USER=admin_f1
heroku config:set DB_PASSWORD=tu_contrasena
```

#### Desplegar

```bash
git push heroku main
```

### 5. Despliegue en AWS EC2

#### 1. Conectar a la instancia

```bash
ssh -i "tu-key.pem" ubuntu@tu-host
```

#### 2. Instalar Java

```bash
sudo apt-get update
sudo apt-get install -y openjdk-21-jdk
```

#### 3. Copiar el JAR

```bash
scp -i "tu-key.pem" target/f1-0.0.1-SNAPSHOT.jar ubuntu@tu-host:/home/ubuntu/
```

#### 4. Ejecutar como servicio

```bash
sudo tee /etc/systemd/system/f1-api.service > /dev/null <<EOF
[Unit]
Description=F1 API Rest Service
After=network.target

[Service]
Type=simple
User=ubuntu
WorkingDirectory=/home/ubuntu
ExecStart=java -jar f1-0.0.1-SNAPSHOT.jar \
  --db.url=jdbc:mysql://tu-db-host:3306/f1 \
  --db.usuario=admin_f1 \
  --db.contrasena=tu_contrasena \
  --server.port=8080
Restart=on-failure

[Install]
WantedBy=multi-user.target
EOF

sudo systemctl daemon-reload
sudo systemctl start f1-api
sudo systemctl enable f1-api
```

### 6. Despliegue en Tomcat

```bash
# Convertir a WAR (requiere cambios en pom.xml)
mvn clean package -DskipTests -Pwar

# Copiar a webapps
cp target/f1-0.0.1-SNAPSHOT.war /path/to/tomcat/webapps/f1.war
```

## Variables de Entorno Recomendadas

| Variable | Ejemplo | Descripción |
|----------|---------|-------------|
| `db.url` | `jdbc:mysql://localhost:3306/f1` | URL de la BD |
| `db.usuario` | `admin_f1` | Usuario de BD |
| `db.contrasena` | `contrasena_segura` | Contraseña de BD |
| `server.port` | `8080` | Puerto del servidor |
| `logging.level.org.springframework.web` | `INFO` | Nivel de log |

## Testing de la API

### Verificar que la API está funcionando

```bash
curl -X GET http://localhost:8080/api/pilotos
```

### Esperado:
- Status: `200 OK`
- Response: Array JSON con pilotos

## Troubleshooting

### ErrorConnecting a BD
```
Error: Unable to connect to database
```
**Solución:** Verifica que:
- La BD está corriendo
- Las credenciales son correctas
- La URL de conexión es válida

### Port Already in Use
```
Error: Address already in use
```
**Solución:** Cambia el puerto en application.properties
```properties
server.port=8081
```

### CORS Error
La API ya tiene CORS habilitado, soporta peticiones desde cualquier origen.

## Monitoreo en Producción

Recomendamos agregar:
- Spring Boot Actuator para monitoreo
- Prometheus para métricas
- ELK Stack para logs centralizados
