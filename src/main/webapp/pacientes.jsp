<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="co.edu.sena.ipsvacunacionweb.modelo.Paciente" %>

<!DOCTYPE html>
<html lang="es">

<head>

    <meta charset="UTF-8">

    <title>Gestión de Pacientes - IPS Preventiva Farallones</title>

    <style>

        body {
            font-family: Arial, sans-serif;
            background-color: #f4f6f8;
            margin: 0;
            padding: 20px;
        }

        .contenedor {
            max-width: 1200px;
            margin: auto;
        }

        h1 {
            color: #1f4e79;
            margin-bottom: 5px;
        }

        h2 {
            color: #555;
            font-size: 20px;
            margin-top: 0;
        }

        .tarjeta {
            background-color: white;
            padding: 20px;
            margin-top: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 6px rgba(0,0,0,0.1);
        }

        .formulario {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 15px;
        }

        .campo {
            display: flex;
            flex-direction: column;
        }

        .campo label {
            font-weight: bold;
            margin-bottom: 5px;
        }

        .campo input,
        .campo select {
            padding: 9px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        .campo-completo {
            grid-column: span 3;
        }

        .boton {
            background-color: #1f4e79;
            color: white;
            border: none;
            padding: 10px 18px;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
        }

        .boton:hover {
            background-color: #163a5c;
        }

        .tabla-contenedor {
            overflow-x: auto;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 15px;
        }

        th {
            background-color: #1f4e79;
            color: white;
            padding: 10px;
            text-align: left;
        }

        td {
            padding: 9px;
            border-bottom: 1px solid #ddd;
        }

        tr:hover {
            background-color: #f1f1f1;
        }

        .boton-eliminar {
            background-color: #b02a37;
            color: white;
            border: none;
            padding: 6px 10px;
            border-radius: 4px;
            cursor: pointer;
        }

        .boton-eliminar:hover {
            background-color: #842029;
        }

        .sin-registros {
            text-align: center;
            padding: 20px;
            color: #666;
        }

        .pie {
            margin-top: 25px;
            text-align: center;
            color: #777;
            font-size: 13px;
        }

    </style>

</head>

<body>

<div class="contenedor">

    <h1>IPS Preventiva Farallones</h1>

    <h2>Sistema de Agendamiento de Citas</h2>

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

                    <label>

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