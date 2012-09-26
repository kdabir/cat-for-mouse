package org.c4m.ui.support.helpers;

import org.c4m.service.Platform;


public class OSHelper {

    public Platform getPlatform(){
        final String osName = System.getProperty("os.name").toLowerCase();
        if (isWindows(osName)){
            return Platform.Windows;
        }
        if (isMac(osName)){
            return Platform.MacOSX;
        }

        return Platform.Linux;
    }

    private boolean isWindows(String osName) {
        return osName.contains(Platform.Windows.getValue());
    }
    private boolean isMac(String osName) {
        return osName.contains(Platform.MacOSX.getValue());
    }

}
