-- Inserción de equipos
INSERT INTO equipo (id, nombre, foto_escudo, foto_fondo) VALUES ('550e8400-e29b-41d4-a716-446655440000', 'Real Madrid', 'foto_Escudo_rm.png', 'foto_Fondo_rm.jpg');
INSERT INTO equipo (id, nombre, foto_escudo, foto_fondo) VALUES ('550e8400-e29b-41d4-a716-446655440001', 'FC Barcelona', 'foto_Escudo_fcb.png', 'foto_Fondo_fcb.jpg');

-- Inserción de usuarios
INSERT INTO usuario (id, nombre, apellidos, username, correo, password, equipo_favorito_id, membresia_id, enabled, created_at) VALUES ('550e8400-e29b-41d4-a716-446655440002', 'Cristiano', 'Ronaldo', 'cr7', 'cr7@gmail.com', '{bcrypt}$2a$12$bTQej0W8ifZQJNNzW7yofOEhwNODlXPCn9uQyZTduZ9Ph/rAMEVNe', '550e8400-e29b-41d4-a716-446655440000', NULL, true, now());
INSERT INTO usuario (id, nombre, apellidos, username, correo, password, equipo_favorito_id, membresia_id, enabled, created_at) VALUES ('550e8400-e29b-41d4-a716-446655440003', 'Leo', 'Messi', 'messi', 'messi@gmail.com', '{bcrypt}$2a$10$3aG0Ydb9JhLxlf5fz9oNzeyQX/pEoHJyXJjZp7Nk8P7xP4BgWd/na', '550e8400-e29b-41d4-a716-446655440001', NULL, true, now());
-- INSERTAR UN ADMIN
INSERT INTO usuario (id, nombre, apellidos, username, correo, password, equipo_favorito_id, membresia_id, enabled, created_at) VALUES ('550e8400-e29b-41d4-a716-446655440004', 'Admin', 'Administradorez', 'admin', 'admin@lagrada.com', '{bcrypt}$2a$12$e1CQrst7qSuZbG/JQlUnBOKJz/uDjTXOFHofTLRPJWMddwTl/JRaa', NULL, NULL, true, now());

-- Inserción de evento
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES ('550e8400-e29b-41d4-a716-446655440005', 'Clásico', 'Partido entre Madrid y Barcelona', '2025-05-11 21:00:00', '550e8400-e29b-41d4-a716-446655440000', '550e8400-e29b-41d4-a716-446655440001', 500, 500, 12.0, 'FINAL');

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
