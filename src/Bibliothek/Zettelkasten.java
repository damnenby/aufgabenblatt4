package Bibliothek;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
/**
 * Zettelkasten verwaltet {@link Medium}-Objekte in einer Liste; add, find (Sortierung), drop (ein/typ/all), globales sort; Iterator liefert Kopie.
 * @author Valentyn Zhernovoi
 * @since 2025-11-09
 * <p>Umgebung: IntelliJ IDEA, JDK 25, Windows 10</p>
 */
public class Zettelkasten implements Iterable<Medium>, Serializable {

    /** to be updated */
    private final ArrayList<Medium> items = new ArrayList<>();

    /** to be updated */
    public void addMedium(Medium m) {
        if (m == null) throw new IllegalArgumentException("Medium null");
        if (m.getTitel() == null || m.getTitel().trim().isEmpty()) throw new IllegalArgumentException("Titel leer");

        if (m instanceof Buch b) {
            if (b.getVerfasser() == null || b.getVerfasser().trim().isEmpty()) throw new IllegalArgumentException("Verfasser leer");
            if (b.getVerlag() == null || b.getVerlag().trim().isEmpty()) throw new IllegalArgumentException("Verlag leer");
            if (b.getIsbn() == null || b.getIsbn().trim().isEmpty()) throw new IllegalArgumentException("ISBN leer");
            if (b.getErscheinungsjahr() <= 0) throw new IllegalArgumentException("Jahr ungültig: " + b.getErscheinungsjahr());
        } else if (m instanceof CD c) {
            if (c.getKuenstler() == null || c.getKuenstler().trim().isEmpty()) throw new IllegalArgumentException("Kuenstler leer");
            if (c.getLabel() == null || c.getLabel().trim().isEmpty()) throw new IllegalArgumentException("Label leer");
        } else if (m instanceof Zeitschrift z) {
            if (z.getIssn() == null || z.getIssn().trim().isEmpty()) throw new IllegalArgumentException("ISSN leer");
            if (z.getVolume() <= 0) throw new IllegalArgumentException("Volume <= 0");
            if (z.getNummer() <= 0) throw new IllegalArgumentException("Nummer <= 0");
        } else if (m instanceof ElektronischesMedium e) {
            if (e.getURL() == null || e.getURL().trim().isEmpty()) throw new IllegalArgumentException("URL leer");
            if (!ElektronischesMedium.checkURL(e.getURL())) throw new IllegalArgumentException("URL ungültig: " + e.getURL());
        } else {
            throw new IllegalArgumentException("Unbekannter Medientyp");
        }

        items.add(m);
    }

    /** to be updated */
    public List<Medium> findMedium(String titel, String order) {
        ArrayList<Medium> res = new ArrayList<>();
        if (titel == null) return res;
        sort(order);
        for (Medium m : items) {
            String t = m.getTitel();
            if (t != null && t.equalsIgnoreCase(titel)) res.add(m);
        }
        return res;
    }

    /** to be updated */
    public int dropMedium(String titel) {
        if (titel == null) return 0;

        int count = 0;
        for (Medium m : items) { // подсчёт
            String t = m.getTitel();
            if (t != null && t.equalsIgnoreCase(titel)) {
                count++;
                if (count > 1) break;
            }
        }
        if (count == 0) return 0;
        if (count > 1) throw new IllegalStateException("duplicateEntry");

        for (int i = 0; i < items.size(); i++) {
            String t = items.get(i).getTitel();
            if (t != null && t.equalsIgnoreCase(titel)) {
                items.remove(i);
                return 1;
            }
        }
        return 0;
    }

    /** to be updated */
    // "buch", "cd", "elmed, "zeitschrift", "all"
    public int dropMedium(String titel, String mode) {
        if (titel == null || mode == null) return 0;
        String m = mode.toLowerCase();

        if ("all".equals(m)) {
            ArrayList<Medium> keep = new ArrayList<>();
            for (Medium x : items) {
                String t = x.getTitel();
                if (t == null || !t.equalsIgnoreCase(titel)) keep.add(x);
            }
            int removed = items.size() - keep.size();
            items.clear();
            items.addAll(keep);
            return removed;
        }

        for (int i = 0; i < items.size(); i++) {
            Medium x = items.get(i);
            String t = x.getTitel();
            if (t == null || !t.equalsIgnoreCase(titel)) continue;

            boolean ok;
            switch (m) {
                case "buch":
                    ok = x instanceof Buch;
                    break;
                case "cd":
                    ok = x instanceof CD;
                    break;
                case "elmed":
                    ok = x instanceof ElektronischesMedium;
                    break;
                case "zeitschrift":
                    ok = x instanceof Zeitschrift;
                    break;
                default:
                    ok = false;
            }
            if (ok) {
                items.remove(i);
                return 1;
            }
        }
        return 0;
    }

    /** to be updated */
    public void sort(String order) {
        if (order == null) throw new IllegalArgumentException("order null");
        String o = order.trim().toLowerCase();

        if (o.equals("asc")) {
            Collections.sort(items);
        } else if (o.equals("desc")) {
            Collections.sort(items);
            Collections.reverse(items);
        } else {
            throw new IllegalArgumentException("order ungültig, use asc or desc: " + order);
        }
    }

    /** to be updated */
    @Override
    public Iterator<Medium> iterator() {
        ArrayList<Medium> itemsCopy = new ArrayList<Medium>(items);
        return itemsCopy.iterator();
    }
}
