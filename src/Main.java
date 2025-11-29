import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.util.ArrayList;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        try {
            // XMLReader erzeugen
            XMLReader xmlReader = XMLReaderFactory.createXMLReader();

            // Pfad zur XML Datei
            FileReader reader = new FileReader("personen.xml");
            InputSource inputSource = new InputSource(reader);

            // DTD kann optional übergeben werden
            // inputSource.setSystemId("X:\\personen.dtd");

            // PersonenContentHandler wird übergeben
            PersonenContentHandler handler = new PersonenContentHandler();
            xmlReader.setContentHandler(handler);

            // Parsen wird gestartet
            xmlReader.parse(inputSource);

            // new
            ArrayList<Person> personen = handler.getAllePersonen();
            Scanner scanner = new Scanner(System.in);
            SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");


            System.out.print("Neue Person eingeben? (j/n): ");
            String antwort = scanner.nextLine();
            while (antwort.equalsIgnoreCase("j")) {
                Person p = new Person();

                int id;
                while (true) {
                    System.out.print("ID: ");
                    String idText = scanner.nextLine();

                    try {
                        id = Integer.parseInt(idText);
                    } catch (NumberFormatException e) {
                        System.out.println("ID muss eine ganze Zahl sein.");
                        continue;
                    }

                    if (id <= 0) {
                        System.out.println("ID muss > 0 sein.");
                        continue;
                    }

                    boolean exists = false;
                    for (Person existing : personen) {
                        if (existing.getId() == id) {
                            exists = true;
                            break;
                        }
                    }
                    if (exists) {
                        System.out.println("ID bereits vorhanden, bitte andere ID waehlen.");
                    } else {
                        break;
                    }
                }

                p.setId(id);

                System.out.print("Name: ");
                p.setName(scanner.nextLine());

                System.out.print("Vorname: ");
                p.setVorname(scanner.nextLine());

                System.out.print("Geburtsdatum (dd.MM.yyyy): ");
                String datumText = scanner.nextLine();
                if (!datumText.trim().isEmpty()) {
                    try {
                        Date d = df.parse(datumText);
                        p.setGeburtsdatum(d);
                    } catch (ParseException e) {
                        System.out.println("Datum ungueltig");
                    }
                }

                System.out.print("Postleitzahl: ");
                p.setPostleitzahl(scanner.nextLine());

                System.out.print("Ort: ");
                p.setOrt(scanner.nextLine());

                System.out.print("Hobby: ");
                p.setHobby(scanner.nextLine());

                System.out.print("Lieblingsgericht: ");
                p.setLieblingsgericht(scanner.nextLine());

                System.out.print("Lieblingsband: ");
                p.setLieblingsband(scanner.nextLine());

                personen.add(p);

                System.out.print("Noch eine Person eingeben? (j/n): ");
                antwort = scanner.nextLine();
            }

            System.out.println("----- XML-Ausgabe -----");
            printAsXml(personen);



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }
    private static void printAsXml(ArrayList<Person> personen) {

        System.out.println("<personen>");
        for (Person p : personen) {
            System.out.println("<person id=\"" + p.getId() + "\">");
            System.out.println("<name>" + p.getName() + "</name>");
            System.out.println("<vorname>" + p.getVorname() + "</vorname>");

            if (p.getGeburtsdatum() != null) {
                System.out.println("<geburtsdatum>" + p.getGeburtsdatum() + "</geburtsdatum>");
            } else {
                System.out.println("<geburtsdatum>Ungültig</geburtsdatum>");
            }
            System.out.println("<postleitzahl>" + p.getPostleitzahl() + "</postleitzahl>");
            System.out.println("<ort>" + p.getOrt() + "</ort>");
            System.out.println("<hobby>" + p.getHobby() + "</hobby>");
            System.out.println("<lieblingsgericht>" + p.getLieblingsgericht() + "</lieblingsgericht>");
            System.out.println("<lieblingsband>" + p.getLieblingsband() + "</lieblingsband>");
            System.out.println("</person>");
        }

        System.out.println("</personen>");
    }


}