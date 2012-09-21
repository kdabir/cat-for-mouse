package org.c4m.service

import org.c4m.repository.ShortcutsJsonRepository

class ShortcutService {

    private final ShortcutsJsonRepository repository;
    //private final Map appToFileNameMappings = [:]

    ShortcutService(ShortcutsJsonRepository repository ) {
        this.repository = repository;
    }

    /**
     *
     * @return collection of all the supported apps
     */
	def getAllApps(){
        // TODO cache this, so that need not do processing as well as repo call
        repository.getFileNames().collect {String fileName -> fileName.replaceAll(".json","")}
	}

    def getAppIconsFor(String appName){
        def result
        repository.getImageFileNames().each {String fileName ->
            if(fileName.startsWith(appName)){
                result = fileName
            }
        }
        return result;
    }

    def getAppsFor(Platform platform){
        def result = [] as Set
        repository.getFileNames().each { String fileName ->
            def json  = repository.getJsonContent(fileName)
            if (containsMappingFor(json,platform)){
                result <<  fileName.replaceAll(".json","")
            }
        }
        return result;
    }

    // TODO -- check performance
    private boolean containsMappingFor(Map json, Platform platform) {
        json.any {context, actions->
            actions.any { action, shortcuts ->
                try {shortcuts.containsKey(platform.value)}
                catch (e) {
                    println(action)
                    println(shortcuts)
                    e.printStackTrace()
                }
            }
        }
    }


    Map getShortcutsFor(String application, Platform platform) {
        def json = repository.getJsonContent(getJsonFileNameFor(application))
        def out = [:]
        json.each {context, actions->
            actions.each { action, shortcuts->
                if (shortcuts.containsKey(platform.value)) {
                    out [action] = [
                            'context': context,
                            'shortcut' : shortcuts.get(platform.value)
                    ]
                }
            }
        }
        return out
    }

    /*
     * TODO -- 1. to support mapping multiple application names to same file name
     * TODO -- 2. to support nested directory structure in the filename
     */
    private String getJsonFileNameFor(String application){
        //appToFileNameMappings[application]
        "${application}.json"
    }
}