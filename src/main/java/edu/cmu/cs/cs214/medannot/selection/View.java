package edu.cmu.cs.cs214.medannot.selection;

public class View {
    private String link;
    private String object;
    public View(String link, String object) {
        this.object = object;
        this.link = link;
    }
    public String getLink() {
        return this.link;
    }

    public String getObject() {
        return this.object;
    }
}
