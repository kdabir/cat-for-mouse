package org.c4m.service;


import org.junit.Test
import org.c4m.repository.ShortcutsJsonRepository

import groovy.json.JsonSlurper

import static org.junit.Assert.fail

/**
 *
 */
public class ShortcutServiceTest {

    static final Map SHORTCUTS = [
        "chrome.json" : new JsonSlurper().parseText("""{
            "Tabs": {
                "Reopens the last tab you've closed": {
                    "mac": "\\u2318-Shift-T",
                    "win": "Ctrl+Shift+T",
                    "linux": "Ctrl+Shift+T"
                },
                "Hides all other windows.": {
                    "mac": "\\u2318-Option-H"
                },
                "Returns the tab to its original position.": {
                    "win": "Press Esc while dragging a tab.",
                    "linux": "Press Esc while dragging a tab."
                },
                "Maximizes or minimizes the window.": {
                    "win": "Double-click the blank area on the tab strip.",
                    "linux": "Double-click the blank area on the tab strip."
                },
            }
        }"""),

        "gmail.json" : new JsonSlurper().parseText("""{
            "Navigation": {
                "Back to threadlist": { "mac": "u"},
                "Newer/older conversation": { "mac": "k / j", "win": "k/j" },
                "Open conversation; collapse/expand conversation": { "mac": "o or <Enter>", "win": "o or <Enter>" },
            },
            "Application": {
                "Compose": { "mac": "c"},
                "Search mail": { "mac": "/"},
            }
        }"""),

        "finder.json" : new JsonSlurper().parseText("""{
            "Finder Shortcuts":{
                "Select all items in the front Finder window (or desktop if no window is open)":{
                    "mac":"Command-A"
                },
                "Deselect all items":{
                    "mac":"Option-Command-A"
                },
                "Open the Applications folder":{
                    "mac":"Shift-Command-A"
                },
            }
        }""")
    ]

    static final ShortcutsJsonRepository STUBBED_REPOSITORY = new ShortcutsJsonRepository() {

        public Set getFileNames() {
            SHORTCUTS.keySet()
        }

        public Map getJsonContent(String fileName) throws FileNotFoundException {
            if (!(fileName in getFileNames())) throw new FileNotFoundException(fileName)
            return SHORTCUTS[fileName]
        }
    }

    @Test
    public void shouldReturnFileNames() throws Exception {
        assert STUBBED_REPOSITORY.fileNames.size() == 3
    }

    @Test
    public void checkContent() throws Exception {
        assert STUBBED_REPOSITORY.getJsonContent("chrome.json")["Tabs"].size() == 4
    }

    @Test(expected = FileNotFoundException.class)
    public void shouldThrowException() throws Exception {
        STUBBED_REPOSITORY.getJsonContent("firefox.json")
        fail()
    }


    ShortcutService shortcutService = new ShortcutService(STUBBED_REPOSITORY)

    @Test
    public void testGetAllApps() throws Exception {
        assert shortcutService.getAllApps().size() == 3
    }

    @Test
    public void shouldStripExtensionFromFileName() throws Exception {
        assert shortcutService.getAllApps().grep{it.endsWith('.json')}.size() == 0
        println shortcutService.allApps
    }


    @Test
    public void testGetAppsFor() throws Exception {
        assert shortcutService.getAppsFor(Platform.Linux).size() == 1
        assert shortcutService.getAppsFor(Platform.Windows).size() == 2
        assert shortcutService.getAppsFor(Platform.MacOSX).size() == 3
    }

    @Test
    public void testShortcutsFor() throws Exception {
        assert shortcutService.getShortcutsFor('chrome',Platform.Linux).size() == 3
        assert shortcutService.getShortcutsFor('finder',Platform.Linux).size() == 0
        assert shortcutService.getShortcutsFor('gmail',Platform.Windows).size() == 2
    }


}



