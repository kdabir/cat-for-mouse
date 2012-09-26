package org.c4m.ui.support.components.support;

import org.c4m.ui.model.ShortcutUIModel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ShortcutUIModelRenderer extends AbstractUIModelRenderer{


    public ShortcutUIModelRenderer(Border normalBorderStyle, Border selectedBorderStyle, Color selectedTextColor, Color textColor, Color selectedBackColor, Color backColor) {
        super(selectedTextColor, selectedBorderStyle, selectedBackColor, textColor, backColor, normalBorderStyle);
    }

    @Override
    public Component getListCellRendererComponent(JList jList, Object model, int index, boolean isSelected, boolean hasFocus) {
        ShortcutUIModel uiModel = (ShortcutUIModel) model;
        ShortcutListUIElement listUiElement = new ShortcutListUIElement(uiModel);

        if (isSelected){
            listUiElement.setBorder(selectedBorderStyle);
            listUiElement.setBackground(selectedBackColor);
            listUiElement.setTextColor(selectedTextColor);
            this.selectedIndex = jList.getSelectedIndex();
        }
        else
        {
            listUiElement.setBorder(normalBorderStyle);
            listUiElement.setBackground(backColor);
            listUiElement.setTextColor(textColor);
        }
        return listUiElement;
    }
}
