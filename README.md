# IPS Vacunación Web

Proyecto académico ADSO SENA para gestionar pacientes, citas de vacunación y un catálogo básico de vacunas.

## Módulos

- **Pacientes:** registro, consulta, edición y eliminación.
- **Citas:** agenda futura para un paciente y permite cambiar su estado.
- **Vacunas:** registra fabricante y dosis disponibles; el nombre identifica cada vacuna.

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
