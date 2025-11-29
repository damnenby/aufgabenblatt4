import java.util.Date;

public class Person {

    private int id;
    private String name = "Ungültig";
    private String vorname = "Ungültig";
    private Date geburtsdatum;
    private String postleitzahl = "Ungültig";
    private String ort = "Ungültig";


    // new
    private String hobby = "Ungültig";
    private String lieblingsgericht = "Ungültig";
    private String lieblingsband = "Ungültig";

    public Person() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        } else {
            this.name = "Ungültig";
        }
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        if (vorname != null && !vorname.trim().isEmpty()) {
            this.vorname = vorname;
        } else {
            this.vorname = "Ungültig";
        }
    }
    public Date getGeburtsdatum() {
        return geburtsdatum;
    }

    public void setGeburtsdatum(Date geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }

    public String getPostleitzahl() {
        return postleitzahl;
    }

    public void setPostleitzahl(String postleitzahl) {
        if (postleitzahl != null && !postleitzahl.trim().isEmpty()) {
            this.postleitzahl = postleitzahl;
        } else {
            this.postleitzahl = "Ungültig";
        }
    }


    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        if (ort != null && !ort.trim().isEmpty()) {
            this.ort = ort;
        } else {
            this.ort = "Ungültig";
        }
    }


    // new
    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        if (hobby != null && !hobby.trim().isEmpty()) {
            this.hobby = hobby;
        } else {
            this.hobby = "Ungültig";
        }
    }

    public String getLieblingsgericht() {
        return lieblingsgericht;
    }

    public void setLieblingsgericht(String lieblingsgericht) {
        if (lieblingsgericht != null && !lieblingsgericht.trim().isEmpty()) {
            this.lieblingsgericht = lieblingsgericht;
        } else {
            this.lieblingsgericht = "Ungültig";
        }
    }

    public String getLieblingsband() {
        return lieblingsband;
    }

    public void setLieblingsband(String lieblingsband) {
        if (lieblingsband != null && !lieblingsband.trim().isEmpty()) {
            this.lieblingsband = lieblingsband;
        } else {
            this.lieblingsband = "Ungültig";
        }
    }

    @Override
    public String toString() {
        return "[[" + this.id + "] [" + this.name + "] [" + this.vorname + "]"
                + " [" + this.ort + "] [" + this.postleitzahl + "] [" + this.geburtsdatum + "]"
                + " [" + this.hobby + "] [" + this.lieblingsgericht + "] [" + this.lieblingsband + "] ]]";
    }
}