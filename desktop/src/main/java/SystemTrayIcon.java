import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Properties;

public class SystemTrayIcon {
    public void setup() {
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
                Cat4Mouse.makeFrameVisible();
            }
        });

        PopupMenu popupMenu = addPopupMenu();
        trayIcon.setPopupMenu(popupMenu);

    }

    private PopupMenu addPopupMenu() {
        PopupMenu popupMenu = new PopupMenu();
        MenuItem menuItem = new MenuItem("Preferences");
        popupMenu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (actionEvent.getActionCommand().contains("Preferences")) {
                    final JFrame frame = new JFrame("Cat4Mouse");
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                    JComponent newContentPane = new JPanel();
                    newContentPane.setOpaque(true);

                    addComponentsTo(newContentPane);
                    frame.setContentPane(newContentPane);
                    frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                    frame.setPreferredSize(new Dimension(700, 500));
                    frame.setLocation(392, 451);
                    frame.setAlwaysOnTop(true);
                    frame.pack();
                    frame.setVisible(true);
                }

            }
        });
        return popupMenu;
    }

    private GridBagConstraints positionElement(int weightx, int gridx, int gridy) {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = weightx;
        c.gridx = gridx;
        c.gridy = gridy;
        return c;
    }

    private void addComponentsTo(JComponent component) {
        final String appNames[] = {"Intellij Idea", "Eclipse"};
        component.setLayout(new GridBagLayout());
        File f = new File("test2.properties");
        final Properties properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream(f);
            properties.load(fileInputStream);
        } catch (IOException e) {
        }
        for (int i = 0; i < appNames.length; i++) {
            component.add(new JLabel(appNames[i]), positionElement(1, 0, i));
            final String versions[] = getVersionForApp();
            ButtonGroup buttonGroup = new ButtonGroup();
            for (int j = 0; j < versions.length; j++) {
                JRadioButton appVersion = new JRadioButton(versions[j]);
                String property = properties.getProperty(appNames[i]);
                if (versions[j].equals(property))
                    appVersion.setSelected(true);
                final int finalI = i;
                final int finalJ = j;
                appVersion.addItemListener(new ItemListener() {
                    @Override
                    public void itemStateChanged(ItemEvent itemEvent) {
                        if (((JRadioButton) itemEvent.getSource()).isSelected()) {
                            properties.setProperty(appNames[finalI], versions[finalJ]);
                            updatePropertiesFileWithSelectedOption(properties);
                        }

                    }
                });
                buttonGroup.add(appVersion);
                component.add(appVersion, positionElement(1, j + 1, i));
            }
        }
    }

    private void updatePropertiesFileWithSelectedOption(Properties properties) {
        try {
            File file = new File("test2.properties");
            FileOutputStream fileOut = new FileOutputStream(file);
            properties.store(fileOut, "Favorite Things");
            fileOut.close();
        } catch (IOException e) {


        }
    }

    private String[] getVersionForApp() {
        return new String[]{"10.5+", "11.0+"};
    }
}
