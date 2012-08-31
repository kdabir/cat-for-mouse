import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

class ShortcutsRenderer extends JLabel implements ListCellRenderer {
    private Font uhOhFont;

    String[] imageNames = {"idea_icon.png", "idea_icon.png", "terminal.jpg", "eclipse_icon.png", "idea_icon.png"};
    ImageIcon[] images = {};
    String[] menuShortCuts = {"Idea Open File", "Idea Save File", "Terminal open", "Eclipse Open file", "Idea new file"};

    public ShortcutsRenderer() {
        setOpaque(true);
        setHorizontalAlignment(LEFT);
        setVerticalAlignment(CENTER);

        images = new ImageIcon[menuShortCuts.length];
        Integer[] intArray = new Integer[menuShortCuts.length];
        for (int i = 0; i < menuShortCuts.length; i++) {
            intArray[i] = new Integer(i);
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
        if (icon != null) {
            setUhOhText(pet, list.getFont());
        } else {
            setUhOhText(pet + " (no image available)", list.getFont());
        }


        return this;
    }

    //Set the font and text when no image was found.
    protected void setUhOhText(String uhOhText, Font normalFont) {
        if (uhOhFont == null) { //lazily create this font
            uhOhFont = normalFont.deriveFont(Font.ITALIC);
        }
        setFont(uhOhFont);
        setText(uhOhText);
    }
}