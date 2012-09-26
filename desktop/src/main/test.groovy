import org.c4m.service.ShortcutService
import org.c4m.repository.LocalShortcutsJsonRepository
import org.c4m.service.Platform

def service = new ShortcutService(new LocalShortcutsJsonRepository("../../../shortcuts", "images"))

println service.allApps

println service.getAppsFor(Platform.Linux)

def shortcuts_for_chrome = service.getShortcutsFor('chrome', Platform.MacOSX)
def actions = shortcuts_for_chrome.keySet() as List

def for_user_selected = shortcuts_for_chrome.get(actions[5])

println """
  action: ${actions[5]}
shortcut: ${for_user_selected.shortcut}
 context: ${for_user_selected.context}
"""



