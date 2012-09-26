package org.c4m.ui.model;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: srideep
 * Date: 22/09/12
 * Time: 7:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class AppUIModel {
    public AppUIModel(ImageIcon icon, String appName) {
        this.icon = new ImageIcon(icon.getImage().getScaledInstance(32,32,Image.SCALE_SMOOTH));
        this.appName = appName;
    }

    private ImageIcon icon;
    private String appName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppUIModel that = (AppUIModel) o;

        if (appName != null ? !appName.equals(that.appName) : that.appName != null) return false;
        if (icon != null ? !icon.equals(that.icon) : that.icon != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = icon != null ? icon.hashCode() : 0;
        result = 31 * result + (appName != null ? appName.hashCode() : 0);
        return result;
    }

    public ImageIcon getIcon() {

        return icon;
    }

    public String getAppName() {
        return appName;
    }
}
