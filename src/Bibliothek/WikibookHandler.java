package Bibliothek;


import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class WikibookHandler implements ContentHandler {

    private String currentValue;
    private String title;
    private String author;

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        currentValue = new String(ch, start, length);
    }

    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes atts) throws SAXException {
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {

        if ("title".equals(localName) && title == null) {
            title = currentValue.trim();
        }

        if ("username".equals(localName) || "ip".equals(localName)) {
            author = currentValue.trim();
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
