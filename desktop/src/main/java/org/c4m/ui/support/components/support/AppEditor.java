package org.c4m.ui.support.components.support;

import org.c4m.ui.model.AppUIModel;
import org.c4m.ui.support.helpers.ShortcutHelper;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import java.awt.*;
import java.awt.event.*;
import java.util.List;


public class AppEditor extends BasicComboBoxEditor {

    private AppEditorUIElement editor;
    private boolean mustRefresh = false;
    private boolean hasSelectionHappened = false;



    public AppEditor(final JComboBox parentCombo, final ShortcutHelper shortcutHelper) {
        editor = new AppEditorUIElement();

        editor.getTextField().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                parentCombo.showPopup();
            }
        });

        editor.getTextField().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent focusEvent) {

            }

            @Override
            public void focusLost(FocusEvent focusEvent) {
                parentCombo.hidePopup();
                performSelection(parentCombo);
            }
        });

        editor.getTextField().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                mustRefresh = false;
                hasSelectionHappened = false;
                if (!parentCombo.isPopupVisible())
                    parentCombo.showPopup();

                if (keyEvent.getKeyCode() == KeyEvent.VK_DOWN){
                    if (parentCombo.getSelectedIndex() < parentCombo.getItemCount() -1){
                        String text = editor.getTextField().getText();
                        Icon image = editor.getImageLbl().getIcon();
                        parentCombo.setSelectedIndex(parentCombo.getSelectedIndex() + 1);
                        editor.getTextField().setText(text);
                        editor.getImageLbl().setIcon(image);
                        return;
                    }
                }

                if (keyEvent.getKeyCode() == KeyEvent.VK_UP){
                    int index = parentCombo.getSelectedIndex() > 0 ? parentCombo.getSelectedIndex() - 1: 0;
                    Icon image = editor.getImageLbl().getIcon();
                    String text = editor.getTextField().getText();
                    parentCombo.setSelectedIndex(index);
                    editor.getTextField().setText(text);
                    editor.getImageLbl().setIcon(image);

                    return;

                }

                if (keyEvent.getKeyCode() == KeyEvent.VK_BACK_SPACE ){
                    mustRefresh = true;
                    parentCombo.setSelectedItem(null);
                    return;
                }
                if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER ){
                    hasSelectionHappened = true;
                }


                mustRefresh = true;

            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                if (!mustRefresh)  return;


                if (hasSelectionHappened)
                {
                    performSelection(parentCombo);
                    return;
                }
                String text = editor.getTextField().getText().trim();
                parentCombo.removeAllItems();
                List<AppUIModel> appList = shortcutHelper.getAppList(text);
                populateAppNameCombo(appList, parentCombo);
                parentCombo.setMaximumRowCount(appList.size());
                editor.getImageLbl().setIcon(null);
                editor.getTextField().setText(text);
                if (parentCombo.getItemCount()!=0){
                    parentCombo.showPopup();
                }

            }
        });


    }

    private void performSelection(JComboBox parentCombo) {
        AbstractUIModelRenderer renderer = (AbstractUIModelRenderer) parentCombo.getRenderer();
        if (parentCombo.getItemCount()==0) return;
        parentCombo.setSelectedIndex(renderer.getSelectedIndex());
        parentCombo.setSelectedItem(parentCombo.getItemAt(renderer.getSelectedIndex()));
        AppUIModel selItem = (AppUIModel) parentCombo.getItemAt(renderer.getSelectedIndex());
        editor.getTextField().setText(selItem.getAppName());
        editor.getImageLbl().setIcon(selItem.getIcon());
    }


    private void populateAppNameCombo(List<AppUIModel> appList, JComboBox parentCombo) {
        for (AppUIModel appUIModel : appList) {
            parentCombo.addItem(appUIModel);
        }
    }

    @Override
    public Component getEditorComponent() {
        return (Component) editor;
    }

    @Override
    public void setItem(Object o) {
        editor.setItem((AppUIModel) o);
    }

    @Override
    public Object getItem() {
        return editor.getItem();
    }

    @Override
    public void selectAll() {
        editor.getTextField().selectAll();
    }

    @Override
    public void addActionListener(ActionListener actionListener) {
        editor.getTextField().addActionListener(actionListener);
    }

    @Override
    public void removeActionListener(ActionListener actionListener) {
        editor.getTextField().removeActionListener(actionListener);
    }
}
