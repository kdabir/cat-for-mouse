import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ShowAppNamesPopup extends PlainDocument {
    JComboBox comboBox;
    ComboBoxModel model;
    JTextComponent editor;
    boolean selecting=false;
    private static String[] apps;


    public ShowAppNamesPopup(final JComboBox comboBox) {
        this.comboBox = comboBox;
        apps= ApplicationMapper.getApps();
        model = comboBox.getModel();
        editor = (JTextComponent)((CustomEditor) comboBox.getEditor()).getTextComponent();
        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!selecting) highlightCompletedText(0);
            }
        });
        editor.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()){
                    case KeyEvent.VK_DOWN:
                    case KeyEvent.VK_UP:
                        comboBox.requestFocus();
                        try {
                            Robot robot = new Robot();
                            robot.keyPress(KeyEvent.VK_DOWN);
                            robot.keyPress(KeyEvent.VK_DOWN);
                        } catch (AWTException e1) {
                        }
                        break;
                    case KeyEvent.VK_ESCAPE:
                        Window frame = SwingUtilities.windowForComponent((Component) e
                                .getSource());
                        frame.setVisible(false);
                        break;
                    default:
                        if(e.getKeyCode()>=65 && e.getKeyCode()<=90){
                            comboBox.setPopupVisible(true);
                        }

                }
            }
        });
    }

    public void remove(int offs, int len) throws BadLocationException {
        // return immediately when selecting an item
        if (selecting) return;
        super.remove(offs, len);
    }

    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        // return immediately when selecting an item
        if (selecting) return;
        // insert the string into the document
        super.insertString(offs, str, a);
        // lookup and select a matching item
        Object item = lookupItem(getText(0, getLength()));
        if (item != null) {
            setSelectedItem(item);
        }
        if(item!=null) setText(item.toString());
       }

    private void setText(String text) throws BadLocationException {
        // remove all text and insert the completed string
        super.remove(0, getLength());
        super.insertString(0, text, null);
    }

    private void highlightCompletedText(int start) {
        editor.setSelectionStart(start);
        editor.setSelectionEnd(getLength());
    }

    private void setSelectedItem(Object item) {
        selecting = true;
        model.setSelectedItem(item);
        selecting = false;
    }

    private Object lookupItem(String pattern) {

        comboBox.removeAllItems();
        int count=0;
        for (String appName : apps) {
            if(startsWithIgnoreCase(appName,pattern)) {
                comboBox.insertItemAt(appName,count++);
                CustomEditor.getMap().put(appName, ApplicationMapper.getAppIconsFor(appName));
            }
        }

        comboBox.setMaximumRowCount(comboBox.getItemCount());
        comboBox.setPopupVisible(true);

        return null;
    }

    // checks if str1 starts with str2 - ignores case
    private boolean startsWithIgnoreCase(String str1, String str2) {
        return str1.toUpperCase().startsWith(str2.toUpperCase());
    }

    public static String[] getApps() {
        return apps;
    }
}
