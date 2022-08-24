package edu.cmu.cs.cs214.medannot.annotation;

public class ButtonView {
    private String button;
    public ButtonView(Integer button) {
        this.button = Integer.toString(button);
    }
    public String getLink() {
        return "/visualizer?button=" + this.button;
    }
    public String getNumber() {
        return this.button;
    }
}