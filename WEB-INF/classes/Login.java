package objects;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import con1.Connexion;
public class Login extends HttpServlet{
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException,IOException {
        PrintWriter out = null;
        out = res.getWriter();
        // res.setContentType("Content-Type:text/plain");
        try {
            String pwd = String.valueOf(req.getParameter("pwd"));
            String name = String.valueOf(req.getParameter("name"));
            Utilisateur temp = new Utilisateur(pwd,name);
            
            HttpSession session = req.getSession();
            String idUser = String.valueOf(session.getAttribute("session"));
            idUser = temp.getIdInDatabase();
            session.setAttribute("session",idUser);
            if(temp.isInDatabase()){
                res.sendRedirect("info.html");
            }
            else{
                res.sendRedirect("index.html");
            }    
        // out.println("fdfdfdgfhjdsiuvgshudfshvidhgeuighurfihg");
        } catch (Exception e) {
            out.println(e.getMessage());
            // e.printStackTrace();
        }
        
    }
}