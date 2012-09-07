import javax.swing.*;
import java.awt.*;
import java.nio.charset.Charset;
import java.util.Arrays;

class ShortcutsRenderer extends JLabel implements ListCellRenderer {

    String[] imageNames = {"idea_icon.png", "idea_icon.png", "terminal.jpg", "eclipse_icon.png", "idea_icon.png"};
    ImageIcon[] images = {};
    String[] menuShortCuts = {"Idea open file", "Idea save file", "Idea new file", "Terminal open", "Eclipse Open file"};

    public ShortcutsRenderer() {
        setOpaque(true);
        setHorizontalAlignment(LEFT);
        setVerticalAlignment(CENTER);

        images = new ImageIcon[menuShortCuts.length];
        for (int i = 0; i < menuShortCuts.length; i++) {
            images[i] = createImageIcon("covers/" + imageNames[i]);
            if (images[i] != null) {
                images[i].setDescription(menuShortCuts[i]);
            }
        }

    }

    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = Cat4Mouse.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
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
            setBackground(list.getSelectionBackground());
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
        System.out.println(uhOhText+(String.format("%" + (30-uhOhText.length()) + "s","cmd")));

        setText(new String((uhOhText + String.format("%" + (30-uhOhText.length()) + "s", "cmd")).getBytes(Charset.defaultCharset())));
    }
}