<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html><html lang="es"><head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1">
<title>Acceso | IPS Farallones</title><link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css"></head>
<body><main class="contenedor acceso"><section class="portada"><h1>IPS Preventiva Farallones</h1><p>Acceso al sistema de vacunación</p></section>
<section class="tarjeta acceso-tarjeta"><h2>Iniciar sesión</h2><p>Ingrese las credenciales asignadas por la IPS.</p>
<c:if test="${not empty error}"><p class="mensaje error" role="alert"><c:out value="${error}"/></p></c:if>
<form method="post" action="${pageContext.request.contextPath}/login" class="formulario acceso-form">
<div class="campo"><label for="nombreUsuario">Usuario</label><input id="nombreUsuario" name="nombreUsuario" required maxlength="60" autocomplete="username"></div>
<div class="campo"><label for="contrasena">Contraseña</label><input id="contrasena" name="contrasena" type="password" required autocomplete="current-password"></div>
<div class="campo campo-completo"><button type="submit">Ingresar</button></div></form></section>
<footer class="pie">Proyecto académico ADSO - SENA.</footer></main></body></html>
