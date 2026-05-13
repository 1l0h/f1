DROP DATABASE IF EXISTS f1;
CREATE DATABASE f1;
USE f1;

-- =========================
-- CREACIÓN DE USUARIOS
-- =========================

-- Eliminar usuarios si existen
DROP USER IF EXISTS 'admin_f1'@'%';
DROP USER IF EXISTS 'consulta_f1'@'%';

-- Crear usuario administrador
CREATE USER 'admin_f1'@'%'
IDENTIFIED BY 'Admin123!';

-- Crear usuario solo lectura
CREATE USER 'consulta_f1'@'%'
IDENTIFIED BY 'Consulta123!';

-- Permisos para administrador
GRANT ALL PRIVILEGES
ON f1.*
TO 'admin_f1'@'%';

-- Permisos solo lectura
GRANT SELECT
ON f1.*
TO 'consulta_f1'@'%';

-- Aplicar cambios

-- =========================
-- TABLAS PRINCIPALES
-- =========================

CREATE TABLE piloto (
	numero INT UNSIGNED PRIMARY KEY,
	nombre VARCHAR(25) NOT NULL,
    apellido VARCHAR(25) NOT NULL,
    nacionalidad VARCHAR(50) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    podios INT UNSIGNED DEFAULT 0,
    victorias INT UNSIGNED DEFAULT 0,
    campeonatos INT UNSIGNED DEFAULT 0,
    poles INT UNSIGNED DEFAULT 0,
    foto VARCHAR(255)
);

CREATE TABLE monoplaza (
	id_modelo INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(30) NOT NULL,
    motor VARCHAR(30) NOT NULL,
    caballos INT UNSIGNED NOT NULL,
    velocidad_max DECIMAL(4,1),
    peso INT UNSIGNED NOT NULL,
    foto VARCHAR(255)
);

CREATE TABLE escuderia (
	id_escuderia INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    pais VARCHAR(30) NOT NULL,
    jefe_equipo VARCHAR(50) NOT NULL,
    anio_entrada YEAR,
    campeonatos INT UNSIGNED DEFAULT 0,
    campeonatos_pilotos INT UNSIGNED DEFAULT 0,
    victorias INT UNSIGNED DEFAULT 0,
    color_hex VARCHAR(6) NOT NULL,
    emblema VARCHAR(255),
    foto_jefe VARCHAR(255)
);

CREATE TABLE gran_premio (
	id_gp INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    ubicacion VARCHAR(50) NOT NULL,
    longitud DECIMAL(4,1) NOT NULL,
    fecha DATE NOT NULL,
    vueltas INT UNSIGNED NOT NULL,
    vuelta_rapida TIME,
    anio_creacion YEAR,
    imagen VARCHAR(255),
    modelo VARCHAR(255)
);

CREATE TABLE temporada (
	anio YEAR PRIMARY KEY,
    num_piloto_ganador INT UNSIGNED,

    FOREIGN KEY (num_piloto_ganador)
    REFERENCES piloto(numero)
    ON DELETE SET NULL
    ON UPDATE CASCADE
);

-- =========================
-- TABLAS INTERMEDIAS
-- =========================

CREATE TABLE escuderia_ficha_piloto (
	numero INT UNSIGNED NOT NULL,
    id_escuderia INT UNSIGNED NOT NULL,
    anio YEAR NOT NULL,

    PRIMARY KEY (numero, id_escuderia, anio),

	FOREIGN KEY (numero)
    REFERENCES piloto(numero)
    ON DELETE CASCADE
    ON UPDATE CASCADE,

	FOREIGN KEY (id_escuderia)
    REFERENCES escuderia(id_escuderia)
    ON DELETE CASCADE
    ON UPDATE CASCADE,

    FOREIGN KEY (anio)
    REFERENCES temporada(anio)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE escuderia_tiene_monoplaza_temporada (
	id_escuderia INT UNSIGNED NOT NULL,
    anio YEAR NOT NULL,
    id_modelo INT UNSIGNED NOT NULL,

    PRIMARY KEY (id_escuderia, anio),

	FOREIGN KEY (id_escuderia)
    REFERENCES escuderia(id_escuderia)
    ON DELETE CASCADE
    ON UPDATE CASCADE,

	FOREIGN KEY (anio)
    REFERENCES temporada(anio)
    ON DELETE CASCADE
    ON UPDATE CASCADE,

	FOREIGN KEY (id_modelo)
    REFERENCES monoplaza(id_modelo)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE piloto_participa_gp (
	num_piloto INT UNSIGNED NOT NULL,
    id_gp INT UNSIGNED NOT NULL,
    posicion INT UNSIGNED NOT NULL,
    tiempo TIME,

    PRIMARY KEY (num_piloto, id_gp),

    FOREIGN KEY (num_piloto)
    REFERENCES piloto(numero)
    ON DELETE CASCADE
    ON UPDATE CASCADE,

    FOREIGN KEY (id_gp)
    REFERENCES gran_premio(id_gp)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

-- =========================
-- TABLA EXTRA: RESULTADOS
-- =========================

CREATE TABLE clasificacion_temporada (
    anio YEAR NOT NULL,
    num_piloto INT UNSIGNED NOT NULL,
    puntos INT UNSIGNED DEFAULT 0,
    posicion_final INT UNSIGNED,

    PRIMARY KEY (anio, num_piloto),

    FOREIGN KEY (anio)
    REFERENCES temporada(anio)
    ON DELETE CASCADE
    ON UPDATE CASCADE,

    FOREIGN KEY (num_piloto)
    REFERENCES piloto(numero)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);
