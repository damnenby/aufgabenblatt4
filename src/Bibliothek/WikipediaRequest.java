package Bibliothek;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

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

                if (title == null || title.isEmpty() || author == null || author.isEmpty()) {
                    System.out.println("Kein Buch oder kein Urheber gefunden.");
                } else {
                    System.out.println(title);
                    System.out.println("Urheber: " + author);
                }
            }

        } catch (IOException | SAXException e) {
            System.out.println("Fehler: " + e.toString());
        }
    }
}
