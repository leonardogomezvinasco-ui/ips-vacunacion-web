<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Agendamiento de citas - IPS Preventiva Farallones</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>
<main class="contenedor">
    <header class="encabezado">
        <h1>IPS Preventiva Farallones</h1>
        <h2>Sistema de Agendamiento de Citas</h2>
    </header>

    <nav class="navegacion" aria-label="Navegación principal">
        <a href="${pageContext.request.contextPath}/">Inicio</a>
        <a href="${pageContext.request.contextPath}/pacientes">Pacientes</a>
        <a class="activo" href="${pageContext.request.contextPath}/citas">Citas</a>
        <a href="${pageContext.request.contextPath}/vacunas">Vacunas</a>
    </nav>

    <section class="tarjeta">
        <h2>Nueva cita</h2>
        <p class="texto-intro">Selecciona el paciente y registra la vacuna y la fecha de atención.</p>
        <form method="post" action="${pageContext.request.contextPath}/citas" class="formulario">
            <div class="campo">
                <label for="idPaciente">Paciente</label>
                <select id="idPaciente" name="idPaciente" required>
                    <option value="">Seleccione un paciente</option>
                    <c:forEach var="paciente" items="${pacientes}">
                        <option value="${paciente.idPaciente}"><c:out value="${paciente.nombre}"/> <c:out value="${paciente.apellido}"/> — <c:out value="${paciente.numeroDocumento}"/></option>
                    </c:forEach>
                </select>
            </div>
            <div class="campo">
                <label for="vacuna">Vacuna</label>
                <input id="vacuna" name="vacuna" maxlength="100" required placeholder="Ej. Influenza">
            </div>
            <div class="campo">
                <label for="fechaHora">Fecha y hora</label>
                <input id="fechaHora" name="fechaHora" type="datetime-local" required>
            </div>
            <div class="campo campo-completo">
                <button type="submit">Agendar cita</button>
            </div>
        </form>
    </section>

    <section class="tarjeta">
        <h2>Citas registradas</h2>
        <div class="tabla-contenedor">
            <table>
                <thead><tr><th>ID</th><th>Paciente</th><th>Vacuna</th><th>Fecha y hora</th><th>Estado</th><th>Acción</th></tr></thead>
                <tbody>
                <c:choose>
                    <c:when test="${not empty citas}">
                        <c:forEach var="cita" items="${citas}">
                            <tr>
                                <td><c:out value="${cita.idCita}"/></td>
                                <td><c:out value="${cita.paciente}"/></td>
                                <td><c:out value="${cita.vacuna}"/></td>
                                <td><c:out value="${cita.fechaHora}"/></td>
                                <td><span class="estado"><c:out value="${cita.estado}"/></span></td>
                                <td>
                                    <form method="post" action="${pageContext.request.contextPath}/citas" class="acciones">
                                        <input type="hidden" name="accion" value="estado">
                                        <input type="hidden" name="idCita" value="${cita.idCita}">
                                        <label class="sr-only" for="estado-${cita.idCita}">Estado de la cita</label>
                                        <select id="estado-${cita.idCita}" name="estado" aria-label="Nuevo estado de la cita">
                                            <option value="Programada" ${cita.estado == 'Programada' ? 'selected' : ''}>Programada</option>
                                            <option value="Atendida" ${cita.estado == 'Atendida' ? 'selected' : ''}>Atendida</option>
                                            <option value="Cancelada" ${cita.estado == 'Cancelada' ? 'selected' : ''}>Cancelada</option>
                                        </select>
                                        <button type="submit">Actualizar</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise><tr><td colspan="6" class="sin-registros">Todavía no hay citas registradas.</td></tr></c:otherwise>
                </c:choose>
                </tbody>
            </table>
        </div>
    </section>
    <footer class="pie">Proyecto desarrollado como evidencia del programa ADSO - SENA.</footer>
</main>
</body>
</html>
