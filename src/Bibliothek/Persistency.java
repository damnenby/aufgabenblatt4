package Bibliothek;
import java.io.IOException;
/**
 * Persistenz-Interface für {@link Zettelkasten}: speichern und laden.
 * Implementierungen: {@link BinaryPersistency}, {@link HumanReadablePersistency}.
 * @author Valentyn Zhernovoi
 * @since 2025-11-09
 * <p>Umgebung: IntelliJ IDEA, JDK 25, Windows 10</p>
 */
public interface Persistency {
    void save(Zettelkasten zk, String dateiname) throws IOException;
    Zettelkasten load(String dateiname) throws IOException, ClassNotFoundException;
}
