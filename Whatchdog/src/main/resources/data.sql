-- Inserción de ciudadanos (Citizen)
INSERT INTO citizen (id, name, email, phone) VALUES ('1', 'Gonzalo Perez', 'juan.perez@example.com', '1234567890');
INSERT INTO citizen (id, name, email, phone) VALUES ('2', 'Maríana Gomez', 'maria.gomez@example.com', '0987654321');
INSERT INTO citizen (id, name, email, phone) VALUES ('3', 'francisco Ruiz', 'carlos.ruiz@example.com', '1122334455');

-- Inserción de problemas (Issue)
INSERT INTO issue (id, category, priority) VALUES ('4', 'Fugas de agua', 'ALTA');
INSERT INTO issue (id, category, priority) VALUES ('5', 'Alumbrado publico', 'MEDIA');
INSERT INTO issue (id, category, priority) VALUES ('6', 'Basura acumulada', 'BAJA');

-- Inserción de administradores (AdminC)
INSERT INTO adminc (id, name) VALUES ('7', 'Juan Perez');
INSERT INTO adminc (id, name) VALUES ('8', 'Maria Gomez');
INSERT INTO adminc (id, name) VALUES ('9', 'Carlos Ruiz');

-- Inserción de reportes (Report)
INSERT INTO report (id, description, citizen_id, issue_id, status,  create_date, update_date, foto_url, id_admin) 
VALUES ('10', 'Fuga de agua en la calle 123', 1, 4, 'EN_LISTA', NOW(), NOW(), 'http://example.com/foto1.jpg', '7');

INSERT INTO report (id, description, citizen_id, issue_id, status,  create_date, update_date, foto_url, id_admin) 
VALUES ('11', 'Alumbrado fallando en la avenida 456', 2, 5, 'EN_REVISION', NOW(), NOW(), 'http://example.com/foto2.jpg', '8');

INSERT INTO report (id, description, citizen_id, issue_id, status, create_date, update_date, foto_url, id_admin) 
VALUES ('12', 'Basura acumulada en el parque central', 3, 6, 'EN_PROCESO', NOW(), NOW(), 'http://example.com/foto3.jpg', '9');