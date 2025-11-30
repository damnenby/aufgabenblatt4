package Bibliothek;

public class Wikibook extends ElektronischesMedium {

    private String urheber; // username oder ip aus Wikibooks

    public Wikibook() {
    }

    public Wikibook(String titel, String url, String urheber) {
        setTitel(titel);
        setURL(url);
        this.urheber = urheber;
    }

    public String getUrheber() {
        return urheber;
    }

    public void setUrheber(String urheber) {
        this.urheber = urheber;
    }

    @Override
    public String calculateRepresentation() {
        StringBuilder sb = new StringBuilder();
        sb.append("Titel: ").append(getTitel()).append("\n");
        sb.append("URL: ").append(getURL()).append("\n");
        sb.append("Urheber: ").append(urheber).append("\n");
        return sb.toString();
    }
}