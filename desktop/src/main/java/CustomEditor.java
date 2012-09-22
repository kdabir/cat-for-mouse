import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;


public class CustomEditor implements ComboBoxEditor {
    ImagePanel panel;
    private static HashMap<String, String> map=new HashMap<String, String>();

    public CustomEditor(){
        panel=new ImagePanel(new ImageIcon(),"");
    }

    public void setItem(Object anObject) {
        if (anObject != null) {
            panel.setText(anObject.toString());
            panel.setIcon(ApplicationMapper.createImageIcon(map.get(anObject.toString())));
        }
    }

    public Component getEditorComponent() {
        return panel;
    }

    public Object getItem() {
        return panel.getText();
    }

    public void selectAll() {
        panel.selectAll();
    }

    public void addActionListener(ActionListener l) {
        panel.addActionListener(l);
    }

    public void removeActionListener(ActionListener l) {
        panel.removeActionListener(l);
    }

    public JTextField getTextComponent() {
        return panel.getTextField();
    }

    public static HashMap<String, String> getMap() {
        return map;
    }

    public static void setMap(HashMap<String, String> map) {
        CustomEditor.map = map;
    }
}
