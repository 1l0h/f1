#!/bin/bash
# Script para verificar configuración CI/CD en EC2 Ubuntu

echo "=== VERIFICACIÓN DE CONFIGURACIÓN CI/CD EN EC2 ==="
echo ""

# 1. Verificar que estamos en Ubuntu
echo "✓ Sistema Operativo:"
lsb_release -d

echo ""
echo "✓ Usuario actual: $USER"
echo "✓ Home directory: $HOME"

# 2. Verificar que el usuario deploy existe
echo ""
echo "=== VERIFICACIÓN DE USUARIO DEPLOY ==="
if id "deploy" &>/dev/null; then
    echo "✓ Usuario 'deploy' existe"
    id deploy
else
    echo "✗ Usuario 'deploy' NO existe"
    echo "  Ejecuta: sudo useradd -m -s /bin/bash deploy"
fi

# 3. Verificar carpeta /home/deploy/app
echo ""
echo "=== VERIFICACIÓN DE CARPETA APP ==="
if [ -d "/home/deploy/app" ]; then
    echo "✓ Carpeta /home/deploy/app existe"
    ls -la /home/deploy/app
else
    echo "✗ Carpeta /home/deploy/app NO existe"
    echo "  Ejecuta: sudo mkdir -p /home/deploy/app && sudo chown deploy:deploy /home/deploy/app"
fi

# 4. Verificar clave SSH autorizada
echo ""
echo "=== VERIFICACIÓN DE SSH KEYS ==="
if [ -f "/home/deploy/.ssh/authorized_keys" ]; then
    echo "✓ Archivo authorized_keys existe"
    echo "  Contenido:"
    sudo cat /home/deploy/.ssh/authorized_keys | head -1
else
    echo "✗ Archivo authorized_keys NO existe"
    echo "  Ejecuta como deploy:"
    echo "  mkdir -p ~/.ssh && touch ~/.ssh/authorized_keys && chmod 700 ~/.ssh && chmod 600 ~/.ssh/authorized_keys"
fi

# 5. Verificar servicio systemd
echo ""
echo "=== VERIFICACIÓN DE SERVICIO SYSTEMD ==="
if [ -f "/etc/systemd/system/f1-app.service" ]; then
    echo "✓ Archivo /etc/systemd/system/f1-app.service existe"
    echo "  Contenido:"
    cat /etc/systemd/system/f1-app.service
else
    echo "✗ Archivo /etc/systemd/system/f1-app.service NO existe"
fi

# 6. Verificar que deploy puede usar sudo sin contraseña
echo ""
echo "=== VERIFICACIÓN DE PERMISOS SUDO ==="
if sudo -u deploy sudo -n systemctl status f1-app &>/dev/null 2>&1; then
    echo "✓ Usuario 'deploy' puede usar sudo sin contraseña"
else
    echo "ℹ Usuario 'deploy' necesita permisos sudo"
    echo "  Ejecuta: sudo visudo"
    echo "  Agrega la línea: deploy ALL=(ALL) NOPASSWD: /bin/systemctl"
fi

# 7. Verificar puerto 8080
echo ""
echo "=== VERIFICACIÓN DE PUERTO 8080 ==="
if netstat -tlnp 2>/dev/null | grep -q 8080; then
    echo "✓ Puerto 8080 está en uso"
    netstat -tlnp | grep 8080
else
    echo "✓ Puerto 8080 está disponible"
fi

# 8. Verificar si Java está instalado
echo ""
echo "=== VERIFICACIÓN DE JAVA ==="
if command -v java &>/dev/null; then
    echo "✓ Java está instalado:"
    java -version
else
    echo "✗ Java NO está instalado"
    echo "  Ejecuta: sudo apt update && sudo apt install -y openjdk-21-jdk"
fi

echo ""
echo "=== FIN DE VERIFICACIÓN ==="
