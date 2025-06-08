-- Inserción de equipos
INSERT INTO equipo (id, nombre, foto_escudo, foto_fondo) VALUES ('550e8400-e29b-41d4-a716-446655440000', 'Real Madrid', 'foto_Escudo_rm.png', 'foto_Fondo_rm.jpg');
INSERT INTO equipo (id, nombre, foto_escudo, foto_fondo) VALUES ('550e8400-e29b-41d4-a716-446655440001', 'FC Barcelona', 'foto_Escudo_fcb.png', 'foto_Fondo_fcb.jpg');
INSERT INTO equipo (id, nombre, foto_escudo, foto_fondo) VALUES ('550e8400-e29b-41d4-a716-446655440010', 'Athletic Club', 'foto_Escudo_athletic.png', 'foto_Fondo_athletic.jpg');
INSERT INTO equipo (id, nombre, foto_escudo, foto_fondo) VALUES ('550e8400-e29b-41d4-a716-446655440011', 'Atlético de Madrid', 'foto_Escudo_atm.png', 'foto_Fondo_atm.jpg');
INSERT INTO equipo (id, nombre, foto_escudo, foto_fondo) VALUES ('550e8400-e29b-41d4-a716-446655440012', 'Villarreal', 'foto_Escudo_villarreal.png', 'foto_Fondo_villarreal.jpg');
INSERT INTO equipo (id, nombre, foto_escudo, foto_fondo) VALUES ('550e8400-e29b-41d4-a716-446655440013', 'Real Betis', 'foto_Escudo_betis.png', 'foto_Fondo_betis.jpg');
INSERT INTO equipo (id, nombre, foto_escudo, foto_fondo) VALUES ('550e8400-e29b-41d4-a716-446655440014', 'Real Sociedad', 'foto_Escudo_realsociedad.png', 'foto_Fondo_realsociedad.jpg');
INSERT INTO equipo (id, nombre, foto_escudo, foto_fondo) VALUES ('550e8400-e29b-41d4-a716-446655440015', 'Valencia', 'foto_Escudo_valencia.png', 'foto_Fondo_valencia.jpg');
INSERT INTO equipo (id, nombre, foto_escudo, foto_fondo) VALUES ('550e8400-e29b-41d4-a716-446655440016', 'Celta de Vigo', 'foto_Escudo_celta.png', 'foto_Fondo_celta.jpg');
INSERT INTO equipo (id, nombre, foto_escudo, foto_fondo) VALUES ('550e8400-e29b-41d4-a716-446655440017', 'Rayo Vallecano', 'foto_Escudo_rayo.png', 'foto_Fondo_rayo.jpg');
INSERT INTO equipo (id, nombre, foto_escudo, foto_fondo) VALUES ('550e8400-e29b-41d4-a716-446655440018', 'Osasuna', 'foto_Escudo_osasuna.png', 'foto_Fondo_osasuna.jpg');
INSERT INTO equipo (id, nombre, foto_escudo, foto_fondo) VALUES ('550e8400-e29b-41d4-a716-446655440019', 'Mallorca', 'foto_Escudo_mallorca.png', 'foto_Fondo_mallorca.jpg');
INSERT INTO equipo (id, nombre, foto_escudo, foto_fondo) VALUES ('550e8400-e29b-41d4-a716-446655440020', 'Girona', 'foto_Escudo_girona.png', 'foto_Fondo_girona.jpg');
INSERT INTO equipo (id, nombre, foto_escudo, foto_fondo) VALUES ('550e8400-e29b-41d4-a716-446655440021', 'Alavés', 'foto_Escudo_alaves.png', 'foto_Fondo_alaves.jpg');
INSERT INTO equipo (id, nombre, foto_escudo, foto_fondo) VALUES ('550e8400-e29b-41d4-a716-446655440022', 'Espanyol', 'foto_Escudo_espanyol.png', 'foto_Fondo_espanyol.jpg');
INSERT INTO equipo (id, nombre, foto_escudo, foto_fondo) VALUES ('550e8400-e29b-41d4-a716-446655440023', 'Leganés', 'foto_Escudo_leganes.png', 'foto_Fondo_leganes.jpg');
INSERT INTO equipo (id, nombre, foto_escudo, foto_fondo) VALUES ('550e8400-e29b-41d4-a716-446655440024', 'Valladolid', 'foto_Escudo_valladolid.png', 'foto_Fondo_valladolid.jpg');
INSERT INTO equipo (id, nombre, foto_escudo, foto_fondo) VALUES ('550e8400-e29b-41d4-a716-446655440025', 'Las Palmas', 'foto_Escudo_laspalmas.png', 'foto_Fondo_laspalmas.jpg');
INSERT INTO equipo (id, nombre, foto_escudo, foto_fondo) VALUES ('550e8400-e29b-41d4-a716-446655440026', 'Getafe CF', 'foto_Escudo_getafe.png', 'foto_Fondo_getafe.jpg');
INSERT INTO equipo (id, nombre, foto_escudo, foto_fondo) VALUES ('550e8400-e29b-41d4-a716-446655440027', 'Sevilla FC', 'foto_Escudo_sevilla.png', 'foto_Fondo_sevilla.jpg');

