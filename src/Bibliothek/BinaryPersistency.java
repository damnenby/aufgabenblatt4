package Bibliothek;

import java.io.*;

/**
 * Speichert und lädt {@link Zettelkasten} binär (Java-Serialisierung).
 * Implementiert {@link Persistency}.
 * @author Valentyn Zhernovoi
 * @since 2025-11-09
 * <p>Umgebung: IntelliJ IDEA, JDK 25, Windows 10</p>
 */
public class BinaryPersistency implements Persistency {
    @Override
    public void save(Zettelkasten zk, String dateiname) throws IOException {
        if (zk == null) throw new IllegalArgumentException("Zettelkasten null");
        if (dateiname == null) throw new IllegalArgumentException("Dateiname null");

        try (FileOutputStream fos = new FileOutputStream(dateiname); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(zk);
            oos.flush();
        }
    }

    @Override
    public Zettelkasten load(String dateiname) throws IOException, ClassNotFoundException {
        if (dateiname == null) throw new IllegalArgumentException("Dateiname null");

        try (FileInputStream fis = new FileInputStream(dateiname); ObjectInputStream ois = new ObjectInputStream(fis)) {
            Object obj = ois.readObject();
            if (obj instanceof Zettelkasten) {
                return (Zettelkasten) obj;
            } else {
                throw new IOException("Unerwarteter Inhalt");
            }
        }
    }
}
