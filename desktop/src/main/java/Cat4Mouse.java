import com.apple.eawt.Application;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.NativeInputEvent;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.HashMap;
import java.util.Map;

public class Cat4Mouse extends JPanel {
    static JComboBox appNameComboBox;
    JComboBox shortcutsComboBox;
    private ShowShortcutsPopup  showShortcutsPopup;


    private HashMap<String,String> map=new HashMap<String, String>();


    public Cat4Mouse() {
        super();
        setOpaque(false);

        setUpAndAddComboBox();
        setUpTrayIcon();
    }

    private void setUpTrayIcon() {
        new SystemTrayIcon().setup();

        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }

    }

    protected static void makeFrameVisible() {
        Window frame = SwingUtilities.windowForComponent(appNameComboBox);
        frame.toFront();
        frame.setVisible(true);
    }

    private void setUpAndAddComboBox() {
        DefaultComboBoxModel comboBoxModel=new DefaultComboBoxModel();
        shortcutsComboBox =createComboBox(comboBoxModel,new ShortcutRenderer());
        JTextComponent editor = (JTextComponent) shortcutsComboBox.getEditor().getEditorComponent();
        showShortcutsPopup = new ShowShortcutsPopup(shortcutsComboBox);
        editor.setDocument(showShortcutsPopup);
        this.shortcutsComboBox.setBorder(BorderFactory.createEmptyBorder());

        DefaultComboBoxModel comboBoxModel1=new DefaultComboBoxModel();
        CustomEditor customEditor = new CustomEditor();
        JTextComponent editor1 =(JTextComponent)customEditor.getTextComponent();
        appNameComboBox=createComboBox(comboBoxModel1,new AppNameRenderer());
        appNameComboBox.setEditor(customEditor);
        editor1.setDocument(new ShowAppNamesPopup(appNameComboBox));

        appNameComboBox.setBorder(BorderFactory.createEmptyBorder());


        appNameComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String appName = (String)appNameComboBox.getEditor().getItem() ;
                if (appName == null)
                     System.out.println(appName);
                Map<String,String> shortcutsForApp = ApplicationMapper.getShortcutsForApp(appName);
                showShortcutsPopup.setShortcutsList(shortcutsForApp);

            }
        });
        //Lay out the demo.
        setLayout(new GridBagLayout());
        add(appNameComboBox);
        appNameComboBox.setPreferredSize(new Dimension(150,20));
        add(shortcutsComboBox);

        setBorder(BorderFactory.createEmptyBorder(20, 22, 20, 5));
    }

    private JComboBox createComboBox(DefaultComboBoxModel comboBoxModel, ListCellRenderer comboBoxRenderer) {
        JComboBox comboBox = new JComboBox(comboBoxModel);
        comboBox.setPreferredSize(new Dimension(300, 20));
        comboBox.setMaximumRowCount(3);
        comboBox.setEditable(true);
        comboBox.setSelectedItem(null);
        comboBox.setFocusable(true);
        comboBox.setRenderer(comboBoxRenderer);
        hideArrowButton(comboBox);
        return comboBox;
    }

    private void hideArrowButton(JComboBox comboBox) {
        final Component[] components = comboBox.getComponents();
        for(Component comp : components)
        {
            if(comp instanceof JButton)
            {
                ((JButton)comp).setVisible(false);
                //break;
            }
            if (comp instanceof JTextField){
                ((JTextField) comp).addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent focusEvent) {
                        String appName = (String)appNameComboBox.getEditor().getItem() ;
                        if (appName == null)
                            System.out.println(appName);
                        Map<String,String> shortcutsForApp = ApplicationMapper.getShortcutsForApp(appName);
                        showShortcutsPopup.setShortcutsList(shortcutsForApp);
                    }

                    @Override
                    public void focusLost(FocusEvent focusEvent) {
                        System.out.println("Focus Lost");

                    }
                });
            }
        }
    }

    private static void createAndShowGUI() {
        final JFrame frame = new JFrame("Cat4Mouse");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JComponent newContentPane = new Cat4Mouse();
        newContentPane.setOpaque(true);

        frame.setContentPane(newContentPane);
        frame.setBackground(new Color(49,38,209));
        frame.setUndecorated(true);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setLocation(392, 451);
        frame.setResizable(true);

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