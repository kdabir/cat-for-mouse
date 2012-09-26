package org.c4m.ui.support.components.support;

import org.c4m.ui.model.ShortcutUIModel;

import javax.swing.*;
import java.awt.*;


public class ShortcutListUIElement extends JPanel {
    private JLabel actionLbl;
    private JLabel shortcutLbl;

    private ShortcutUIModel shortcutUIModel;

    public ShortcutListUIElement(ShortcutUIModel shortcutUIModel){
        this.shortcutUIModel = shortcutUIModel;
        actionLbl = new JLabel(shortcutUIModel.getAction());
        shortcutLbl = new JLabel(shortcutUIModel.getShortcut());
        actionLbl.setHorizontalAlignment(SwingConstants.LEFT);
        shortcutLbl.setHorizontalAlignment(SwingConstants.RIGHT);

        this.setLayout(new BorderLayout());
        add(actionLbl, BorderLayout.NORTH);
        add(shortcutLbl, BorderLayout.SOUTH);

        actionLbl.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        shortcutLbl.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        actionLbl.setFont(stylizeFont(actionLbl.getFont(),Font.BOLD, 12));
        shortcutLbl.setFont(stylizeFont(shortcutLbl.getFont(),Font.ITALIC, 12));
    }

    private Font stylizeFont(Font original, int style, int size)
    {
        Font fnt = new Font(original.getFontName(), style, size);
        return fnt;
    }


    public ShortcutUIModel getShortcutUIModel() {
        return shortcutUIModel;
    }

    public void setTextColor(Color selectedTextColor) {
        actionLbl.setForeground(selectedTextColor);
        shortcutLbl.setForeground(selectedTextColor);
    }
}
