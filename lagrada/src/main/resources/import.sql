-- Inserción de equipos
INSERT INTO equipo (id, nombre, foto_escudo, foto_fondo) VALUES (gen_random_uuid(), 'Real Madrid', 'foto_Escudo_rm.png', 'foto_Fondo_rm.jpg');
INSERT INTO equipo (id, nombre, foto_escudo, foto_fondo) VALUES (gen_random_uuid(), 'FC Barcelona', 'foto_Escudo_fcb.png', 'foto_Fondo_fcb.jpg');

-- Inserción de usuarios
INSERT INTO usuario (id, nombre, apellidos, username, correo, password, equipo_favorito_id, membresia_id, enabled, created_at) VALUES (gen_random_uuid(), 'Juan', 'Pérez', 'juanp', 'juan@example.com', '{bcrypt}$2a$10$7QnBzDq6w.vZp/BfIxE9iOq5MyZ2jibXJQjvo6lKe6PCD1SpHf/QC', (SELECT id FROM equipo ORDER BY random() LIMIT 1), NULL, true, now());
INSERT INTO usuario (id, nombre, apellidos, username, correo, password, equipo_favorito_id, membresia_id, enabled, created_at) VALUES (gen_random_uuid(), 'Ana', 'Gómez', 'anag', 'ana@example.com', '{bcrypt}$2a$10$3aG0Ydb9JhLxlf5fz9oNzeyQX/pEoHJyXJjZp7Nk8P7xP4BgWd/na', (SELECT id FROM equipo ORDER BY random() LIMIT 1), NULL, true, now());
--INSERTAR UN ADMIN
INSERT INTO usuario (id, nombre, apellidos, username, correo, password, equipo_favorito_id, membresia_id, enabled, created_at) VALUES (gen_random_uuid(), 'Admin', 'Administradorez', 'admin', 'admin@lagrada.com', '{bcrypt}$2a$12$e1CQrst7qSuZbG/JQlUnBOKJz/uDjTXOFHofTLRPJWMddwTl/JRaa', NULL, NULL, true, now());
-- Inserción de evento
INSERT INTO evento (id, nombre, descripcion, fechayhora, equipo1_id, equipo2_id, entradas_restantes, entradas_totales, precio, tipo_evento) VALUES (gen_random_uuid(), 'Clásico', 'Partido entre Madrid y Barcelona', now() + interval '7 days', (SELECT id FROM equipo WHERE nombre = 'Real Madrid' LIMIT 1), (SELECT id FROM equipo WHERE nombre = 'FC Barcelona' LIMIT 1), 500, 500, 12.0, 'FINAL');

-- Inserción de entradas
INSERT INTO entrada (id, usuario_id, evento_id) VALUES (gen_random_uuid(), (SELECT id FROM usuario ORDER BY random() LIMIT 1), (SELECT id FROM evento ORDER BY random() LIMIT 1));
INSERT INTO entrada (id, usuario_id, evento_id) VALUES (gen_random_uuid(), (SELECT id FROM usuario ORDER BY random() LIMIT 1), (SELECT id FROM evento ORDER BY random() LIMIT 1));

-- Inserción de membresía
--INSERT INTO membresia (id, nombre, precio, descripcion, tipo_membresia) VALUES (gen_random_uuid(), 'Membresía Plus', 100.0, 'Acceso a eventos importantes', 'PLUS');
INSERT INTO membresia (membresias_restantes, precio, equipo_id, id, tipo_membresia, descripcion, nombre, tipo) VALUES (100, 100.0, (SELECT id FROM equipo WHERE nombre='Real Madrid'), gen_random_uuid(), 'PLUS', 'Acceso a eventos importantes', 'Membresía Plus', 'PLUS');
INSERT INTO membresia (membresias_restantes, precio, equipo_id, id, tipo_membresia, descripcion, nombre, tipo) VALUES (-1, 100.0, null, gen_random_uuid(), 'PLUS', 'Acceso a eventos importantes', 'Membresía Global PLUS', 'PLUS');

-- Inserción de membresía equipo
--INSERT INTO membresia_equipo (id, equipo_id, membresias_restantes) VALUES (gen_random_uuid(), (SELECT id FROM equipo ORDER BY random() LIMIT 1), 100);

-- Inserción de usuario con membresía
INSERT INTO usuario (id, nombre, apellidos, username, correo, password, equipo_favorito_id, membresia_id, enabled, created_at) VALUES (gen_random_uuid(), 'Carlos', 'López', 'carlosl', 'carlos@example.com', '$2a$10$b8QpE4E8Xz8cO7AqZw6qhe2q/K2kq5mQpmN8eLy0FzJrDbE3mA99.', (SELECT id FROM equipo ORDER BY random() LIMIT 1), (SELECT id FROM membresia ORDER BY random() LIMIT 1), true, now());

--insercion de roles
INSERT INTO user_roles (roles, user_id) VALUES (0, (SELECT id FROM usuario WHERE id = (SELECT id FROM usuario WHERE username = 'juanp')));
INSERT INTO user_roles (roles, user_id) VALUES (0, (SELECT id FROM usuario WHERE id = (SELECT id FROM usuario WHERE username = 'anag')));
INSERT INTO user_roles (roles, user_id) VALUES (1, (SELECT id FROM usuario WHERE id = (SELECT id FROM usuario WHERE username = 'admin')));
