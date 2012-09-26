package org.c4m.ui.model;


public class ShortcutUIModel {
    private String action;
    private String shortcut;


    public ShortcutUIModel(String action, String shortcut) {
        this.action = action;
        this.shortcut = shortcut;
    }

    public String getAction() {
        return action;
    }

    public String getShortcut() {
        return shortcut;
    }

}
