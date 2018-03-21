/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sharemyspot.web;

/**
 *
 * @author Budda
 * Änderung 15.03.18: Becker: Bezeichnungen von ort,anschrift,telefon,vorname,nachname zu englischen Begriffen umgewandelt 
 */
import sharemyspot.ejb.ValidationBean;
import sharemyspot.ejb.UserBean;
import sharemyspot.jpa.Spot;
import sharemyspot.jpa.User;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet für die Registrierungsseite. Hier kann sich ein neuer Benutzer
 * registrieren. Anschließend wird der auf die Startseite weitergeleitet.
 */
@WebServlet(urlPatterns = {"/app/user/"})
public class UserEditServlet extends HttpServlet {
    
    @EJB
    ValidationBean validationBean;
            
    @EJB
    UserBean userBean;
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                
        User user = this.userBean.getCurrentUser();
        request.setAttribute("edit_form",createEditForm(user));
        
        // Anfrage an dazugerhörige JSP weiterleiten
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/app/user_edit.jsp");
        dispatcher.forward(request, response);
        
        // Alte Formulardaten aus der Session entfernen
        HttpSession session = request.getSession();
        session.removeAttribute("edit_form");
    }
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Formulareingaben auslesen
        request.setCharacterEncoding("utf-8");
        
        String username = request.getParameter("edit_username");
        String oldpw = request.getParameter("edit_oldpw");
        String password1 = request.getParameter("signup_password1");
        String password2 = request.getParameter("signup_password2");
        String lastName = request.getParameter("signup_lastName");
        String firstName = request.getParameter("signup_firstName");
        String road = request.getParameter("signup_road");
        String roadnumber = request.getParameter("signup_roadnumber");
        String plz = request.getParameter("signup_plz");
        String place = request.getParameter("signup_place");
        String phoneNumber= request.getParameter("signup_phoneNumber");
        String email = request.getParameter("signup_email");
       
//        int iplz=00000;
//        try{
//             iplz = Integer.parseInt(plz);
//        
//        }catch (NumberFormatException nfe){
//            
//        }
        
        // Eingaben prüfen
        User user = new User(username, password1, lastName, firstName, place, plz, road, roadnumber, phoneNumber, email);
        List<String> errors = this.validationBean.validate(user);
        this.validationBean.validate(user.getPassword(), errors);
        
        if (password1 != null && password2 != null && !password1.equals(password2)) {
            errors.add("Die beiden Passwörter stimmen nicht überein.");
        }
        
        // Neuen Benutzer anlegen
        try{
        if (errors.isEmpty()) {
                this.userBean.update(user); }
        }
        catch(IllegalStateException ie){
        }
        
        // Weiter zur nächsten Seite
        if (errors.isEmpty()) {
            // Keine Fehler: Startseite aufrufen
            response.sendRedirect(WebUtils.appUrl(request, "/app/spots/"));
        } else {
            // Fehler: Formular erneut anzeigen
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);
            
            HttpSession session = request.getSession();
            session.setAttribute("edit_form", formValues);
            
            response.sendRedirect(request.getRequestURI());
        }
    }
    private FormValues createEditForm(User user) {
        Map<String, String[]> values = new HashMap<>();

        values.put("edit_username", new String[]{
            user.getUsername()
        });

        values.put("edit_lastname", new String[]{
            user.getLastName()
        });
        
        values.put("edit_firstname", new String[]{
            user.getFirstName()
        });
        
        values.put("edit_password1", new String[]{
            "default"
        });
        
        values.put("edit_password2", new String[]{
            "default"
        });
        
        values.put("edit_oldpw", new String[]{
            "default"
        });

        values.put("edit_road", new String[]{
            user.getRoad()
        });
        
        values.put("edit_roadnumber", new String[]{
            user.getRoadnumber()
        });

        values.put("edit_plz", new String[]{
            user.getPlz()
        });

        values.put("edit_place", new String[]{
            user.getPlace()
        });
        
        values.put("edit_phoneNumber", new String[]{
            user.getPhoneNumber()
        });
        
        values.put("edit_email", new String[]{
            user.getEmail()
        });

        FormValues formValues = new FormValues();
        formValues.setValues(values);
        return formValues;
    }
    
}
