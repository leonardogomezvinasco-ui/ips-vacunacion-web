package co.edu.sena.ipsvacunacionweb.servlet;

import co.edu.sena.ipsvacunacionweb.dao.PacienteDAO;
import co.edu.sena.ipsvacunacionweb.modelo.Paciente;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/editar-paciente")
public class EditarPacienteServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final PacienteDAO pacienteDAO = new PacienteDAO();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("id");

        if (id == null || id.isBlank()) {
            response.sendRedirect(
                    request.getContextPath() + "/pacientes");
            return;
        }

        try {

            int idPaciente = Integer.parseInt(id);

            Paciente paciente =
                    pacienteDAO.buscarPorId(idPaciente);

            if (paciente == null) {
                response.sendRedirect(
                        request.getContextPath() + "/pacientes");
                return;
            }

            request.setAttribute("paciente", paciente);

            request.getRequestDispatcher(
                    "/editar-paciente.jsp")
                    .forward(request, response);

        } catch (NumberFormatException excepcion) {

            response.sendRedirect(
                    request.getContextPath() + "/pacientes");
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {

            int idPaciente = Integer.parseInt(
                    request.getParameter("idPaciente"));

            String tipoDocumento =
                    request.getParameter("tipoDocumento");

            String numeroDocumento =
                    request.getParameter("numeroDocumento");

            String nombre =
                    request.getParameter("nombre");

            String apellido =
                    request.getParameter("apellido");

            String telefono =
                    request.getParameter("telefono");

            String direccion =
                    request.getParameter("direccion");

            String ciudad =
                    request.getParameter("ciudad");

            boolean consentimiento =
                    request.getParameter("consentimiento") != null;

            LocalDate fechaRegistro =
                    LocalDate.parse(
                            request.getParameter("fechaRegistro"));

            Paciente paciente = new Paciente();

            paciente.setIdPaciente(idPaciente);
            paciente.setTipoDocumento(tipoDocumento);
            paciente.setNumeroDocumento(numeroDocumento);
            paciente.setNombre(nombre);
            paciente.setApellido(apellido);
            paciente.setTelefono(telefono);
            paciente.setDireccion(direccion);
            paciente.setCiudad(ciudad);
            paciente.setConsentimiento(consentimiento);
            paciente.setFechaRegistro(fechaRegistro);

            boolean actualizado =
                    pacienteDAO.actualizar(paciente);

            if (actualizado) {

                response.sendRedirect(
                        request.getContextPath() + "/pacientes");

            } else {

                request.setAttribute(
                        "mensaje",
                        "No fue posible actualizar el paciente.");

                request.setAttribute(
                        "paciente",
                        paciente);

                request.getRequestDispatcher(
                        "/editar-paciente.jsp")
                        .forward(request, response);
            }

        } catch (Exception excepcion) {

            excepcion.printStackTrace();

            request.setAttribute(
                    "mensaje",
                    "Ocurrió un error al actualizar el paciente.");

            request.getRequestDispatcher(
                    "/editar-paciente.jsp")
                    .forward(request, response);
        }
    }
}