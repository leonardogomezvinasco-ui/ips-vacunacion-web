# Guía de la evidencia GA8-220501096-AA1-EV01

## 1. ¿Qué estamos construyendo?

El sistema ya tenía el módulo **Pacientes**, que registra y consulta a las personas atendidas por la IPS. Para demostrar la integración de componentes se añadieron dos módulos relacionados con ese proceso:

- **Citas:** agenda una atención de vacunación para un paciente y permite marcarla como programada, atendida o cancelada.
- **Vacunas:** registra el nombre, fabricante y cantidad disponible de cada vacuna.

La portada enlaza a los tres módulos. El módulo de citas usa el identificador interno del paciente para guardar la relación, pero la pantalla presenta el nombre y documento para que la persona usuaria pueda seleccionarlo sin conocer ese identificador.

## 2. Cómo viaja una solicitud por las capas

1. La persona usuaria abre una página JSP y llena un formulario (capa de presentación).
2. El formulario envía sus datos a una ruta, por ejemplo `/citas` (controlador Servlet).
3. El Servlet lee y valida datos básicos y llama al DAO correspondiente (capa de aplicación y acceso a datos).
4. El DAO utiliza JDBC y sentencias preparadas para consultar o guardar datos en MySQL (persistencia).
5. El Servlet devuelve la página con los datos o redirige al listado.

```text
Navegador → JSP → Servlet → DAO → JDBC → MySQL
                         ← datos ←
```

Los DAOs concentran el SQL; los Servlets coordinan la solicitud; los modelos (`Paciente`, `Cita`, `Vacuna`) representan los datos. Esta separación facilita mantener cada parte sin mezclar HTML y consultas SQL.

## 3. Componentes agregados

| Módulo | Entrada | Proceso | Salida |
|---|---|---|---|
| Citas | Paciente, vacuna, fecha y hora | Comprueba datos requeridos y que la fecha sea futura; guarda la cita | Listado de citas y estado |
| Estado de cita | Identificador y estado permitido | Actualiza el estado de la cita | Redirección al listado actualizado |
| Vacunas | Nombre, fabricante y dosis | Valida nombre y que las dosis no sean negativas; guarda o actualiza por nombre | Catálogo con inventario |

## 4. Historias de usuario

- Como auxiliar de la IPS, quiero seleccionar un paciente existente y agendar la vacuna y fecha para organizar su atención.
- Como auxiliar, quiero cambiar el estado de una cita para reflejar si fue atendida o cancelada.
- Como responsable del inventario, quiero registrar vacunas y sus dosis disponibles para consultar existencias.

## 5. Estructura de código

```text
src/main/java/co/edu/sena/ipsvacunacionweb/
  conexion/  → conexión JDBC existente
  modelo/    → Paciente, Cita y Vacuna
  dao/       → PacienteDAO, CitaDAO y VacunaDAO
  servlet/   → controladores web
src/main/webapp/
  index.jsp, pacientes.jsp, citas.jsp, vacunas.jsp
database/    → scripts SQL de los módulos nuevos
```

## 6. Preparar la base de datos

1. Inicia MySQL y confirma que ya existe la base `ips_vacunacion` y la tabla `paciente` usada por el sistema original.
2. Abre una herramienta SQL (por ejemplo MySQL Workbench) y ejecuta `database/modulo_citas.sql`.
3. Ejecuta `database/modulo_vacunas.sql`.
4. La tabla `cita` tiene una llave foránea hacia `paciente`: por eso se requiere registrar primero el paciente. También evita duplicar la misma fecha y hora para un paciente.

La conexión actual se encuentra en `conexion/ConexionBD.java`; apunta a `localhost:3306`, base `ips_vacunacion`, usuario `root` y contraseña vacía. Si la instalación local usa otras credenciales, hay que ajustarlas antes de iniciar el sistema. Para un despliegue real, las credenciales deben salir del código y guardarse en configuración protegida.

## 7. Ejecutar en ambiente local

Requisitos del proyecto: JDK 17, Maven, MySQL y Tomcat 10.1 (Jakarta Servlet 6). En una terminal abierta en la carpeta del proyecto:

```text
mvn clean package
```

