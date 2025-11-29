import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class RssContentHandler implements ContentHandler {

    private ArrayList<String> alleTitel = new ArrayList<String>();
    private String currentValue;
    private boolean inItem = false;

    public ArrayList<String> getAlleTitel() {
        return alleTitel;
    }

    // Aktuelle Zeichen, die gelesen werden, in eine Zwischenvariable speichern
    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {

        currentValue = new String(ch, start, length);
    }

    // Wird aufgerufen, wenn der Parser zu einem Start-Tag kommt
    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes atts) throws SAXException {

        if (localName.equals("item")) {
            inItem = true;
        }
    }

    // End-Tag
    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {

        // <title> innerhalb eines <item>
        if (inItem && localName.equals("title")) {
            String title = currentValue.trim();
            alleTitel.add(title);
            System.out.println(title);
        }

        // Ende von <item>
        if (localName.equals("item")) {
            inItem = false;
        }
    }


    @Override public void startDocument() throws SAXException { }
    @Override public void endDocument() throws SAXException { }
    @Override public void startPrefixMapping(String prefix, String uri) throws SAXException { }
    @Override public void endPrefixMapping(String prefix) throws SAXException { }
    @Override public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException { }
    @Override public void processingInstruction(String target, String data) throws SAXException { }
    @Override public void setDocumentLocator(Locator locator) { }
    @Override public void skippedEntity(String name) throws SAXException { }
}