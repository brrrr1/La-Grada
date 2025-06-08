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
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655440005', 'Clásico', 'Partido entre Madrid y Barcelona', '2026-05-11 21:00:00', '550e8400-e29b-41d4-a716-446655440000', '550e8400-e29b-41d4-a716-446655440001', 500, 500, 12.0, 'FINAL');

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
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655440050', 'Madrid vs Betis (2022)', 'Partido jugado en 2022', '2022-05-11 21:00:00', '550e8400-e29b-41d4-a716-446655440000', '550e8400-e29b-41d4-a716-446655440013', 0, 500, 15.0, 'COTIDIANO');
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655440051', 'Barça vs Atlético (2023)', 'Partido jugado en 2023', '2023-03-15 20:00:00', '550e8400-e29b-41d4-a716-446655440001', '550e8400-e29b-41d4-a716-446655440011', 0, 500, 18.0, 'COTIDIANO');

-- Entradas para admin en eventos pasados
INSERT INTO entrada (id, usuario_id, evento_id) VALUES ('550e8400-e29b-41d4-a716-446655440060', '550e8400-e29b-41d4-a716-446655440004', '550e8400-e29b-41d4-a716-446655440050');
INSERT INTO entrada (id, usuario_id, evento_id) VALUES ('550e8400-e29b-41d4-a716-446655440061', '550e8400-e29b-41d4-a716-446655440004', '550e8400-e29b-41d4-a716-446655440051');

-- Jornada 1 (2025-2026)
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655440100', 'Real Madrid vs Getafe', 'Primera jornada de LaLiga', '2025-08-16 21:00:00', '550e8400-e29b-41d4-a716-446655440000', '550e8400-e29b-41d4-a716-446655440026', 500, 500, 5.0, 'COTIDIANO');
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655440101', 'Barcelona vs Sevilla', 'Primera jornada de LaLiga', '2025-08-17 18:30:00', '550e8400-e29b-41d4-a716-446655440001', '550e8400-e29b-41d4-a716-446655440027', 500, 500, 5.0, 'COTIDIANO');
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655440102', 'Atlético Madrid vs Athletic', 'Primera jornada de LaLiga', '2025-08-17 21:00:00', '550e8400-e29b-41d4-a716-446655440011', '550e8400-e29b-41d4-a716-446655440010', 500, 500, 5.0, 'COTIDIANO');

-- Jornada 2 (2025-2026)
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655440103', 'Sevilla vs Real Madrid', 'Segunda jornada de LaLiga', '2025-08-23 21:00:00', '550e8400-e29b-41d4-a716-446655440027', '550e8400-e29b-41d4-a716-446655440000', 500, 500, 5.0, 'COTIDIANO');
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655440104', 'Getafe vs Barcelona', 'Segunda jornada de LaLiga', '2025-08-24 18:30:00', '550e8400-e29b-41d4-a716-446655440026', '550e8400-e29b-41d4-a716-446655440001', 500, 500, 5.0, 'COTIDIANO');

-- Jornada 3 (2025-2026) - Primer Clásico
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655440105', 'Real Madrid vs Barcelona', 'Primer Clásico de la temporada', '2025-08-30 21:00:00', '550e8400-e29b-41d4-a716-446655440000', '550e8400-e29b-41d4-a716-446655440001', 500, 500, 12.0, 'FINAL');

-- Jornada 4 (2025-2026) - Derbi Madrileño
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655440106', 'Atlético Madrid vs Real Madrid', 'Derbi Madrileño', '2025-09-14 21:00:00', '550e8400-e29b-41d4-a716-446655440011', '550e8400-e29b-41d4-a716-446655440000', 500, 500, 8.0, 'IMPORTANTE');

-- Jornada 5 (2025-2026)
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655440107', 'Barcelona vs Atlético Madrid', 'Quinta jornada de LaLiga', '2025-09-21 21:00:00', '550e8400-e29b-41d4-a716-446655440001', '550e8400-e29b-41d4-a716-446655440011', 500, 500, 5.0, 'COTIDIANO');

-- Jornada 6 (2025-2026) - Derbi Sevillano
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655440108', 'Sevilla vs Betis', 'Derbi Sevillano', '2025-09-28 21:00:00', '550e8400-e29b-41d4-a716-446655440027', '550e8400-e29b-41d4-a716-446655440013', 500, 500, 8.0, 'IMPORTANTE');

-- Jornada 7 (2025-2026)
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655440109', 'Real Madrid vs Sevilla', 'Séptima jornada de LaLiga', '2025-10-05 21:00:00', '550e8400-e29b-41d4-a716-446655440000', '550e8400-e29b-41d4-a716-446655440027', 500, 500, 5.0, 'COTIDIANO');

-- Jornada 8 (2025-2026)
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655440110', 'Barcelona vs Real Madrid', 'Segundo Clásico de la temporada', '2025-10-26 21:00:00', '550e8400-e29b-41d4-a716-446655440001', '550e8400-e29b-41d4-a716-446655440000', 500, 500, 12.0, 'FINAL');

