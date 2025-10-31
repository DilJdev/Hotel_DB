-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 23-10-2025 a las 18:25:36
-- Versión del servidor: 10.4.28-MariaDB
-- Versión de PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `hotel_db`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `habitacion`
--

CREATE TABLE `habitacion` (
  `numhabitacion` int(11) NOT NULL,
  `estado` varchar(45) DEFAULT 'Disponible',
  `idtipohabitacion` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `hospedaje`
--

CREATE TABLE `hospedaje` (
  `idhospedaje` int(11) NOT NULL,
  `fechaingreso` date NOT NULL,
  `fechasalida` date DEFAULT NULL,
  `horaingreso` time NOT NULL,
  `horasalida` time DEFAULT NULL,
  `importetotal` decimal(10,2) DEFAULT 0.00,
  `idpersonal` int(11) NOT NULL,
  `numhabitacion` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `hospedajehuesped`
--

CREATE TABLE `hospedajehuesped` (
  `idhospedaje` int(11) NOT NULL,
  `idhuesped` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `huesped`
--

CREATE TABLE `huesped` (
  `ididentificacionhuesped` int(11) NOT NULL,
  `cedulaidentidad` varchar(45) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `paterno` varchar(45) NOT NULL,
  `materno` varchar(45) DEFAULT NULL,
  `direccion` varchar(150) DEFAULT NULL,
  `fechanacimiento` date DEFAULT NULL,
  `estadocivil` varchar(45) DEFAULT NULL,
  `tipohuesped` varchar(45) DEFAULT NULL,
  `telefono` varchar(45) DEFAULT NULL,
  `idprocedencia` int(11) DEFAULT NULL,
  `idprofesion` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pais`
--

CREATE TABLE `pais` (
  `idpais` int(11) NOT NULL,
  `descripcionpais` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `personal`
--

CREATE TABLE `personal` (
  `idpersonal` int(11) NOT NULL,
  `cedulaidentidad` varchar(45) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `paterno` varchar(45) NOT NULL,
  `materno` varchar(45) DEFAULT NULL,
  `fechaingreso` date NOT NULL,
  `direccion` varchar(150) DEFAULT NULL,
  `telefono` varchar(45) DEFAULT NULL,
  `tipperonal` varchar(45) NOT NULL,
  `estado` varchar(45) DEFAULT 'Activo',
  `usuario` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `procedencia`
--

CREATE TABLE `procedencia` (
  `idprocedencia` int(11) NOT NULL,
  `descripcionprocedencia` varchar(150) NOT NULL,
  `idpais` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `profesion`
--

CREATE TABLE `profesion` (
  `idprofesion` int(11) NOT NULL,
  `descripcionprofesion` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipohabitacion`
--

CREATE TABLE `tipohabitacion` (
  `idtipohabitacion` int(11) NOT NULL,
  `descripcionhabitacion` varchar(150) NOT NULL,
  `precio` decimal(10,2) NOT NULL,
  `numpersonas` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `habitacion`
--
ALTER TABLE `habitacion`
  ADD PRIMARY KEY (`numhabitacion`),
  ADD KEY `idtipohabitacion` (`idtipohabitacion`);

--
-- Indices de la tabla `hospedaje`
--
ALTER TABLE `hospedaje`
  ADD PRIMARY KEY (`idhospedaje`),
  ADD KEY `idpersonal` (`idpersonal`),
  ADD KEY `numhabitacion` (`numhabitacion`);

--
-- Indices de la tabla `hospedajehuesped`
--
ALTER TABLE `hospedajehuesped`
  ADD UNIQUE KEY `unique_hospedaje_huesped` (`idhospedaje`,`idhuesped`),
  ADD KEY `idhuesped` (`idhuesped`);

--
-- Indices de la tabla `huesped`
--
ALTER TABLE `huesped`
  ADD PRIMARY KEY (`ididentificacionhuesped`),
  ADD UNIQUE KEY `cedulaidentidad` (`cedulaidentidad`),
  ADD KEY `idprocedencia` (`idprocedencia`),
  ADD KEY `idprofesion` (`idprofesion`);

--
-- Indices de la tabla `pais`
--
ALTER TABLE `pais`
  ADD PRIMARY KEY (`idpais`);

--
-- Indices de la tabla `personal`
--
ALTER TABLE `personal`
  ADD PRIMARY KEY (`idpersonal`),
  ADD UNIQUE KEY `cedulaidentidad` (`cedulaidentidad`),
  ADD UNIQUE KEY `usuario` (`usuario`);

--
-- Indices de la tabla `procedencia`
--
ALTER TABLE `procedencia`
  ADD PRIMARY KEY (`idprocedencia`),
  ADD KEY `idpais` (`idpais`);

--
-- Indices de la tabla `profesion`
--
ALTER TABLE `profesion`
  ADD PRIMARY KEY (`idprofesion`);

--
-- Indices de la tabla `tipohabitacion`
--
ALTER TABLE `tipohabitacion`
  ADD PRIMARY KEY (`idtipohabitacion`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `hospedaje`
--
ALTER TABLE `hospedaje`
  MODIFY `idhospedaje` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `huesped`
--
ALTER TABLE `huesped`
  MODIFY `ididentificacionhuesped` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `pais`
--
ALTER TABLE `pais`
  MODIFY `idpais` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `personal`
--
ALTER TABLE `personal`
  MODIFY `idpersonal` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `procedencia`
--
ALTER TABLE `procedencia`
  MODIFY `idprocedencia` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `profesion`
--
ALTER TABLE `profesion`
  MODIFY `idprofesion` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `tipohabitacion`
--
ALTER TABLE `tipohabitacion`
  MODIFY `idtipohabitacion` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `habitacion`
--
ALTER TABLE `habitacion`
  ADD CONSTRAINT `habitacion_ibfk_1` FOREIGN KEY (`idtipohabitacion`) REFERENCES `tipohabitacion` (`idtipohabitacion`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `hospedaje`
--
ALTER TABLE `hospedaje`
  ADD CONSTRAINT `hospedaje_ibfk_1` FOREIGN KEY (`idpersonal`) REFERENCES `personal` (`idpersonal`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `hospedaje_ibfk_2` FOREIGN KEY (`numhabitacion`) REFERENCES `habitacion` (`numhabitacion`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `hospedajehuesped`
--
ALTER TABLE `hospedajehuesped`
  ADD CONSTRAINT `hospedajehuesped_ibfk_1` FOREIGN KEY (`idhospedaje`) REFERENCES `hospedaje` (`idhospedaje`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `hospedajehuesped_ibfk_2` FOREIGN KEY (`idhuesped`) REFERENCES `huesped` (`ididentificacionhuesped`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `huesped`
--
ALTER TABLE `huesped`
  ADD CONSTRAINT `huesped_ibfk_1` FOREIGN KEY (`idprocedencia`) REFERENCES `procedencia` (`idprocedencia`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `huesped_ibfk_2` FOREIGN KEY (`idprofesion`) REFERENCES `profesion` (`idprofesion`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Filtros para la tabla `procedencia`
--
ALTER TABLE `procedencia`
  ADD CONSTRAINT `procedencia_ibfk_1` FOREIGN KEY (`idpais`) REFERENCES `pais` (`idpais`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
