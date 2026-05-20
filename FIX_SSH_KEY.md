# Solucionar el Error de SSH en CI/CD

## 🔍 Verificar tu Clave SSH Local

### En Windows (PowerShell):

```powershell
# 1. Verifica dónde está tu clave SSH
ls $env:USERPROFILE\.ssh\

# 2. Mira el contenido de la clave PRIVADA (la que acabas en "id_rsa" SIN extensión .pub)
Get-Content $env:USERPROFILE\.ssh\id_rsa

# Si usas una clave específica para EC2:
Get-Content $env:USERPROFILE\.ssh\tu-clave.pem
```

El contenido debe lucir así (para claves RSA):
```
-----BEGIN RSA PRIVATE KEY-----
MIIEpAIBAAKCAQEA...
[muchas líneas de caracteres]
-----END RSA PRIVATE KEY-----
```

O así (para claves ED25519 o ECDSA):
```
-----BEGIN PRIVATE KEY-----
MIIEvAIBADANBgkq...
[muchas líneas de caracteres]
-----END PRIVATE KEY-----
```

## ✅ Actualizar el Secreto EC2_SSH_KEY

### Paso 1: Copiar la clave completa

**En PowerShell (Windows):**
```powershell
# Copiar la clave privada completa al portapapeles
Get-Content $env:USERPROFILE\.ssh\id_rsa | Set-Clipboard

# O si tienes una clave específica:
Get-Content $env:USERPROFILE\.ssh\tu-clave.pem | Set-Clipboard
```

**En Linux/Mac:**
```bash
cat ~/.ssh/id_rsa | xclip -selection clipboard
# O:
cat ~/.ssh/tu-clave.pem | pbcopy
```

### Paso 2: Actualizar el Secreto en GitHub

1. Ve a tu repositorio en GitHub
2. **Settings → Secrets and variables → Actions**
3. Busca `EC2_SSH_KEY`
4. Haz clic en el lápiz (editar)
5. **Borra el contenido actual completamente**
6. Pega la clave completa (Ctrl+V)
7. Click en **Update secret**

## 🔐 Verificar Conectividad SSH

En tu máquina local, prueba la conexión:

```bash
# Reemplaza con tus datos reales
ssh -i ~/.ssh/id_rsa ec2-user@54.123.456.789

# O con clave específica:
ssh -i ~/.ssh/tu-clave.pem ubuntu@tu-dominio.com
```

Si funciona, deberías entrar en el servidor sin pedir contraseña.

## 🆘 Si Aún No Funciona

### Opción 1: Regenerar la Clave SSH en EC2

En tu servidor EC2:
```bash
# Conectate como usuario deploy
sudo -u deploy bash

# Genera nueva clave
ssh-keygen -t ed25519 -f ~/.ssh/id_ed25519 -N ""

# Copia la clave pública
cat ~/.ssh/id_ed25519.pub

# Sale del usuario deploy
exit
```

Luego en tu máquina local, agrega esa clave pública:
```bash
# Conectate al servidor
ssh -i tu-clave-actual.pem ec2-user@tu-host

# Agrega la clave pública del deploy user a authorized_keys
sudo bash -c 'echo "PEGA_LA_CLAVE_PUBLICA_AQUI" >> /home/deploy/.ssh/authorized_keys'
sudo chown deploy:deploy /home/deploy/.ssh/authorized_keys
sudo chmod 600 /home/deploy/.ssh/authorized_keys
```

### Opción 2: Usar GitHub Deploy Keys (Más Seguro)

1. En tu máquina local, genera una clave específica:
```bash
ssh-keygen -t ed25519 -f ~/.ssh/github_deploy -N ""
```

2. El archivo `github_deploy` es tu CLAVE PRIVADA → Actualiza el secreto `EC2_SSH_KEY`

3. El archivo `github_deploy.pub` es tu CLAVE PÚBLICA → Cópialo al servidor:
```bash
ssh-copy-id -i ~/.ssh/github_deploy deploy@tu-host
```

## 🧪 Test en el Workflow

Después de actualizar, haz un push pequeño para activar el workflow:

```bash
git add .
git commit --allow-empty -m "test ci/cd ssh"
git push origin main
```

Ve a **Actions** en GitHub y revisa:
- Si el build paso ✅
- Si el deploy muestra logs del SSH ✅

## 📋 Checklist Final

- [ ] Clave SSH con formato correcto (comienza con `-----BEGIN` y termina con `-----END`)
- [ ] Secreto `EC2_SSH_KEY` actualizado en GitHub
- [ ] Secreto `EC2_HOST` configurado (IP o dominio)
- [ ] Secreto `EC2_USER` configurado (ubuntu, ec2-user, deploy, etc.)
- [ ] El usuario en EC2 tiene la carpeta `/home/{user}/app` con permisos correctos
- [ ] El usuario en EC2 tiene permisos sudo (si es necesario para systemctl)
- [ ] La clave pública está en `~/.ssh/authorized_keys` del usuario en EC2

## 🚀 Una vez esté funcionando

Deberías ver en GitHub Actions:
1. ✅ Job "build" completado
2. ✅ Job "deploy" conectándose al servidor
3. ✅ Logs de systemctl status mostrando la app corriendo
