package org.palomafp.f1.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DatabaseConfig {
    
    @Value("${db.url}")
    private String url;
    
    @Value("${db.usuario}")
    private String usuario;
    
    @Value("${db.contrasena}")
    private String contrasena;
    
    public String getUrl() {
        return url;
    }
    
    public String getUsuario() {
        return usuario;
    }
    
    public String getContrasena() {
        return contrasena;
    }
}
