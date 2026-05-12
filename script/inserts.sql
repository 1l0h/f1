USE f1;

-- =========================
-- TEMPORADA 2026
-- =========================

INSERT INTO temporada (anio, num_piloto_ganador)
VALUES (2026, 1);

-- =========================
-- PILOTOS
-- =========================

INSERT INTO piloto VALUES
(1, 'Lando', 'Norris', 'Británica', '1999-11-13', 42, 11, 1, 15, NULL),
(81, 'Oscar', 'Piastri', 'Australiana', '2001-04-06', 28, 8, 0, 6, NULL),
(63, 'George', 'Russell', 'Británica', '1998-02-15', 18, 4, 0, 5, NULL),
(12, 'Kimi', 'Antonelli', 'Italiana', '2006-08-25', 2, 0, 0, 1, NULL),
(16, 'Charles', 'Leclerc', 'Monegasca', '1997-10-16', 39, 8, 0, 26, NULL),
(44, 'Lewis', 'Hamilton', 'Británica', '1985-01-07', 201, 105, 7, 104, NULL),
(33, 'Max', 'Verstappen', 'Neerlandesa', '1997-09-30', 115, 67, 4, 44, NULL),
(6, 'Isack', 'Hadjar', 'Francesa', '2004-09-28', 0, 0, 0, 0, NULL),
(14, 'Fernando', 'Alonso', 'Española', '1981-07-29', 106, 32, 2, 22, NULL),
(18, 'Lance', 'Stroll', 'Canadiense', '1998-10-29', 3, 0, 0, 1, NULL),
(10, 'Pierre', 'Gasly', 'Francesa', '1996-02-07', 5, 1, 0, 0, NULL),
(43, 'Franco', 'Colapinto', 'Argentina', '2003-05-27', 1, 0, 0, 0, NULL),
(31, 'Esteban', 'Ocon', 'Francesa', '1996-09-17', 4, 1, 0, 0, NULL),
(87, 'Oliver', 'Bearman', 'Británica', '2005-05-08', 0, 0, 0, 0, NULL),
(55, 'Carlos', 'Sainz', 'Española', '1994-09-01', 27, 4, 0, 6, NULL),
(23, 'Alex', 'Albon', 'Tailandesa', '1996-03-23', 2, 0, 0, 0, NULL),
(30, 'Liam', 'Lawson', 'Neozelandesa', '2002-02-11', 0, 0, 0, 0, NULL),
(41, 'Arvid', 'Lindblad', 'Británica', '2007-08-08', 0, 0, 0, 0, NULL),
(27, 'Nico', 'Hulkenberg', 'Alemana', '1987-08-19', 0, 0, 0, 1, NULL),
(5, 'Gabriel', 'Bortoleto', 'Brasileña', '2004-10-14', 0, 0, 0, 0, NULL),
(11, 'Sergio', 'Perez', 'Mexicana', '1990-01-26', 39, 6, 0, 3, NULL),
(77, 'Valtteri', 'Bottas', 'Finlandesa', '1989-08-28', 67, 10, 0, 20, NULL);

-- =========================
-- ESCUDERIAS
-- =========================

INSERT INTO escuderia
(nombre, pais, jefe_equipo, anio_entrada, campeonatos,
campeonatos_pilotos, victorias, color_hex, emblema, foto_jefe)
VALUES
('McLaren', 'Reino Unido', 'Andrea Stella', 1966, 9, 13, 195, 'FF8000', NULL, NULL),
('Mercedes', 'Alemania', 'Toto Wolff', 2010, 8, 9, 129, '00D2BE', NULL, NULL),
('Ferrari', 'Italia', 'Frederic Vasseur', 1950, 16, 15, 248, 'DC0000', NULL, NULL),
('Red Bull Racing', 'Austria', 'Christian Horner', 2005, 6, 8, 122, '1E41FF', NULL, NULL),
('Aston Martin', 'Reino Unido', 'Mike Krack', 2021, 0, 0, 1, '006F62', NULL, NULL),
('Alpine', 'Francia', 'Bruno Famin', 2021, 2, 2, 21, 'FF87BC', NULL, NULL),
('Haas', 'Estados Unidos', 'Ayao Komatsu', 2016, 0, 0, 0, 'FFFFFF', NULL, NULL),
('Williams', 'Reino Unido', 'James Vowles', 1977, 9, 7, 114, '005AFF', NULL, NULL),
('Racing Bulls', 'Italia', 'Laurent Mekies', 2024, 0, 0, 2, '6692FF', NULL, NULL),
('Audi', 'Alemania', 'Andreas Seidl', 2026, 0, 0, 0, 'C0C0C0', NULL, NULL),
('Cadillac', 'Estados Unidos', 'Graeme Lowdon', 2026, 0, 0, 0, '003B5C', NULL, NULL);

-- =========================
-- MONOPLAZAS
-- =========================

