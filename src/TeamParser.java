public class TeamParser {

    private final String TAG_TEAM     = "team";
    private final String TAG_PERSON   = "person";
    private final String TAG_VORNAME  = "vorname";
    private final String TAG_NACHNAME = "nachname";
    private final String TAG_ALIAS    = "alias";

    public static void main(String[] args) {
        TeamParser parser = new TeamParser();
        parser.runTests();
    }

    private void runTests() {

        String ok1 =
                "<team>" +
                        "<person><vorname>Peter</vorname><nachname>Quill</nachname><alias>Starlord</alias></person>" +
                        "<person><vorname>Rocket</vorname><nachname>Racoon</nachname><alias>---</alias></person>" +
                        "</team>";

        String ok2 =
                "<team>" +
                        "<person><nachname>Quill</nachname><vorname>Peter</vorname><vorname>Albert</vorname><alias>Starlord</alias></person>" +
                        "<person><alias>---</alias><vorname>Rocket</vorname><nachname>Racoon</nachname></person>" +
                        "</team>";

        String ok3 =
                "<team>" +
                        "<person><vorname>Peter</vorname><nachname>Quill</nachname><alias>Starlord</alias></person>" +
                        "<person><nachname>Racoon</nachname><vorname>Rocket</vorname><alias>---</alias></person>" +
                        "<person><vorname>Groot</vorname><nachname>---</nachname></person>" +
                        "</team>";

        String err1 =
                "<team><person><nachname>Spengler</nachname><titel>Dr.</alias>" +
                        "<vorname>Egon</vorname></person>" +
                        "<mensch><vorname>Peter</vorname><nachname>Venkman</nachname><title>-" +
                        "</title></person></team>";

        String err2 =
                "<person><vorname>Peter</vorname><nachname>Quill</nachname><alias>Starlord</alias></person>";

        String err3 =
                "<team>" +
                        "<person><vorname>Peter</vorname><nachname>Quill</nachname><alias>Starlord</alias></person>" +
                        "</team>" +
                        "<team>" +
                        "<person><vorname>Rocket</vorname><nachname>Racoon</nachname><alias>---</alias></person>" +
                        "</team>";

        parseTeam(ok1);
        parseTeam(ok2);
        parseTeam(ok3);
        parseTeam(err1);
        parseTeam(err2);
        parseTeam(err3);
    }

    private void parseTeam(String xml) {

        checkTagCounts(xml, TAG_TEAM,   true,  true);
        checkTagCounts(xml, TAG_PERSON, true,  false);
        checkTagCounts(xml, TAG_ALIAS,  false, false);

        String teamInhalt = getSingleTagContent(xml, TAG_TEAM);
        if (teamInhalt == null) {
            System.err.println("Fehler: team-Element fehlt oder ist nicht vollständig.");
            return;
        }

        String openPersonTag  = "<" + TAG_PERSON + ">";
        String closePersonTag = "</" + TAG_PERSON + ">";

        int pos = 0;
        boolean foundPerson = false;

        while (true) {
            int start = teamInhalt.indexOf(openPersonTag, pos);
            if (start == -1) {
                break;
            }

            int end = teamInhalt.indexOf(closePersonTag, start + openPersonTag.length());
            if (end == -1) {
                System.err.println("Fehler: </" + TAG_PERSON + "> fehlt.");
                break;
            }

            String personXml = teamInhalt.substring(start, end + closePersonTag.length());
            printPerson(personXml);

            foundPerson = true;
            pos = end + closePersonTag.length();
        }

        if (!foundPerson) {
            System.err.println("Fehler: kein <" + TAG_PERSON + "> innerhalb von <" + TAG_TEAM + "> gefunden.");
        }
    }

    private void printPerson(String personXml) {

        String vornamen = getAllTagContents(personXml, TAG_VORNAME);
        String nachname = getSingleTagContent(personXml, TAG_NACHNAME);
        String alias    = getSingleTagContent(personXml, TAG_ALIAS);

        if (vornamen == null || vornamen.isEmpty()) {
            System.err.println("Fehler: Vorname fehlt in einer Person.");
            return;
        }
        if (nachname == null || nachname.trim().isEmpty()) {
            System.err.println("Fehler: Nachname fehlt in einer Person.");
            return;
        }

        if (alias == null || alias.trim().isEmpty()) {
            alias = "---";
        }

        System.out.println(vornamen + " " + nachname + " - " + alias);
    }

    private String getSingleTagContent(String xml, String tag) {
        String open  = "<" + tag + ">";
        String close = "</" + tag + ">";

        int start = xml.indexOf(open);
        if (start == -1) {
            return null;
        }
        int end = xml.indexOf(close, start + open.length());
        if (end == -1) {
            System.err.println("Fehler: schließendes " + close + " fehlt.");
            return null;
        }

        return xml.substring(start + open.length(), end).trim();
    }

    private String getAllTagContents(String xml, String tag) {
        String open  = "<" + tag + ">";
        String close = "</" + tag + ">";

        StringBuilder result = new StringBuilder();
        int pos = 0;
        int count = 0;

        while (true) {
            int start = xml.indexOf(open, pos);
            if (start == -1) {
                break;
            }
            int end = xml.indexOf(close, start + open.length());
            if (end == -1) {
                System.err.println("Fehler: schließendes " + close + " fehlt.");
                break;
            }

            String value = xml.substring(start + open.length(), end).trim();
            if (!value.isEmpty()) {
                if (result.length() > 0) {
                    result.append(" ");
                }
                result.append(value);
            }

            count++;
            pos = end + close.length();
        }

        if (count == 0) {
            return null;
        }

        return result.toString();
    }

    private void checkTagCounts(String xml, String tag, boolean required, boolean mustBeSingle) {
        String open  = "<" + tag + ">";
        String close = "</" + tag + ">";

        int openCount  = countOccurrences(xml, open);
        int closeCount = countOccurrences(xml, close);

        if (required && (openCount == 0 || closeCount == 0)) {
            System.err.println("Fehler: <" + tag + "> oder </" + tag + "> fehlt.");
        }

        if (openCount != closeCount) {
            System.err.println("Fehler: unterschiedliche Anzahl von " + open + " und " + close
                    + " (" + openCount + "/" + closeCount + ").");
        }

        if (mustBeSingle && openCount > 1) {
            System.err.println("Fehler: mehr als ein <" + tag + ">-Element gefunden.");
        }
    }

    private int countOccurrences(String text, String pattern) {
        int count = 0;
        int idx = 0;
        while (true) {
            idx = text.indexOf(pattern, idx);
            if (idx == -1) {
                break;
            }
            count++;
            idx += pattern.length();
        }
        return count;
    }
}
