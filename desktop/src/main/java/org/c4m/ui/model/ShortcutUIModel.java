package org.c4m.ui.model;

/**
 * Created with IntelliJ IDEA.
 * User: srideep
 * Date: 24/09/12
 * Time: 5:59 PM
 * To change this template use File | Settings | File Templates.
 */
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
