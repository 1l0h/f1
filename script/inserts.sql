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
('GP de Abu Dhabi', 'Yas Marina', 5.2, '2026-12-06', 58, '00:01:24', 2009, NULL, NULL),
('GP de Emilia-Romaña', 'Imola', 4.9, '2026-05-17', 63, '00:01:18', 1980, NULL, NULL),
('GP de Mónaco', 'Montecarlo', 3.3, '2026-05-24', 78, '00:01:12', 1950, NULL, NULL),
('GP de Canadá', 'Montreal', 4.4, '2026-06-07', 70, '00:01:14', 1978, NULL, NULL),
('GP de Austria', 'Spielberg', 4.3, '2026-06-28', 71, '00:01:06', 1970, NULL, NULL),
('GP de Bélgica', 'Spa-Francorchamps', 7.0, '2026-07-19', 44, '00:01:44', 1950, NULL, NULL),
('GP de Hungría', 'Budapest', 4.4, '2026-08-02', 70, '00:01:16', 1986, NULL, NULL),
('GP de Países Bajos', 'Zandvoort', 4.3, '2026-08-23', 72, '00:01:11', 1952, NULL, NULL),
('GP de Azerbaiyán', 'Bakú', 6.0, '2026-09-20', 51, '00:01:43', 2016, NULL, NULL),
('GP de Singapur', 'Singapur', 4.9, '2026-10-04', 62, '00:01:34', 2008, NULL, NULL),
('GP de Estados Unidos', 'Austin', 5.5, '2026-10-18', 56, '00:01:36', 2012, NULL, NULL),
('GP de México', 'Ciudad de México', 4.3, '2026-10-25', 71, '00:01:17', 1963, NULL, NULL),
('GP de Brasil', 'São Paulo', 4.3, '2026-11-08', 71, '00:01:10', 1973, NULL, NULL),
('GP de Las Vegas', 'Las Vegas', 6.2, '2026-11-21', 50, '00:01:33', 2023, NULL, NULL),
('GP de Qatar', 'Losail', 5.4, '2026-11-29', 57, '00:01:22', 2021, NULL, NULL);

-- =========================
-- RESULTADOS DE ALGUNOS GP
-- =========================

-- 1. Australia
(1, 1, 1, '01:31:22'),
(81, 1, 2, '01:31:27'),
(33, 1, 3, '01:31:35'),

-- 2. China
(33, 2, 1, '01:38:11'),
(1, 2, 2, '01:38:16'),
(16, 2, 3, '01:38:20'),

-- 3. Japón
(81, 3, 1, '01:29:51'),
(1, 3, 2, '01:29:59'),
(63, 3, 3, '01:30:08'),

-- 4. Bahréin
(1, 4, 1, '01:35:11'),
(33, 4, 2, '01:35:16'),
(81, 4, 3, '01:35:20'),

-- 5. Arabia Saudí
(33, 5, 1, '01:21:10'),
(44, 5, 2, '01:21:15'),
(1, 5, 3, '01:21:18'),

-- 6. Miami
(1, 6, 1, '01:28:44'),
(12, 6, 2, '01:28:49'),
(81, 6, 3, '01:28:56'),

-- 7. Emilia-Romaña
(16, 7, 1, '01:27:44'),
(1, 7, 2, '01:27:49'),
(44, 7, 3, '01:27:58'),

-- 8. Mónaco
(16, 8, 1, '01:49:15'),
(14, 8, 2, '01:49:24'),
(33, 8, 3, '01:49:30'),

-- 9. Canadá
(63, 9, 1, '01:33:02'),
(1, 9, 2, '01:33:09'),
(12, 9, 3, '01:33:16'),

-- 10. España (Madrid)
(14, 10, 1, '01:32:55'),
(1, 10, 2, '01:33:00'),
(16, 10, 3, '01:33:04'),

-- 11. Austria
(33, 11, 1, '01:20:17'),
(81, 11, 2, '01:20:20'),
(1, 11, 3, '01:20:26'),

-- 12. Reino Unido
(1, 12, 1, '01:25:10'),
(63, 12, 2, '01:25:18'),
(44, 12, 3, '01:25:21'),

-- 13. Bélgica
(81, 13, 1, '01:37:22'),
(33, 13, 2, '01:37:29'),
(1, 13, 3, '01:37:35'),

-- 14. Hungría
(1, 14, 1, '01:31:11'),
(81, 14, 2, '01:31:18'),
(16, 14, 3, '01:31:22'),

-- 15. Países Bajos
(33, 15, 1, '01:36:44'),
(1, 15, 2, '01:36:49'),
(81, 15, 3, '01:36:55'),

-- 16. Italia
(16, 16, 1, '01:18:04'),
(44, 16, 2, '01:18:09'),
(1, 16, 3, '01:18:17'),

-- 17. Azerbaiyán
(44, 17, 1, '01:42:04'),
(55, 17, 2, '01:42:09'),
(16, 17, 3, '01:42:17'),

-- 18. Singapur
(1, 18, 1, '01:58:33'),
(81, 18, 2, '01:58:39'),
(14, 18, 3, '01:58:48'),

-- 19. Estados Unidos
(33, 19, 1, '01:34:55'),
(63, 19, 2, '01:35:00'),
(1, 19, 3, '01:35:04'),

-- 20. México
(11, 20, 1, '01:36:17'),
(33, 20, 2, '01:36:25'),
(55, 20, 3, '01:36:31'),

-- 21. Brasil
(81, 21, 1, '01:41:08'),
(1, 21, 2, '01:41:12'),
(63, 21, 3, '01:41:18'),

-- 22. Las Vegas
(44, 22, 1, '01:29:44'),
(16, 22, 2, '01:29:49'),
(33, 22, 3, '01:29:53'),

-- 23. Qatar
(1, 23, 1, '01:24:51'),
(81, 23, 2, '01:24:57'),
(33, 23, 3, '01:25:04'),

-- 24. Abu Dhabi
(1, 24, 1, '01:30:21'),
(81, 24, 2, '01:30:28'),
(16, 24, 3, '01:30:35');

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
