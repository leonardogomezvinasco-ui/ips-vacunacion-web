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
import java.util.List;

@WebServlet("/pacientes")
public class PacienteServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final PacienteDAO pacienteDAO = new PacienteDAO();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        List<Paciente> pacientes = pacienteDAO.listar();

        request.setAttribute("pacientes", pacientes);

        request.getRequestDispatcher("/pacientes.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String accion = request.getParameter("accion");

        if ("registrar".equals(accion)) {
            registrarPaciente(request, response);
        } else if ("actualizar".equals(accion)) {
            actualizarPaciente(request, response);
        } else if ("eliminar".equals(accion)) {
           eliminarPaciente(request, response);
} else {
            response.sendRedirect(
                    request.getContextPath() + "/pacientes");
        }
    }

    private void registrarPaciente(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        Paciente paciente = new Paciente();

        paciente.setTipoDocumento(
                request.getParameter("tipoDocumento"));
        paciente.setNumeroDocumento(
                request.getParameter("numeroDocumento"));
        paciente.setNombre(
                request.getParameter("nombre"));
        paciente.setApellido(
                request.getParameter("apellido"));
        paciente.setTelefono(
                request.getParameter("telefono"));
        paciente.setDireccion(
                request.getParameter("direccion"));
        paciente.setCiudad(
                request.getParameter("ciudad"));

        String consentimiento =
                request.getParameter("consentimiento");

        paciente.setConsentimiento(
                "true".equals(consentimiento));

        paciente.setFechaRegistro(
                LocalDate.parse(
                        request.getParameter("fechaRegistro")));

        pacienteDAO.insertar(paciente);

        response.sendRedirect(
                request.getContextPath() + "/pacientes");
    }

        private void actualizarPaciente(
                HttpServletRequest request,
                HttpServletResponse response)
                throws IOException {
        
                String id = request.getParameter("id");
        
                if (id != null && !id.isBlank()) {
                try {
                        int idPaciente = Integer.parseInt(id);
        
                        Paciente paciente = new Paciente();
        
                        paciente.setIdPaciente(idPaciente);
                        paciente.setTipoDocumento(
                                request.getParameter("tipoDocumento"));
                        paciente.setNumeroDocumento(
                                request.getParameter("numeroDocumento"));
                        paciente.setNombre(
                                request.getParameter("nombre"));
                        paciente.setApellido(
                                request.getParameter("apellido"));
                        paciente.setTelefono(
                                request.getParameter("telefono"));
                        paciente.setDireccion(
                                request.getParameter("direccion"));
                        paciente.setCiudad(
                                request.getParameter("ciudad"));
        
                        String consentimiento =
                                request.getParameter("consentimiento");
        
                        paciente.setConsentimiento(
                                "true".equals(consentimiento));
        
                        paciente.setFechaRegistro(
                                LocalDate.parse(
                                        request.getParameter("fechaRegistro")));
        
                        pacienteDAO.actualizar(paciente);
                } catch (NumberFormatException excepcion) {
                        // Se ignora un ID que no tenga formato numérico.
                }
                }
        
                response.sendRedirect(
                        request.getContextPath() + "/pacientes");
        }

    private void eliminarPaciente(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        String id = request.getParameter("id");

        if (id != null && !id.isBlank()) {
            try {
                int idPaciente = Integer.parseInt(id);
                pacienteDAO.eliminar(idPaciente);
            } catch (NumberFormatException excepcion) {
                // Se ignora un ID que no tenga formato numérico.
            }
        }

        response.sendRedirect(
                request.getContextPath() + "/pacientes");
    }
}
