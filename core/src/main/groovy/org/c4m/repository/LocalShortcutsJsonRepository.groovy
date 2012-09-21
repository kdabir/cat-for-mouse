package org.c4m.repository

import groovy.json.JsonSlurper

/**
 * 
 */
class LocalShortcutsJsonRepository implements ShortcutsJsonRepository {

    def baseDir = "./shortcuts"
    def imageBaseDir="./images"

    LocalShortcutsJsonRepository() {
    }

    LocalShortcutsJsonRepository(String baseDir, String imageBaseDir) {
        this.baseDir = baseDir
        this.imageBaseDir=imageBaseDir
    }

    @Override
    Set getFileNames() {
        def root = new File(baseDir)
        def names = [] as Set

        root.eachFile { file ->
            if (file.isFile() && file.name =~/.*\.json/) {
                names << file.name
            }
        }
        return names
    }

    Set getImageFileNames(){
        def root = new File(imageBaseDir)
        def names = [] as Set<String>

        root.eachFile { file ->
            if (file.isFile()) {
                names << file.name
            }
        }
        return names
    }

    @Override
    Map getJsonContent(String fileName) {
        def file = new File("${baseDir}/${fileName}")
        new JsonSlurper().parseText(file.text)
    }
}
