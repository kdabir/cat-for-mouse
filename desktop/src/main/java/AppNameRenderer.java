import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

class AppNameRenderer extends JLabel implements ListCellRenderer {
    ImageIcon[] images = {};

    String[] menuShortCuts = null;

    public AppNameRenderer() {
        setOpaque(true);
        setHorizontalAlignment(LEFT);
        setVerticalAlignment(CENTER);
        menuShortCuts = ApplicationMapper.getApps();
        images = ApplicationMapper.getAppIcons();

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
        int selectedIndex = Arrays.asList(menuShortCuts).indexOf(value);

        ImageIcon icon = images[selectedIndex];
        String pet = menuShortCuts[selectedIndex];

        if (isSelected) {
            setBackground(new Color(49,38,209));
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        Image image = icon.getImage();
        Image newImage = image.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        setIcon(new ImageIcon(newImage));
        if (newImage != null) {
            setUhOhText(pet);
        } else {
            setUhOhText(pet + " (no image available)");
        }


        return this;
    }

    //Set the font and text when no image was found.
    protected void setUhOhText(String uhOhText) {
        setFont(new Font("Courier",Font.PLAIN,12));
        setText(uhOhText);
    }
}