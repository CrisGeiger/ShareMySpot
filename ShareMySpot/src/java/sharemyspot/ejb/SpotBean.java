/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sharemyspot.ejb;

import java.util.Date;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import sharemyspot.jpa.Category;
import sharemyspot.jpa.Spot;
import sharemyspot.jpa.User;

/**
 *
 * @author JU_FI
 * @editor Becker
 * 18.3: Geiger Methode updateSearch() gelöscht
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
      public List<Spot> search(String description, User owner, String place, String plz, String road, String roadnumber, Category category){
        
        //Hilfsojekt zum Bauen der Query
            CriteriaBuilder cd = this.em.getCriteriaBuilder();
    
        //Select t FROM Spot t
            CriteriaQuery<Spot> query = cd.createQuery(Spot.class);
            Root<Spot> from = query.from(Spot.class);
            query.select(from);
    
        //Suche nach Text
        if (description != null && !description.trim().isEmpty()){
            query.where(cd.like(from.get("description"), "%" + description + "%"));
        }
        
        //Suche nach Kategorie
        if (category != null){
            query.where(cd.equal(from.get("category"), category));
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
        if (road != null){  
            query.where(cd.equal(from.get("road"), road));
        }
        
        //Suche nach Hausnummer
        if(roadnumber != null) {
            query.where(cd.equal(from.get("roadnumber"), roadnumber));
        }
        
        
        return em.createQuery(query).getResultList();
    }
    
      
      
      
    
    //Methode um alle verfügbaren Spots anzuzeigen die zu einem gesetzten Datum frei sind
    public List<Spot> updateSpots(List<Spot> spots) {

        Date currentDate = new Date();
                for (Spot s : spots) {
            if(currentDate.after(s.getFreeFrom()) && currentDate.before(s.getFreeTo())){
                if(s.getSpotStatus().getLabel().equals("BOOKED")){
                    spots.remove(s);
                }
            }else{
                spots.remove(s);
            }

        }
        
        return spots;
    }
    
    public List<Spot> updateSpotsDate(List<Spot> spots, Date searchDate) {
        for (Spot s : spots) {
            if(searchDate.after(s.getFreeFrom()) && searchDate.before(s.getFreeTo())){
                if(s.getSpotStatus().getLabel().equals("BOOKED")){
                    spots.remove(s);
                }
            }else{
                spots.remove(s);
            }
        }
        
        return spots;
    }
     
    
}
    
    
