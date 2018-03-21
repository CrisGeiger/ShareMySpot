/*
 * Zu Risiken und Nebenwirkungen lesen Sie die Packunsgbeilage und fragen Sie ihren Arzt oder Apotheker
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
        
        
        List<Spot> ownSpots = sb.findByUsername(this.ub.getCurrentUser().getUsername());
        request.setAttribute("ownSpots", ownSpots);
        
        request.getRequestDispatcher("/WEB-INF/app/spot_list.jsp").forward(request, response);
    }
}
