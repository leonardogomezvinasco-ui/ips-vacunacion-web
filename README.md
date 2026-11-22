# IPS Vacunación Web

Proyecto académico ADSO SENA para gestionar pacientes, citas de vacunación y un catálogo básico de vacunas.

## Módulos

- **Pacientes:** registro, consulta, edición y eliminación.
- **Citas:** agenda futura para un paciente y permite cambiar su estado.
- **Vacunas:** registra fabricante y dosis disponibles; el nombre identifica cada vacuna.
- **Personal de salud:** registra profesionales activos e inactivos y los vincula a usuarios.
- **Usuarios y roles:** autenticación con contraseñas protegidas; roles de administrador, auxiliar y personal de salud.
- **Vacunación aplicada:** registra cita, paciente, vacuna, profesional, dosis, lote y observaciones; actualiza inventario y estado de la cita de forma atómica.

## Interfaz

Las páginas JSP comparten el estilo definido en `src/main/webapp/css/estilos.css`. La paleta azul, las tarjetas, formularios, tablas y navegación toman como referencia la interfaz del módulo de pacientes desarrollado anteriormente. El diseño se adapta a pantallas pequeñas y conserva los formularios conectados con sus Servlets.

## Tecnologías

Java 17, Maven, Jakarta Servlet/JSP, JSTL, JDBC, MySQL y Apache Tomcat 10.1.

## Preparar la base de datos

El sistema requiere la base `ips_vacunacion` y la tabla `paciente` del módulo anterior. En MySQL, ejecutar los dos scripts de `database/`:

1. `modulo_citas.sql`
2. `modulo_vacunas.sql`

La conexión local se configura en `src/main/java/co/edu/sena/ipsvacunacionweb/conexion/ConexionBD.java`. Ajustar usuario y contraseña según la instalación de MySQL.

## Compilar y probar

Desde la carpeta que contiene `pom.xml`:

```text
mvn clean verify
```

El paquete de despliegue se genera en `target/ips-vacunacion-web.war`. Las pruebas automatizadas se encuentran bajo `src/test/java` y los informes Maven bajo `target/surefire-reports`.

## Desplegar localmente

Copiar el WAR a la carpeta `webapps` de Apache Tomcat 10.1 y abrir `http://localhost:8080/ips-vacunacion-web/`. Tomcat 8.5 no es compatible con Jakarta Servlet 6.

## Documentación de la evidencia

Consultar [`documentacion/GUIA_EVIDENCIA.md`](documentacion/GUIA_EVIDENCIA.md) para la arquitectura, historias de usuario, pruebas manuales, resultados unitarios y pasos de instalación.

El informe escrito de la evidencia, con anexos de capturas, se encuentra en `documentacion/Informe_GA8-220501096-AA1-EV01.docx`.

## Funciones agregadas: usuarios y registro de vacunación

La versión actual del código añade autenticación, control de acceso por roles, gestión de personal de salud y registro de vacunas aplicadas. El documento de evidencia GA8 conserva el alcance que tenía el proyecto cuando se elaboró; esta sección describe el avance posterior del código.

### Preparar el esquema de pruebas

La configuración local predeterminada apunta a `127.0.0.1:3306/ips_vacunacion_test`, que corresponde al puerto de MySQL usado por Laragon en este equipo. `ConexionBD` también acepta `IPS_DB_URL`, `IPS_DB_USER` e `IPS_DB_PASSWORD` (o las propiedades Java `ips.db.url`, `ips.db.user` e `ips.db.password`) para cambiar la conexión sin editar el código. Conserva una copia de seguridad antes de ejecutar la migración. La tabla `paciente` y la tabla `cita` deben existir en ese esquema. Ejecuta una sola vez `database/modulos_pendientes.sql` conectado a la base `ips_vacunacion_test`. El script crea las tablas de personal, usuarios y registro de vacunación, crea el catálogo si falta, agrega la relación cita-vacuna y enlaza las citas antiguas cuyo texto coincide con un nombre del catálogo. Las citas históricas sin coincidencia se conservan y aparecen en el listado, pero no se pueden usar para registrar una aplicación hasta que tengan vacuna vinculada.

La migración está preparada para el esquema de QA indicado. Para usar otro esquema, cambia la sentencia `USE ips_vacunacion_test;` del script antes de ejecutarlo. La migración agrega relaciones y tablas; no borra los datos existentes. En MySQL las operaciones DDL se aplican por pasos, así que si una sentencia falla, revisa qué objetos alcanzó a crear antes de volver a ejecutarlo.

### Crear el primer administrador

No hay una cuenta ni contraseña predeterminadas. Después de compilar, abre una consola interactiva en la carpeta del proyecto y ejecuta:

```text
mvn -DskipTests package
java -cp target/classes co.edu.sena.ipsvacunacionweb.seguridad.GenerarHash
```

La utilidad solicita una contraseña de al menos 12 caracteres sin mostrarla y devuelve un hash PBKDF2. Inserta la cuenta administradora con ese hash en el esquema de QA, sustituyendo el valor entre corchetes:

```sql
INSERT INTO usuario (nombre_usuario, hash_contrasena, rol)
VALUES ('admin', '[PEGAR_HASH_GENERADO]', 'ADMINISTRADOR');
```

Luego copia `target/ips-vacunacion-qa.war` a la carpeta `webapps` de Tomcat 10.1, inicia el servidor y abre `http://localhost:8080/ips-vacunacion-qa/login`. Si despliegas el nombre original `ips-vacunacion-web.war`, la ruta será `/ips-vacunacion-web/login`. Desde **Usuarios** se crean las cuentas auxiliares y de personal de salud. El usuario del personal debe vincularse a un profesional activo.

### Permisos y comportamiento

- **Administrador:** acceso a todos los módulos, cuentas y personal.
- **Auxiliar:** administra pacientes, citas y catálogo de vacunas.
- **Personal de salud:** registra aplicaciones de vacunación realizadas. La cuenta queda asociada a su ficha de personal.

Las contraseñas se verifican con PBKDF2-HMAC-SHA256 y una sal aleatoria por cuenta. Las solicitudes de escritura requieren token CSRF. Las citas nuevas eligen una vacuna del catálogo y la guardan relacionada por `id_vacuna`, conservando también el nombre como dato compatible con citas antiguas. Al registrar una aplicación, el sistema valida la cita programada, el profesional activo, la fecha y el inventario; inserta el registro, descuenta una dosis y cambia la cita a Atendida en una transacción. Si alguna parte falla, revierte los cambios.

Los módulos nuevos son: `/login`, `/usuarios`, `/personal` y `/registro-vacunacion`. Se requiere correr la migración antes de entrar al sistema; sin ella faltarán las tablas y columnas nuevas.
