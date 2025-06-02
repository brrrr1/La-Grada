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

-- Inserción de usuarios
INSERT INTO usuario (id, nombre, apellidos, username, correo, password, equipo_favorito_id, membresia_id, enabled, created_at) VALUES ('550e8400-e29b-41d4-a716-446655440002', 'Cristiano', 'Ronaldo', 'cr7', 'cr7@gmail.com', '{bcrypt}$2a$12$bTQej0W8ifZQJNNzW7yofOEhwNODlXPCn9uQyZTduZ9Ph/rAMEVNe', '550e8400-e29b-41d4-a716-446655440000', NULL, true, now());
INSERT INTO usuario (id, nombre, apellidos, username, correo, password, equipo_favorito_id, membresia_id, enabled, created_at) VALUES ('550e8400-e29b-41d4-a716-446655440003', 'Leo', 'Messi', 'messi', 'messi@gmail.com', '{bcrypt}$2a$10$3aG0Ydb9JhLxlf5fz9oNzeyQX/pEoHJyXJjZp7Nk8P7xP4BgWd/na', '550e8400-e29b-41d4-a716-446655440001', NULL, true, now());
-- INSERTAR UN ADMIN
INSERT INTO usuario (id, nombre, apellidos, username, correo, password, equipo_favorito_id, membresia_id, enabled, created_at) VALUES ('550e8400-e29b-41d4-a716-446655440004', 'Admin', 'Administradorez', 'admin', 'admin@lagrada.com', '{bcrypt}$2a$12$e1CQrst7qSuZbG/JQlUnBOKJz/uDjTXOFHofTLRPJWMddwTl/JRaa', NULL, NULL, true, now());

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

-- Jornada 1
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655440030', 'Athletic Club vs Atlético de Madrid', 'Partido de la jornada 1 de LaLiga que enfrenta a Athletic Club y Atlético de Madrid.', '2026-05-12 21:00:00', '550e8400-e29b-41d4-a716-446655440010', '550e8400-e29b-41d4-a716-446655440011', 500, 500, 12.0, 'COTIDIANO');
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655440031', 'Villarreal vs Real Betis', 'Partido de la jornada 1 de LaLiga que enfrenta a Villarreal y Real Betis.', '2026-05-12 21:00:00', '550e8400-e29b-41d4-a716-446655440012', '550e8400-e29b-41d4-a716-446655440013', 500, 500, 12.0, 'COTIDIANO');
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655440032', 'Real Sociedad vs Valencia', 'Partido de la jornada 1 de LaLiga que enfrenta a Real Sociedad y Valencia.', '2026-05-12 21:00:00', '550e8400-e29b-41d4-a716-446655440014', '550e8400-e29b-41d4-a716-446655440015', 500, 500, 12.0, 'COTIDIANO');
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655440033', 'Celta de Vigo vs Rayo Vallecano', 'Partido de la jornada 1 de LaLiga que enfrenta a Celta de Vigo y Rayo Vallecano.', '2026-05-12 21:00:00', '550e8400-e29b-41d4-a716-446655440016', '550e8400-e29b-41d4-a716-446655440017', 500, 500, 12.0, 'COTIDIANO');
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655440034', 'Osasuna vs Mallorca', 'Partido de la jornada 1 de LaLiga que enfrenta a Osasuna y Mallorca.', '2026-05-12 21:00:00', '550e8400-e29b-41d4-a716-446655440018', '550e8400-e29b-41d4-a716-446655440019', 500, 500, 12.0, 'COTIDIANO');
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655440035', 'Girona vs Alavés', 'Partido de la jornada 1 de LaLiga que enfrenta a Girona y Alavés.', '2026-05-12 21:00:00', '550e8400-e29b-41d4-a716-446655440020', '550e8400-e29b-41d4-a716-446655440021', 500, 500, 12.0, 'COTIDIANO');
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655440036', 'Espanyol vs Leganés', 'Partido de la jornada 1 de LaLiga que enfrenta a Espanyol y Leganés.', '2026-05-12 21:00:00', '550e8400-e29b-41d4-a716-446655440022', '550e8400-e29b-41d4-a716-446655440023', 500, 500, 12.0, 'COTIDIANO');
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655440037', 'Valladolid vs Las Palmas', 'Partido de la jornada 1 de LaLiga que enfrenta a Valladolid y Las Palmas.', '2026-05-12 21:00:00', '550e8400-e29b-41d4-a716-446655440024', '550e8400-e29b-41d4-a716-446655440025', 500, 500, 12.0, 'COTIDIANO');

