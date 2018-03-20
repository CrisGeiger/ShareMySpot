/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sharemyspot.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sharemyspot.ejb.SpotBean;
import sharemyspot.ejb.UserBean;
import sharemyspot.ejb.ValidationBean;
import sharemyspot.jpa.Spot;
import sharemyspot.jpa.User;
import sharemyspot.jpa.Category;
import sharemyspot.jpa.SpotStatus;

/**
 *
 * @author JU_FI
 */
@WebServlet(name = "SpotEditServlet", urlPatterns = {"/SpotEditServlet"})
public class SpotEditServlet extends HttpServlet {

    @EJB
    SpotBean spotBean;
    
    @EJB
    UserBean userBean;
    
    @EJB
    ValidationBean validationBean; 
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        User user = this.userBean.getCurrentUser(); 
        
        //Zu bearbeitender Parkplatz einlesen
         Spot spot = this.getRequestedSpot(request);
        
        request.setAttribute("spot_form", this.createSpotForm(spot));
        request.setAttribute("owner", spot.getOwner());
        request.setAttribute("user", this.userBean.getCurrentUser());
     
        // Anfrage an dazugerhörige JSP weiterleiten
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/app/spot_edit.jsp");
        dispatcher.forward(request, response);
     
        // Alte Formulardaten aus der Session entfernen
        HttpSession session = request.getSession();
        session.removeAttribute("spot_form");
        
    }
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Angeforderte Aktion ausführen
        request.setCharacterEncoding("utf-8");

        String action = request.getParameter("action");

        if (action == null) {
            action = "";
        }

        switch (action) {
            case "save":
                this.saveSpot(request, response);
                break;
            case "delete":
                this.deleteSpot(request, response);
                break;
        }
        
    }
        
        
     /**
     * Aufgerufen in doPost(): Neuer oder vorhandene Spot speichern
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
      private void saveSpot(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
          
          List<String> errors = new ArrayList<>();
          
          String spotPlace = request.getParameter("spot_place");
          String spotPlz = request.getParameter("spot_plz");
          String spotRoad = request.getParameter("spot_road");
          String spotRoadNumber = request.getParameter("spot_roadNumber");
          String spotDescription = request.getParameter("spot_description"); 
          String spotCategory = request.getParameter("spot_category");
          String spotStatus = request.getParameter("spot_status");
          String spotFreeFrom = request.getParameter("spot_freeFrom");
          String spotFreeTo = request.getParameter("spot_freeTo");
          
          Spot spot = this.getRequestedSpot(request);
          
          if (spotCategory != null && !spotCategory.trim().isEmpty()) {
            try {
                spot.setCategory(Category.valueOf(spotCategory));
            } catch (NumberFormatException ex) {
                // Ungültige oder keine ID mitgegeben
            }
           }
          
          if (spotStatus != null && !spotStatus.trim().isEmpty()) {
            try {
                spot.setSpotStatus(SpotStatus.valueOf(spotStatus));
            } catch (NumberFormatException ex) {
                // Ungültige oder keine ID mitgegeben
            }
           }
          
          spot.setPlace(spotPlace);
          spot.setPlz(spotPlz);
          spot.setRoad(spotRoad);
          spot.setroadNumber(spotRoadNumber);
          spot.setDescription(spotDescription);
          //spot.setFreeFrom(SimpleDateFormat.parse(spotFreeFrom));
          //spot.setFreeTo(SimpleDateFormat.parse(spotFreeTo));
          
      
          this.validationBean.validate(spot, errors);
          
          // Nächste Seite aufrufen
            if (errors.isEmpty()) {
            // Keine Fehler: Startseite aufrufen
            response.sendRedirect(WebUtils.appUrl(request, "/app/Startseite/"));
        } else {
            // Fehler: Formuler erneut anzeigen
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);

            HttpSession session = request.getSession();
            session.setAttribute("spot_form", formValues);

            response.sendRedirect(request.getRequestURI());
      }
      }
      /**
     * Aufgerufen in doPost: Vorhandener Spot löschen
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
       private void deleteSpot(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
           
           //Datensatz löschen 
           Spot spot = this.getRequestedSpot(request);
           this.spotBean.delete(spot);
           
           //zurück zur Übersicht
           response.sendRedirect(WebUtils.appUrl(request, "/app/Startseite/"));   
           }
       
      /**
     * Zu bearbeitende Aufgabe aus der URL ermitteln und zurückgeben. Gibt
     * entweder einen vorhandenen Datensatz oder ein neues, leeres Objekt
     * zurück.
     *
     * @param request HTTP-Anfrage
     * @return Zu bearbeitende Aufgabe
     */
       private Spot getRequestedSpot(HttpServletRequest request){
           
            // Zunächst davon ausgehen, dass ein neuer Satz angelegt werden soll
           
           Spot spot = new Spot();
           spot.setOwner(this.userBean.getCurrentUser());
           
           // ID aus der URL herausschneiden
           String spotId = request.getPathInfo();
           
           if (spotId == null) {
            spotId = "";
        }

        spotId = spotId.substring(1);

        if (spotId.endsWith("/")) {
            spotId = spotId.substring(0, spotId.length() - 1);
        }

        // Versuchen, den Datensatz mit der übergebenen ID zu finden
        try {
            spot = this.spotBean.findById(Long.parseLong(spotId));
        } catch (NumberFormatException ex) {
            // Ungültige oder keine ID in der URL enthalten
        }

        return spot;   
       }
       
     /**
     * Neues FormValues-Objekt erzeugen und mit den Daten eines aus der
     * Datenbank eingelesenen Datensatzes füllen. Dadurch müssen in der JSP
     * keine hässlichen Fallunterscheidungen gemacht werden, ob die Werte im
     * Formular aus der Entity oder aus einer vorherigen Formulareingabe
     * stammen.
     *
     * @param spot Der zu bearbeitende Parkplatz
     * @return Neues, gefülltes FormValues-Objekt
     */
       
       private FormValues createSpotForm(Spot spot){
           Map<String, String[]> values = new HashMap<>();

        if (spot.getCategory() != null) {
            values.put("spot_category", new String[]{
                spot.getCategory().toString()
            });
        }
        
         if (spot.getSpotStatus() != null) {
            values.put("spot_status", new String[]{
                spot.getSpotStatus().toString()
            });
        }
        
        values.put("spot_place", new String[]{
            spot.getPlace()
            });
                
        values.put("spot_plz", new String[]{
            spot.getPlz()
           });   
  
        
        values.put("spot_road", new String[]{
            spot.getRoad()
        });
        
        values.put("spot_roadNumber", new String[]{
            spot.getroadNumber()
        });
        
       values.put("spot_description", new String[]{
            spot.getDescription()
        }); 
       
       values.put("spot_FreeFrom", new String[]{
            spot.getFreeFrom().toString()
        });
       
       values.put("spot_FreeTo", new String[]{
            spot.getFreeTo().toString()
        });
       
        FormValues formValues = new FormValues();
        formValues.setValues(values);
        return formValues;
    }
     
}
    



