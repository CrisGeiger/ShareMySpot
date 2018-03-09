/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sharemyspot.jpa;

import java.io.Serializable;
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
 */
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
    private String ort;
    
    @Column(name = "POSTALNUMBER", length = 5)
    @NotNull
    @Size(min = 5, max = 5, message = "Die PLZ muss 5 Zeichen lang sein")
    private int plz;
    
    @Column(name = "ROAD", length = 64)
    @NotNull
    @Size(min = 5, max = 64, message = "Straße musss zw. 5 und 64 Zeichen lang sein")
    private String anschrift;
    
    @Column(name = "DESCRIPTION", length = 128)
    @NotNull
    @Size(min = 4, max = 160, message = "Straße musss zw. 4 und 160 Zeichen lang sein")
    private String beschreibung;
    
    @Enumerated(EnumType.STRING)
    @NotNull
    private SpotStatus status = SpotStatus.FREE;
    
    @Enumerated(EnumType.STRING)
    @NotNull
    private Category category = Category.CAR;
    
    @Column(name = "FAVORIT")
    private Boolean favorite = false;

//<editor-fold defaultstate="collapsed" desc="Konstruktoren">
    public Spot() {
    }

    public Spot(User owner, String ort, int plz, String anschrift, String beschreibung,Category category, boolean favorite) {
        this.owner = owner;
        this.category = category;
        this.plz = plz;
        this.ort = ort;
        this.anschrift = anschrift;
        this.beschreibung = beschreibung;
        this.favorite = favorite;
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
    
    public String getOrt(){
        return ort;
    }
    
    public void setOrt(String ort){
        this.ort = ort;
    }
    
    public String getAnschrift(){
        return anschrift;
    }
    
    public void setAnschrift(String anschrift){
        this.anschrift = anschrift;
    }
    
    public int getPlz(){
        return plz;
    }
    
    public void setPlz(int plz){
        this.plz = plz;
    }
    
    public String getBeschreibung(){
        return beschreibung;
    }
    
    public void setBeschreibung(String beschreibung){
        this.beschreibung = beschreibung;
    }
    
    public Category getCategory(){
        return category;
    }
    
    public void setCategory(Category category){
        this.category = category;
    }
    
    public Boolean getFavorite(){
        return favorite;
    }
    
    public void setFavorite(Boolean favorite){
        this.favorite = favorite;
    }
    //</editor-fold>
}
