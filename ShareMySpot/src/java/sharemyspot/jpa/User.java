/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sharemyspot.jpa;

import java.io.Serializable;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author cgeiger1
 */

//geändert am 14.3. von Cristian Geiger: Favoritenliste hinzugefügt
/**
 * Änderung 15.03.18: Becker: deutsche Bezeichnungen geändert in englische Namen
 * zugleich im Konstruktor und in deren Methoden 
 * wie z.B. vorname in firstName, nachname in lastName,telefon in phoneNumber, anschrift in adrese
 * 18.03. Geiger: Listen aus Klasse entfernt
 */
@Stateless
@Entity
@Table(name = "SMS_USER")
public class User implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    
    @Column(name = "USERNAME", length = 64)
    @NotNull(message = "Der Name muss ausgefüllt sein")
    @Size(min = 3, max = 64, message = "Username muss zwischen 3 und 64 Zeichen besitzen")
    private String username;
    
    public class Password {
        @Size(min = 6, max = 64, message = "Das Passwort muss zwischen 6 und 64 Zeichen lang sein.")
        public String password = "";
    }
    
    
    @Transient
    private final Password password = new Password();

    @Column(name = "PASSWORD_HASH", length = 64)
    @NotNull(message = "Das Passwort darf nicht leer sein.")
    private String passwordHash;
    
    @Column(name = "LASTNAME", length = 64)
    @NotNull(message = "Der Nachname darf nicht leer sein")
    private String lastName;
    
    @Column(name = "FIRSTNAME", length = 64)
    @NotNull(message = "Der Vorname darf nicht leer sein")
    private String firstName;
    
    @Column(name = "PLACE", length = 64)
    @Size(max = 5)
    @NotNull(message = "Der Ort darf nicht leer sein")
    private String place;
    
    @Column(name = "POSTALNUMBER")
    @NotNull(message = "Die Postleitzahl darf nicht leer sein")
    private String plz;
    
    @Column(name = "ROAD")
    @NotNull(message = "Die Straße darf nicht leer sein")
    private String road;
    
    @Column(name = "ROADNUMBER")
    @NotNull(message = "Die Hausnummer darf nicht leer sein")
    private String roadNumber;
    
    @Column(name = "PHONENUMBER")
    @NotNull(message = "Die Telefonnummer darf nicht leer sein")
    private String phoneNumber;
    
    @Column(name = "EMAIL")
    @NotNull(message = "Die Email darf nicht leer sein")
    private String email;    
    
    @ElementCollection
    @CollectionTable(
            name = "SMS_USER_GROUP",
            joinColumns = @JoinColumn(name = "USERNAME")
    )
    
    @Column(name = "GROUPNAME")
    List<String> groups = new ArrayList<>();
    
    @OneToMany
    private Spot ownSpot;
    
    


    //<editor-fold defaultstate="collapsed" desc="Konstruktoren">
    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password.password = password;
        this.passwordHash = this.hashPassword(password);
    }
    
    public User(String username, String password, String lastName, String firstName, String place, String plz, String road, String roadNumber, String phoneNumber, String email) {
        this.username = username;
        this.password.password = password;
        this.passwordHash = this.hashPassword(password);
        this.lastName = lastName;
        this.firstName = firstName;
        this.place = place;
        this.plz = plz;
        this.road = road;
        this.roadNumber = roadNumber;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getter und Setter">
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPasswordHash() {
        return passwordHash;
    }
    
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getPlace() {
        return place;
    }
    
    public void setPlace(String place) {
        this.place = place;
    }
    
    public String getPlz() {
        return plz;
    }
    
    public void setPlz(String plz) {
        this.plz = plz;
    }
    
    public String getRoad() {
        return road;
    }
    
    public void setRoad(String road) {
        this.road = road;
    }
    
    public String getRoadnumber() {
        return roadNumber;
    }
    
    public void setRoadnumber(String roadnumber) {
        this.roadNumber = roadNumber;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public Spot getOwnSpot() {
        return ownSpot;
    }
    
    public void setOwnSpot(Spot ownSpot) {
        this.ownSpot = ownSpot;
    }
//</editor-fold>
            
    

    //<editor-fold defaultstate="collapsed" desc="Passwort setzen und prüfen">
    /**
     * Berechnet den Hash-Wert zu einem Passwort.
     *
     * @param password Passwort
     * @return Hash-Wert
     */
    private String hashPassword(String password) {
        byte[] hash;

        if (password == null) {
            password = "";
        }
        
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException ex) {
            hash = "!".getBytes(StandardCharsets.UTF_8);
        }

        BigInteger bigInt = new BigInteger(1, hash);
        return bigInt.toString(16);
    }


    public void setPassword(String password) {
        this.password.password = password;
        this.passwordHash = this.hashPassword(password);
    }

   
    public Password getPassword() {
        return this.password;
    }
    

    public boolean checkPassword(String password) {
        return this.passwordHash.equals(this.hashPassword(password));
    }
    //</editor-fold> 
 
    //<editor-fold defaultstate="collapsed" desc="Zuordnung zu Benutzergruppen">
    /**
     * @return Eine unveränderliche Liste aller Benutzergruppen
     */
    public List<String> getGroups() {
        List<String> groupsCopy = new ArrayList<>();

        this.groups.forEach((groupname) -> {
            groupsCopy.add(groupname);
        });

        return groupsCopy;
    }

    /**
     * Fügt den Benutzer einer weiteren Benutzergruppe hinzu.
     *
     * @param groupname Name der Benutzergruppe
     */
    public void addToGroup(String groupname) {
        if (!this.groups.contains(groupname)) {
            this.groups.add(groupname);
        }
    }

    /**
     * Entfernt den Benutzer aus der übergebenen Benutzergruppe.
     *
     * @param groupname Name der Benutzergruppe
     */
    public void removeFromGroup(String groupname) {
        this.groups.remove(groupname);
    }
    //</editor-fold>
    
}
