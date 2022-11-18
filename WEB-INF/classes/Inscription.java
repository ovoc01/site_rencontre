package objects;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import con1.Connexion;
public class Inscription extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException,IOException {
        PrintWriter out = null;
        out = res.getWriter();
        // res.setContentType("Content-Type:text/plain");
        try {
            Connexion c = new Connexion("postgres","postgres","password");
            String pwd = String.valueOf(req.getParameter("pwd"));
            String name = String.valueOf(req.getParameter("name"));
            Utilisateur temp = new Utilisateur();
            temp.setPwd(pwd);
            temp.setNom(name);
            HttpSession session = req.getSession();
            String idUser = String.valueOf(session.getAttribute("session"));
            idUser = temp.getIdUser();
            session.setAttribute("session",idUser);
            temp.insertToDb(c,"postgres");
            res.sendRedirect("info.html");
                
        // out.println("fdfdfdgfhjdsiuvgshudfshvidhgeuighurfihg");
        } catch (Exception e) {
            out.println(e.getMessage());
            // e.printStackTrace();
        }
        
    }    
}