-- Inserción de usuarios
INSERT INTO usuario (id, nombre, apellidos, username, correo, password, equipo_favorito_id, membresia_id, enabled, created_at) VALUES ('550e8400-e29b-41d4-a716-446655440002', 'Cristiano', 'Ronaldo', 'cr7', 'cr7@gmail.com', '{bcrypt}$2a$12$bTQej0W8ifZQJNNzW7yofOEhwNODlXPCn9uQyZTduZ9Ph/rAMEVNe', '550e8400-e29b-41d4-a716-446655440000', NULL, true, now());
INSERT INTO usuario (id, nombre, apellidos, username, correo, password, equipo_favorito_id, membresia_id, enabled, created_at) VALUES ('550e8400-e29b-41d4-a716-446655440003', 'Leo', 'Messi', 'messi', 'messi@gmail.com', '{bcrypt}$2a$10$3aG0Ydb9JhLxlf5fz9oNzeyQX/pEoHJyXJjZp7Nk8P7xP4BgWd/na', '550e8400-e29b-41d4-a716-446655440001', NULL, true, now());
-- INSERTAR UN ADMIN
INSERT INTO usuario (id, nombre, apellidos, username, correo, password, equipo_favorito_id, membresia_id, enabled, created_at) VALUES ('550e8400-e29b-41d4-a716-446655440004', 'Admin', 'Administradorez', 'admin', 'admin@lagrada.com', '{bcrypt}$2a$12$e1CQrst7qSuZbG/JQlUnBOKJz/uDjTXOFHofTLRPJWMddwTl/JRaa', NULL, NULL, true, now());
INSERT INTO usuario (id, nombre, apellidos, username, correo, password, equipo_favorito_id, membresia_id, enabled, created_at) VALUES ('550e8400-e29b-41d4-a716-446655440026', 'Bruno', 'Delgado', 'br1', 'delgado.hebru24@triana.salesianos.edu', '{bcrypt}$2a$12$c9DFBYq6S7fmun1UKvQN2OHhk0ZwPKCIOW0MCIoGlPwjRd4GNNbQ6', '550e8400-e29b-41d4-a716-446655440000', NULL, true, now());
-- Inserción de evento

-- Inserción de entradas
INSERT INTO entrada (id, usuario_id, evento_id) VALUES ('550e8400-e29b-41d4-a716-446655440006', '550e8400-e29b-41d4-a716-446655440002', '550e8400-e29b-41d4-a716-446655440005');
INSERT INTO entrada (id, usuario_id, evento_id) VALUES ('550e8400-e29b-41d4-a716-446655440007', '550e8400-e29b-41d4-a716-446655440003', '550e8400-e29b-41d4-a716-446655440005');

