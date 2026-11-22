package co.edu.sena.ipsvacunacionweb.servlet;

import co.edu.sena.ipsvacunacionweb.dao.UsuarioDAO;
import co.edu.sena.ipsvacunacionweb.modelo.UsuarioSesion;
import co.edu.sena.ipsvacunacionweb.seguridad.AutenticacionFiltro;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final UsuarioDAO usuarios = new UsuarioDAO();
    @Override protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req, res);
    }
    @Override protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");
        try {
            UsuarioSesion usuario = usuarios.autenticar(req.getParameter("nombreUsuario"), req.getParameter("contrasena"));
            if (usuario == null) {
                res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                req.setAttribute("error", "Usuario o contraseña incorrectos, o cuenta inactiva.");
                req.getRequestDispatcher("/login.jsp").forward(req, res); return;
            }
            HttpSession anterior = req.getSession(false);
            if (anterior != null) anterior.invalidate();
            HttpSession session = req.getSession(true);
            req.changeSessionId();
            session.setMaxInactiveInterval(30 * 60);
            session.setAttribute("usuarioActual", usuario);
            session.setAttribute("csrfToken", AutenticacionFiltro.nuevoTokenCsrf());
            res.sendRedirect(req.getContextPath() + "/");
        } catch (SQLException e) { throw new ServletException("No se pudo consultar la cuenta de usuario", e); }
    }
}
