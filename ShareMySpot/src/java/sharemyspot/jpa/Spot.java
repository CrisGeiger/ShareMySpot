/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sharemyspot.jpa;

import java.io.Serializable;
import javax.ejb.Stateless;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Budda
 * 
 */
//geändert am 14.03. von Cristian Geiger: Zwei Zeitvariablen zur Bestimmung wann ein Parkplatz zur Verfügung steht eingefügt 
//Konstruktor angepasst und Getter und Setter hinzugefügt
/** Änderung 15.03.18: Becker: Die Variablen Ort zu Place und Beschreibung zu Descrption abgeändert zusätzlich auch in deren Methoden und im Konstruktor 
 *  Die Anschrift wurde aufgeteilt in ROAD und roadNumber und dazu neue Methoden hinzugefügt und im Konstruktor eingearbeitet 
 * 18.03. Geiger: Klasse Daten ausgelagert in Booking Class
 * 
 */
@Stateless
@Entity
@Table(name = "SMS_SPOT")
public class Spot implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(generator = "spot_ids")
    @TableGenerator(name = "spot_ids", initialValue = 0, allocationSize = 50)
    private Long id;
    
    @ManyToOne
    @NotNull(message = "Die Aufgabe muss einem Benutzer geordnet werden.")
    private User owner;
    
    @Column(name = "PLACE", length = 64)
    @NotNull(message = "Ort darf nicht leer sein")
    @Size(min = 3, max = 64, message = "Der Ort muss zw. 3 und 64 Zeichen lang sein")
    private String place;
    
    @Column(name = "POSTALNUMBER", length = 5)
    @NotNull
    @Size(min = 5, max = 5, message = "Die PLZ muss 5 Zeichen lang sein")
    private int plz;
    
    @Column(name = "ROAD", length = 64)
    @NotNull
    @Size(min = 5, max = 64, message = "Straße musss zw. 5 und 64 Zeichen lang sein")
    private String road;
    
    @Column (name ="ROADNUMBER", length= 3)
    @NotNull
    @Size(min =1,max = 3,message ="Die Hausnummer muss zw. 1 und 3 Zeichen lanf sein")
    private int roadNumber;
    
    @Column(name = "DESCRIPTION", length = 128)
    @NotNull
    //@Size(min = 4, max = 160, message = "Straße musss zw. 4 und 160 Zeichen lang sein")
    private String description;
    
    @Enumerated(EnumType.STRING)
    @NotNull
    private SpotStatus status = SpotStatus.FREE;
    
    @Enumerated(EnumType.STRING)
    @NotNull
    private Category category = Category.CAR;
    
    
    

    
    

//<editor-fold defaultstate="collapsed" desc="Konstruktoren">
    public Spot() {
    }

    public Spot(User owner, String place, int plz, String road,int roadNumber, String description,Category category) {
        this.owner = owner;
        this.category = category;
        this.plz = plz;
        this.place = place;
        this.road = road;
        this.roadNumber=roadNumber;
        this.description = description;
        
    }
    //</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Setter und Getter">
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
    
    public String getPlace(){
        return place;
    }
    
    public void setPlace(String place){
        this.place = place;
    }
    
    public String getRoad(){
        return road;
    }
    
    public void setRoad(String road){
        this.road = road;
    }
    
    public void setroadNumber(int roadNumber){
        this.roadNumber=roadNumber;
    }
    
    public int getroadNumber(){
        return roadNumber;
    }
    
    public int getPlz(){
        return plz;
    }
    
    public void setPlz(int plz){
        this.plz = plz;
    }
    
    public String getDescription(){
        return description;
    }
    
    public void setDescription(String description){
        this.description = description;
    }
    
    public Category getCategory(){
        return category;
    }
    
    public void setCategory(Category category){
        this.category = category;
    }
    //</editor-fold>
}
