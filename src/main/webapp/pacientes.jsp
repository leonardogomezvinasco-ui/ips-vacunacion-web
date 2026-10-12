<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="co.edu.sena.ipsvacunacionweb.modelo.Paciente" %>

<!DOCTYPE html>
<html lang="es">

<head>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Gestión de Pacientes - IPS Preventiva Farallones</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head>

<body>

<div class="contenedor">

    <header class="encabezado">
        <h1>IPS Preventiva Farallones</h1>
        <h2>Sistema de Agendamiento de Citas</h2>
    </header>

    <nav class="navegacion" aria-label="Navegación principal">
        <a href="${pageContext.request.contextPath}/">Inicio</a>
        <a class="activo" href="${pageContext.request.contextPath}/pacientes">Pacientes</a>
        <a href="${pageContext.request.contextPath}/citas">Citas</a>
        <a href="${pageContext.request.contextPath}/vacunas">Vacunas</a>
    </nav>

    <!-- FORMULARIO DE REGISTRO -->

    <div class="tarjeta">

        <h2>Registrar paciente</h2>

        <form method="post"
              action="${pageContext.request.contextPath}/pacientes">

            <input type="hidden"
                   name="accion"
                   value="registrar">

            <div class="formulario">

                <div class="campo">

                    <label for="tipoDocumento">
                        Tipo de documento
                    </label>

                    <select id="tipoDocumento"
                            name="tipoDocumento"
                            required>

                        <option value="">Seleccione</option>
                        <option value="CC">Cédula de ciudadanía</option>
                        <option value="TI">Tarjeta de identidad</option>
                        <option value="CE">Cédula de extranjería</option>
                        <option value="PA">Pasaporte</option>

                    </select>

                </div>


                <div class="campo">

                    <label for="numeroDocumento">
                        Número de documento
                    </label>

                    <input type="text"
                           id="numeroDocumento"
                           name="numeroDocumento"
                           required>

                </div>


                <div class="campo">

                    <label for="nombre">
                        Nombre
                    </label>

                    <input type="text"
                           id="nombre"
                           name="nombre"
                           required>

                </div>


                <div class="campo">

                    <label for="apellido">
                        Apellido
                    </label>

                    <input type="text"
                           id="apellido"
                           name="apellido"
                           required>

                </div>


                <div class="campo">

                    <label for="telefono">
                        Teléfono
                    </label>

                    <input type="text"
                           id="telefono"
                           name="telefono">

                </div>


                <div class="campo">

                    <label for="ciudad">
                        Ciudad
                    </label>

                    <input type="text"
                           id="ciudad"
                           name="ciudad">

                </div>


                <div class="campo campo-completo">

                    <label for="direccion">
                        Dirección
                    </label>

                    <input type="text"
                           id="direccion"
                           name="direccion">

                </div>


                <div class="campo">

                    <label for="fechaRegistro">
                        Fecha de registro
                    </label>

                    <input type="date"
                           id="fechaRegistro"
                           name="fechaRegistro"
                           required>

                </div>


                <div class="campo">

                    <label>
                        Consentimiento
                    </label>

                    <label class="checkbox-line">

                        <input type="checkbox"
                               name="consentimiento"
                               value="true">

                        El paciente autoriza el tratamiento de sus datos.

                    </label>

                </div>


                <div class="campo">

                    <label>&nbsp;</label>

                    <button type="submit"
                            class="boton">

                        Registrar paciente

                    </button>

                </div>

            </div>

        </form>

    </div>


    <!-- LISTADO DE PACIENTES -->

    <div class="tarjeta">

        <h2>Pacientes registrados</h2>

        <div class="tabla-contenedor">

            <table>

                <thead>

                <tr>

                    <th>ID</th>
                    <th>Documento</th>
                    <th>Número</th>
                    <th>Nombre</th>
                    <th>Apellido</th>
                    <th>Teléfono</th>
                    <th>Ciudad</th>
                    <th>Consentimiento</th>
                    <th>Fecha</th>
                    <th>Acción</th>

                </tr>

                </thead>

                <tbody>

                <%
                    List<Paciente> pacientes =
                            (List<Paciente>) request.getAttribute("pacientes");

                    if (pacientes != null && !pacientes.isEmpty()) {

                        for (Paciente paciente : pacientes) {
                %>

                <tr>

                    <td>
                        <%= paciente.getIdPaciente() %>
                    </td>

                    <td>
                        <%= paciente.getTipoDocumento() %>
                    </td>

                    <td>
                        <%= paciente.getNumeroDocumento() %>
                    </td>

                    <td>
                        <%= paciente.getNombre() %>
                    </td>

                    <td>
                        <%= paciente.getApellido() %>
                    </td>

                    <td>
                        <%= paciente.getTelefono() %>
                    </td>

                    <td>
                        <%= paciente.getCiudad() %>
                    </td>

                    <td>
                        <%= paciente.isConsentimiento()
                                ? "Sí"
                                : "No" %>
                    </td>

                    <td>
                        <%= paciente.getFechaRegistro() %>
                    </td>

                    <td>

                        <form method="get"
                              action="${pageContext.request.contextPath}/editar-paciente">

                            <input type="hidden"
                                   name="id"
                                   value="<%= paciente.getIdPaciente() %>">

                            <button type="submit"
                                    class="boton">

                                Editar

                            </button>

                        </form>

                        <form method="post"
                              action="${pageContext.request.contextPath}/pacientes">

                            <input type="hidden"
                                   name="accion"
                                   value="eliminar">

                            <input type="hidden"
                                   name="id"
                                   value="<%= paciente.getIdPaciente() %>">

                            <button type="submit"
                                    class="boton-eliminar"
                                    onclick="return confirm('¿Desea eliminar este paciente?');">

                                Eliminar

                            </button>

                        </form>

                    </td>

                </tr>

                <%
                        }

                    } else {
                %>

                <tr>

                    <td colspan="10"
                        class="sin-registros">

                        No hay pacientes registrados.

                    </td>

                </tr>

                <%
                    }
                %>

                </tbody>

            </table>

        </div>

    </div>


    <div class="pie">

        Proyecto desarrollado como evidencia del programa ADSO - SENA.

    </div>

</div>

</body>

</html>
