-- Datos de muestra para la base de datos del hotel
-- Archivo: datos_muestra.sql

-- Insertar tipos de habitación
INSERT INTO tipohabitacion (idtipohabitacion, nombre, descripcion, capacidad, precio) VALUES
(1, 'Individual', 'Habitación individual con cama simple', 1, 250.00),
(2, 'Doble', 'Habitación doble con cama matrimonial', 2, 400.00),
(3, 'Suite', 'Suite junior con sala de estar', 3, 650.00),
(4, 'Presidencial', 'Suite presidencial con amenities premium', 4, 1200.00);

-- Insertar habitaciones
INSERT INTO habitacion (numhabitacion, estado, idtipohabitacion) VALUES
(101, 'Libre', 1),
(102, 'Libre', 1),
(103, 'Libre', 1),
(104, 'Libre', 1),
(105, 'Libre', 1),
(201, 'Libre', 2),
(202, 'Libre', 2),
(203, 'Libre', 2),
(204, 'Libre', 2),
(205, 'Libre', 2),
(301, 'Libre', 3),
(302, 'Libre', 3),
(303, 'Libre', 3),
(401, 'Libre', 4),
(402, 'Libre', 4);

-- Insertar personal
INSERT INTO personal (nombre, paterno, materno, cedulaidentidad, telefono, direccion, estado, fechaingreso, tipperonal, usuario, password) VALUES
('Juan', 'Pérez', 'González', '12345678', '71234567', 'Calle Principal 123', 'Activo', '2023-01-15', 'Administrador', 'jperez', 'admin123'),
('María', 'Rodríguez', 'López', '87654321', '72345678', 'Avenida Central 456', 'Activo', '2023-02-20', 'Recepcionista', 'mrodriguez', 'recepcion123'),
('Carlos', 'Martínez', 'Sánchez', '11223344', '73456789', 'Plaza Mayor 789', 'Activo', '2023-03-10', 'Recepcionista', 'cmartinez', 'recepcion123'),
('Ana', 'García', 'Fernández', '55667788', '74567890', 'Calle Secundaria 321', 'Activo', '2023-04-05', 'Administrador', 'agarcia', 'admin123'),
('Luis', 'Hernández', 'Díaz', '99887766', '75678901', 'Boulevard 654', 'Activo', '2023-05-12', 'Mantenimiento', 'lhernandez', 'mantenimiento123');

-- Insertar huéspedes
INSERT INTO huesped (nombre, paterno, materno, cedulaidentidad, telefono, direccion, estadocivil, nacionalidad) VALUES
('Roberto', 'Silva', 'Mendoza', '11112222', '76789012', 'La Paz, Bolivia', 'Casado', 'Boliviano'),
('Carmen', 'Torres', 'Vargas', '33334444', '77890123', 'Cochabamba, Bolivia', 'Soltera', 'Boliviana'),
('Miguel', 'Ramírez', 'Quispe', '55556666', '78901234', 'Santa Cruz, Bolivia', 'Casado', 'Boliviano'),
('Elena', 'Mendoza', 'Paredes', '77778888', '79012345', 'Sucre, Bolivia', 'Divorciada', 'Boliviana'),
('Diego', 'Vargas', 'Soto', '99990000', '80123456', 'Potosí, Bolivia', 'Soltero', 'Boliviano'),
('Sofía', 'Quispe', 'Luna', '12131415', '81234567', 'Oruro, Bolivia', 'Casada', 'Boliviana'),
('Andrés', 'Paredes', 'Rojas', '16171819', '82345678', 'Beni, Bolivia', 'Soltero', 'Boliviano'),
('Lucía', 'Soto', 'Mamani', '20212223', '83456789', 'Pando, Bolivia', 'Soltera', 'Boliviana'),
('Fernando', 'Luna', 'Choque', '24252627', '84567890', 'Tarija, Bolivia', 'Casado', 'Boliviano'),
('Isabel', 'Rojas', 'Aguirre', '28293031', '85678901', 'Chuquisaca, Bolivia', 'Viuda', 'Boliviana');

-- Insertar algunos hospedajes de ejemplo
INSERT INTO hospedaje (idhospedaje, fechaingreso, fechasalida, numhabitacion) VALUES
(1, '2024-11-15', '2024-11-18', 101),
(2, '2024-11-16', '2024-11-20', 201),
(3, '2024-11-17', '2024-11-19', 301);

-- Insertar relaciones hospedaje-huésped
INSERT INTO hospedajehuesped (idhospedaje, cedulaidentidad) VALUES
(1, '11112222'),
(1, '33334444'),
(2, '55556666'),
(3, '77778888'),
(3, '99990000');

-- Actualizar estados de habitaciones ocupadas
UPDATE habitacion SET estado = 'Ocupado' WHERE numhabitacion IN (101, 201, 301);
