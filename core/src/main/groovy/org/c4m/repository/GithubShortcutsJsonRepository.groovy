package org.c4m.repository

import groovy.json.JsonSlurper

/**
 *
 * This one uses github api v3
 * some implementation can use direct get from eg. https://raw.github.com/kdabir/cat-for-mouse/master/shortcuts/chrome.json
 *
 */
class GithubShortcutsJsonRepository implements ShortcutsJsonRepository {

    def baseUrl = "https://api.github.com/repos/kdabir/cat-for-mouse/contents/shortcuts"

    GithubShortcutsJsonRepository() {
    }

    GithubShortcutsJsonRepository(String baseUrl) {
        this.baseUrl = baseUrl
    }

    @Override
    Set getFileNames() {
        def url = new URL(baseUrl)
        def names = [] as Set

        def items = new groovy.json.JsonSlurper().parseText(url.text)
        items.each { item ->
            if (item.type == 'file' && item.name =~/.*\.json/) {
                names << item.name
            }
        }
        return names
    }

    @Override
    Set getImageFileNames() {
        return null  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    Map getJsonContent(String fileName) {
        def url = new URL("${baseUrl}/${fileName}")

        def response = new groovy.json.JsonSlurper().parseText(url.text)
        byte[] decoded = response.content.decodeBase64()
        def content = new String(decoded)

        new JsonSlurper().parseText(content)
    }
}
