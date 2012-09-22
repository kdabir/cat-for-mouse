import javax.swing.*;
import java.awt.*;

public class ShortcutRenderer extends JPanel implements ListCellRenderer {
    private JLabel text;
    private JLabel shortcut;
    private Font uhOhFont;

    public ShortcutRenderer() {
        text=new JLabel("hello");
        shortcut=new JLabel("hi");
    }

    /*
    * This method finds the image and text corresponding
    * to the selected value and returns the label, set up
    * to display the text and image.
    */
    public Component getListCellRendererComponent(
            JList list,
            Object value,
            int index,
            boolean isSelected,
            boolean cellHasFocus) {

        ShortcutModel model = (ShortcutModel)value;

        if (isSelected) {
            setBackground(new Color(49,38,209));
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        setText(model, list.getFont());


        return this;
    }

    //Set the font and text when no image was found.
    protected void setText(ShortcutModel model, Font normalFont) {
        text.setText(model.getAction());
        shortcut.setText(model.getShortCut());
        setLayout(new BorderLayout());
        add(text,BorderLayout.WEST);
        add(shortcut,BorderLayout.EAST);
     //   setText(new String((uhOhText + String.format("%" + (30-uhOhText.length()) + "s", "cmd")).getBytes(Charset.defaultCharset())));
    }
}