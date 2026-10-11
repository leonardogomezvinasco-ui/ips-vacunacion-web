package co.edu.sena.ipsvacunacionweb.servlet;

import co.edu.sena.ipsvacunacionweb.dao.CitaDAO;
import co.edu.sena.ipsvacunacionweb.dao.PacienteDAO;
import co.edu.sena.ipsvacunacionweb.modelo.Cita;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

@WebServlet("/citas")
public class CitaServlet extends HttpServlet {
    private final CitaDAO dao = new CitaDAO();
    private final PacienteDAO pacienteDAO = new PacienteDAO();
    @Override protected void doGet(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException {
        try {
            req.setAttribute("citas", dao.listar());
            req.setAttribute("pacientes", pacienteDAO.listar());
            req.getRequestDispatcher("/citas.jsp").forward(req,res);
        }
        catch (SQLException e) { throw new IOException("No fue posible consultar las citas",e); }
    }
    @Override protected void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException {
        req.setCharacterEncoding("UTF-8");
        try {
            if ("estado".equals(req.getParameter("accion"))) dao.cambiarEstado(Integer.parseInt(req.getParameter("idCita")),req.getParameter("estado"));
            else { Cita c=new Cita(); c.setIdPaciente(Integer.parseInt(req.getParameter("idPaciente"))); c.setVacuna(req.getParameter("vacuna").trim()); c.setFechaHora(LocalDateTime.parse(req.getParameter("fechaHora"))); if(c.getVacuna().isBlank() || !c.getFechaHora().isAfter(LocalDateTime.now())) throw new IllegalArgumentException("Indique una vacuna y una fecha futura"); dao.crear(c); }
            res.sendRedirect(req.getContextPath()+"/citas");
        } catch (SQLException|IllegalArgumentException e) { res.sendError(400,"Revise los datos de la cita: "+e.getMessage()); }
    }
}