-- Jornada 9 (2025-2026)
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655440111', 'Getafe vs Atlético Madrid', 'Novena jornada de LaLiga', '2025-11-02 21:00:00', '550e8400-e29b-41d4-a716-446655440026', '550e8400-e29b-41d4-a716-446655440011', 500, 500, 5.0, 'COTIDIANO');

-- Jornada 10 (2025-2026)
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655440112', 'Real Madrid vs Getafe', 'Décima jornada de LaLiga', '2025-11-09 21:00:00', '550e8400-e29b-41d4-a716-446655440000', '550e8400-e29b-41d4-a716-446655440026', 500, 500, 5.0, 'COTIDIANO');

-- Jornada 11 (2025-2026)
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655440113', 'Real Madrid vs Atlético Madrid', 'Derbi Madrileño', '2025-11-23 21:00:00', '550e8400-e29b-41d4-a716-446655440000', '550e8400-e29b-41d4-a716-446655440011', 500, 500, 8.0, 'IMPORTANTE');

-- Jornada 12 (2025-2026)
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655440114', 'Barcelona vs Sevilla', 'Duodécima jornada de LaLiga', '2025-11-30 21:00:00', '550e8400-e29b-41d4-a716-446655440001', '550e8400-e29b-41d4-a716-446655440027', 500, 500, 5.0, 'COTIDIANO');

-- Jornada 13 (2025-2026)
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655440115', 'Sevilla vs Getafe', 'Decimotercera jornada de LaLiga', '2025-12-07 21:00:00', '550e8400-e29b-41d4-a716-446655440027', '550e8400-e29b-41d4-a716-446655440026', 500, 500, 5.0, 'COTIDIANO');

-- Jornada 14 (2025-2026)
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655440116', 'Atlético Madrid vs Barcelona', 'Decimocuarta jornada de LaLiga', '2025-12-14 21:00:00', '550e8400-e29b-41d4-a716-446655440011', '550e8400-e29b-41d4-a716-446655440001', 500, 500, 5.0, 'COTIDIANO');

-- Jornada 15 (2025-2026)
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655440117', 'Betis vs Sevilla', 'Derbi Sevillano', '2025-12-21 21:00:00', '550e8400-e29b-41d4-a716-446655440013', '550e8400-e29b-41d4-a716-446655440027', 500, 500, 8.0, 'IMPORTANTE');

-- Jornada 16 (2025-2026)
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655440118', 'Getafe vs Real Madrid', 'Decimosexta jornada de LaLiga', '2026-01-04 21:00:00', '550e8400-e29b-41d4-a716-446655440026', '550e8400-e29b-41d4-a716-446655440000', 500, 500, 5.0, 'COTIDIANO');

-- Jornada 17 (2025-2026)
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655440119', 'Barcelona vs Getafe', 'Decimoséptima jornada de LaLiga', '2026-01-11 21:00:00', '550e8400-e29b-41d4-a716-446655440001', '550e8400-e29b-41d4-a716-446655440026', 500, 500, 5.0, 'COTIDIANO');

-- Jornada 18 (2025-2026)
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655440120', 'Sevilla vs Barcelona', 'Decimoctava jornada de LaLiga', '2026-01-18 21:00:00', '550e8400-e29b-41d4-a716-446655440027', '550e8400-e29b-41d4-a716-446655440001', 500, 500, 5.0, 'COTIDIANO');

-- Jornada 19 (2025-2026)
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655440121', 'Real Madrid vs Sevilla', 'Decimonovena jornada de LaLiga', '2026-01-25 21:00:00', '550e8400-e29b-41d4-a716-446655440000', '550e8400-e29b-41d4-a716-446655440027', 500, 500, 5.0, 'COTIDIANO');

-- Jornada 20 (2025-2026)
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655440122', 'Real Madrid vs Barcelona', 'Tercer Clásico de la temporada', '2026-02-01 21:00:00', '550e8400-e29b-41d4-a716-446655440000', '550e8400-e29b-41d4-a716-446655440001', 500, 500, 12.0, 'FINAL');

-- Jornada 21 (2025-2026)
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655440123', 'Getafe vs Sevilla', 'Vigésima primera jornada de LaLiga', '2026-02-08 21:00:00', '550e8400-e29b-41d4-a716-446655440026', '550e8400-e29b-41d4-a716-446655440027', 500, 500, 5.0, 'COTIDIANO');

-- Jornada 22 (2025-2026)
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655440124', 'Atlético Madrid vs Real Madrid', 'Derbi Madrileño', '2026-02-15 21:00:00', '550e8400-e29b-41d4-a716-446655440011', '550e8400-e29b-41d4-a716-446655440000', 500, 500, 8.0, 'IMPORTANTE');

-- Jornada 23 (2025-2026)
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655440125', 'Barcelona vs Atlético Madrid', 'Vigésima tercera jornada de LaLiga', '2026-02-22 21:00:00', '550e8400-e29b-41d4-a716-446655440001', '550e8400-e29b-41d4-a716-446655440011', 500, 500, 5.0, 'COTIDIANO');

