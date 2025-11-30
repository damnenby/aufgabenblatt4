package Bibliothek;


/**
 * Erstellt verschiedene {@link Medium}-Objekte (Buch, CD, Zeitschrift, ElektronischesMedium),
 * speichert sie in einem Array und gibt deren textuelle Repräsentationen aus, testet {@link #parseBibTex(String)}.
 * @author Valentyn Zhernovoi
 * @since 2025-10-19
 * <p>Umgebung: IntelliJ IDEA, JDK 25, Windows 10</p>
 */
public class Bibliothek {
    /**
     * Hauptmethode: erzeugt mehrere Medienobjekte, speichert sie in einem Array
     * und gibt deren Darstellung über {@code toString()} auf der Konsole aus.
     * @param args wird nicht verwendet
     */
    public static void main(String[] args) {

        Zettelkasten zettelkasten = new Zettelkasten();

        zettelkasten.addMedium(new CD("Live At Wembley", "Queen", "Parlophone (EMI)"));
        zettelkasten.addMedium(new Buch("Duden 01. Die deutsche Rechtschreibung", "-", "Bibliographisches Institut, Mannheim", 2004, "3-411-04013-0"));
        zettelkasten.addMedium(new Zeitschrift("Der Spiegel", "0038-7452", 54, 6));
        zettelkasten.addMedium(new ElektronischesMedium("Hochschule Stralsund", "http://www.hochschule-stralsund.de"));

        zettelkasten.sort("desc");

        for (Medium medium : zettelkasten) {
            System.out.println(medium.calculateRepresentation());
        }


        Persistency bin = new BinaryPersistency();
        Persistency txt = new HumanReadablePersistency();

        String binFile = "zk.bin";
        String txtFile = "zk.txt";

        try {
            bin.save(zettelkasten, binFile);

            Zettelkasten geladen = bin.load(binFile);

            System.out.println("------ ORIGINAL ------");
            for (Medium m : zettelkasten) {
                System.out.println(m.getTitel());
            }

            System.out.println("------ GELADEN ------");
            for (Medium m : geladen) {
                System.out.println(m.getTitel());
            }


            txt.save(zettelkasten, txtFile);

            System.out.println("Gespeichert: " + binFile);
            System.out.println("Gespeichert: " + txtFile);
        } catch (Exception e) {
            System.out.println("Fehler: " + e);
        }

        System.out.println("----- A7 TEST -----");
        zettelkasten.addMedium(new Buch("SAME", "-", "Pub", 2001, "3-598-21508-8"));
        zettelkasten.addMedium(new CD("SAME", "Artist", "Lbl"));
        zettelkasten.addMedium(new Zeitschrift("SAME", "1234-5678", 1, 1));

        System.out.println("----- findMedium asc -----");
        for (Medium m : zettelkasten.findMedium("SAME", "asc")) {
            System.out.println(m.getClass().getSimpleName() + " | " + m.getTitel());
        }

        System.out.println("----- dropMedium(String) -----");
        try {
            zettelkasten.dropMedium("SAME");
            System.out.println("FAIL: no exception");
        } catch (IllegalStateException ex) {
            System.out.println("OK: " + ex.getMessage());
        }

        System.out.println("----- dropMedium by type cd -----");
        int r1 = zettelkasten.dropMedium("SAME", "cd");
        System.out.println("removed: " + r1);

        System.out.println("----- dropMedium by type buch -----");
        zettelkasten.dropMedium("SAME", "buch");

        System.out.println("----- dropMedium(String) now single -----");
        int r2 = zettelkasten.dropMedium("SAME");
        System.out.println("removed single: " + r2);

        System.out.println("-- remaining items --");
        for (Medium m : zettelkasten) {
            System.out.println(m.getTitel());
        }

    }