INSERT INTO monoplaza
(nombre, motor, caballos, velocidad_max, peso, foto)
VALUES
('MCL40', 'Mercedes', 1050, 365.0, 798, NULL),
('W17', 'Mercedes', 1040, 362.5, 800, NULL),
('SF-26', 'Ferrari', 1045, 364.2, 799, NULL),
('RB22', 'Ford RBPT', 1055, 366.1, 798, NULL),
('AMR26', 'Honda', 1030, 359.9, 801, NULL),
('A526', 'Renault', 1025, 357.0, 803, NULL),
('VF-26', 'Ferrari', 1018, 355.5, 804, NULL),
('FW48', 'Mercedes', 1022, 356.4, 802, NULL),
('VCARB02', 'Honda', 1015, 354.0, 803, NULL),
('Audi E-Tron F1', 'Audi', 1020, 355.0, 804, NULL),
('Cadillac C26', 'Ferrari', 1012, 352.7, 805, NULL);

-- =========================
-- RELACION ESCUDERIA-MONOPLAZA
-- =========================

INSERT INTO escuderia_tiene_monoplaza_temporada VALUES
(1, 2026, 1),
(2, 2026, 2),
(3, 2026, 3),
(4, 2026, 4),
(5, 2026, 5),
(6, 2026, 6),
(7, 2026, 7),
(8, 2026, 8),
(9, 2026, 9),
(10, 2026, 10),
(11, 2026, 11);

-- =========================
-- FICHAJES PILOTOS 2026
-- =========================

INSERT INTO escuderia_ficha_piloto VALUES
(1, 1, 2026),
(81, 1, 2026),

(63, 2, 2026),
(12, 2, 2026),

(16, 3, 2026),
(44, 3, 2026),

(33, 4, 2026),
(6, 4, 2026),

(14, 5, 2026),
(18, 5, 2026),

(10, 6, 2026),
(43, 6, 2026),

(31, 7, 2026),
(87, 7, 2026),

(55, 8, 2026),
(23, 8, 2026),

(30, 9, 2026),
(41, 9, 2026),

(27, 10, 2026),
(5, 10, 2026),

(11, 11, 2026),
(77, 11, 2026);

-- =========================
-- GRANDES PREMIOS 2026
-- =========================

INSERT INTO gran_premio
(nombre, ubicacion, longitud, fecha, vueltas,
vuelta_rapida, anio_creacion, imagen, modelo)
VALUES
('GP de Australia', 'Melbourne', 5.3, '2026-03-08', 58, '00:01:19', 1996, NULL, NULL),
('GP de China', 'Shanghai', 5.4, '2026-03-22', 56, '00:01:31', 2004, NULL, NULL),
('GP de Japón', 'Suzuka', 5.8, '2026-04-05', 53, '00:01:28', 1987, NULL, NULL),
('GP de Bahréin', 'Sakhir', 5.4, '2026-04-12', 57, '00:01:30', 2004, NULL, NULL),
('GP de Arabia Saudí', 'Yeda', 6.1, '2026-04-19', 50, '00:01:27', 2021, NULL, NULL),
('GP de Miami', 'Miami', 5.4, '2026-05-03', 57, '00:01:30', 2022, NULL, NULL),
('GP de España', 'Madrid', 5.5, '2026-06-14', 66, '00:01:21', 2026, NULL, NULL),
('GP de Reino Unido', 'Silverstone', 5.8, '2026-07-05', 52, '00:01:26', 1950, NULL, NULL),
('GP de Italia', 'Monza', 5.7, '2026-09-06', 53, '00:01:20', 1950, NULL, NULL),
('GP de Abu Dhabi', 'Yas Marina', 5.2, '2026-12-06', 58, '00:01:24', 2009, NULL, NULL);

-- =========================
-- RESULTADOS DE ALGUNOS GP
-- =========================

INSERT INTO piloto_participa_gp VALUES
(1, 1, 1, '01:31:22'),
(33, 1, 2, '01:31:30'),
(81, 1, 3, '01:31:40'),

(33, 2, 1, '01:38:11'),
(16, 2, 2, '01:38:20'),
(1, 2, 3, '01:38:27'),

(81, 3, 1, '01:29:51'),
(1, 3, 2, '01:30:02'),
(63, 3, 3, '01:30:08'),

(1, 4, 1, '01:35:11'),
(81, 4, 2, '01:35:20'),
(16, 4, 3, '01:35:25'),

(33, 5, 1, '01:21:10'),
(44, 5, 2, '01:21:15'),
(1, 5, 3, '01:21:18');

-- =========================
-- CLASIFICACION FINAL 2026
-- =========================

INSERT INTO clasificacion_temporada VALUES
(2026, 1, 428, 1),
(2026, 33, 389, 2),
(2026, 81, 361, 3),
(2026, 16, 320, 4),
(2026, 63, 287, 5),
(2026, 44, 251, 6),
(2026, 55, 184, 7),
(2026, 14, 141, 8),
(2026, 12, 132, 9),
(2026, 10, 97, 10),
(2026, 23, 74, 11),
(2026, 31, 62, 12),
(2026, 87, 49, 13),
(2026, 30, 38, 14),
(2026, 6, 35, 15),
(2026, 43, 29, 16),
(2026, 27, 20, 17),
(2026, 5, 14, 18),
(2026, 11, 11, 19),
(2026, 77, 9, 20),
(2026, 18, 7, 21),
(2026, 41, 3, 22);
