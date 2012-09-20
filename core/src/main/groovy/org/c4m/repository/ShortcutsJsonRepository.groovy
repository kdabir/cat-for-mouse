package org.c4m.repository

/**
 * Abstracts out the Repository (read-only) operations from the implementations details
 * Implementations can make HTTP calls, FileSystem call, Caching and other performance
 * optimizations as needed.
 *
 */

interface ShortcutsJsonRepository {
    /**
     *
     * @return the names of all the available Shortcuts json files
     */
    Set getFileNames()

    /**
     *
     * @param fileName
     * @return the json object in form Map
     * @throws FileNotFoundException
     */
    Map getJsonContent(String fileName) throws FileNotFoundException
}
