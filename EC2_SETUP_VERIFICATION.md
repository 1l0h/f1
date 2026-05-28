# Verificación del Servidor EC2

## ✅ Checklist de Configuración en EC2

Conectate a tu servidor EC2 y verifica:

```bash
ssh -i tu-clave.pem ec2-user@tu-ip-ec2
```

### 1. Usuario de Deploy Existe

```bash
id deploy
# Debe mostrar algo como: uid=1001(deploy) gid=1001(deploy) groups=1001(deploy)

# Si no existe, créalo:
sudo useradd -m -s /bin/bash deploy
sudo usermod -aG sudo deploy  # Para que pueda usar sudo sin contraseña
```

### 2. Carpeta de la App Existe y Tiene Permisos

```bash
ls -la /home/deploy/
# Debe mostrarse:
# drwxr-xr-x deploy:deploy app

# Si no existe:
sudo mkdir -p /home/deploy/app
sudo chown deploy:deploy /home/deploy/app
sudo chmod 755 /home/deploy/app
```

### 3. Clave SSH del GitHub Actions está Autorizada

```bash
# Ver las claves autorizadas
sudo cat /home/deploy/.ssh/authorized_keys

# Debe contener la clave pública que configuraste en GitHub
# Si está vacío o no existe:
sudo mkdir -p /home/deploy/.ssh
sudo touch /home/deploy/.ssh/authorized_keys
sudo chmod 700 /home/deploy/.ssh
sudo chmod 600 /home/deploy/.ssh/authorized_keys
sudo chown -R deploy:deploy /home/deploy/.ssh

# Agrega tu clave pública:
echo "ssh-ed25519 AAAA... tu-comentario" | sudo tee -a /home/deploy/.ssh/authorized_keys
```

### 4. Servicio SystemD Configurado

```bash
# Ver si el servicio existe
sudo systemctl status f1-app

# Si no existe, créalo:
sudo nano /etc/systemd/system/f1-app.service
```

Pega esto en el editor (asegúrate de adaptar el usuario y rutas si es necesario):

```ini
[Unit]
Description=F1 API REST Application
After=network.target

[Service]
Type=simple
User=deploy
WorkingDirectory=/home/deploy/app
Environment="PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin"
ExecStart=/usr/bin/java -jar /home/deploy/app/f1-0.0.1-SNAPSHOT.jar \
  --spring.config.location=file:/home/deploy/app/application-prod.properties
Restart=on-failure
RestartSec=10
StandardOutput=journal
StandardError=journal
SyslogIdentifier=f1-app

[Install]
WantedBy=multi-user.target
```

Guarda con `Ctrl+X`, `Y`, `Enter`.

### 5. Habilitar y Probar el Servicio

```bash
# Recargar la configuración de systemd
sudo systemctl daemon-reload

# Habilitar para que se inicie al boot
sudo systemctl enable f1-app

# Iniciar el servicio (sin JAR aún, solo para verificar que funciona)
sudo systemctl start f1-app

# Ver el estado
sudo systemctl status f1-app
# Esperamos ver algo como: "Job for f1-app.service failed"
# porque el JAR no existe aún, pero eso es normal.
```

### 6. Archivo de Configuración de Producción

```bash
# Crear el archivo
sudo nano /home/deploy/app/application-prod.properties
```

Pega esto (adapta con tus datos):

```properties
server.port=8080
server.servlet.context-path=/api
spring.application.name=f1

# MySQL Database
db.url=jdbc:mysql://localhost:3306/f1
db.usuario=admin_f1
db.contrasena=TU_CONTRASEÑA_SEGURA_AQUI

# Connection Pool
spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.minimum-idle=5

# Logging
logging.level.root=INFO
logging.level.org.springframework=WARN
logging.level.org.palomafp.f1=DEBUG
logging.file.name=/var/log/f1-app/app.log
logging.file.max-size=10MB
logging.file.max-history=10

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=validate
```

### 7. Carpeta de Logs (Opcional pero Recomendado)

```bash
# Crear carpeta de logs
sudo mkdir -p /var/log/f1-app
sudo chown deploy:deploy /var/log/f1-app
sudo chmod 755 /var/log/f1-app
```

### 8. Permisos para Sudo sin Contraseña (Necesario para systemctl)

```bash
# Ver la configuración actual
sudo visudo -c

# Editar sudoers
sudo visudo

# Agrega esta línea al final (importante: usa tabulación, no espacios):
deploy ALL=(ALL) NOPASSWD: /bin/systemctl

# Guarda con Ctrl+X, Y, Enter
```

## 🧪 Prueba Manual

Una vez todo está configurado, prueba manualmente:

```bash
# Conectate como deploy
sudo su - deploy

# Copia un JAR de prueba
# (El workflow haría esto automáticamente)
cd ~/app

# Intenta iniciar el servicio
sudo systemctl restart f1-app

# Verifica el estado
sudo systemctl status f1-app

# Ve los logs
sudo journalctl -u f1-app -n 50 -f
```

## 🔍 Debug de Problemas

### Ver logs del servicio
```bash
sudo journalctl -u f1-app -n 100
# O con más detalles:
sudo journalctl -u f1-app -n 100 --no-pager
```

### Ver procesos Java
```bash
ps aux | grep java
```

### Verificar puerto 8080
```bash
netstat -tlnp | grep 8080
# O:
sudo lsof -i :8080
```

### Ver si el deploy user puede ejecutar sudo
```bash
sudo -u deploy sudo systemctl status f1-app
```

## ✅ Checklist Final

- [ ] Usuario `deploy` existe
- [ ] Carpeta `/home/deploy/app` existe con permisos correctos
- [ ] Clave SSH pública está en `/home/deploy/.ssh/authorized_keys`
- [ ] Archivo `/etc/systemd/system/f1-app.service` existe y es correcto
- [ ] Servicio está habilitado: `sudo systemctl is-enabled f1-app`
- [ ] Usuario deploy puede ejecutar `sudo systemctl` sin contraseña
- [ ] Archivo `application-prod.properties` existe en `/home/deploy/app/`
- [ ] Puerto 8080 no está siendo usado por otra aplicación

Una vez todo esté listo, el próximo push a `main` debería desplegar correctamente.
