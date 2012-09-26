package org.c4m.ui.support.components.support;

import org.c4m.ui.model.AppUIModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * Created with IntelliJ IDEA.
 * User: srideep
 * Date: 22/09/12
 * Time: 8:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class AppEditorUIElement extends JPanel {

    private JLabel imageLbl ;

    private JTextField textField;


    private AppUIModel item;

    public AppEditorUIElement(){
        textField = new JTextField();
        imageLbl = new JLabel();
        this.setLayout(new BorderLayout());
        add(imageLbl,BorderLayout.WEST);
        imageLbl.setBorder(BorderFactory.createRaisedBevelBorder());
        add(textField,BorderLayout.CENTER);
    }


    public void setItem(AppUIModel appUIModel){
        this.item = appUIModel;
        if (appUIModel == null) {
            imageLbl.setIcon(null);
            textField.setText(null);

            return;
        };
        imageLbl.setIcon(appUIModel.getIcon());
        textField.setText(appUIModel.getAppName());
    }

    public JTextField getTextField() {
        return textField;
    }

    public AppUIModel getItem() {
        return item;
    }

    public JLabel getImageLbl() {
        return imageLbl;
    }


}
