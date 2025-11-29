
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class RssReader {
    public static void main(String[] args) {
        try {
            URL url = new URL("https://rss.dw.com/xml/rss-de-all");

            URLConnection connection = url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            connection.setDoInput(true);


            try (InputStream inStream = connection.getInputStream()) {

                InputSource inputSource = new InputSource(inStream);
                inputSource.setEncoding("UTF-8"); // Umlaute

                // XMLReader (SAX) erzeugen
                XMLReader xmlReader = XMLReaderFactory.createXMLReader();

                // Handler registrieren
                xmlReader.setContentHandler(new RssContentHandler());

                // Parsen starten
                xmlReader.parse(inputSource);
            }

        } catch (IOException | SAXException e) {
            System.out.println(e.toString());
        }
    }
}