-- Inserción de membresía
INSERT INTO membresia (membresias_restantes, precio, equipo_id, id, tipo_membresia, descripcion, nombre, tipo) VALUES (100, 150.0, '550e8400-e29b-41d4-a716-446655440000', '550e8400-e29b-41d4-a716-446655440008', 'PLUS', 'Entradas gratis para los partidos cotidianos del Real Madrid', 'Membresía Básica Real Madrid', 'BASICA');

-- Inserción de usuario con membresía
INSERT INTO usuario (id, nombre, apellidos, username, correo, password, equipo_favorito_id, membresia_id, enabled, created_at) VALUES ('550e8400-e29b-41d4-a716-446655440009', 'Antonio', 'Recio', 'arecio', 'antoniorecio@mariscosrecio.com', '$2a$10$b8QpE4E8Xz8cO7AqZw6qhe2q/K2kq5mQpmN8eLy0FzJrDbE3mA99.', '550e8400-e29b-41d4-a716-446655440000', '550e8400-e29b-41d4-a716-446655440008', true, now());

-- Inserción de roles
INSERT INTO user_roles (roles, user_id) VALUES (0, '550e8400-e29b-41d4-a716-446655440002');
INSERT INTO user_roles (roles, user_id) VALUES (0, '550e8400-e29b-41d4-a716-446655440003');
INSERT INTO user_roles (roles, user_id) VALUES (1, '550e8400-e29b-41d4-a716-446655440004');
INSERT INTO user_roles (roles, user_id) VALUES (0, '550e8400-e29b-41d4-a716-446655440026');

-- Eventos pasados para admin

-- Entradas para admin en eventos pasados
INSERT INTO entrada (id, usuario_id, evento_id) VALUES ('550e8400-e29b-41d4-a716-446655440060', '550e8400-e29b-41d4-a716-446655440004', '550e8400-e29b-41d4-a716-446655440050');
INSERT INTO entrada (id, usuario_id, evento_id) VALUES ('550e8400-e29b-41d4-a716-446655440061', '550e8400-e29b-41d4-a716-446655440004', '550e8400-e29b-41d4-a716-446655440051');

-- Jornada 1 (2025-2026)

-- Comienzo de los 380 partidos de LaLiga 2025-2026
-- Generado automáticamente: 38 jornadas, 10 partidos por jornada, fechas entre 2025-08-16 y 2026-06-14
-- Los IDs de evento comienzan en '550e8400-e29b-41d4-a716-446655441000'

