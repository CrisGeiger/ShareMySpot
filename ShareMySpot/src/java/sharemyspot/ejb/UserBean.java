/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sharemyspot.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import sharemyspot.jpa.User;

/**
 *
 * @author Studium
 */
        /**
         * Die Userbean bietet verschiedene Methoden, um als Benutzer am Benutzerprofil zu bearbeiten 
         */
  
@Stateless
public class UserBean {
    
     @PersistenceContext 
     EntityManager em;
     
     public void delete(User user){ // Methode zum löschen eines Benutzers
        this.em.remove(user);
     }
     
     public void update(User user){ // Methode zum Aktualisieren eines Benutzersprofils
         this.em.merge(user);
     }
     
     public void signup(String username,String password)throws SignupException{
         
         if(em.find(User.class,username)!=null){
             throw new SignupException("Benutzer ist bereits vergeben.");
         }
         User user= new User(username,password);
         em.persist(user);
     }
     public void getCurrentUser(User user){
         this.em.find(User.class, user);
     }
     
     /**
      * selbstgeschriebene SignupException erbt von Exception und 
      * dient als Ausnahme für die Fehlermeldung beim Registrieren 
      */
     public class SignupException extends Exception{
     
         public SignupException(String message){
             super(message);
         }
     }
}
