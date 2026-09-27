package co.edu.sena.ipsvacunacionweb;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/inicio")
public class InicioServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html lang='es'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>IPS Vacunación</title>");
        out.println("</head>");
        out.println("<body>");

        out.println("<h1>IPS Preventiva Farallones</h1>");
        out.println("<h2>Sistema de Agendamiento de Citas</h2>");

        out.println("<p>El Servlet de inicio funciona correctamente.</p>");

        out.println("<hr>");

        out.println("<p>Proyecto ADSO - SENA</p>");

        out.println("</body>");
        out.println("</html>");
    }
}
