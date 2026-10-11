<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html><html lang="es"><head><meta charset="UTF-8"><title>Citas</title></head><body>
<h1>Agendamiento de citas</h1><a href="${pageContext.request.contextPath}/">Inicio</a>
<h2>Nueva cita</h2>
<form method="post" action="${pageContext.request.contextPath}/citas">
<label>Paciente <select name="idPaciente" required><option value="">Seleccione un paciente</option><c:forEach var="paciente" items="${pacientes}"><option value="${paciente.idPaciente}"><c:out value="${paciente.nombre}"/> <c:out value="${paciente.apellido}"/> — <c:out value="${paciente.numeroDocumento}"/></option></c:forEach></select></label>
<label>Vacuna <input name="vacuna" maxlength="100" required></label>
<label>Fecha y hora <input name="fechaHora" type="datetime-local" required></label><button>Agendar</button></form>
<h2>Citas registradas</h2><table border="1"><thead><tr><th>ID</th><th>Paciente</th><th>Vacuna</th><th>Fecha</th><th>Estado</th><th>Acción</th></tr></thead><tbody>
<c:forEach var="cita" items="${citas}"><tr><td><c:out value="${cita.idCita}"/></td><td><c:out value="${cita.paciente}"/></td><td><c:out value="${cita.vacuna}"/></td><td><c:out value="${cita.fechaHora}"/></td><td><c:out value="${cita.estado}"/></td><td><form method="post" action="${pageContext.request.contextPath}/citas"><input type="hidden" name="accion" value="estado"><input type="hidden" name="idCita" value="${cita.idCita}"><select name="estado"><option>Programada</option><option>Atendida</option><option>Cancelada</option></select><button>Actualizar</button></form></td></tr></c:forEach>
</tbody></table></body></html>
