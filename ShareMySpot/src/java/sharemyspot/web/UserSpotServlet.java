/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sharemyspot.web;

import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jboss.weld.logging.Category;
import sharemyspot.ejb.SpotBean;
import sharemyspot.ejb.UserBean;
import sharemyspot.jpa.Spot;
import sharemyspot.jpa.SpotStatus;

/**
 *Servlet-Klasse zum anzeigen von eigens angelegten Parkplätzen
 * @author cgeiger1
 */
@WebServlet(urlPatterns = {"/app/meineSpots/"})
public class UserSpotServlet extends HttpServlet {
    
    @EJB
    private SpotBean sb;
    
    @EJB
    private UserBean ub;
    
    @Override
    public void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //verfügbare Status und Kategorien auslesen, ich bin mir unsicher, ob wir das brauchen, oder soll der Benutzer seine eigenen Plätze nach Kategorie und Status
        //suchen können über ein Dropdown-Menü? Falls unklar ist, was ich meine, einfach mal die jTodo laufen lassen, dann wirds klarer
        request.setAttribute("category", Category.values());
        request.setAttribute("status", SpotStatus.values());
        
        //username aus URL lesen
        String searchUsername = request.getParameter("search_username");
        
        //Nach den Aufgaben des Eigentümers suchen
        if (searchUsername != null) {
            List<Spot> ownSpots = sb.findByUsername(this.ub.getCurrentUser().getUsername());
        }
        
        request.getRequestDispatcher("/WEB-INF/app/spot_list.jsp").forward(request, response);
    }
}
