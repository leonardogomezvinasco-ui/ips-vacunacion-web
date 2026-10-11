package co.edu.sena.ipsvacunacionweb.servlet;
import co.edu.sena.ipsvacunacionweb.dao.VacunaDAO; import co.edu.sena.ipsvacunacionweb.modelo.Vacuna;
import jakarta.servlet.ServletException; import jakarta.servlet.annotation.WebServlet; import jakarta.servlet.http.*; import java.io.IOException; import java.sql.SQLException;
@WebServlet("/vacunas") public class VacunaServlet extends HttpServlet {
 private final VacunaDAO dao=new VacunaDAO();
 @Override protected void doGet(HttpServletRequest q,HttpServletResponse s)throws ServletException,IOException{try{q.setAttribute("vacunas",dao.listar());q.getRequestDispatcher("/vacunas.jsp").forward(q,s);}catch(SQLException e){throw new IOException("Error al consultar vacunas",e);}}
 @Override protected void doPost(HttpServletRequest q,HttpServletResponse s)throws IOException{q.setCharacterEncoding("UTF-8");try{Vacuna v=new Vacuna();v.setNombre(q.getParameter("nombre"));v.setFabricante(q.getParameter("fabricante"));v.setDosisDisponibles(Integer.parseInt(q.getParameter("dosis")));dao.guardar(v);s.sendRedirect(q.getContextPath()+"/vacunas");}catch(SQLException|IllegalArgumentException e){s.sendError(400,"Revise los datos de la vacuna: "+e.getMessage());}}
}
