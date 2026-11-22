package co.edu.sena.ipsvacunacionweb.servlet;

import co.edu.sena.ipsvacunacionweb.dao.*;
import jakarta.servlet.*; import jakarta.servlet.annotation.WebServlet; import jakarta.servlet.http.*;
import java.io.IOException; import java.sql.SQLException;

@WebServlet("/usuarios")
public class UsuarioServlet extends HttpServlet {
    private final UsuarioDAO usuarios=new UsuarioDAO(); private final PersonalSaludDAO personal=new PersonalSaludDAO();
    @Override protected void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException { cargar(req); req.getRequestDispatcher("/usuarios.jsp").forward(req,res); }
    @Override protected void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException { req.setCharacterEncoding("UTF-8"); try { if("estado".equals(req.getParameter("accion"))){usuarios.cambiarActivo(Integer.parseInt(req.getParameter("idUsuario")),Boolean.parseBoolean(req.getParameter("activo")),((co.edu.sena.ipsvacunacionweb.modelo.UsuarioSesion)req.getAttribute("usuarioActual")).getIdUsuario());} else {Integer idPersonal=null; String raw=req.getParameter("idPersonal"); if(raw!=null&&!raw.isBlank()) idPersonal=Integer.valueOf(raw); usuarios.crear(req.getParameter("nombreUsuario"),req.getParameter("contrasena"),req.getParameter("rol"),idPersonal);} res.sendRedirect(req.getContextPath()+"/usuarios"); } catch(SQLException|IllegalArgumentException e){req.setAttribute("error",e.getMessage()); cargar(req); req.getRequestDispatcher("/usuarios.jsp").forward(req,res);} }
    private void cargar(HttpServletRequest req)throws ServletException { try { req.setAttribute("usuarios",usuarios.listar()); req.setAttribute("personalActivo",personal.listar(true)); } catch(SQLException e){throw new ServletException("No fue posible consultar los usuarios",e);} }
}
