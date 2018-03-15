/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sharemyspot.ejb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import sharemyspot.jpa.Category;
import sharemyspot.jpa.Spot;
import sharemyspot.jpa.SpotStatus;
import sharemyspot.jpa.User;

/**
 *
 * @author JU_FI
 * @editor Becker
 *
 * Einfache EJB mit den üblichen CRUD-Methoden für Parkplätze.
 */

@Stateless
@RolesAllowed("ShareMySpot-user")
public class SpotBean extends EntityBean<Spot, Long> {
    
    public SpotBean(){
        super(Spot.class);
    }
    
        /**
     * Alle Parkplätze eines Benutzers
     * @param username Benutzername
     * @return Alle Parkplätze des Benutzers
     */
    public List<Spot> findByUsername(String username) {
        return em.createQuery("SELECT t FROM Spot t WHERE t.owner.username = :username")
                 .setParameter("username", username)
                 .getResultList();
    }
    
/**
     * Suche nach Parkplätzen anhand ihrer Kategorie, Status, PLZ, Ort und Eigentümer
     * @param owner
     * @param place
     * @param plz 
     * @param status
     * @param category
     * @param description
     * @return Liste mit den gefundenen Parkplätzen
     */
    /**Änderung 15.03.18: Becker: - Deklaratioin der Parameter place,plz der Methode search geändert
    *von Deklaration Spot und street als Parameter hinzugefügt  
    * - Bezeichnung ort zu place und search zu description in der gesamten Methode wurde abgeändert.
    */
      public List<Spot> search(String description, User owner, String place, String plz,String street, SpotStatus status, Category category){
        
        //Hilfsojekt zum Bauen der Query
            CriteriaBuilder cd = this.em.getCriteriaBuilder();
    
        //Select t FROM Spot t
            CriteriaQuery<Spot> query = cd.createQuery(Spot.class);
            Root<Spot> from = query.from(Spot.class);
            query.select(from);
    
        //Suche nach Text
        if (description != null && !description.trim().isEmpty()){
            query.where(cd.like(from.get("beschreibung"), "%" + description + "%"));
        }
        
        //Suche nach Kategorie
        if (category != null){
            query.where(cd.equal(from.get("category"), category));
        }
        
        //Suche nach Status
        if (status != null){
            query.where(cd.equal(from.get("status"), status));
        }
        
        //Suche nach Eigentümer
        if (owner != null){
            query.where(cd.equal(from.get("owner"), owner));
        }
        
        //Suche nach Ort
        if (place != null){
            query.where(cd.equal(from.get("place"), place));
        }
        
        //Suche nach PLZ
        if (plz != null){
            query.where(cd.equal(from.get("plz"), plz));
        }
        //Änderung 15.03.18: Becker: street als zu seuchender Weg hinzugefügt
        //Suche nach Street
        if (street != null){  
            query.where(cd.equal(from.get("street"), street));
        }
        
        
        return em.createQuery(query).getResultList();
    }
    
    //Änderung 14.03.18:Geiger: Methode updateSearch hinzugefügt
    //Methode die eine neue Liste mit nur Verfügbaren Parkplätzen liefert
    //Ich bin mir nicht sicher ob diese Methode in die Spot-Klasse muss, bitte prüfen!
    public List<Spot> updateSearch(List<Spot> spots, Date freeFrom, Date freeTo) {
        List<Spot> updatedList = new ArrayList<>();
            for (Spot s : spots) {
                if (s.getFreeFrom().before(freeTo) && s.getFreeTo().after(freeFrom)) {
                    updatedList.add(s);
                    
                }
            }
            
        return updatedList;
     
    }
}
    
    