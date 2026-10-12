<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Catálogo de vacunas - IPS Preventiva Farallones</title>
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
        <a href="${pageContext.request.contextPath}/citas">Citas</a>
        <a class="activo" href="${pageContext.request.contextPath}/vacunas">Vacunas</a>
    </nav>

    <section class="tarjeta">
        <h2>Registrar o actualizar vacuna</h2>
        <p class="texto-intro">Registra el nombre, fabricante y cantidad disponible en el inventario.</p>
        <form method="post" action="${pageContext.request.contextPath}/vacunas" class="formulario">
            <div class="campo">
                <label for="nombre">Nombre de la vacuna</label>
                <input id="nombre" name="nombre" required maxlength="100" placeholder="Ej. Influenza">
            </div>
            <div class="campo">
                <label for="fabricante">Fabricante</label>
                <input id="fabricante" name="fabricante" maxlength="100" placeholder="Nombre del fabricante">
            </div>
            <div class="campo">
                <label for="dosis">Dosis disponibles</label>
                <input id="dosis" name="dosis" type="number" min="0" required placeholder="0">
            </div>
            <div class="campo campo-completo">
                <button type="submit">Registrar / actualizar</button>
            </div>
        </form>
    </section>

    <section class="tarjeta">
        <h2>Inventario de vacunas</h2>
        <div class="tabla-contenedor">
            <table>
                <thead><tr><th>Vacuna</th><th>Fabricante</th><th>Dosis disponibles</th></tr></thead>
                <tbody>
                <c:choose>
                    <c:when test="${not empty vacunas}">
                        <c:forEach var="vacuna" items="${vacunas}">
                            <tr>
                                <td><c:out value="${vacuna.nombre}"/></td>
                                <td><c:out value="${vacuna.fabricante}"/></td>
                                <td><span class="estado"><c:out value="${vacuna.dosisDisponibles}"/></span></td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise><tr><td colspan="3" class="sin-registros">Todavía no hay vacunas registradas.</td></tr></c:otherwise>
                </c:choose>
                </tbody>
            </table>
        </div>
    </section>
    <footer class="pie">Proyecto desarrollado como evidencia del programa ADSO - SENA.</footer>
</main>
</body>
</html>
