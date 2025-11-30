package Bibliothek;

/**
 * Zeitschrift mit ISSN, Volume und Nummer.
 * @author Valentyn Zhernovoi
 * @since 2025-10-19
 * <p>Umgebung: IntelliJ IDEA, JDK 25, Windows 10</p>
 */
public class Zeitschrift extends Medium{
    /** ISSN der Zeitschrift. */
    private String issn;
    /** Heftband (Volume). */
    private int volume;
    /** Heftnummer. */
    private int nummer;

    /** to be updated */
    private int auflage;

    /** to be updated */
    private int seitenanzahl;

    /** to be updated */
    private boolean ausgeliehen;

    /** to be updated */
    public Zeitschrift() {}

    /** to be updated */
    public Zeitschrift(String titel, String issn, int volume, int nummer) {
        setTitel(titel);
        setIssn(issn);
        setVolume(volume);
        setNummer(nummer);
    }

    /** to be updated */
    public int getAuflage() {
        return this.auflage;
    }

    /** to be updated */
    public void setAuflage(int auflage) {
        if (auflage <= 0) throw new IllegalArgumentException("Auflage <= 0");
        this.auflage = auflage;
    }

    /** to be updated */
    public boolean isAusgeliehen() {
        return this.ausgeliehen;
    }

    /** to be updated */
    public void setAusgeliehen(boolean ausgeliehen) {
        this.ausgeliehen = ausgeliehen;
    }

    public int getSeitenanzahl() {
        return this.seitenanzahl;
    }

    /** to be updated */
    public void setSeitenanzahl(int seitenanzahl) {
        if (seitenanzahl <= 0) throw new IllegalArgumentException("Seitenanzahl <= 0");
        this.seitenanzahl = seitenanzahl;
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

    /** Gibt die ISSN zurück.
     * @return ISSN */
    public String getIssn() {
        return this.issn;
    }

    /** Setzt die ISSN.
     * @param issn ISSN */
    public void setIssn(String issn) {
        if (issn == null || issn.trim().isEmpty()) throw new IllegalArgumentException("ISSN leer");
        this.issn = issn;
    }

    /** Gibt das Volume zurück.
     * @return Volume */
    public int getVolume() {
        return this.volume;
    }

    /** Setzt das Volume.
     * @param Volume Volume */
    public void setVolume(int Volume) {
        if (Volume <= 0) throw new IllegalArgumentException("Volume <= 0");
        this.volume = Volume;
    }

    /** Gibt die Nummer zurück.
     * @return Nummer */
    public int getNummer() {
        return this.nummer;
    }

    /** Setzt die Nummer.
     * @param Nummer Nummer */
    public void setNummer(int Nummer) {
        if (Nummer <= 0) throw new IllegalArgumentException("Nummer <= 0");
        this.nummer = Nummer;
    }

    /** Liefert die Darstellung (Titel, ISSN, Volume, Nummer).
     * @return Text */
    @Override
    public String calculateRepresentation() {
        StringBuilder sb = new StringBuilder();
        sb.append("Titel: ").append(Titel).append("\n");
        sb.append("ISSN: ").append(issn).append("\n");
        sb.append("Volume: ").append(volume).append("\n");
        sb.append("Nummer: ").append(nummer).append("\n");
        sb.append("Auflage: ").append(auflage).append("\n");
        sb.append("Seiten: ").append(seitenanzahl).append("\n");
        sb.append("Ausgeliehen: ").append(ausgeliehen).append("\n");
        return sb.toString();
    }
}
