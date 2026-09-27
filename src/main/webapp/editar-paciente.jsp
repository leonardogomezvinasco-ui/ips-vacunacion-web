<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Editar paciente</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f6f8;
            margin: 0;
            padding: 30px;
        }

        .contenedor {
            width: 550px;
            margin: auto;
            background-color: white;
            padding: 25px;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
        }

        h1 {
            text-align: center;
            margin-bottom: 25px;
        }

        label {
            display: block;
            margin-top: 15px;
            margin-bottom: 5px;
            font-weight: bold;
        }

        input,
        select {
            width: 100%;
            padding: 10px;
            box-sizing: border-box;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        .checkbox {
            display: flex;
            align-items: center;
            gap: 10px;
            margin-top: 15px;
        }

        .checkbox input {
            width: auto;
        }

        .botones {
            margin-top: 25px;
            display: flex;
            gap: 10px;
        }

        button,
        .cancelar {
            flex: 1;
            padding: 10px;
            border: none;
            border-radius: 4px;
            text-align: center;
            text-decoration: none;
            cursor: pointer;
            font-size: 15px;
        }

        button {
            background-color: #198754;
            color: white;
        }

        .cancelar {
            background-color: #6c757d;
            color: white;
        }
    </style>
</head>

<body>

<div class="contenedor">

    <h1>Editar paciente</h1>

    <form action="${pageContext.request.contextPath}/editar-paciente"
          method="post">

        <!-- ID del paciente -->
        <input type="hidden"
               name="idPaciente"
               value="${paciente.idPaciente}">

        <label for="tipoDocumento">
            Tipo de documento:
        </label>

        <select id="tipoDocumento"
                name="tipoDocumento"
                required>

            <option value="CC"
                ${paciente.tipoDocumento == 'CC' ? 'selected' : ''}>
                Cédula de ciudadanía
            </option>

            <option value="CE"
                ${paciente.tipoDocumento == 'CE' ? 'selected' : ''}>
                Cédula de extranjería
            </option>

            <option value="TI"
                ${paciente.tipoDocumento == 'TI' ? 'selected' : ''}>
                Tarjeta de identidad
            </option>

            <option value="PP"
                ${paciente.tipoDocumento == 'PP' ? 'selected' : ''}>
                Pasaporte
            </option>

        </select>

        <label for="numeroDocumento">
            Número de documento:
        </label>

        <input type="text"
               id="numeroDocumento"
               name="numeroDocumento"
               value="${paciente.numeroDocumento}"
               required>

        <label for="nombre">
            Nombre:
        </label>

        <input type="text"
               id="nombre"
               name="nombre"
               value="${paciente.nombre}"
               required>

        <label for="apellido">
            Apellido:
        </label>

        <input type="text"
               id="apellido"
               name="apellido"
               value="${paciente.apellido}"
               required>

        <label for="telefono">
            Teléfono:
        </label>

        <input type="text"
               id="telefono"
               name="telefono"
               value="${paciente.telefono}">

        <label for="direccion">
            Dirección:
        </label>

        <input type="text"
               id="direccion"
               name="direccion"
               value="${paciente.direccion}">

        <label for="ciudad">
            Ciudad:
        </label>

        <input type="text"
               id="ciudad"
               name="ciudad"
               value="${paciente.ciudad}">

        <label for="fechaRegistro">
            Fecha de registro:
        </label>

        <input type="date"
               id="fechaRegistro"
               name="fechaRegistro"
               value="${paciente.fechaRegistro}"
               required>

        <div class="checkbox">

            <input type="checkbox"
                   id="consentimiento"
                   name="consentimiento"
                   value="true"
                   ${paciente.consentimiento ? 'checked' : ''}>

            <label for="consentimiento">
                El paciente otorgó su consentimiento
            </label>

        </div>

        <div class="botones">

            <button type="submit">
                Actualizar paciente
            </button>

            <a href="${pageContext.request.contextPath}/pacientes"
               class="cancelar">
                Cancelar
            </a>

        </div>

    </form>

</div>

</body>
</html>