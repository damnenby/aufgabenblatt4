
package Bibliothek;
import java.io.Serializable;
/**
 * Basisklasse mit Titel.
 * @author Valentyn Zhernovoi
 * @since 2025-10-19
 * <p>Umgebung: IntelliJ IDEA, JDK 25, Windows 10</p>
 */
public abstract class Medium implements Comparable<Medium>, Serializable {
    /** Titel des Mediums. */
    protected String Titel;

    /** Gibt den Titel zurück.
     * @return Titel */
    public String getTitel() {
        return this.Titel;
    }

    /** Setzt den Titel.
     * @param Titel Titel */
    public void setTitel(String Titel) {
        if (Titel == null || Titel.trim().isEmpty()) throw new IllegalArgumentException("Titel leer");
        this.Titel = Titel;
    }


    /** Liefert die Darstellung.
     * @return Text */
    public abstract String calculateRepresentation();

    /** Gibt {@link #calculateRepresentation()} zurück.
     * @return Text */
    @Override
    public String toString(){
        return calculateRepresentation();
    }

    /** to be updated */
    @Override
    public int compareTo(Medium o) {
        if (o == null) throw new NullPointerException("o null");

        int r = this.Titel.compareToIgnoreCase(o.Titel);
        if (r != 0) return r;

        int thisTypeInt = 9;
        if (this instanceof Buch) thisTypeInt = 0;
        else if (this instanceof CD) thisTypeInt = 1;
        else if (this instanceof ElektronischesMedium) thisTypeInt = 2;
        else if (this instanceof Zeitschrift) thisTypeInt = 3;

        int otherTypeInt = 9;
        if (o instanceof Buch) otherTypeInt = 0;
        else if (o instanceof CD) otherTypeInt = 1;
        else if (o instanceof ElektronischesMedium) otherTypeInt = 2;
        else if (o instanceof Zeitschrift) otherTypeInt = 3;

        if (thisTypeInt < otherTypeInt) return -1;
        if (thisTypeInt > otherTypeInt) return 1;

        int r2 = this.Titel.compareTo(o.Titel);
        if (r2 < 0) return -1;
        if (r2 > 0) return 1;
        return 0;
    }
}
