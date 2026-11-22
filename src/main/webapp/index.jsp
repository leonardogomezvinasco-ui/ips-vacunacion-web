<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
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
        <c:if test="${usuarioActual.rol == 'ADMINISTRADOR' || usuarioActual.rol == 'AUXILIAR'}"><a href="${pageContext.request.contextPath}/pacientes">Pacientes</a><a href="${pageContext.request.contextPath}/citas">Citas</a><a href="${pageContext.request.contextPath}/vacunas">Vacunas</a></c:if>
        <c:if test="${usuarioActual.rol == 'ADMINISTRADOR'}"><a href="${pageContext.request.contextPath}/personal">Personal</a><a href="${pageContext.request.contextPath}/usuarios">Usuarios</a></c:if>
        <c:if test="${usuarioActual.rol == 'ADMINISTRADOR' || usuarioActual.rol == 'PERSONAL_SALUD'}"><a href="${pageContext.request.contextPath}/registro-vacunacion">Vacunaciones</a></c:if>
        <a href="${pageContext.request.contextPath}/logout">Cerrar sesión</a>
    </nav>

    <section class="tarjetas-modulos" aria-label="Módulos del sistema">
        <c:if test="${usuarioActual.rol == 'ADMINISTRADOR'}"><article class="tarjeta tarjeta-modulo">
            <h2>Personal de salud</h2><p>Administra los profesionales y sus registros institucionales.</p>
            <a class="boton" href="${pageContext.request.contextPath}/personal">Abrir personal</a>
        </article></c:if>
        <c:if test="${usuarioActual.rol == 'ADMINISTRADOR' || usuarioActual.rol == 'PERSONAL_SALUD'}"><article class="tarjeta tarjeta-modulo">
            <h2>Vacunación aplicada</h2><p>Registra lote, dosis y profesional responsable de cada aplicación.</p>
            <a class="boton" href="${pageContext.request.contextPath}/registro-vacunacion">Abrir vacunaciones</a>
        </article></c:if>
        <c:if test="${usuarioActual.rol == 'ADMINISTRADOR'}"><article class="tarjeta tarjeta-modulo">
            <h2>Usuarios y roles</h2><p>Administra el acceso según la responsabilidad dentro de la IPS.</p>
            <a class="boton" href="${pageContext.request.contextPath}/usuarios">Abrir usuarios</a>
        </article></c:if>
        <c:if test="${usuarioActual.rol == 'ADMINISTRADOR' || usuarioActual.rol == 'AUXILIAR'}"><article class="tarjeta tarjeta-modulo">
            <h2>Pacientes</h2>
            <p>Registra y consulta la información de las personas atendidas por la IPS.</p>
            <a class="boton" href="${pageContext.request.contextPath}/pacientes">Abrir pacientes</a>
        </article></c:if>
        <c:if test="${usuarioActual.rol == 'ADMINISTRADOR' || usuarioActual.rol == 'AUXILIAR'}"><article class="tarjeta tarjeta-modulo">
            <h2>Citas</h2>
            <p>Programa una cita para un paciente y actualiza su estado de atención.</p>
            <a class="boton" href="${pageContext.request.contextPath}/citas">Abrir citas</a>
        </article></c:if>
        <c:if test="${usuarioActual.rol == 'ADMINISTRADOR' || usuarioActual.rol == 'AUXILIAR'}"><article class="tarjeta tarjeta-modulo">
            <h2>Vacunas</h2>
            <p>Consulta y actualiza el catálogo y las dosis disponibles.</p>
            <a class="boton" href="${pageContext.request.contextPath}/vacunas">Abrir vacunas</a>
        </article></c:if>
    </section>

    <footer class="pie">Proyecto desarrollado como evidencia del programa ADSO - SENA.</footer>
</main>
</body>
</html>