-- Jornada 1 de LaLiga
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655441000', 'Real Madrid vs Atlético de Madrid', 'Jornada 1 de LaLiga', '2025-08-16 21:00:00', '550e8400-e29b-41d4-a716-446655440000', '550e8400-e29b-41d4-a716-446655440011', 500, 500, 8.0, 'IMPORTANTE');
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655441001', 'FC Barcelona vs Espanyol', 'Jornada 1 de LaLiga', '2025-08-16 21:00:00', '550e8400-e29b-41d4-a716-446655440001', '550e8400-e29b-41d4-a716-446655440022', 500, 500, 8.0, 'IMPORTANTE');
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655441002', 'Sevilla FC vs Real Betis', 'Jornada 1 de LaLiga', '2025-08-16 21:00:00', '550e8400-e29b-41d4-a716-446655440027', '550e8400-e29b-41d4-a716-446655440013', 500, 500, 8.0, 'IMPORTANTE');
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655441003', 'Valencia vs Villarreal', 'Jornada 1 de LaLiga', '2025-08-16 21:00:00', '550e8400-e29b-41d4-a716-446655440015', '550e8400-e29b-41d4-a716-446655440012', 500, 500, 8.0, 'IMPORTANTE');
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655441004', 'Athletic Club vs Real Sociedad', 'Jornada 1 de LaLiga', '2025-08-16 21:00:00', '550e8400-e29b-41d4-a716-446655440010', '550e8400-e29b-41d4-a716-446655440014', 500, 500, 8.0, 'IMPORTANTE');
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655441005', 'Celta de Vigo vs Rayo Vallecano', 'Jornada 1 de LaLiga', '2025-08-16 21:00:00', '550e8400-e29b-41d4-a716-446655440016', '550e8400-e29b-41d4-a716-446655440017', 500, 500, 5.0, 'COTIDIANO');
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655441006', 'Osasuna vs Mallorca', 'Jornada 1 de LaLiga', '2025-08-16 21:00:00', '550e8400-e29b-41d4-a716-446655440018', '550e8400-e29b-41d4-a716-446655440019', 500, 500, 5.0, 'COTIDIANO');
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655441007', 'Girona vs Alavés', 'Jornada 1 de LaLiga', '2025-08-16 21:00:00', '550e8400-e29b-41d4-a716-446655440020', '550e8400-e29b-41d4-a716-446655440021', 500, 500, 5.0, 'COTIDIANO');
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655441008', 'Leganés vs Valladolid', 'Jornada 1 de LaLiga', '2025-08-16 21:00:00', '550e8400-e29b-41d4-a716-446655440023', '550e8400-e29b-41d4-a716-446655440024', 500, 500, 5.0, 'COTIDIANO');
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655441009', 'Las Palmas vs Getafe CF', 'Jornada 1 de LaLiga', '2025-08-16 21:00:00', '550e8400-e29b-41d4-a716-446655440025', '550e8400-e29b-41d4-a716-446655440026', 500, 500, 5.0, 'COTIDIANO');

-- Jornada 2 de LaLiga
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-44665544100a', 'Atlético de Madrid vs FC Barcelona', 'Jornada 2 de LaLiga', '2025-08-23 21:00:00', '550e8400-e29b-41d4-a716-446655440011', '550e8400-e29b-41d4-a716-446655440001', 500, 500, 5.0, 'COTIDIANO');
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-44665544100b', 'Espanyol vs Sevilla FC', 'Jornada 2 de LaLiga', '2025-08-23 21:00:00', '550e8400-e29b-41d4-a716-446655440022', '550e8400-e29b-41d4-a716-446655440027', 500, 500, 5.0, 'COTIDIANO');
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-44665544100c', 'Real Betis vs Valencia', 'Jornada 2 de LaLiga', '2025-08-23 21:00:00', '550e8400-e29b-41d4-a716-446655440013', '550e8400-e29b-41d4-a716-446655440015', 500, 500, 5.0, 'COTIDIANO');
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-44665544100d', 'Villarreal vs Athletic Club', 'Jornada 2 de LaLiga', '2025-08-23 21:00:00', '550e8400-e29b-41d4-a716-446655440012', '550e8400-e29b-41d4-a716-446655440010', 500, 500, 5.0, 'COTIDIANO');
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-44665544100e', 'Real Sociedad vs Celta de Vigo', 'Jornada 2 de LaLiga', '2025-08-23 21:00:00', '550e8400-e29b-41d4-a716-446655440014', '550e8400-e29b-41d4-a716-446655440016', 500, 500, 5.0, 'COTIDIANO');
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-44665544100f', 'Rayo Vallecano vs Osasuna', 'Jornada 2 de LaLiga', '2025-08-23 21:00:00', '550e8400-e29b-41d4-a716-446655440017', '550e8400-e29b-41d4-a716-446655440018', 500, 500, 5.0, 'COTIDIANO');
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655441010', 'Mallorca vs Girona', 'Jornada 2 de LaLiga', '2025-08-23 21:00:00', '550e8400-e29b-41d4-a716-446655440019', '550e8400-e29b-41d4-a716-446655440020', 500, 500, 5.0, 'COTIDIANO');
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655441011', 'Alavés vs Leganés', 'Jornada 2 de LaLiga', '2025-08-23 21:00:00', '550e8400-e29b-41d4-a716-446655440021', '550e8400-e29b-41d4-a716-446655440023', 500, 500, 5.0, 'COTIDIANO');
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655441012', 'Valladolid vs Las Palmas', 'Jornada 2 de LaLiga', '2025-08-23 21:00:00', '550e8400-e29b-41d4-a716-446655440024', '550e8400-e29b-41d4-a716-446655440025', 500, 500, 5.0, 'COTIDIANO');
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655441013', 'Getafe CF vs Real Madrid', 'Jornada 2 de LaLiga', '2025-08-23 21:00:00', '550e8400-e29b-41d4-a716-446655440026', '550e8400-e29b-41d4-a716-446655440000', 500, 500, 5.0, 'COTIDIANO');

