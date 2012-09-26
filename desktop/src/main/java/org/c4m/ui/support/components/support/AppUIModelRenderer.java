package org.c4m.ui.support.components.support;

import org.c4m.ui.model.AppUIModel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;


public class AppUIModelRenderer extends AbstractUIModelRenderer{


    public AppUIModelRenderer(Border normalBorderStyle, Border selectedBorderStyle, Color selectedTextColor, Color textColor, Color selectedBackColor, Color backColor) {
        super(selectedTextColor, selectedBorderStyle, selectedBackColor, textColor, backColor, normalBorderStyle);
    }

    @Override
    public Component getListCellRendererComponent(JList jList, Object model, int index, boolean isSelected, boolean hasFocus) {
        //System.out.println(jList.getSelectedIndex());
            AppUIModel uiModel = (AppUIModel) model;
        AppPopupUIElement popupUiElement = new AppPopupUIElement(uiModel);


        if (isSelected){
            popupUiElement.setBorder(selectedBorderStyle);
            popupUiElement.setBackground(selectedBackColor);
            popupUiElement.setTextColor(selectedTextColor);
            this.selectedIndex = jList.getSelectedIndex();
        }
        else
        {
            popupUiElement.setBorder(normalBorderStyle);
            popupUiElement.setBackground(backColor);
            popupUiElement.setTextColor(textColor);
        }
        return popupUiElement;
    }
}
