package Bibliothek;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class WikipediaRequest {

    public static void main(String[] args) {
        String suchbegriff;

        if (args.length == 0) {
            suchbegriff = "Java_Standard";
            System.out.println("Suchbegriff: " + suchbegriff);
        } else {
            suchbegriff = args[0];
        }

        String urlString = "https://de.wikibooks.org/wiki/Spezial:Exportieren/" + suchbegriff;

        try {
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            connection.setDoInput(true);

            try (InputStream inStream = connection.getInputStream()) {
                InputSource inputSource = new InputSource(inStream);
                inputSource.setEncoding("UTF-8");

                XMLReader xmlReader = XMLReaderFactory.createXMLReader();
                WikibookHandler handler = new WikibookHandler();
                xmlReader.setContentHandler(handler);

                xmlReader.parse(inputSource);


                String title = handler.getTitle();
                String author = handler.getAuthor();
                String timestamp = handler.getTimestamp();

                if (title == null || title.isEmpty() || author == null || author.isEmpty()) {
                    System.out.println("Kein Buch oder kein Urheber gefunden.");
                } else {
                    System.out.println("Suche nach: " + title);

                    if (timestamp != null && !timestamp.isEmpty()) {
                        try {
                            Instant instant = Instant.parse(timestamp);
                            ZonedDateTime local = instant.atZone(ZoneId.systemDefault());
                            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd.MM.yyyy 'um' HH:mm 'Uhr'");
                            String formatted = local.format(fmt);
                            System.out.println("Letzte Änderung: " + formatted);
                        } catch (Exception e) {
                            System.out.println("Letzte Änderung (roh): " + timestamp);
                        }
                    }

                    System.out.println("Urheber: " + author);
                }
            }

        } catch (IOException | SAXException e) {
            System.out.println("Fehler: " + e.toString());
        }
    }
}