El archivo WAR esperado se genera en `target/ips-vacunacion-web.war`. Cópialo a la carpeta `webapps` de Tomcat, inicia Tomcat y abre:

```text
http://localhost:8080/ips-vacunacion-web/
```

Desde la portada entra a **Pacientes**, **Citas** o **Vacunas**. Para agendar una cita, primero debe haber pacientes cargados y tablas creadas.

## 8. Pruebas manuales realizadas

Ambiente reportado durante la ejecución: Windows 11, Java 26.0.2.1, Maven 3.9.16, MySQL 8.0.30 y Apache Tomcat 10.1.60. Fecha de ejecución reportada: 10 de octubre de 2026. La aplicación se abrió localmente en `http://localhost:8080/ips-vacunacion-web/`.

| Prueba | Resultado esperado | Resultado observado | Estado |
|---|---|---|---|
| Abrir la página principal | Ver los enlaces a pacientes, citas y vacunas | La portada cargó en el navegador | Aprobada |
| Abrir el catálogo de vacunas | Consultar la tabla `vacuna` sin error | Se mostró el formulario y el listado | Aprobada |
| Registrar vacuna de prueba | Guardar y listar nombre, fabricante y dosis | Se registró “Vacuna de prueba”, fabricante “Prueba académica”, 5 dosis | Aprobada |
| Abrir gestión de pacientes | Consultar pacientes y mostrar formulario | La página cargó y mostró la lista vacía | Aprobada |
| Registrar paciente ficticio | Guardar paciente y mostrarlo en la lista | El usuario confirmó que se registró correctamente | Aprobada |
| Agendar cita futura | Asociar paciente, vacuna, fecha y estado inicial | La cita apareció con estado `Programada` | Aprobada |
| Cambiar estado de la cita | Guardar y mostrar el estado seleccionado | El usuario confirmó cambio a `Cancelada` | Aprobada |
| Reiniciar Tomcat | Volver a cargar los datos ya almacenados | El usuario confirmó que la vacuna y la cita continuaron en pantalla después del reinicio | Aprobada |

Las capturas del navegador compartidas durante la sesión sirven como evidencia visual. La cita y el paciente son datos ficticios de prueba. La aplicación no tiene una función web para eliminar citas; si se desea limpiar los datos de prueba, hacerlo desde MySQL teniendo en cuenta primero borrar la cita para respetar la llave foránea.

## 9. Pruebas unitarias automatizadas

Se añadieron pruebas JUnit 5 para las reglas de negocio de `ValidadorCita` y `ValidadorVacuna`. Cubren citas futuras, rechazo de fechas pasadas, estados permitidos, nombres vacíos y dosis negativas. Estas pruebas se separan del acceso a MySQL, por lo que verifican las reglas sin modificar datos.

Las pruebas se deben ejecutar ahora en el computador de desarrollo con:

```text
mvn clean verify
```

Ejecución realizada el 10 de octubre de 2026: `Tests run: 6, Failures: 0, Errors: 0, Skipped: 0`; Maven finalizó con `BUILD SUCCESS` y generó `target/ips-vacunacion-web.war`.

## 10. Tecnologías y seguridad aplicada

Java 17, Maven, Jakarta Servlet/JSP, JDBC, MySQL y Tomcat 10.1. Los DAOs nuevos usan `PreparedStatement`, lo cual separa los valores del SQL. Las páginas usan `<c:out>` al mostrar los campos ingresados por usuarios. Los estados de cita se limitan a tres valores válidos y el inventario no acepta cantidades negativas.

La aplicación es una evidencia académica local. Antes de publicarla para uso real se requiere, entre otras cosas, autenticación y autorización, protección CSRF, configuración segura de secretos, manejo uniforme de errores y revisión de privacidad de los datos personales.

## 11. Entrega de la evidencia

La carpeta contiene el código fuente y los scripts de base de datos. Para completar los entregables solicitados por la guía, después de verificar el proyecto se debe: guardar el código en el repositorio Git, generar el WAR, documentar pruebas y ambientes, comprimir el paquete y anotar la URL pública solamente si se realiza un despliegue. No hay URL desplegada en esta etapa.
