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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author cgeiger1
 */
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
    private String nachname;
    
    @Column(name = "FIRSTNAME", length = 64)
    @NotNull(message = "Der Vorname darf nicht leer sein")
    private String vorname;
    
    @Column(name = "PLACE", length = 64)
    @Size(max = 5)
    @NotNull(message = "Der Ort darf nicht leer sein")
    private String ort;
    
    @Column(name = "POSTALNUMBER")
    @NotNull(message = "Die Postleitzahl darf nicht leer sein")
    private String plz;
    
    @Column(name = "ROAD")
    @NotNull(message = "Die Straße darf nicht leer sein")
    private String anschrift;
    
    @Column(name = "PHONENUMBER")
    @NotNull(message = "Die Telefonnummer darf nicht leer sein")
    private String telefon;
    
    @Column(name = "EMAIL")
    @NotNull(message = "Die Email darf nicht leer sein")
    private String email;
    
    @OneToMany(mappedBy ="owner", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    List<Spot> spots = new ArrayList<>();
    
    


    //<editor-fold defaultstate="collapsed" desc="Konstruktoren">
    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password.password = password;
        this.passwordHash = this.hashPassword(password);
    }
    
    public User(String username, String password, String nachname, String vorname, String ort, String plz, String anschrift, String telefon, String email) {
        this.username = username;
        this.password.password = password;
        this.passwordHash = this.hashPassword(password);
        this.nachname = nachname;
        this.vorname = vorname;
        this.ort = ort;
        this.plz = plz;
        this.anschrift = anschrift;
        this.telefon = telefon;
        this.email = email;
    }
    //</editor-fold>
            
    //<editor-fold defaultstate="collapsed" desc="Setter und Getter">
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getNachname() {
        return nachname;
    }
    
    public void setNachname(String nachname) {
        this.nachname = nachname;
    }
    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getAnschrift() {
        return anschrift;
    }

    public void setAnschrift(String anschrift) {
        this.anschrift = anschrift;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Passwort setzen und prüfen">
    /**
     * Berechnet der Hash-Wert zu einem Passwort.
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
    
}