-- Jornada 2
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655440040', 'Atlético de Madrid vs Villarreal', 'Partido de la jornada 2 de LaLiga que enfrenta a Atlético de Madrid y Villarreal.', '2026-05-19 21:00:00', '550e8400-e29b-41d4-a716-446655440011', '550e8400-e29b-41d4-a716-446655440012', 500, 500, 12.0, 'COTIDIANO');
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655440041', 'Real Betis vs Real Sociedad', 'Partido de la jornada 2 de LaLiga que enfrenta a Real Betis y Real Sociedad.', '2026-05-19 21:00:00', '550e8400-e29b-41d4-a716-446655440013', '550e8400-e29b-41d4-a716-446655440014', 500, 500, 12.0, 'COTIDIANO');
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655440042', 'Valencia vs Celta de Vigo', 'Partido de la jornada 2 de LaLiga que enfrenta a Valencia y Celta de Vigo.', '2026-05-19 21:00:00', '550e8400-e29b-41d4-a716-446655440015', '550e8400-e29b-41d4-a716-446655440016', 500, 500, 12.0, 'COTIDIANO');
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655440043', 'Rayo Vallecano vs Osasuna', 'Partido de la jornada 2 de LaLiga que enfrenta a Rayo Vallecano y Osasuna.', '2026-05-19 21:00:00', '550e8400-e29b-41d4-a716-446655440017', '550e8400-e29b-41d4-a716-446655440018', 500, 500, 12.0, 'COTIDIANO');
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655440044', 'Mallorca vs Girona', 'Partido de la jornada 2 de LaLiga que enfrenta a Mallorca y Girona.', '2026-05-19 21:00:00', '550e8400-e29b-41d4-a716-446655440019', '550e8400-e29b-41d4-a716-446655440020', 500, 500, 12.0, 'COTIDIANO');
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655440045', 'Alavés vs Espanyol', 'Partido de la jornada 2 de LaLiga que enfrenta a Alavés y Espanyol.', '2026-05-19 21:00:00', '550e8400-e29b-41d4-a716-446655440021', '550e8400-e29b-41d4-a716-446655440022', 500, 500, 12.0, 'COTIDIANO');
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655440046', 'Leganés vs Valladolid', 'Partido de la jornada 2 de LaLiga que enfrenta a Leganés y Valladolid.', '2026-05-19 21:00:00', '550e8400-e29b-41d4-a716-446655440023', '550e8400-e29b-41d4-a716-446655440024', 500, 500, 12.0, 'COTIDIANO');
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655440047', 'Las Palmas vs Athletic Club', 'Partido de la jornada 2 de LaLiga que enfrenta a Las Palmas y Athletic Club.', '2026-05-19 21:00:00', '550e8400-e29b-41d4-a716-446655440025', '550e8400-e29b-41d4-a716-446655440010', 500, 500, 12.0, 'COTIDIANO');

-- Eventos pasados para admin
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655440050', 'Madrid vs Betis (2022)', 'Partido jugado en 2022', '2022-05-11 21:00:00', '550e8400-e29b-41d4-a716-446655440000', '550e8400-e29b-41d4-a716-446655440013', 0, 500, 15.0, 'COTIDIANO');
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655440051', 'Barça vs Atlético (2023)', 'Partido jugado en 2023', '2023-03-15 20:00:00', '550e8400-e29b-41d4-a716-446655440001', '550e8400-e29b-41d4-a716-446655440011', 0, 500, 18.0, 'COTIDIANO');

-- Entradas para admin en eventos pasados
INSERT INTO entrada (id, usuario_id, evento_id) VALUES ('550e8400-e29b-41d4-a716-446655440060', '550e8400-e29b-41d4-a716-446655440004', '550e8400-e29b-41d4-a716-446655440050');
INSERT INTO entrada (id, usuario_id, evento_id) VALUES ('550e8400-e29b-41d4-a716-446655440061', '550e8400-e29b-41d4-a716-446655440004', '550e8400-e29b-41d4-a716-446655440051');
