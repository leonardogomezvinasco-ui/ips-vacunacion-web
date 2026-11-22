package co.edu.sena.ipsvacunacionweb.servlet;

import co.edu.sena.ipsvacunacionweb.dao.PersonalSaludDAO;
import co.edu.sena.ipsvacunacionweb.modelo.PersonalSalud;
import jakarta.servlet.*; import jakarta.servlet.annotation.WebServlet; import jakarta.servlet.http.*;
import java.io.IOException; import java.sql.SQLException;

@WebServlet("/personal")
public class PersonalSaludServlet extends HttpServlet {
    private final PersonalSaludDAO dao=new PersonalSaludDAO();
    @Override protected void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException { try { req.setAttribute("personal",dao.listar(false));String id=req.getParameter("id");if(id!=null){PersonalSalud edit=dao.buscarPorId(Integer.parseInt(id));if(edit==null)req.setAttribute("error","No se encontró el profesional seleccionado.");else req.setAttribute("edicion",edit);} req.getRequestDispatcher("/personal.jsp").forward(req,res); } catch(SQLException|IllegalArgumentException e){throw new ServletException("No fue posible consultar el personal de salud",e);} }
    @Override protected void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException { req.setCharacterEncoding("UTF-8"); try { String accion=req.getParameter("accion"); if("estado".equals(accion)){dao.cambiarActivo(Integer.parseInt(req.getParameter("idPersonal")),Boolean.parseBoolean(req.getParameter("activo")));} else {PersonalSalud p=leer(req);if("actualizar".equals(accion)){p.setIdPersonal(Integer.parseInt(req.getParameter("idPersonal")));dao.actualizar(p);}else dao.crear(p);} res.sendRedirect(req.getContextPath()+"/personal"); } catch(SQLException|IllegalArgumentException e){req.setAttribute("error",e.getMessage()); doGet(req,res);} }
    private PersonalSalud leer(HttpServletRequest req){PersonalSalud p=new PersonalSalud();p.setTipoDocumento(req.getParameter("tipoDocumento"));p.setNumeroDocumento(req.getParameter("numeroDocumento"));p.setNombres(req.getParameter("nombres"));p.setApellidos(req.getParameter("apellidos"));p.setRegistroProfesional(req.getParameter("registroProfesional"));p.setCargo(req.getParameter("cargo"));p.setTelefono(req.getParameter("telefono"));return p;}
}
