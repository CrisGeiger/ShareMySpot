/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sharemyspot.web;

/**
 *
 * @author Budda
 */

import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sharemyspot.ejb.UserBean;
import sharemyspot.ejb.ValidationBean;
import sharemyspot.jpa.User;

/**
 * Servlet für die Registrierungsseite. Hier kann sich ein neuer Benutzer
 * registrieren. Anschließend wird der auf die Startseite weitergeleitet.
 */
@WebServlet(urlPatterns = {"/signup/"})
public class SignUpServlet extends HttpServlet {
    
    @EJB
    ValidationBean validationBean;
            
    @EJB
    UserBean userBean;
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Anfrage an dazugerhörige JSP weiterleiten
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/login/signup.jsp");
        dispatcher.forward(request, response);
        
        // Alte Formulardaten aus der Session entfernen
        HttpSession session = request.getSession();
        session.removeAttribute("signup_form");
    }
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Formulareingaben auslesen
        request.setCharacterEncoding("utf-8");
        
        String username = request.getParameter("signup_username");
        String password1 = request.getParameter("signup_password1");
        String password2 = request.getParameter("signup_password2");
        String nachname = request.getParameter("signup_nachname");
        String vorname = request.getParameter("signup_vorname");
        String anschrift = request.getParameter("signup_anschrift");
        String plz = request.getParameter("signup_plz");
        String ort = request.getParameter("signup_ort");
        String telefon = request.getParameter("signup_telefon");
        String email = request.getParameter("signup_email");
        
        /*int iplz=00000;
        try{
             iplz = Integer.parseInt(plz);
        
        }catch (NumberFormatException nfe){
            
        }*/
        
        // Eingaben prüfen
        User user = new User(username, password1, nachname, vorname,ort, plz,anschrift, telefon, email);
        List<String> errors = this.validationBean.validate(user);
        this.validationBean.validate(user.getPassword(), errors);
        
        if (password1 != null && password2 != null && !password1.equals(password2)) {
            errors.add("Die beiden Passwörter stimmen nicht überein.");
        }
        
        // Neuen Benutzer anlegen
        if (errors.isEmpty()) {
            try {
                this.userBean.registration(username, password1, password2, nachname, vorname, ort, plz, anschrift, telefon, email);
            } catch (UserBean.UsernameException ex) {
                errors.add(ex.getMessage());
            }
            catch (UserBean.PasswordException ex) {
                errors.add(ex.getMessage());
            }
        }
        
        // Weiter zur nächsten Seite
        if (errors.isEmpty()) {
            // Keine Fehler: Startseite aufrufen
            request.login(username, password1);
            response.sendRedirect(WebUtils.appUrl(request, "/app/spots/"));
        } else {
            // Fehler: Formuler erneut anzeigen
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);
            
            HttpSession session = request.getSession();
            session.setAttribute("signup_form", formValues);
            
            response.sendRedirect(request.getRequestURI());
        }
    }
    
}