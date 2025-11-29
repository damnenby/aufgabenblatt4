import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class RssReader {
    public static void main(String[] args) {
        try {
            URL url = new URL("https://rss.dw.com/xml/rss-de-all");

            URLConnection connection = url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");

            try (InputStream inStream = connection.getInputStream();
                 BufferedReader input = new BufferedReader(new InputStreamReader(inStream, "UTF-8"))) {

                String line;
                while ((line = input.readLine()) != null) {
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }
}