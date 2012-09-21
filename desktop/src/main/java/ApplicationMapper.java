import org.c4m.repository.LocalShortcutsJsonRepository;
import org.c4m.service.Platform;
import org.c4m.service.ShortcutService;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class ApplicationMapper {

    private static final ShortcutService service = new ShortcutService(new LocalShortcutsJsonRepository("shortcuts", "images"));
    private static String[] apps=null;

    public ApplicationMapper() {
        getAppNames();
    }

    private static String[] getAppNames() {
        Object[] appNames = ((LinkedHashSet) service.getAppsFor(Platform.MacOSX)).toArray();
        apps = Arrays.asList(appNames).toArray(new String[appNames.length]);
        return apps;
    }


    public static Map<String,String> getShortcutsForApp(String appName){
        Map<String,Map<String,String>> shortcutMap = service.getShortcutsFor(appName, Platform.MacOSX);
        Map<String,String> retMap = new HashMap<String,String>();

        for (String key : shortcutMap.keySet()) {
            retMap.put(key, shortcutMap.get(key).get("shortcut"));
        }
        return retMap;
    }

    public static String[] getApps() {
        return (apps==null)? getAppNames() :apps;
    }

    public static ImageIcon[] getAppIcons(){
        ImageIcon[] imageIcons = new ImageIcon[apps.length];
        for (int i = 0; i < apps.length; i++) {
            imageIcons[i] = createImageIcon(getAppIconsFor(apps[i]));
            if (imageIcons[i] != null) {
                imageIcons[i].setDescription(apps[i]);
            }
        }
        return imageIcons;
    }

    protected static ImageIcon createImageIcon(String path) {
        ImageIcon icon = null;
        try {
            icon = new ImageIcon(new File("./images/"+path).toURL());
        } catch (MalformedURLException e) {

        }
        Image image = icon.getImage();
        return new ImageIcon(image.getScaledInstance(20, 20, Image.SCALE_SMOOTH));
    }

    public static String getAppIconsFor(String appName) {
       return (String) service.getAppIconsFor(appName);
    }
}
