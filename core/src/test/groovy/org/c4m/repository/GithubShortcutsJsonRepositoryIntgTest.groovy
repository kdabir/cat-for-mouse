package org.c4m.repository

import org.junit.Test
import org.junit.BeforeClass
import org.junit.Ignore

/**
 * badly written but optimizes github api calls :)
 */
class GithubShortcutsJsonRepositoryIntgTest {

    static ShortcutsJsonRepository githubRepo = new GithubShortcutsJsonRepository();

    static List fileNames

    @BeforeClass
    static void loadFileNames () {
        fileNames  = []
        //fileNames.addAll(githubRepo.fileNames)
    }

    @Test @Ignore // for travis
    void testGetFileNames() {
        println fileNames
        assert fileNames.size() > 0
    }

    @Test @Ignore
    void testGetJsonContent() {
        def content = githubRepo.getJsonContent(fileNames[0])
        println content
        assert content.size() > 0
    }
}
