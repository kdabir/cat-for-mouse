import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class ImagePanel extends JPanel {
    JLabel imageIconLabel;

    JTextField textField;

    public ImagePanel(Icon image,String text) {
        setLayout(new BorderLayout());
        imageIconLabel = new JLabel(image);
        imageIconLabel.setBorder(new BevelBorder(BevelBorder.RAISED));

        textField = new JTextField(text);
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setColumns(20);
        textField.setBorder(new BevelBorder(BevelBorder.LOWERED));

        add(imageIconLabel, BorderLayout.WEST);
        add(textField);
    }

    public void setText(String s) {
        textField.setText(s);
    }

    public String getText() {
        return (textField.getText());
    }

    public void setIcon(Icon i) {
        imageIconLabel.setIcon(i);
        repaint();
    }

    public void selectAll() {
        textField.selectAll();
    }

    public void addActionListener(ActionListener l) {
        textField.addActionListener(l);
    }

    public void removeActionListener(ActionListener l) {
        textField.removeActionListener(l);
    }

    public JTextField getTextField() {
        return textField;
    }
}

