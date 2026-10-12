<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>IPS Preventiva Farallones</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>
<main class="contenedor">
    <section class="portada">
        <h1>IPS Preventiva Farallones</h1>
        <p>Sistema de agendamiento de citas de vacunación. Administra los pacientes, consulta el inventario y organiza las citas desde un mismo lugar.</p>
    </section>

    <nav class="navegacion" aria-label="Navegación principal">
        <a class="activo" href="${pageContext.request.contextPath}/">Inicio</a>
        <a href="${pageContext.request.contextPath}/pacientes">Pacientes</a>
        <a href="${pageContext.request.contextPath}/citas">Citas</a>
        <a href="${pageContext.request.contextPath}/vacunas">Vacunas</a>
    </nav>

    <section class="tarjetas-modulos" aria-label="Módulos del sistema">
        <article class="tarjeta tarjeta-modulo">
            <h2>Pacientes</h2>
            <p>Registra y consulta la información de las personas atendidas por la IPS.</p>
            <a class="boton" href="${pageContext.request.contextPath}/pacientes">Abrir pacientes</a>
        </article>
        <article class="tarjeta tarjeta-modulo">
            <h2>Citas</h2>
            <p>Programa una cita para un paciente y actualiza su estado de atención.</p>
            <a class="boton" href="${pageContext.request.contextPath}/citas">Abrir citas</a>
        </article>
        <article class="tarjeta tarjeta-modulo">
            <h2>Vacunas</h2>
            <p>Consulta y actualiza el catálogo y las dosis disponibles.</p>
            <a class="boton" href="${pageContext.request.contextPath}/vacunas">Abrir vacunas</a>
        </article>
    </section>

    <footer class="pie">Proyecto desarrollado como evidencia del programa ADSO - SENA.</footer>
</main>
</body>
</html>
