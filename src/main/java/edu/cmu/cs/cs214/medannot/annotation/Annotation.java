package edu.cmu.cs.cs214.medannot.annotation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Annotation {
    private final List<Integer> buttons;
    private final Map<Integer, String> annotations;
    private String annotation;
    public Annotation(Map<Integer, String> annotations) {
        buttons = annotations.keySet().stream().toList();
        this.annotations = new HashMap<>(annotations);
        this.annotation = null;
    }
    public void buttonPressed(Integer button) {
        if (button != null) this.annotation = this.annotations.get(button);
    }
    public String getAnnotation() {
        if (this.annotation == null) {
            return "No annotation selected.";
        } else {
            return this.annotation;
        }
    }

    public String getImage() {
        return "image";
    }
    public String getImagename() {
        return "Rendered";
    }
    public ButtonView[] getButtons() {
        ButtonView[] views = new ButtonView[this.buttons.size()];
        for (int i = 0; i < views.length; i++) {
            views[i] = new ButtonView(this.buttons.get(i));
        }
        return views;
    }
}
