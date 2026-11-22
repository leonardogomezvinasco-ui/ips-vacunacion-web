package co.edu.sena.ipsvacunacionweb.servlet;

import co.edu.sena.ipsvacunacionweb.dao.*;
import co.edu.sena.ipsvacunacionweb.modelo.UsuarioSesion;
import jakarta.servlet.*; import jakarta.servlet.annotation.WebServlet; import jakarta.servlet.http.*;
import java.io.IOException; import java.sql.SQLException; import java.time.LocalDateTime;

@WebServlet("/registro-vacunacion")
public class RegistroVacunacionServlet extends HttpServlet {
    private final RegistroVacunacionDAO registros=new RegistroVacunacionDAO();
    private final CitaDAO citas=new CitaDAO(); private final PersonalSaludDAO personal=new PersonalSaludDAO();
    @Override protected void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException { cargar(req); req.getRequestDispatcher("/registro-vacunacion.jsp").forward(req,res); }
    @Override protected void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException {
        req.setCharacterEncoding("UTF-8"); try {
            UsuarioSesion usuario=(UsuarioSesion)req.getAttribute("usuarioActual");
            int idPersonal;
            if(usuario.getIdPersonal()!=null) idPersonal=usuario.getIdPersonal();
            else idPersonal=Integer.parseInt(req.getParameter("idPersonal"));
            registros.registrar(Integer.parseInt(req.getParameter("idCita")),idPersonal,
                    Integer.parseInt(req.getParameter("numeroDosis")),req.getParameter("lote"),
                    req.getParameter("observaciones"),LocalDateTime.parse(req.getParameter("fechaAplicacion")));
            res.sendRedirect(req.getContextPath()+"/registro-vacunacion");
        } catch(SQLException|IllegalArgumentException e){req.setAttribute("error",e.getMessage());cargar(req);req.getRequestDispatcher("/registro-vacunacion.jsp").forward(req,res);}
    }
    private void cargar(HttpServletRequest req)throws ServletException { try { req.setAttribute("citasPendientes",citas.listarPendientesAplicacion());req.setAttribute("registros",registros.listar());req.setAttribute("personalActivo",personal.listar(true)); } catch(SQLException e){throw new ServletException("No fue posible consultar las vacunaciones",e);} }
}
