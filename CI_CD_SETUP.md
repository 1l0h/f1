# Configuración de CI/CD con GitHub Actions

## 📋 Descripción del Pipeline

Tu pipeline de CI/CD está configurado para:

1. **Build (Automático en cada push/PR)**
   - Compila el código con Maven
   - Ejecuta los tests
   - Genera el JAR de la aplicación
   - Guarda el artifact por 5 días

2. **Deploy (Solo en push a main después de build exitoso)**
   - Descarga el JAR
   - Lo copia al servidor EC2
   - Reinicia el servicio de la aplicación

## 🔑 Configuración de Secretos en GitHub

Necesitas añadir estos secretos en tu repositorio de GitHub:

### Pasos:
1. Ve a: **Settings → Secrets and variables → Actions → New repository secret**
2. Añade los siguientes secretos:

| Nombre | Descripción | Ejemplo |
|--------|-------------|---------|
| `EC2_HOST` | IP o dominio del servidor EC2 | `54.123.456.789` o `api.tudominio.com` |
| `EC2_USER` | Usuario SSH en EC2 | `ec2-user` o `ubuntu` |
| `EC2_SSH_KEY` | Clave privada SSH (contenido completo) | (archivo .pem convertido en texto) |

### 📝 Cómo obtener la clave SSH:

```bash
# Si tienes la clave en formato .pem
cat /ruta/a/tu/clave.pem | xsel -b  # Linux/Mac
type C:\ruta\a\tu\clave.pem | clip  # Windows
```

Luego pégala directamente en el campo del secreto.

## 🖥️ Configuración del Servidor EC2

### 1. Crear usuario de deploy (si es necesario)

```bash
sudo useradd -m -s /bin/bash deploy
sudo mkdir -p /home/deploy/app
sudo chown deploy:deploy /home/deploy/app
```

### 2. Configurar servicio systemd para la aplicación

Crear archivo: `/etc/systemd/system/f1-app.service`

```ini
[Unit]
Description=F1 API REST Application
After=network.target

[Service]
Type=simple
User=deploy
WorkingDirectory=/home/deploy/app
ExecStart=/usr/bin/java -jar /home/deploy/app/f1-0.0.1-SNAPSHOT.jar \
  --spring.config.location=file:/home/deploy/app/application-prod.properties
Restart=on-failure
RestartSec=10

[Install]
WantedBy=multi-user.target
```

### 3. Habilitar el servicio

```bash
sudo systemctl daemon-reload
sudo systemctl enable f1-app
sudo systemctl start f1-app
```

### 4. Crear archivo de configuración de producción

Crear: `/home/deploy/app/application-prod.properties`

```properties
server.port=8080
spring.application.name=f1

# Base de datos
db.url=jdbc:mysql://db-host:3306/f1
db.usuario=admin_f1
db.contrasena=TU_CONTRASEÑA_SEGURA

# Logging
logging.level.root=INFO
logging.level.org.palomafp.f1=DEBUG
```

### 5. Dar permisos SSH al usuario deploy

```bash
# En tu máquina local, agrega la clave pública al servidor:
ssh-copy-id -i ~/.ssh/tu-clave.pub deploy@EC2_HOST

# O manualmente en el servidor:
sudo -u deploy bash -c 'mkdir -p ~/.ssh && cat >> ~/.ssh/authorized_keys'
# Pega la clave pública
```

## 🔄 Monitorear el Pipeline

1. Ve a **Actions** en tu repositorio de GitHub
2. Verás los workflows ejecutándose
3. Haz clic en un workflow para ver detalles
4. En el deploy, verás los logs de la conexión SSH

## 🧪 Probar Localmente antes de Commit

```bash
# Compilar y empaquetar
mvn clean package

# Probar el JAR
java -jar target/f1-0.0.1-SNAPSHOT.jar

# Con configuración de producción
java -jar target/f1-0.0.1-SNAPSHOT.jar \
  --spring.config.location=file:./application-prod.properties
```

## ⚠️ Troubleshooting

### "Permission denied (publickey)"
- Verifica que `EC2_SSH_KEY` esté correctamente configurada
- Asegúrate de que es la clave **privada**, no la pública
- La clave debe tener permisos 600 en el servidor

### "No such file or directory: target/*.jar"
- Verifica que el build fue exitoso
- Comprueba que el pom.xml genera un JAR

### Deploy no se ejecuta
- Asegúrate de que el push es a la rama `main`
- Verifica que el build completó exitosamente
- Revisa los logs en la pestaña de Actions

## 📚 Referencias

- [GitHub Actions Documentation](https://docs.github.com/en/actions)
- [appleboy/scp-action](https://github.com/appleboy/scp-action)
- [appleboy/ssh-action](https://github.com/appleboy/ssh-action)
- [Spring Boot Deployment](https://spring.io/guides/gs/spring-boot/)
