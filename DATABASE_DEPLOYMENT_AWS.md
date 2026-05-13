# Guía de Despliegue de Base de Datos en AWS EC2 con Ubuntu

## Tabla de Contenidos
1. [Preparación de la Instancia EC2](#preparación-de-la-instancia-ec2)
2. [Instalación de MySQL](#instalación-de-mysql)
3. [Transferencia de Scripts](#transferencia-de-scripts)
4. [Creación de la Base de Datos](#creación-de-la-base-de-datos)
5. [Inserción de Datos](#inserción-de-datos)
6. [Verificación](#verificación)
7. [Configuración de Seguridad](#configuración-de-seguridad)
8. [Solución de Problemas](#solución-de-problemas)

---

## Preparación de la Instancia EC2

### 1. Conectarse a la Instancia
```bash
ssh -i ruta/a/tu/clave.pem ubuntu@tu-ip-ec2-pública
```

### 2. Actualizar el Sistema
```bash
sudo apt update
sudo apt upgrade -y
```

### 3. Crear un Directorio de Trabajo
```bash
mkdir -p ~/f1-database
cd ~/f1-database
```

---

## Instalación de MySQL

### 1. Instalar MySQL Server
```bash
sudo apt install mysql-server -y
```

### 2. Ejecutar Script de Seguridad (Opcional pero Recomendado)
```bash
sudo mysql_secure_installation
```

### 3. Verificar que MySQL está Corriendo
```bash
sudo systemctl status mysql
```

### 4. Iniciar MySQL en Boot
```bash
sudo systemctl enable mysql
```

---

## Transferencia de Scripts

### Opción 1: Usando SCP desde tu Máquina Local
```bash
# Copiar scripts desde tu máquina local a la EC2
scp -i ruta/a/tu/clave.pem \
    ruta/local/script.sql \
    ubuntu@tu-ip-ec2:/home/ubuntu/f1-database/

scp -i ruta/a/tu/clave.pem \
    ruta/local/inserts.sql \
    ubuntu@tu-ip-ec2:/home/ubuntu/f1-database/
```

### Opción 2: Clonar el Repositorio
```bash
# En la instancia EC2
cd ~/f1-database
git clone https://tu-repo-url.git .
```

### Opción 3: Crear los Scripts Manualmente
```bash
# Crear el archivo script.sql
nano script.sql
# Pegar el contenido y guardar (Ctrl+X, Y, Enter)

# Crear el archivo inserts.sql
nano inserts.sql
# Pegar el contenido y guardar
```

---

## Creación de la Base de Datos

### 1. Acceder a MySQL como Root
```bash
sudo mysql -u root
```

### 2. Ejecutar el Script de Creación
```mysql
source /home/ubuntu/f1-database/script.sql;
FLUSH PRIVILEGES;
EXIT;
```

**O desde la línea de comandos (sin entrar en el cliente MySQL):**
```bash
sudo mysql -u root < /home/ubuntu/f1-database/script.sql
sudo mysql -u root -e "FLUSH PRIVILEGES;"
```

### 3. Verificar la Creación
```bash
sudo mysql -u root -e "SHOW DATABASES;"
sudo mysql -u root -e "USE f1; SHOW TABLES;"
```

---

## Inserción de Datos

### 1. Ejecutar el Script de Inserciones
```bash
sudo mysql -u root < /home/ubuntu/f1-database/inserts.sql
```

### 2. Verificar la Inserción (Opcional)
```bash
sudo mysql -u root -e "USE f1; SELECT COUNT(*) as total_pilotos FROM piloto;"
sudo mysql -u root -e "USE f1; SELECT COUNT(*) as total_escuderias FROM escuderia;"
```

---

## Verificación

### 1. Verificar Usuarios Creados
```bash
sudo mysql -u root -e "SELECT User, Host FROM mysql.user WHERE Host='%';"
```

### 2. Verificar Permisos del Usuario de Consulta
```bash
sudo mysql -u consulta_f1 -pConsulta123! -e "USE f1; SELECT * FROM piloto LIMIT 5;"
```

### 3. Verificar Permisos del Usuario Administrador
```bash
sudo mysql -u admin_f1 -pAdmin123! -e "USE f1; SHOW TABLES;"
```

---

## Configuración de Seguridad

### 1. Permitir Conexiones Remotas (Si es necesario)

#### Editar Configuración de MySQL
```bash
sudo nano /etc/mysql/mysql.conf.d/mysqld.cnf
```

Buscar la línea `bind-address` y cambiarla de:
```
bind-address = 127.0.0.1
```
a:
```
bind-address = 0.0.0.0
```

Guardar y salir (Ctrl+X, Y, Enter)

#### Reiniciar MySQL
```bash
sudo systemctl restart mysql
```

### 2. Configurar Security Group en AWS

En la consola de AWS:
1. Ir a **EC2 > Security Groups**
2. Seleccionar el grupo de seguridad de tu instancia
3. Editar reglas de entrada (Inbound Rules)
4. Agregar regla:
   - **Type:** MySQL/Aurora
   - **Protocol:** TCP
   - **Port Range:** 3306
   - **Source:** Especificar el rango de IPs permitidas o usar tu IP fija
   
**⚠️ IMPORTANTE:** No abrir el puerto 3306 a `0.0.0.0/0` en producción

### 3. Cambiar Contraseñas (Recomendado)
```bash
sudo mysql -u root
```

```mysql
ALTER USER 'admin_f1'@'%' IDENTIFIED BY 'nueva_contraseña_admin';
ALTER USER 'consulta_f1'@'%' IDENTIFIED BY 'nueva_contraseña_consulta';
FLUSH PRIVILEGES;
EXIT;
```

### 4. Deshabilitar el Usuario Root Remoto
```bash
sudo mysql -u root -e "DELETE FROM mysql.user WHERE User='root' AND Host NOT IN ('localhost', '127.0.0.1', '::1');"
sudo mysql -u root -e "FLUSH PRIVILEGES;"
```

---

## Solución de Problemas

### Error: "Access denied for user 'root'@'localhost'"
```bash
# Reiniciar MySQL en modo seguro
sudo systemctl stop mysql
sudo mysqld_safe --skip-grant-tables &
mysql -u root
```
```mysql
FLUSH PRIVILEGES;
ALTER USER 'root'@'localhost' IDENTIFIED BY 'nueva_contraseña';
EXIT;
```

### Error: "Can't read file 'script.sql'"
- Verificar que el archivo existe: `ls -la /home/ubuntu/f1-database/`
- Asegurarse de que la ruta es correcta
- Cambiar permisos si es necesario: `chmod 644 /home/ubuntu/f1-database/*.sql`

### MySQL no inicia después de cambiar bind-address
```bash
sudo systemctl restart mysql
sudo systemctl status mysql
# Ver logs si hay error:
sudo tail -f /var/log/mysql/error.log
```

### No se puede conectar desde la aplicación
1. Verificar que el Security Group permite el puerto 3306
2. Verificar la IP pública de la EC2
3. Verificar credentials en `application.properties`
4. Probar conexión: `mysql -h tu-ip-ec2 -u consulta_f1 -pConsulta123!`

---

## Resumen de Credenciales

| Usuario | Contraseña | Permisos |
|---------|-----------|----------|
| `admin_f1` | `Admin123!` | Todos (CREATE, ALTER, DROP, SELECT, INSERT, UPDATE, DELETE) |
| `consulta_f1` | `Consulta123!` | Solo SELECT (lectura) |
| `root` | *Sistema* | Acceso total (solo localhost) |

---

## Checklist de Despliegue

- [ ] Instancia EC2 creada y accesible via SSH
- [ ] MySQL Server instalado
- [ ] Scripts transferidos al servidor
- [ ] Script de creación ejecutado (`script.sql`)
- [ ] Script de inserciones ejecutado (`inserts.sql`)
- [ ] Base de datos `f1` existe
- [ ] Tablas creadas correctamente
- [ ] Usuarios creados (`admin_f1` y `consulta_f1`)
- [ ] Datos insertados
- [ ] Security Group configurado
- [ ] Conexión remota probada (si es necesario)
- [ ] Contraseñas cambiadas a valores seguros
- [ ] Backups configurados

---

## Comandos Útiles

### Backup de la Base de Datos
```bash
mysqldump -u admin_f1 -pAdmin123! f1 > f1_backup_$(date +%Y%m%d_%H%M%S).sql
```

### Restaurar desde Backup
```bash
mysql -u admin_f1 -pAdmin123! f1 < f1_backup_20260513_120000.sql
```

### Monitorar MySQL
```bash
sudo mysqladmin -u root ping
sudo mysqladmin -u root status
```

### Ver Espacio en Disco
```bash
df -h
```

---

## Referencias
- [Documentación MySQL Oficial](https://dev.mysql.com/doc/)
- [AWS EC2 Security Groups](https://docs.aws.amazon.com/vpc/latest/userguide/VPC_SecurityGroups.html)
- [Ubuntu MySQL Installation](https://ubuntu.com/server/docs/mysql)