    /**
     * Parst eine Zeile und erstellt das passende {@link Medium}.
     * Format: @type{key = {value}, ...}
     * @param inputText Eingabe
     * @return Medium (Buch/CD/Zeitschrift/ElektronischesMedium)
     * @throws IllegalArgumentException bei Fehlern
     * @throws NumberFormatException Zahlenfehlern
     */
    public static Medium parseBibTex(String inputText) {
        if (inputText == null) {
            throw new IllegalArgumentException("null");
        }

        String trimmedString = inputText.trim();
        if (!trimmedString.startsWith("@") || !trimmedString.endsWith("}")) {
            throw new IllegalArgumentException("Format");
        }

        int typeEndIndex = trimmedString.indexOf('{', 1);
        if (typeEndIndex < 0) {
            throw new IllegalArgumentException("Klammer");
        }

        String typeNameLower = trimmedString.substring(1, typeEndIndex).trim().toLowerCase();
        String bodyText = trimmedString.substring(typeEndIndex + 1, trimmedString.length() - 1).trim();

        if (typeNameLower.equals("book")) {
            String title = getField(bodyText, "title");
            if (title.isEmpty()) throw new IllegalArgumentException("Feld fehlt: title");

            String author = getField(bodyText, "author");
            if (author.isEmpty()) author = "-";

            String publisher = getField(bodyText, "publisher");
            if (publisher.isEmpty()) throw new IllegalArgumentException("Feld fehlt: publisher");

            String yearText = getField(bodyText, "year");
            if (yearText.isEmpty()) throw new IllegalArgumentException("Feld fehlt: year");
            int year = Integer.parseInt(yearText.trim());

            String isbn = getField(bodyText, "isbn");
            if (isbn.isEmpty()) throw new IllegalArgumentException("Feld fehlt: isbn");

            Buch buch = new Buch();
            buch.setTitel(title);
            buch.setVerfasser(author);
            buch.setVerlag(publisher);
            buch.setErscheinungsjahr(year);
            buch.setIsbn(isbn);
            return buch;
        } else if (typeNameLower.equals("journal")) {
            String title = getField(bodyText, "title");
            if (title.isEmpty()) throw new IllegalArgumentException("Feld fehlt: title");

            String issn = getField(bodyText, "issn");
            if (issn.isEmpty()) throw new IllegalArgumentException("Feld fehlt: issn");

            String volumeText = getField(bodyText, "volume");
            if (volumeText.isEmpty()) throw new IllegalArgumentException("Feld fehlt: volume");
            int volume = Integer.parseInt(volumeText.trim());

            String numberText = getField(bodyText, "number");
            if (numberText.isEmpty()) throw new IllegalArgumentException("Feld fehlt: number");
            int number = Integer.parseInt(numberText.trim());

            Zeitschrift zeitschrift = new Zeitschrift();
            zeitschrift.setTitel(title);
            zeitschrift.setIssn(issn);
            zeitschrift.setVolume(volume);
            zeitschrift.setNummer(number);
            return zeitschrift;
        } else if (typeNameLower.equals("cd")) {
            String title = getField(bodyText, "title");
            if (title.isEmpty()) throw new IllegalArgumentException("Feld fehlt: title");

            String artist = getField(bodyText, "artist");
            if (artist.isEmpty()) throw new IllegalArgumentException("Feld fehlt: artist");

            String label = getField(bodyText, "label");
            if (label.isEmpty()) throw new IllegalArgumentException("Feld fehlt: label");

            CD cd = new CD();
            cd.setTitel(title);
            cd.setKuenstler(artist);
            cd.setLabel(label);
            return cd;
        } else if (typeNameLower.equals("elmed")) {
            String title = getField(bodyText, "title");
            if (title.isEmpty()) throw new IllegalArgumentException("Feld fehlt: title");

            String url = getField(bodyText, "url");
            if (url.isEmpty()) throw new IllegalArgumentException("Feld fehlt: url");

            ElektronischesMedium elektronischesMedium = new ElektronischesMedium();
            elektronischesMedium.setTitel(title);
            elektronischesMedium.setURL(url);
            return elektronischesMedium;
        } else {
            throw new IllegalArgumentException("Typ: " + typeNameLower);
        }
    }


    /**
     * Schlüsselsuche
     * @param bodyText Textbereich innerhalb der Klammern
     * @param key gesuchter Name
     * @return Wert
     */
    private static String getField(String bodyText, String key) {
        if (bodyText == null || key == null) return "";
        String lower = bodyText.toLowerCase();
        String k = key.toLowerCase();

        int kPos = lower.indexOf(k);
        if (kPos < 0) return "";

        int eqPos = lower.indexOf('=', kPos + k.length());
        if (eqPos < 0) return "";

        int openPos = lower.indexOf('{', eqPos + 1);
        if (openPos < 0) return "";

        int closePos = bodyText.indexOf('}', openPos + 1);
        if (closePos < 0) return "";

        return bodyText.substring(openPos + 1, closePos).trim();
    }


}