-- Jornada 24 (2025-2026)
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655440126', 'Sevilla vs Betis', 'Derbi Sevillano', '2026-03-01 21:00:00', '550e8400-e29b-41d4-a716-446655440027', '550e8400-e29b-41d4-a716-446655440013', 500, 500, 8.0, 'IMPORTANTE');

-- Jornada 25 (2025-2026)
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655440127', 'Real Madrid vs Getafe', 'Vigésima quinta jornada de LaLiga', '2026-03-08 21:00:00', '550e8400-e29b-41d4-a716-446655440000', '550e8400-e29b-41d4-a716-446655440026', 500, 500, 5.0, 'COTIDIANO');

-- Jornada 26 (2025-2026)
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655440128', 'Barcelona vs Sevilla', 'Vigésima sexta jornada de LaLiga', '2026-03-15 21:00:00', '550e8400-e29b-41d4-a716-446655440001', '550e8400-e29b-41d4-a716-446655440027', 500, 500, 5.0, 'COTIDIANO');

-- Jornada 27 (2025-2026)
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655440129', 'Sevilla vs Real Madrid', 'Vigésima séptima jornada de LaLiga', '2026-03-22 21:00:00', '550e8400-e29b-41d4-a716-446655440027', '550e8400-e29b-41d4-a716-446655440000', 500, 500, 5.0, 'COTIDIANO');

-- Jornada 28 (2025-2026)
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655440130', 'Getafe vs Barcelona', 'Vigésima octava jornada de LaLiga', '2026-04-05 21:00:00', '550e8400-e29b-41d4-a716-446655440026', '550e8400-e29b-41d4-a716-446655440001', 500, 500, 5.0, 'COTIDIANO');

-- Jornada 29 (2025-2026)
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655440131', 'Real Madrid vs Atlético Madrid', 'Vigésima novena jornada de LaLiga', '2026-04-12 21:00:00', '550e8400-e29b-41d4-a716-446655440000', '550e8400-e29b-41d4-a716-446655440011', 500, 500, 5.0, 'COTIDIANO');

-- Jornada 30 (2025-2026)
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655440132', 'Barcelona vs Getafe', 'Trigésima jornada de LaLiga', '2026-04-19 21:00:00', '550e8400-e29b-41d4-a716-446655440001', '550e8400-e29b-41d4-a716-446655440026', 500, 500, 5.0, 'COTIDIANO');

-- Jornada 31 (2025-2026)
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655440133', 'Sevilla vs Real Madrid', 'Trigésima primera jornada de LaLiga', '2026-04-26 21:00:00', '550e8400-e29b-41d4-a716-446655440027', '550e8400-e29b-41d4-a716-446655440000', 500, 500, 5.0, 'COTIDIANO');

-- Jornada 32 (2025-2026)
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655440134', 'Barcelona vs Real Madrid', 'Cuarto Clásico de la temporada', '2026-05-03 21:00:00', '550e8400-e29b-41d4-a716-446655440001', '550e8400-e29b-41d4-a716-446655440000', 500, 500, 12.0, 'FINAL');

-- Jornada 33 (2025-2026)
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655440135', 'Getafe vs Sevilla', 'Trigésima tercera jornada de LaLiga', '2026-05-10 21:00:00', '550e8400-e29b-41d4-a716-446655440026', '550e8400-e29b-41d4-a716-446655440027', 500, 500, 5.0, 'COTIDIANO');

-- Jornada 34 (2025-2026)
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655440136', 'Real Madrid vs Getafe', 'Trigésima cuarta jornada de LaLiga', '2026-05-17 21:00:00', '550e8400-e29b-41d4-a716-446655440000', '550e8400-e29b-41d4-a716-446655440026', 500, 500, 5.0, 'COTIDIANO');

-- Jornada 35 (2025-2026)
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655440137', 'Barcelona vs Sevilla', 'Trigésima quinta jornada de LaLiga', '2026-05-24 21:00:00', '550e8400-e29b-41d4-a716-446655440001', '550e8400-e29b-41d4-a716-446655440027', 500, 500, 5.0, 'COTIDIANO');

-- Jornada 36 (2025-2026)
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655440138', 'Sevilla vs Real Madrid', 'Trigésima sexta jornada de LaLiga', '2026-05-31 21:00:00', '550e8400-e29b-41d4-a716-446655440027', '550e8400-e29b-41d4-a716-446655440000', 500, 500, 5.0, 'COTIDIANO');

-- Jornada 37 (2025-2026)
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655440139', 'Getafe vs Barcelona', 'Trigésima séptima jornada de LaLiga', '2026-06-07 21:00:00', '550e8400-e29b-41d4-a716-446655440026', '550e8400-e29b-41d4-a716-446655440001', 500, 500, 5.0, 'COTIDIANO');

-- Jornada 38 (2025-2026) - Última jornada
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655440140', 'Real Madrid vs Barcelona', 'Último Clásico de la temporada', '2026-06-14 21:00:00', '550e8400-e29b-41d4-a716-446655440000', '550e8400-e29b-41d4-a716-446655440001', 500, 500, 12.0, 'FINAL');
