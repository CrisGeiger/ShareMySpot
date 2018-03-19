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
import sharemyspot.ejb.ValidationBean;
import sharemyspot.jpa.Category;
import sharemyspot.jpa.Spot;
import sharemyspot.jpa.SpotStatus;


/**
 * Das Servlet ermöglicht nach beliebigen Vorgaben Spots zu suchen, die nicht reseriert sind. Es gibt keine extra Suche deshalb, um nochmal seperat für die Buchung Suchen zu können. Da es die Benutzerfreundlichkeit wegen vielleicht doppelten Suchen  nur senken würde.
 * @author Alexander Becker
 */
@WebServlet(name = "SearchServlet", urlPatterns = {"/SearchServlet"})
public class SearchServlet extends HttpServlet {

    @EJB
    SpotBean spotBean;
    
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
        request.setAttribute("spotStatus", SpotStatus.values());
        
        // doGet empfängt die Eingaben und hinterlegt diese in den neuen Variablen
        String startDate=request.getParameter("search_startDate");
        String startTime=request.getParameter("search_startTime");
        String endDate=request.getParameter("search_endDate");
        String endTime=request.getParameter("search_endTime");
        String place=request.getParameter("search_place");
        String plz=request.getParameter("search_plz");
        String adresse=request.getParameter("search_adresse"); // Abändern, wenn Attribute street vorhanden
        String kategoryStatus=request.getParameter("search_category");
        //String spotVariante=request.getParameter("search_spotStatus");
        String description=request.getParameter("search_description");
        String username=request.getParameter("search_owner");
        
        // Das eingegbene Datum und die eingegbene Zeit wird versucht in die vorgegebene Form umzuwandeln
        Date dueStartDate = WebUtils.parseDate(startDate);
        Time dueStartTime = WebUtils.parseTime(startTime);
        
        Date dueEndDate = WebUtils.parseDate(endDate);
        Time dueEndTime = WebUtils.parseTime(endTime);
        
        // Diese Hilfsvariablen dienen dazu, die ausgewählten Values des Enums zu prüfen  
        Category category=null;
        SpotStatus spotStatus=null;
        
        
        if (kategoryStatus != null) {
            try {
                category = Category.valueOf(kategoryStatus);
            } catch (IllegalArgumentException ex) {
                category = null;
            }
        }
        
        /**   if (spotVariante != null) {
            try {
                spotStatus = SpotStatus.valueOf(spotVariante);
            } catch (IllegalArgumentException ex) {
                spotStatus = null;
            }
        }
        */
        /**Spot spot = new Spot(username, place, plz,adresse,description,category,favorite);
        *List<String> errors = this.validationBean.validate(spot);
        this.validationBean.validate(spot, errors);
        */
    
        // Erstellen einer Fehlerliste zum Speichern der Fehler, die am Ende sobald welche existieren ausgegeben werden in einer JSP.
             List <String>errors = new ArrayList(); // Anwender wird verpflicht egal bei welcher Suche mindestends einen richtigen Zeitraum anzugeben. Das ermöglicht, dass dieser sich ein Gesamtbild des Angebots an Parkplätzen unseres Service anschauen kann
         if (dueStartDate==null) {
            errors.add("Das Datum muss dem Format dd.mm.yyyy entsprechen.");
         }
         if (dueEndDate==null) {
            errors.add("Das Datum muss dem Format dd.mm.yyyy entsprechen.");
         }
         
        /** if (dueStartTime==null) {
            errors.add("Das Uhrzeit muss dem Format hh.mm.ss entsprechen.");
         }
         
         if (dueEndTime==null) {
            errors.add("Das Uhrzeit muss dem Format hh.mm.ss entsprechen.");
         }
         */

        // Die Liste freeSpots empfängt die Ergebnisse, die den Vorgaben der Suche (search) entsprechen. 
        List<Spot> ListSpots;
        List<Spot> freeSpots;
         if (errors.isEmpty()) {
           // ListSpots=this.spotBean.search(description, username,place,plz,adresse, category); // die Zeit und das Datum müssen noch in Spot und SpotBean abgeändert werden.
            //freeSpots=this.spotBean.updateSearch( ListSpots,dueStartDate, dueEndDate); 
            //request.setAttribute("freeSpots",freeSpots );
        }

        // Weiter zur nächsten Seite
        if (errors.isEmpty()) {
            // Keine Fehler: neue Seite aufrufen
            // Anfrage an die JSP weiterleiten
            request.getRequestDispatcher("/WEB-INF/app/Spot_list.jsp").forward(request, response);
        } else {
            // Fehler: Formuler erneut anzeigen
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);

            HttpSession session = request.getSession();
            session.setAttribute("spotList_form", formValues);// dient als Zwischenspeicher der Fehler
            request.setAttribute("session",session);
            // Anfrage mit Fehler an die JSP weiterleiten
            request.getRequestDispatcher("/WEB-INF/app/Spot_list.jsp").forward(request, response);
        }

       
        
        
        
    }

    
}
