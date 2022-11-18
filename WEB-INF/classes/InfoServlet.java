package objects;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import con1.Connexion;
public class InfoServlet extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException,IOException {
        PrintWriter out = null;
        out = res.getWriter();
        // res.setContentType("Content-Type:text/plain");
        try {
            Connexion c = new Connexion("postgres","postgres","password");
            HttpSession session = req.getSession();
            String idUser = String.valueOf(session.getAttribute("session")) ;
            String estCroyant = String.valueOf(req.getParameter("croyant"));
            String salaire = String.valueOf(req.getParameter("salaire"));
            int longueur = Integer.parseInt(String.valueOf(req.getParameter("longueur")));
            String diplome = String.valueOf(req.getParameter("diplome"));
            String nationality = String.valueOf(req.getParameter("nationality"));
            String genre = String.valueOf(req.getParameter("genre"));

            Info info_user = new Info(idUser,estCroyant,salaire,longueur,diplome,nationality,genre);
            info_user.insertToDb(c,"postgres");
        } catch (Exception e) {
            out.println(e.getMessage());
            // e.printStackTrace();
        }
        
    }
}
