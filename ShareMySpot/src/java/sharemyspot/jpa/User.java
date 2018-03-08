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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
    @NotNull("Der Name muss ausgefüllt sein")
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
    private int plz;
    
    @Column(name = "ROAD")
    @NotNull(message = "Die Straße darf nicht leer sein")
    private String anschrift;
    
    @Column(name = "PHONENUMBER")
    @NotNull(message = "Die Telefonnummer darf nicht leer sein")
    private long telefon;
    
    @Column(name = "EMAIL")
    @NotNull(message = "Die Email darf nicht leer sein")
    private String email;
    

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

    public int getPlz() {
        return plz;
    }

    public void setPlz(int plz) {
        this.plz = plz;
    }

    public String getAnschrift() {
        return anschrift;
    }

    public void setAnschrift(String anschrift) {
        this.anschrift = anschrift;
    }

    public long getTelefon() {
        return telefon;
    }

    public void setTelefon(long telefon) {
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
    
    
    
}
