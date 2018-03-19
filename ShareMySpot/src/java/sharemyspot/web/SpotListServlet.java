/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sharemyspot.web;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sharemyspot.ejb.SpotBean;
import sharemyspot.ejb.UserBean;
import sharemyspot.ejb.ValidationBean;
import sharemyspot.jpa.Category;
import sharemyspot.jpa.Spot;
import sharemyspot.jpa.SpotStatus;
import sharemyspot.jpa.User;


/**
 * Das Servlet ermöglicht nach beliebigen Vorgaben Spots zu suchen, die nicht reseriert sind. Es gibt keine extra Suche deshalb, um nochmal seperat für die Buchung Suchen zu können. Da es die Benutzerfreundlichkeit wegen vielleicht doppelten Suchen  nur senken würde.
 * @author Alexander Becker
 */
@WebServlet(name = "SearchServlet", urlPatterns = {"/Spots"})
public class SpotListServlet extends HttpServlet {

    @EJB
    SpotBean spotBean;
    
    @EJB
    UserBean userBean;
    
    @EJB
    ValidationBean validationBean;
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
   

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        // Definiert welche Zeichen verarbeitet werden dürfen
         request.setCharacterEncoding("utf-8");
        
        // Anfrage erfährt, welche Option es zu wählen gibt in den zwei Enums 
        request.setAttribute("categories", Category.values());
        
        
        // doGet empfängt die Eingaben und hinterlegt diese in den neuen Variablen
        String searchDate=request.getParameter("search_searchDate");       
        String place = request.getParameter("search_place");
        String plz = request.getParameter("search_plz");
        String road = request.getParameter("search_road");
        String roadnumber = request.getParameter("search_roadnumber");
        String kategoryStatus = request.getParameter("search_category");
        String description = request.getParameter("search_description");
        String searchOwner = request.getParameter("search_owner");
        
        // Das eingegbene Datum und die eingegbene Zeit wird versucht in die vorgegebene Form umzuwandeln
        Date dueSearchDate = WebUtils.parseDate(searchDate);
        
        
                
        // Diese Hilfsvariablen dienen dazu, die ausgewählten Values des Enums zu prüfen  
        Category category=null;
        User owner = null;
        
        
        if (kategoryStatus != null) {
            try {
                category = Category.valueOf(kategoryStatus);
            } catch (IllegalArgumentException ex) {
                category = null;
            }
        }
        
        if (searchOwner != null) {
            try {
                owner = this.userBean.findUser(searchOwner);
            } catch (IllegalArgumentException ex) {
                owner = null;
            }
        }
       
        // Die Liste freeSpots empfängt die Ergebnisse, die den Vorgaben der Suche (search) entsprechen. 
        List<Spot> ListSpots;
        List<Spot> freeSpots;
       
        if (searchDate == null) {
           ListSpots=this.spotBean.search(description, owner, place, plz, road, roadnumber, category); // die Zeit und das Datum müssen noch in Spot und SpotBean abgeändert werden.
           freeSpots=this.spotBean.updateSpots(ListSpots); 
           request.setAttribute("freeSpots",freeSpots );
            
            
        } else {
           ListSpots=this.spotBean.search(description, owner, place, plz, road, roadnumber, category); // die Zeit und das Datum müssen noch in Spot und SpotBean abgeändert werden.
           freeSpots=this.spotBean.updateSpotsDate(ListSpots, dueSearchDate); 
           request.setAttribute("freeSpots",freeSpots ); 
        }
      

        
        request.getRequestDispatcher("/WEB-INF/app/Spot_list.jsp").forward(request, response);


       
        
        
        
    }

    
}
