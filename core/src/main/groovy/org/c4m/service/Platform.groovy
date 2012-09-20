package org.c4m.service

/**
 * 
 */
public enum Platform {
    MacOSX("mac"),
    Windows("win"),
    Linux("linux")

    final String value;

    Platform(String value) {
        this.value = value
    }

    @Override
    public String toString() {
        this.value
    }
}