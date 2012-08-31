import com.apple.eawt.Application;
import com.apple.laf.AquaComboBoxUI;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.NativeInputEvent;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Cat4Mouse extends JPanel {
    JComboBox appNameComboBox;
    JComboBox shortcutsComboBox;

    String[] menuShortCuts = {"Idea Open File", "Idea Save File", "Terminal open", "Eclipse Open file", "Idea new file"};


    public Cat4Mouse() {
        super(new BorderLayout());

        setUpAndAddComboBox();

        setUpTrayIcon();
    }

    private void setUpTrayIcon() {
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
                makeFrameVisible();
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

    private void makeFrameVisible() {
        Window frame = SwingUtilities.windowForComponent(appNameComboBox);
        frame.toFront();
        frame.setVisible(true);
    }

    private void setUpAndAddComboBox() {
        ShortcutsRenderer renderer = new ShortcutsRenderer();
        renderer.setPreferredSize(new Dimension(300, 20));

        DefaultComboBoxModel comboBoxModel=new DefaultComboBoxModel(menuShortCuts);
        shortcutsComboBox =createComboBox(comboBoxModel,new ShortcutsRenderer());

        DefaultComboBoxModel comboBoxModel1=new DefaultComboBoxModel();
        appNameComboBox=createComboBox(comboBoxModel1, new AppNameRenderer());

        JTextComponent editor = (JTextComponent) this.shortcutsComboBox.getEditor().getEditorComponent();
        editor.setDocument(new ShowShortcutsPopup(this.shortcutsComboBox));

        JTextComponent editor1 = (JTextComponent) appNameComboBox.getEditor().getEditorComponent();
        editor1.setDocument(new ShowAppNamePopup(appNameComboBox));

        this.shortcutsComboBox.setBorder(BorderFactory.createEmptyBorder());
        appNameComboBox.setBorder(BorderFactory.createEmptyBorder());
        //Lay out the demo.
        setLayout(new GridLayout(1, 2, 0, 0));

        add(appNameComboBox);
        add(shortcutsComboBox);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    private JComboBox createComboBox(DefaultComboBoxModel comboBoxModel, ListCellRenderer comboBoxRenderer) {
        JComboBox comboBox = new JComboBox(comboBoxModel);
        comboBox.setSize(70, 20);
        comboBox.setMaximumRowCount(3);
        comboBox.setEditable(true);
        comboBox.setSelectedItem(null);
        comboBox.setFocusable(true);
        comboBox.setRenderer(comboBoxRenderer);
        comboBox.setUI(new AquaComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                super.createArrowButton();

                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });

        return comboBox;
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

        GlobalScreen.getInstance().addNativeKeyListener(new NativeKeyListener() {
            @Override
            public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {

                if (nativeKeyEvent.getModifiers()==NativeInputEvent.ALT_MASK && NativeKeyEvent.BUTTON2_MASK == nativeKeyEvent.getKeyCode()) {
                    frame.setVisible(true);
                    frame.setAlwaysOnTop(true);
                    Application.getApplication().requestForeground(true);
                }
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
}