/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sharemyspot.ejb;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import sharemyspot.jpa.User;

/**
 *
 * @author Alexander Becker: 10.3 - 11.3 Bean erstellt mit allen vorhandenen Methoden außer changePassword,weil ingesamt eine Synchronisation der aktuellen Klasse mit dieser Methode nicht mit GitHub geklappt hat. 
 * @editor Bastian Schabbach: 11.3 wegen technischen Problemen bei Alexander B. füge Bastian 
 * die Methode changePassword hinzu und löschte aus kommentierte Methoden  ,
 * Cristian Geiger: 12.3 extends wurde von Cristian ergänzt und die Klasse darauf hin angepasst 
 * edited Bastian Schabbach 19.03 Methoden findUser ergänzt
 * edited Schabbach /21.03.2018/ Registration methode an Entityklasse angepasst
 */
        /**
         * Die Userbean bietet verschiedene Methoden, um als Benutzer am Benutzerprofil zu bearbeiten 
         */
  
@Stateless
public class UserBean extends EntityBean<User, Long> {  
    public UserBean() {
            super(User.class);
                    }
            
    @PersistenceContext 
    EntityManager em;
     
    @Resource
    EJBContext ctx;
    
    @Override
    @RolesAllowed("ShareMySpot-user")
    public void delete(User user){ // Methode zum löschen eines Benutzers
       this.em.remove(user);
    }
    
    @Override
    @RolesAllowed("ShareMySpot-user")
    public User update(User user){ // Methode zum Aktualisieren eines Benutzersprofils
       this.em.merge(user);
       return user;
    }
     
     /**
      * Die registration-Methode wird für die Registrierung des Benutzers mit dessen Personaldaten genutzt.
      * In der Methode wird zuerst geprüft, ob der Benutzer schon existiert, wenn ja, wird eine Benachrichtigung(SignupException)
      * dem Anwender angezeigt. Wenn der Benutzername noch nicht im System vorhanden ist,
      * werden die folgenden Parameter an den User-Konstruktor weitergegeben. 
      * Dadurch wird ein neuer User erstellt.
      * 
      * @param username
      * @param password
      * @param password2
      * @param nachname
      * @param vorname
      * @param ort
      * @param plz
      * @param anschrift
      * @param telefon
      * @param email
      * @throws sharemyspot.ejb.UserBean.UsernameException
      * @throws sharemyspot.ejb.UserBean.PasswordException
    
      */
     public void registration(String username,String password,String password2,String nachname,String vorname,String ort,String plz,String road, String roadnumber,String telefon,String email)
             throws UsernameException, PasswordException{
         
         if(em.find(User.class,username)!= null){
             throw new UsernameException("Benutzer ist bereits vergeben.");
         }
         if(!password.equals(password2)) {
             throw new PasswordException("Die Passwörter stimmen nicht überein.");
         }
         
         User user= new User(username, password, nachname, vorname, ort, plz, road, roadnumber, telefon, email);
         user.addToGroup("ShareMySpot-user");
         em.persist(user);
     }

     
     public User getCurrentUser(){
         return this.em.find(User.class, this.ctx.getCallerPrincipal().getName());
     }
     
     public User findUser(String user){
         return (User)em.createQuery("SELECT u FROM User u WHERE u.username = :user")
                 .setParameter("user", user)
                 .getResultList();
     }
     
     /**
     * Password ändern, wenn man eingeloggt ist
     * @param user
     * @param oldPassword
     * @param newPassword
     * @throws sharemyspot.ejb.UserBean.InvalidCredentialsException
     */
     @RolesAllowed("ShareMySpot-user")
    public void changePassword(User user, String oldPassword, String newPassword) throws InvalidCredentialsException {
        if (user == null || !user.checkPassword(oldPassword)) {
            throw new InvalidCredentialsException("Benutzername oder Passwort sind falsch.");
        }

        user.setPassword(newPassword);
    }
    
     /**
      * Password ändern, wenn man noch nicht eingeloggt ist.
      * @param username
      * @param email 
      */
     
     /**
      * selbstgeschriebene SignupException erbt von Exception und 
      * dient als Ausnahme für die Fehlermeldung beim Registrieren 
      */
     public class UsernameException extends Exception{
     
         public UsernameException(String message){
             super(message);
         }
     }
     
     public class PasswordException extends Exception{
         public PasswordException(String message){
             super(message);
         }
     }
     
     public class InvalidCredentialsException extends Exception {

        public InvalidCredentialsException(String message) {
            super(message);
        }
    }
}
