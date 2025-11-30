package Bibliothek;
import java.net.URL;


/**
 * Elektronisches Medium mit URL.
 * @author Valentyn Zhernovoi
 * @since 2025-10-19
 * <p>Umgebung: IntelliJ IDEA, JDK 25, Windows 10</p>
 */
public class ElektronischesMedium extends Medium{

    /** URL des Mediums. */
    private String URL;

    private String dateiformat;

    /** to be updated */
    private long groesse;

    /** to be updated */
    private boolean ausgeliehen;

    /** to be updated */
    public ElektronischesMedium() {}

    /** to be updated */
    public ElektronischesMedium(String titel, String url) {
        setTitel(titel);
        setURL(url);
    }

    /** Gibt die URL zurück.
     * @return URL */
    public String getURL() {
        return this.URL;
    }

    /** Setzt die URL (mit Prüfung).
     * @param URL URL */
    public void setURL(String URL) {
        if (checkURL(URL)){
            this.URL = URL;
        }
        else {
            throw new IllegalArgumentException("URL ungültig: " + URL);
        }
    }

    /** URL-Prüfung
     * @param urlString Eingabe
     * @return boolean */
    public static boolean checkURL(String urlString)
    {
        try
        {
            URL url = new URL(urlString);
            url.toURI();
            return true;
        } catch (Exception exception)
        {
            return false;
        }
    }

    /** to be updated */
    public String getDateiformat() {
        return this.dateiformat;
    }

    /** to be updated */
    public void setDateiformat(String dateiformat) {
        if (dateiformat == null || dateiformat.isEmpty()) throw new IllegalArgumentException("Dateiformat leer");
        this.dateiformat = dateiformat;
    }

    /** to be updated */
    public long getGroesse() {
        return this.groesse;
    }

    /** to be updated */
    public void setGroesse(long groesse) {
        if (groesse <= 0) throw new IllegalArgumentException("Groesse <= 0");
        this.groesse = groesse;
    }

    /** to be updated */
    public boolean isAusgeliehen() {
        return this.ausgeliehen;
    }

    /** to be updated */
    public void setAusgeliehen(boolean ausgeliehen) {
        this.ausgeliehen = ausgeliehen;
    }


    /** to be updated */
    public void ausleihen(){
        this.ausgeliehen = true;
    }

    /** to be updated */
    public void rueckgabe(){
        this.ausgeliehen = false;
    }

    /** to be updated */
    public void verlaengern(){
        /* to be updated */
    }


    /** Liefert die Darstellung (Titel und URL).
     * @return Text */
    @Override
    public String calculateRepresentation() {
        StringBuilder sb = new StringBuilder();
        sb.append("Titel: ").append(Titel).append("\n");
        sb.append("URL: ").append(URL).append("\n");
        sb.append("Dateiformat: ").append(dateiformat).append("\n");
        sb.append("Groesse (byte): ").append(groesse).append("\n");
        sb.append("Ausgeliehen: ").append(ausgeliehen).append("\n");
        return sb.toString();
    }
}
