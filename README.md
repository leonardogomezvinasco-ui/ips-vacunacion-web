 # Sistema de Agendamiento de Citas
>> ## IPS Preventiva Farallones
>> 
>> Aplicación web desarrollada como proyecto académico del programa **Tecnología en Análisis y Desarrollo de Software (ADSO) del SENA**.
>> 
>> El proyecto tiene como propósito apoyar la gestión de pacientes de una IPS y servir como base para el desarrollo posterior de un sistema de agendamiento de citas de vacunación.
>> 
>> ---
>> 
>> ## 1. Descripción del proyecto
>> 
>> **IPS Vacunación Web** es una aplicación web desarrollada con Java, Maven, Jakarta Servlet, JSP y MySQL.
>> 
>> La versión actual permite realizar la gestión básica de pacientes mediante un módulo CRUD:
>> 
>> - Registrar pacientes.
>> - Consultar y listar pacientes registrados.
>> - Editar información de pacientes.
>> - Actualizar información de pacientes.
>> - Eliminar pacientes.
>> 
>> El proyecto se encuentra en desarrollo y posteriormente se incorporarán las funcionalidades relacionadas con el **agendamiento y gestión de citas de vacunación**.
>> 
>> ---
>> 
>> ## 2. Objetivo
>> 
>> Desarrollar una aplicación web que permita gestionar la información de los pacientes de una IPS y establecer una base tecnológica para implementar posteriormente el proceso de agendamiento de citas de vacunación.
>> 
>> ---
>> 
>> ## 3. Funcionalidades implementadas
>> 
>> ### Gestión de pacientes
>> 
>> Actualmente se encuentran implementadas y probadas las siguientes operaciones:
>> 
>> ### Crear paciente
>> 
>> Permite registrar un nuevo paciente mediante un formulario web.
>> 
>> Los datos registrados incluyen:
>> 
>> - Tipo de documento.
>> - Número de documento.
>> - Nombre.
>> - Apellido.
>> - Teléfono.
>> - Dirección.
>> - Ciudad.
>> - Fecha de registro.
>> - Consentimiento para el tratamiento de datos.
>> 
>> ### Consultar pacientes
>> 
>> Permite visualizar los pacientes registrados en una tabla.
>> 
>> La información mostrada incluye:
>> 
>> - ID.
>> - Tipo de documento.
>> - Número de documento.
>> - Nombre.
>> - Apellido.
>> - Teléfono.
>> - Ciudad.
>> - Consentimiento.
>> - Fecha de registro.
>> 
>> ### Editar paciente
>> 
>> Permite seleccionar un paciente registrado y cargar sus datos en un formulario para realizar modificaciones.
>> 
>> La información modificada se almacena nuevamente en la base de datos.
>> 
>> ### Eliminar paciente
>> 
>> Permite eliminar un paciente registrado desde la lista de pacientes.
>> 
>> ---
>> 
>> ## 4. Tecnologías utilizadas
>> 
>> El proyecto utiliza las siguientes tecnologías:
>> 
>> - **Java**
>> - **Jakarta Servlet**
>> - **JSP (JavaServer Pages)**
>> - **Maven**
>> - **MySQL**
>> - **JDBC**
>> - **Apache Tomcat**
>> - **HTML**
>> - **Git**
>> - **GitHub**
>> - **Visual Studio Code**
>> 
>> ---
>> 
>> ## 5. Arquitectura básica
>> 
>> La aplicación utiliza una estructura basada en separación de responsabilidades:
>> 
>> ```text
>> Usuario
>>    │
>>    ▼
>> JSP / HTML
>>    │
>>    ▼
>> Servlet
>>    │
>>    ▼
>> DAO
>>    │
>>    ▼
>> JDBC
>>    │
>>    ▼
>> MySQL
>> ```
>> 
>> ### Componentes principales
>> 
>> **Modelo**
>> 
>> Contiene las clases que representan los datos de la aplicación.
>> 
>> Ejemplo:
>> 
>> ```text
>> Paciente.java
>> ```
>> 
>> **DAO**
>> 
>> Contiene las operaciones de acceso a la base de datos.
>> 
>> Ejemplo:
>> 
>> ```text
>> PacienteDAO.java
>> ```
>> 
>> **Servlets**
>> 
>> Controlan las solicitudes realizadas desde la aplicación web.
>> 
>> Ejemplos:
>> 
>> ```text
>> InicioServlet.java
>> PacienteServlet.java
>> EditarPacienteServlet.java
>> ```
>> 
>> **JSP**
>> 
>> Contiene las páginas utilizadas para presentar la información al usuario.
>> 
>> Ejemplos:
>> 
>> ```text
>> index.jsp
>> pacientes.jsp
>> editar-paciente.jsp
>> ```
>> 
>> ---
>> 
>> ## 6. Estructura del proyecto
>> 
>> La estructura principal del proyecto es:
>> 
>> ```text
>> ips-vacunacion-web/
>> │
>> ├── pom.xml
>> ├── .gitignore
>> │
>> └── src/
>>     └── main/
>>         ├── java/
>>         │   └── co/
>>         │       └── edu/
>>         │           └── sena/
>>         │               └── ipsvacunacionweb/
>>         │                   │
>>         │                   ├── InicioServlet.java
>>         │                   │
>>         │                   ├── conexion/
>>         │                   │   └── ConexionBD.java
>>         │                   │
>>         │                   ├── dao/
>>         │                   │   └── PacienteDAO.java
>>         │                   │
>>         │                   ├── modelo/
>>         │                   │   └── Paciente.java
>>         │                   │
>>         │                   └── servlet/
>>         │                       ├── PacienteServlet.java
>>         │                       └── EditarPacienteServlet.java
>>         │
>>         └── webapp/
>>             ├── index.jsp
>>             ├── pacientes.jsp
>>             └── editar-paciente.jsp
>> ```
>> 
>> ---
>> 
>> ## 7. Base de datos
>> 
>> La aplicación utiliza una base de datos MySQL para almacenar la información de los pacientes.
>> 
>> La conexión se realiza mediante JDBC.
>> 
>> La clase encargada de establecer la conexión es:
>> 
>> ```text
>> ConexionBD.java
>> ```
>> 
>> El acceso y las operaciones sobre la tabla de pacientes se realizan mediante:
>> 
>> ```text
>> PacienteDAO.java
>> ```
>> 
>> Las operaciones implementadas actualmente son:
>> 
>> ```text
>> INSERT
>> SELECT
>> UPDATE
>> DELETE
>> ```
>> 
>> ---
>> 
>> ## 8. Tabla de pacientes
>> 
>> La aplicación trabaja actualmente con la entidad:
>> 
>> ```text
>> Paciente
>> ```
>> 
>> Entre los campos utilizados se encuentran:
>> 
>> ```text
>> id_paciente
>> tipo_documento
>> numero_documento
>> nombre
>> apellido
>> telefono
>> direccion
>> ciudad
>> consentimiento
>> fecha_registro
>> ```
>> 
>> ---
>> 
>> ## 9. Requisitos para ejecutar el proyecto
>> 
>> Para ejecutar el proyecto localmente se requiere contar con:
>> 
>> - JDK compatible con el proyecto.
>> - Apache Maven.
>> - MySQL.
>> - Apache Tomcat 10.1.x.
>> - Visual Studio Code u otro IDE compatible con Java.
>> - Git.
>> 
>> ---
>> 
>> ## 10. Compilación del proyecto
>> 
>> Desde la carpeta raíz del proyecto se puede ejecutar:
>> 
>> ```bash
>> mvn clean package
>> ```
>> 
>> El comando genera el archivo:
>> 
>> ```text
>> target/ips-vacunacion-web.war
>> ```
>> 
>> La carpeta `target/` no se incluye en el repositorio Git porque contiene archivos generados automáticamente por Maven.
>> 
>> ---
>> 
>> ## 11. Despliegue en Apache Tomcat
>> 
>> El archivo WAR generado puede ser desplegado en Apache Tomcat.
>> 
>> Archivo generado:
>> 
>> ```text
>> ips-vacunacion-web.war
>> ```
>> 
>> Después del despliegue, la aplicación puede accederse mediante:
>> 
>> ```text
>> http://localhost:8080/ips-vacunacion-web/
>> ```
>> 
>> El módulo de pacientes se encuentra disponible en:
>> 
>> ```text
>> http://localhost:8080/ips-vacunacion-web/pacientes
>> ```
>> 
>> ---
>> 
>> ## 12. Pruebas realizadas
>> 
>> Durante el desarrollo se realizaron pruebas funcionales del módulo de pacientes.
>> 
>> ### Registro
>> 
>> Se verificó el registro de un paciente mediante el formulario web.
>> 
>> ### Consulta
>> 
>> Se verificó que el paciente registrado apareciera correctamente en la lista.
>> 
>> ### Actualización
>> 
>> Se realizó una prueba modificando el teléfono de un paciente:
>> 
>> ```text
>> 3001234567
>> ```
>> 
>> a:
>> 
>> ```text
>> 3001234568
>> ```
>> 
>> La modificación fue almacenada correctamente y posteriormente se visualizó en la lista de pacientes.
>> 
>> ### Eliminación
>> 
>> Se realizó una prueba de eliminación utilizando un paciente de prueba denominado:
>> 
>> ```text
>> Juan Prueba
>> ```
>> 
>> El registro fue eliminado correctamente y dejó de aparecer en la lista de pacientes.
>> 
>> ### Compilación
>> 
>> El proyecto fue compilado mediante Maven y se obtuvo:
>> 
>> ```text
>> BUILD SUCCESS
>> ```
>> 
>> ---
>> 
>> ## 13. Control de versiones
>> 
>> El proyecto utiliza **Git** para el control de versiones y **GitHub** como repositorio remoto.
>> 
>> Repositorio:
>> 
>> ```text
>> https://github.com/leonardogomezvinasco-ui/ips-vacunacion-web
>> ```
>> 
>> Rama principal actual:
>> 
>> ```text
>> master
>> ```
>> 
>> Primer commit:
>> 
>> ```text
>> 030a449
>> ```
>> 
>> Mensaje del primer commit:
>> 
>> ```text
>> Version inicial aplicacion web IPS Vacunacion
>> ```
>> 
>> El repositorio local se encuentra sincronizado con el repositorio remoto mediante:
>> 
>> ```bash
>> git push
>> ```
>> 
>> Para consultar el estado del proyecto:
>> 
>> ```bash
>> git status
>> ```
>> 
>> Resultado esperado cuando no existen cambios pendientes:
>> 
>> ```text
>> nothing to commit, working tree clean
>> ```
>> 
>> ---
>> 
>> ## 14. Flujo básico de versionamiento
>> 
>> Para registrar nuevos cambios realizados durante el desarrollo se utiliza el siguiente flujo:
>> 
>> ```bash
>> git status
>> git add .
>> git commit -m "Descripcion del cambio"
>> git push
>> ```
>> 
>> Este proceso permite mantener un historial de las diferentes versiones del proyecto.
>> 
>> ---
>> 
>> ## 15. .gitignore
>> 
>> El proyecto cuenta con un archivo `.gitignore` para evitar almacenar archivos generados automáticamente o configuraciones propias del entorno de desarrollo.
>> 
>> Entre los elementos excluidos se encuentra:
>> 
>> ```text
>> target/
>> ```
>> 
>> También se excluyen archivos y carpetas relacionados con configuraciones locales del IDE y archivos compilados.
>> 
>> ---
>> 
>> ## 16. Estado actual del proyecto
>> 
>> ### Implementado
>> 
>> - [x] Estructura inicial del proyecto web.
>> - [x] Configuración Maven.
>> - [x] Conexión con MySQL mediante JDBC.
>> - [x] Modelo `Paciente`.
>> - [x] DAO de pacientes.
>> - [x] Registro de pacientes.
>> - [x] Consulta y listado de pacientes.
>> - [x] Edición de pacientes.
>> - [x] Actualización de pacientes.
>> - [x] Eliminación de pacientes.
>> - [x] Despliegue en Apache Tomcat.
>> - [x] Control de versiones con Git.
>> - [x] Repositorio remoto en GitHub.
>> 
>> ### En desarrollo
>> 
>> - [ ] Gestión de citas de vacunación.
>> - [ ] Registro de citas.
>> - [ ] Consulta de citas.
>> - [ ] Actualización de estados de las citas.
>> - [ ] Gestión de personal de salud.
>> - [ ] Registro de vacunación.
>> - [ ] Notificaciones relacionadas con las citas.
>> - [ ] Otras funcionalidades definidas durante el desarrollo del proyecto.
>> 
>> ---
>> 
>> ## 17. Contexto académico
>> 
>> Este proyecto es desarrollado como parte del proceso de formación del programa:
>> 
>> **Tecnología en Análisis y Desarrollo de Software (ADSO)**
>> 
>> **Servicio Nacional de Aprendizaje – SENA**
>> 
>> El proyecto se utiliza como base para aplicar conocimientos relacionados con:
>> 
>> - Desarrollo de software.
>> - Programación en Java.
>> - Bases de datos.
>> - Desarrollo web.
>> - Arquitectura de aplicaciones.
>> - Acceso a datos mediante JDBC.
>> - Control de versiones.
>> - Git y GitHub.
>> - Pruebas funcionales.
>> - Despliegue de aplicaciones web.
>> 
>> ---
>> 
>> ## 18. Autor
>> 
>> **Leonardo Gómez Vinasco**
>> 
>> Proyecto académico ADSO – SENA.
>> 
>> ---
>> 
>> ## 19. Repositorio
>> 
>> Código fuente disponible en GitHub:
>> 
>> https://github.com/leonardogomezvinasco-ui/ips-vacunacion-web
>> 
>> ---
>> 
>> ## 20. Nota
>> 
>> Este proyecto se encuentra en proceso de desarrollo. Las funcionalidades descritas como implementadas corresponden a la versión actual del sistema.
>> 
>> Las funcionalidades relacionadas con el agendamiento completo de citas de vacunación serán incorporadas progresivamente durante las siguientes etapas del proyecto.