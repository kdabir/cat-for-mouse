package org.c4m.repository

/**
 * 
 */
class LocalShortcutsJsonRepositoryIntgTest extends GroovyTestCase {
    // todo --- find more elegant solution for this
    // a bad hack to get shortcuts dir when run from root project and from subproject
    private def getShortcutsDir () {
        if (new File("./shortcuts").exists()) return "shortcuts"
        if (new File("../shortcuts").exists()) return "../shortcuts"
        throw new RuntimeException("This test can be run either from root project dir or subproject dir only")
    }

    LocalShortcutsJsonRepository repository = new LocalShortcutsJsonRepository(getShortcutsDir())

    void testGetFileNames() {
        assert repository.fileNames.size() > 4
    }

    void testGetJsonContent() {
        def x = repository.fileNames as List
        assert repository.getJsonContent(x[0]).size() > 0
    }
}
