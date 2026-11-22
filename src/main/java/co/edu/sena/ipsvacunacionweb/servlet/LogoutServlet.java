package co.edu.sena.ipsvacunacionweb.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        cerrar(req,res);
    }
    @Override protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        cerrar(req,res);
    }
    private void cerrar(HttpServletRequest req,HttpServletResponse res)throws IOException {
        HttpSession session = req.getSession(false); if (session != null) session.invalidate();
        res.sendRedirect(req.getContextPath() + "/login");
    }
}
