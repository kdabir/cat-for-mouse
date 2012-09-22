import com.strsrch.main.FuzzyStringComparator;
import com.strsrch.model.SrchResult;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ShowShortcutsPopup extends PlainDocument {
    JComboBox comboBox;
    ComboBoxModel model;
    JTextComponent editor;
    // flag to indicate if setSelectedItem has been called
    // subsequent calls to remove/insertString should be ignored
    boolean selecting = false;
    private String enteredText="";
    private FuzzyStringComparator stringComparator;

    private List<String> shortcutsList;
    private Map<String,String> actionShortcutMap;


    public ShowShortcutsPopup(final JComboBox comboBox) {
        stringComparator =new FuzzyStringComparator();
        this.comboBox = comboBox;
        model = comboBox.getModel();
        editor = (JTextComponent) comboBox.getEditor().getEditorComponent();
        editor.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_DOWN:
                    case KeyEvent.VK_UP:
                        break;
                    case KeyEvent.VK_ESCAPE:
                        Window frame = SwingUtilities.windowForComponent((Component) e
                                .getSource());
                        frame.setVisible(false);
                        break;
                    case KeyEvent.VK_BACK_SPACE:
                        deleteText();
                        break;
                    case KeyEvent.VK_ENTER:
                        enteredText="";
                    default :
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

    private void deleteText() {
        if((editor.getSelectionEnd()-editor.getSelectionStart()) > 0) {
            String highlightedText = enteredText.substring(editor.getSelectionStart(), editor.getSelectionEnd());
            enteredText = enteredText.replace(highlightedText, "");
        }
        else {
            int position = editor.getCaretPosition()-1;
            enteredText=enteredText.substring(0,position)+enteredText.substring(position+1);
        }
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
        } else {
            comboBox.getSelectedItem();
            comboBox.getToolkit().beep();
        }
        enteredText+=str;
        setText(enteredText);
    }

    private void setText(String text) throws BadLocationException {
        // remove all text and insert the completed string
        super.remove(0, getLength());
        super.insertString(0, text, null);
    }


    private void setSelectedItem(Object item) {
        selecting = true;
        model.setSelectedItem(item);
        selecting = false;
    }

    private Object lookupItem(String userInput) {
        List<SrchResult> srchResults = stringComparator.fuzzyCompareAndRank(shortcutsList,userInput);
        comboBox.removeAllItems();
        int count=0;
        for (SrchResult srchResult : srchResults) {
            comboBox.insertItemAt(new ShortcutModel(srchResult.getData(), actionShortcutMap.get(srchResult.getData())),count++);
        }

        comboBox.setMaximumRowCount(comboBox.getItemCount() );
        comboBox.setPopupVisible(true);

        return null;
    }

    private boolean startsWithIgnoreCase(String str1, String str2) {
        return str1.toUpperCase().startsWith(str2.toUpperCase());
    }

    public void setShortcutsList(Map<String, String> actionShortcutMap) {
        this.actionShortcutMap = actionShortcutMap;
        this.shortcutsList = Arrays.asList(actionShortcutMap.keySet().toArray(new String[actionShortcutMap.keySet().size()]));
    }
}
