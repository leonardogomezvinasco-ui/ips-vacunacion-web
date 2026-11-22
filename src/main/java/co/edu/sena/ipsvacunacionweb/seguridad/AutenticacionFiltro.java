package co.edu.sena.ipsvacunacionweb.seguridad;

import co.edu.sena.ipsvacunacionweb.modelo.UsuarioSesion;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.UUID;

@WebFilter("/*")
public class AutenticacionFiltro implements Filter {
    @Override public void doFilter(ServletRequest solicitud, ServletResponse respuesta, FilterChain cadena)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) solicitud;
        HttpServletResponse res = (HttpServletResponse) respuesta;
        String path = req.getRequestURI().substring(req.getContextPath().length());
        if (path.equals("/login") || path.equals("/login.jsp") || path.startsWith("/css/")
                || path.startsWith("/img/") || path.equals("/favicon.ico")) {
            cadena.doFilter(solicitud, respuesta); return;
        }
        HttpSession session = req.getSession(false);
        UsuarioSesion usuario = session == null ? null : (UsuarioSesion) session.getAttribute("usuarioActual");
        if (usuario == null) { res.sendRedirect(req.getContextPath() + "/login"); return; }

        if ("POST".equalsIgnoreCase(req.getMethod())) {
            String esperado = (String) session.getAttribute("csrfToken");
            String recibido = req.getParameter("csrfToken");
            if (esperado == null || recibido == null || !java.security.MessageDigest.isEqual(
                    esperado.getBytes(java.nio.charset.StandardCharsets.UTF_8),
                    recibido.getBytes(java.nio.charset.StandardCharsets.UTF_8))) {
                res.sendError(HttpServletResponse.SC_FORBIDDEN, "La sesión del formulario expiró. Vuelva a cargar la página."); return;
            }
        }

        boolean permitido = switch (path) {
            case "/usuarios", "/usuarios.jsp", "/personal", "/personal.jsp" -> usuario.tieneRol("ADMINISTRADOR");
            case "/registro-vacunacion", "/registro-vacunacion.jsp" -> usuario.tieneRol("ADMINISTRADOR", "PERSONAL_SALUD");
            case "/", "/index.jsp", "/logout" -> true;
            case "/inicio", "/pacientes", "/pacientes.jsp", "/editar-paciente", "/editar-paciente.jsp", "/citas", "/citas.jsp", "/vacunas", "/vacunas.jsp" ->
                    usuario.tieneRol("ADMINISTRADOR", "AUXILIAR");
            default -> false;
        };
        if (!permitido) { res.sendError(HttpServletResponse.SC_FORBIDDEN, "No tiene permisos para acceder a este módulo."); return; }
        req.setAttribute("usuarioActual", usuario);
        req.setAttribute("csrfToken", session.getAttribute("csrfToken"));
        res.setHeader("Cache-Control", "no-store");
        cadena.doFilter(solicitud, respuesta);
    }

    public static String nuevoTokenCsrf() { return UUID.randomUUID().toString(); }
}
