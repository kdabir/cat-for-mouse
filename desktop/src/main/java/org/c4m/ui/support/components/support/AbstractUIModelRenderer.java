package org.c4m.ui.support.components.support;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: srideep
 * Date: 24/09/12
 * Time: 6:27 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractUIModelRenderer implements ListCellRenderer {
    protected Border selectedBorderStyle;
    protected Border normalBorderStyle;
    protected Color selectedTextColor;
    protected Color textColor;
    protected Color selectedBackColor;
    protected Color backColor;
    protected int selectedIndex;

    public AbstractUIModelRenderer(Color selectedTextColor, Border selectedBorderStyle, Color selectedBackColor, Color textColor, Color backColor, Border normalBorderStyle) {
        this.selectedTextColor = selectedTextColor;
        this.selectedBorderStyle = selectedBorderStyle;
        this.selectedBackColor = selectedBackColor;
        this.textColor = textColor;
        this.backColor = backColor;
        this.normalBorderStyle = normalBorderStyle;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

}
