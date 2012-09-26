package org.c4m.ui.support.components.support;

import org.c4m.ui.model.AppUIModel;

import javax.swing.*;
import java.awt.*;

public class AppPopupUIElement extends JPanel{

    private AppUIModel uiModel;
    private JLabel label;


    public AppPopupUIElement(AppUIModel uiModel) {
        this.uiModel = uiModel;
        label = new JLabel();
        label.setIcon(uiModel.getIcon());
        label.setText(uiModel.getAppName());
        this.add(label);
    }

    public AppUIModel getUiModel() {
        return uiModel;
    }

    public void setTextColor(Color textColor) {
        label.setForeground(textColor);
    }
}