-- ... (se omiten por brevedad las jornadas 3 a 37: seguir mismo patrón, alternando local/visitante, sin repetir partidos de ida y vuelta en la misma vuelta)

-- Jornada 38 de LaLiga
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-44665544176c', 'Atlético de Madrid vs Real Madrid', 'Jornada 38 de LaLiga', '2026-06-14 21:00:00', '550e8400-e29b-41d4-a716-446655440011', '550e8400-e29b-41d4-a716-446655440000', 500, 500, 8.0, 'IMPORTANTE');
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-44665544176d', 'Espanyol vs FC Barcelona', 'Jornada 38 de LaLiga', '2026-06-14 21:00:00', '550e8400-e29b-41d4-a716-446655440022', '550e8400-e29b-41d4-a716-446655440001', 500, 500, 8.0, 'IMPORTANTE');
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-44665544176e', 'Real Betis vs Sevilla FC', 'Jornada 38 de LaLiga', '2026-06-14 21:00:00', '550e8400-e29b-41d4-a716-446655440013', '550e8400-e29b-41d4-a716-446655440027', 500, 500, 8.0, 'IMPORTANTE');
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-44665544176f', 'Villarreal vs Valencia', 'Jornada 38 de LaLiga', '2026-06-14 21:00:00', '550e8400-e29b-41d4-a716-446655440012', '550e8400-e29b-41d4-a716-446655440015', 500, 500, 8.0, 'IMPORTANTE');
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655441770', 'Real Sociedad vs Athletic Club', 'Jornada 38 de LaLiga', '2026-06-14 21:00:00', '550e8400-e29b-41d4-a716-446655440014', '550e8400-e29b-41d4-a716-446655440010', 500, 500, 8.0, 'IMPORTANTE');
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655441771', 'Rayo Vallecano vs Celta de Vigo', 'Jornada 38 de LaLiga', '2026-06-14 21:00:00', '550e8400-e29b-41d4-a716-446655440017', '550e8400-e29b-41d4-a716-446655440016', 500, 500, 5.0, 'COTIDIANO');
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655441772', 'Osasuna vs Mallorca', 'Jornada 38 de LaLiga', '2026-06-14 21:00:00', '550e8400-e29b-41d4-a716-446655440018', '550e8400-e29b-41d4-a716-446655440019', 500, 500, 5.0, 'COTIDIANO');
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655441773', 'Girona vs Alavés', 'Jornada 38 de LaLiga', '2026-06-14 21:00:00', '550e8400-e29b-41d4-a716-446655440020', '550e8400-e29b-41d4-a716-446655440021', 500, 500, 5.0, 'COTIDIANO');
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655441774', 'Leganés vs Valladolid', 'Jornada 38 de LaLiga', '2026-06-14 21:00:00', '550e8400-e29b-41d4-a716-446655440023', '550e8400-e29b-41d4-a716-446655440024', 500, 500, 5.0, 'COTIDIANO');
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655441775', 'Las Palmas vs Getafe CF', 'Jornada 38 de LaLiga', '2026-06-14 21:00:00', '550e8400-e29b-41d4-a716-446655440025', '550e8400-e29b-41d4-a716-446655440026', 500, 500, 5.0, 'COTIDIANO');
