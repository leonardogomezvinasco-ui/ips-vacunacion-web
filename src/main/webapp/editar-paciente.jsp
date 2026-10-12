<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Editar paciente - IPS Preventiva Farallones</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>
<main class="contenedor">
    <header class="encabezado">
        <h1>IPS Preventiva Farallones</h1>
        <h2>Editar información del paciente</h2>
    </header>

    <nav class="navegacion" aria-label="Navegación principal">
        <a href="${pageContext.request.contextPath}/">Inicio</a>
        <a class="activo" href="${pageContext.request.contextPath}/pacientes">Pacientes</a>
        <a href="${pageContext.request.contextPath}/citas">Citas</a>
        <a href="${pageContext.request.contextPath}/vacunas">Vacunas</a>
    </nav>

    <section class="tarjeta">
        <a class="enlace-volver" href="${pageContext.request.contextPath}/pacientes">← Volver a pacientes</a>
        <form action="${pageContext.request.contextPath}/editar-paciente" method="post" class="formulario formulario-uno">
            <input type="hidden" name="idPaciente" value="${paciente.idPaciente}">

            <div class="campo">
                <label for="tipoDocumento">Tipo de documento</label>
                <select id="tipoDocumento" name="tipoDocumento" required>
                    <option value="CC" ${paciente.tipoDocumento == 'CC' ? 'selected' : ''}>Cédula de ciudadanía</option>
                    <option value="CE" ${paciente.tipoDocumento == 'CE' ? 'selected' : ''}>Cédula de extranjería</option>
                    <option value="TI" ${paciente.tipoDocumento == 'TI' ? 'selected' : ''}>Tarjeta de identidad</option>
                    <option value="PA" ${paciente.tipoDocumento == 'PA' ? 'selected' : ''}>Pasaporte</option>
                </select>
            </div>
            <div class="campo">
                <label for="numeroDocumento">Número de documento</label>
                <input id="numeroDocumento" name="numeroDocumento" value="${paciente.numeroDocumento}" required>
            </div>
            <div class="campo">
                <label for="nombre">Nombre</label>
                <input id="nombre" name="nombre" value="${paciente.nombre}" required>
            </div>
            <div class="campo">
                <label for="apellido">Apellido</label>
                <input id="apellido" name="apellido" value="${paciente.apellido}" required>
            </div>
            <div class="campo">
                <label for="telefono">Teléfono</label>
                <input id="telefono" name="telefono" value="${paciente.telefono}">
            </div>
            <div class="campo">
                <label for="direccion">Dirección</label>
                <input id="direccion" name="direccion" value="${paciente.direccion}">
            </div>
            <div class="campo">
                <label for="ciudad">Ciudad</label>
                <input id="ciudad" name="ciudad" value="${paciente.ciudad}">
            </div>
            <div class="campo">
                <label for="fechaRegistro">Fecha de registro</label>
                <input id="fechaRegistro" name="fechaRegistro" type="date" value="${paciente.fechaRegistro}" required>
            </div>
            <div class="campo">
                <label class="checkbox-line" for="consentimiento">
                    <input id="consentimiento" name="consentimiento" type="checkbox" value="true" ${paciente.consentimiento ? 'checked' : ''}>
                    El paciente otorgó su consentimiento
                </label>
            </div>
            <div class="acciones">
                <button class="boton-verde" type="submit">Guardar cambios</button>
                <a class="boton-secundario" href="${pageContext.request.contextPath}/pacientes">Cancelar</a>
            </div>
        </form>
    </section>
    <footer class="pie">Proyecto desarrollado como evidencia del programa ADSO - SENA.</footer>
</main>
</body>
</html>
