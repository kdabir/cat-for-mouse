package org.c4m.ui.support.helpers;

import com.strsrch.main.FuzzyStringComparator;
import com.strsrch.model.SrchResult;
import org.c4m.repository.LocalShortcutsJsonRepository;
import org.c4m.service.Platform;
import org.c4m.service.ShortcutService;
import org.c4m.ui.model.AppUIModel;
import org.c4m.ui.model.ShortcutUIModel;

import javax.swing.*;
import java.io.File;
import java.util.*;


public class ShortcutHelper {
    private  final ShortcutService service;
    private Platform platform;
    private List<AppUIModel> appList;
    private static String baseDir = System.getProperty("user.dir");
    private FuzzyStringComparator comparator;
    private final String imageBaseDir;

    public ShortcutHelper(){
        String parentPath = new File(System.getProperty("user.dir")).getParent();
        imageBaseDir = parentPath + "/images/";
        service = new ShortcutService(new LocalShortcutsJsonRepository(parentPath +"/shortcuts", imageBaseDir));
        platform = new OSHelper().getPlatform();
        comparator = new FuzzyStringComparator();
        initAppList();

    }

    public List<AppUIModel> getAppList(){
        return this.appList;
    }

    public List<AppUIModel> getAppList(String filterCriteria){
        filterCriteria = filterCriteria.toLowerCase().trim();
        List<AppUIModel> filteredList = new ArrayList<AppUIModel>();
        for (AppUIModel appUIModel : appList) {
           if (appUIModel.getAppName().toLowerCase().contains(filterCriteria)){
               filteredList.add(appUIModel);
           }
        }
        return filteredList;
    }

    private void initAppList(){
        String[] strAppNames = getRawAppList();
        appList = new ArrayList<AppUIModel>();
        for (String strAppName : strAppNames) {
            appList.add(new AppUIModel(new ImageIcon(getAppIconsFor(strAppName)), strAppName));
        }
    }

    private String[] getRawAppList() {
        Object[] appNames = ((LinkedHashSet) service.getAppsFor(platform)).toArray();
        return Arrays.asList(appNames).toArray(new String[appNames.length]);
    }

    private String getAppIconsFor(String appName) {
        System.out.println(imageBaseDir);
        return (String) imageBaseDir + service.getAppIconsFor(appName);
    }


    public List<ShortcutUIModel> getShortcutsForApp(String appName,String userInput) {
        Map<String,Map<String,String>> shortcutMap = service.getShortcutsFor(appName, platform);
        List<ShortcutUIModel> retList = new ArrayList<ShortcutUIModel>();

        Set<String> actionsSet = shortcutMap.keySet();
        List<String> actionsList = Arrays.asList(actionsSet.toArray(new String[actionsSet.size()]));

        List<SrchResult> srchResults = comparator.fuzzyCompareAndRank(actionsList, userInput);

        for (SrchResult srchResult : srchResults) {
            retList.add(new ShortcutUIModel(srchResult.getData(), shortcutMap.get(srchResult.getData()).get("shortcut")));

        }
        return retList;
    }
}
