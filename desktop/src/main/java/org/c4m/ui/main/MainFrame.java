package org.c4m.ui.main;

import com.strsrch.main.FuzzyStringComparator;
import org.c4m.ui.model.AppUIModel;
import org.c4m.ui.model.ShortcutUIModel;
import org.c4m.ui.support.components.support.AppEditor;
import org.c4m.ui.support.components.support.AppEditorUIElement;
import org.c4m.ui.support.components.support.AppUIModelRenderer;
import org.c4m.ui.support.components.support.ShortcutUIModelRenderer;
import org.c4m.ui.support.helpers.GlobalKeyListener;
import org.c4m.ui.support.helpers.ShortcutHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: srideep
 * Date: 22/09/12
 * Time: 7:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class MainFrame extends JFrame{

    public static final int ITEM_SIZE = 40;
    private JComboBox appCombo;
    private JList shortcutList;
    private JTextField shortcutQueryField;
    private ShortcutHelper shortcutHelper;
    private int compHeight = 40;
    private final ShortcutUIModelRenderer shortcutCellRenderer;
    private DefaultListModel listModel;
    private static final int MAX_ROWS = 10;

    public MainFrame(){

        setupCoreListeners();
        setupNotificationIcon();

        listModel = new DefaultListModel();
        shortcutQueryField = new JTextField();
        shortcutHelper = new ShortcutHelper();
        FuzzyStringComparator comparator = new FuzzyStringComparator();


        this.setSize(500, compHeight * 2);
        this.setAlwaysOnTop(true);
        final Container contentPane = this.getContentPane();
        contentPane.setLayout(null);
        appCombo = new JComboBox();
        ListCellRenderer appRenderer = new AppUIModelRenderer(BorderFactory.createEmptyBorder(),BorderFactory.createLoweredBevelBorder(), Color.CYAN, Color.BLACK, Color.LIGHT_GRAY, Color.WHITE);

        appCombo.setEditor(new AppEditor(appCombo, shortcutHelper));
        appCombo.setRenderer(appRenderer);
        appCombo.setEditable(true);
        appCombo.setPreferredSize(new Dimension(this.getWidth(), 40));
        appCombo.setSize(this.getWidth(),55);


        hideArrowButton(appCombo);

        appCombo.setBounds(0, 0, this.getWidth() / 2, compHeight);
        shortcutQueryField.setBounds(this.getWidth() / 2, 0, this.getWidth() /2 , compHeight);

        shortcutList = new JList(listModel);
        shortcutList.setBounds(0, compHeight, this.getWidth(),this.getHeight());
        shortcutList.setVisible(false);

        shortcutCellRenderer = new ShortcutUIModelRenderer(BorderFactory.createEmptyBorder(), BorderFactory.createLoweredBevelBorder(), Color.BLUE, Color.BLACK, Color.YELLOW, Color.WHITE);
        shortcutList.setCellRenderer(shortcutCellRenderer);
        shortcutList.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);

        contentPane.add(appCombo);
        contentPane.add(shortcutQueryField);
        contentPane.add(shortcutList);


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        resizeToFit();

        AppEditorUIElement editor = (AppEditorUIElement) appCombo.getEditor().getEditorComponent();
        editor.getTextField().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent keyEvent) {

                listModel.removeAllElements();
                shortcutList.revalidate();
                resizeToFit();
                shortcutQueryField.setText("");

            }
        });

        shortcutQueryField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent keyEvent) {
                listModel.removeAllElements();


                if (appCombo.getSelectedItem() == null || shortcutQueryField.getText().trim().length() == 0) {
                    resizeToFit();
                    return;
                }
                String appName = ((AppUIModel) appCombo.getSelectedItem()).getAppName();
                List<ShortcutUIModel> matchedShortcuts = shortcutHelper.getShortcutsForApp(appName, shortcutQueryField.getText());
                if (matchedShortcuts.isEmpty()){
                    resizeToFit();
                    return;
                }

                List<ShortcutUIModel> shortcutsForApp = (matchedShortcuts.size() > MAX_ROWS) ? matchedShortcuts.subList(0, MAX_ROWS) : matchedShortcuts;

                for (ShortcutUIModel shortcutUIModel : shortcutsForApp) {
                    listModel.addElement(shortcutUIModel);
                }
                //shortcutList.removeAll();
                shortcutList.setFixedCellHeight(ITEM_SIZE);
                shortcutList.setFixedCellWidth(contentPane.getWidth());
                shortcutList.setVisibleRowCount(MAX_ROWS);
                shortcutList.setVisible(true);
                shortcutList.revalidate();


                resizeToFit();

            }
        });

    }

    private PopupMenu addPopupMenu() {
        PopupMenu popupMenu = new PopupMenu();
        MenuItem menuItem = new MenuItem("Exit");
        popupMenu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
            System.exit(0);
            }
        });
        return popupMenu;
    }


    private void setupNotificationIcon() {
            Image image = new ImageIcon(new ImageIcon("./icon/appicon.png").getImage()).getImage();
            final TrayIcon trayIcon = new TrayIcon(image);
            trayIcon.setToolTip("Cat4Mouse");
            try {
                SystemTray.getSystemTray().add(trayIcon);
            } catch (AWTException e2) {
                e2.printStackTrace();
            }

            trayIcon.setPopupMenu(addPopupMenu());
            trayIcon.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    setVisible(true);
                }
            });

    }

    private void setupCoreListeners() {
        setupDisposeListener();
        setupActivationListener();
    }

    private void setupActivationListener() {
        GlobalKeyListener.registerGlobalKeyListener();
        GlobalKeyListener.setupGlobalShortcut(this);
    }

    private void setupDisposeListener() {
        KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,
                0, false);
        Action escapeAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        };
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke,
                "ESCAPE");
        getRootPane().getActionMap().put("ESCAPE", escapeAction);
    }

    public void resizeToFit(){
        int size = shortcutList.getModel().getSize();
        shortcutList.setBounds(0, compHeight, this.getWidth(), size * shortcutList.getFixedCellHeight());
        shortcutList.setSize(this.getWidth(), size * shortcutList.getFixedCellHeight());
        setSize(getWidth(), shortcutList.getY() + (size * shortcutList.getFixedCellHeight()));
    }

    public static void main(String args[]){
        MainFrame frm = new MainFrame();
        frm.setLocationRelativeTo(null);
        frm.setUndecorated(true);
        frm.setResizable(false);
        frm.setVisible(true);
    }

    private  void hideArrowButton(JComboBox comboBox) {
        final Component[] components = comboBox.getComponents();
        for(Component comp : components)
        {
            if(comp instanceof JButton)
            {
                ((JButton)comp).setVisible(false);
                break;
            }

        }
    }
}