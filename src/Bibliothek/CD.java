package Bibliothek;

/**
 * CD mit Label und Künstler.
 * @author Valentyn Zhernovoi
 * @since 2025-10-19
 * <p>Umgebung: IntelliJ IDEA, JDK 25, Windows 10</p>
 */
public class CD extends Medium{

    /** Label der CD. */
    private String label;

    /** Künstlername. */
    private String kuenstler;

    /** to be updated */
    private int gesamtdauer;

    /** to be updated */
    private String altersfreigabe;

    /** to be updated */
    private boolean ausgeliehen;

    /** Gibt den Künstler zurück.
     * @return Künstler */
    public String getKuenstler() {
        return this.kuenstler;
    }

    public CD() {}

    public CD (String titel, String kuenstler, String label) {
        setTitel(titel);
        setKuenstler(kuenstler);
        setLabel(label);
    }


    /** Setzt den Küsntler.
     * @param kuenstler Künstler */
    public void setKuenstler(String kuenstler) {
        if (kuenstler == null || kuenstler.isEmpty()) throw new IllegalArgumentException("Kuenstler leer");
        this.kuenstler = kuenstler;
    }

    /** Gibt das Label zurück.
     * @return Label */
    public String getLabel() {
        return this.label;
    }

    /** Setzt das Label.
     * @param label Label */
    public void setLabel(String label) {
        if (label == null || label.isEmpty()) throw new IllegalArgumentException("Label leer");
        this.label = label;
    }

    /** to be updated */
    public String getAltersfreigabe() {
        return this.altersfreigabe;
    }

    /** to be updated */
    public void setAltersfreigabe(String altersfreigabe) {
        if (altersfreigabe == null || altersfreigabe.isEmpty()) throw new IllegalArgumentException("Altersfreigabe leer");
        this.altersfreigabe = altersfreigabe;
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
    public int getGesamtdauer() {
        return this.gesamtdauer;
    }

    /** to be updated */
    public void setGesamtdauer(int gesamtdauer) {
        if (gesamtdauer <= 0) throw new IllegalArgumentException("Gesamtdauer <= 0");
        this.gesamtdauer = gesamtdauer;
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

    /** Liefert die Darstellung (Titel, Label, Künstler).
     * @return Text */
    @Override
    public String calculateRepresentation() {
        StringBuilder sb = new StringBuilder();
        sb.append("Titel: ").append(Titel).append("\n");
        sb.append("Label: ").append(label).append("\n");
        sb.append("Künstler: ").append(kuenstler).append("\n");
        sb.append("Gesamtdauer: ").append(gesamtdauer).append("\n");
        sb.append("Altersfreigabe: ").append(altersfreigabe).append("\n");
        sb.append("Ausgeliehen: ").append(ausgeliehen).append("\n");
        return sb.toString();
    }

}
