# Configuración de la Base de Datos

## Ubicación del archivo de propiedades

Las credenciales de la base de datos se configuran en:
- **Desarrollo**: `src/main/resources/application.properties`
- **Producción**: `src/main/resources/application-prod.properties` (si está siendo usada por el deployment)

## Variables de configuración

El archivo contiene tres propiedades críticas:

```properties
db.url=jdbc:mysql://[HOST]:[PUERTO]/[NOMBRE_BD]
db.usuario=[USUARIO]
db.contrasena=[CONTRASENA]
```

### Descripción de cada variable:

| Variable | Descripción | Ejemplo |
|----------|------------|---------|
| `db.url` | URL de conexión a MySQL. Incluye el host, puerto y nombre de la base de datos | `jdbc:mysql://localhost:3306/f1` |
| `db.usuario` | Usuario de MySQL que tiene permisos en la BD | `consulta_f1` |
| `db.contrasena` | Contraseña del usuario de MySQL | `Consulta123!` |

## Configuración por entorno

### Desarrollo local (localhost)

```properties
db.url=jdbc:mysql://localhost:3306/f1
db.usuario=consulta_f1
db.contrasena=Consulta123!
```

### Producción en EC2 AWS

Modifica el archivo `application-prod.properties` (o `application.properties` si no usas perfiles):

```properties
db.url=jdbc:mysql://[IP_O_DNS_DE_EC2]:3306/f1
db.usuario=[USUARIO_PRODUCCION]
db.contrasena=[CONTRASEÑA_PRODUCCION]
```

**Ejemplo con IP real:**
```properties
db.url=jdbc:mysql://52.123.45.67:3306/f1
db.usuario=f1_app
db.contrasena=ProduccionPassword123!
```

## Estructura esperada de la Base de Datos

La aplicación espera las siguientes tablas en la BD MySQL:

### Tabla: `pilotos`
```sql
CREATE TABLE pilotos (
    numero INT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    nacionalidad VARCHAR(50),
    fechaNacimiento DATE,
    podios INT DEFAULT 0,
    victorias INT DEFAULT 0,
    campeonatos INT DEFAULT 0,
    poles INT DEFAULT 0
);
```

### Tabla: `granpremios`
```sql
CREATE TABLE granpremios (
    idGp INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(150) NOT NULL,
    ubicacion VARCHAR(100),
    longitud DOUBLE,
    vueltas INT,
    tiempoPromedio VARCHAR(20),
    fechaPrimeraCarrera DATE
);
```

### Tabla: `modelosCoches`
```sql
CREATE TABLE modelosCoches (
    idModelo INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    motor VARCHAR(50),
    potencia INT,
    peso INT,
    velocidadMaxima INT
);
```

### Tabla: `escuderias`
```sql
CREATE TABLE escuderias (
    idEscuderia INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    pais VARCHAR(50),
    directorEquipo VARCHAR(100),
    fechaFundacion DATE,
    campeonatos INT DEFAULT 0,
    victorias INT DEFAULT 0,
    podios INT DEFAULT 0,
    color VARCHAR(20),
    idModelo INT,
    FOREIGN KEY (idModelo) REFERENCES modelosCoches(idModelo)
);
```

### Tabla: `escuderiaPilotos` (relación muchos-a-muchos)
```sql
CREATE TABLE escuderiaPilotos (
    idEscuderia INT,
    numeroPiloto INT,
    PRIMARY KEY (idEscuderia, numeroPiloto),
    FOREIGN KEY (idEscuderia) REFERENCES escuderias(idEscuderia),
    FOREIGN KEY (numeroPiloto) REFERENCES pilotos(numero)
);
```

### Tabla: `participaciones`
```sql
CREATE TABLE participaciones (
    idParticipacion INT PRIMARY KEY AUTO_INCREMENT,
    idGp INT NOT NULL,
    numeroPiloto INT NOT NULL,
    tiempoCarrera VARCHAR(20),
    posicion INT,
    FOREIGN KEY (idGp) REFERENCES granpremios(idGp),
    FOREIGN KEY (numeroPiloto) REFERENCES pilotos(numero)
);
```

### Tabla: `temporadas`
```sql
CREATE TABLE temporadas (
    anio INT PRIMARY KEY,
    numeroPilotoGanador INT,
    FOREIGN KEY (numeroPilotoGanador) REFERENCES pilotos(numero)
);
```

## Pasos para configurar en EC2

1. **Instala MySQL en la EC2** (si no está ya instalado)
   ```bash
   sudo apt update
   sudo apt install mysql-server -y
   ```

2. **Crea la base de datos y carga el script**
   ```bash
   mysql -u root -p < script/script.sql
   ```

3. **Crea un usuario específico para la aplicación**
   ```sql
   CREATE USER 'f1_app'@'%' IDENTIFIED BY 'ProduccionPassword123!';
   GRANT ALL PRIVILEGES ON f1.* TO 'f1_app'@'%';
   FLUSH PRIVILEGES;
   ```

4. **Actualiza `application.properties` antes de desplegar el JAR**
   ```properties
   db.url=jdbc:mysql://localhost:3306/f1
   db.usuario=f1_app
   db.contrasena=ProduccionPassword123!
   ```

5. **Compila y desplega**
   ```bash
   mvn clean package
   java -jar target/f1-0.0.1-SNAPSHOT.jar
   ```

## Verificación de conexión

Para verificar que la aplicación se conecta correctamente a la BD:

1. Inicia la aplicación
2. Realiza una petición GET a uno de los endpoints:
   ```bash
   curl http://localhost:8080/f1/pilotos
   ```
3. Si recibes un JSON con pilotos, significaría está correcta. Si intentalas datos mock localmente, significa que hay un problema de conexión (pero la app aún funciona con modo fallback).

## Modo Fallback (Mock Data)

Si la aplicación no puede conectarse a la BD, automáticamente:
- Imprime un error en los logs
- Carga datos mock locales para que el servicio siga funcionando

Esta es una característica de resiliencia. Verifica los logs para identificar problemas de conexión.
