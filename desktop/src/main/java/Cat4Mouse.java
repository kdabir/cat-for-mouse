import com.apple.eawt.Application;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.NativeInputEvent;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

public class Cat4Mouse extends JPanel {
    ImageIcon[] images = {};
    String[] imageNames = {"idea_icon.png", "idea_icon.png", "terminal.jpg", "eclipse_icon.png", "idea_icon.png"};
    String[] menuShortCuts = {"Idea Open File", "Idea Save File", "Terminal open", "Eclipse Open file", "Idea new file"};


    public Cat4Mouse() {
        super(new BorderLayout());

        images = new ImageIcon[menuShortCuts.length];
        Integer[] intArray = new Integer[menuShortCuts.length];
        for (int i = 0; i < menuShortCuts.length; i++) {
            intArray[i] = new Integer(i);
            images[i] = createImageIcon("covers/" + imageNames[i]);
            if (images[i] != null) {
                images[i].setDescription(menuShortCuts[i]);
            }
        }

        //Create the combo box.
        final JComboBox comboBox = setUpAndAddComboBox();

        setUpTrayIcon(comboBox);
    }

    private void setUpTrayIcon(final JComponent comboBox) {
        Image image = new ImageIcon(Cat4Mouse.class.getResource("covers/shortcuts.png")).getImage();
        final TrayIcon trayIcon = new TrayIcon(image);
        trayIcon.setToolTip("cat4Mouse");
        try {
            SystemTray.getSystemTray().add(trayIcon);
        } catch (AWTException e2) {
            e2.printStackTrace();
        }

        trayIcon.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                Window frame = SwingUtilities.windowForComponent(comboBox);
                frame.toFront();
                frame.setVisible(true);
            }
        });

        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }

    }

    private JComboBox setUpAndAddComboBox() {
        final JComboBox comboBox = new JComboBox(menuShortCuts);
        ComboBoxRenderer renderer = new ComboBoxRenderer();
        renderer.setPreferredSize(new Dimension(300, 20));
        comboBox.setSize(100, 20);
        comboBox.setRenderer(renderer);
        comboBox.setMaximumRowCount(3);
        comboBox.setEditable(true);
        comboBox.setSelectedItem(null);
        comboBox.setFocusable(true);
        JTextComponent editor = (JTextComponent) comboBox.getEditor().getEditorComponent();
        // change the editor's document
        editor.setDocument(new S09ShowPopup(comboBox));

        //Lay out the demo.
        add(new JLabel("Cat4Mouse"), BorderLayout.PAGE_START);
        add(comboBox, BorderLayout.CENTER);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        return comboBox;
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

    private static void createAndShowGUI() {

        final JFrame frame = new JFrame("Cat4Mouse");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JComponent newContentPane = new Cat4Mouse();
        newContentPane.setOpaque(true);

        frame.setContentPane(newContentPane);
        frame.setUndecorated(true);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setSize(700, 20);
        frame.setLocation(392, 451);
        frame.pack();
        frame.setVisible(true);

        activateFrameOnGlobalKeyPressAltPlusSpace(frame);

    }

    private static void activateFrameOnGlobalKeyPressAltPlusSpace(final JFrame frame) {
        final int[] previousKeyCode = {0};

        GlobalScreen.getInstance().addNativeKeyListener(new NativeKeyListener() {
            @Override
            public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {

                if (previousKeyCode[0] == NativeInputEvent.BUTTON1_MASK && NativeKeyEvent.BUTTON2_MASK == nativeKeyEvent.getKeyCode()) {
                    try {
                        Robot robot = new Robot();
                        robot.delay(40);
                        robot.keyPress(KeyEvent.VK_BACK_SPACE);
                        robot.keyRelease(KeyEvent.VK_BACK_SPACE);
                        robot.keyPress(KeyEvent.VK_CLEAR);
                        robot.keyRelease(KeyEvent.VK_CLEAR);

                        frame.setVisible(true);
                        frame.setAlwaysOnTop(true);

                        Application.getApplication().requestForeground(true);
                    } catch (AWTException e) {
                    }
                }
                previousKeyCode[0] = nativeKeyEvent.getKeyCode();
            }

            @Override
            public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
            }

            @Override
            public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {
            }

        });
    }

    public static void main(String[] args) throws AWTException, ClassNotFoundException, UnsupportedLookAndFeelException, IllegalAccessException, InstantiationException {
        Application application = Application.getApplication();
        application.setDockIconBadge("cat4Mouse");
        Image image = new ImageIcon(Cat4Mouse.class.getResource("covers/shortcuts.png")).getImage();
        application.setDockIconImage(image);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });

    }

    class ComboBoxRenderer extends JLabel
            implements ListCellRenderer {
        private Font uhOhFont;

        public ComboBoxRenderer() {
            setOpaque(true);
            setHorizontalAlignment(LEFT);
            setVerticalAlignment(CENTER);
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
}