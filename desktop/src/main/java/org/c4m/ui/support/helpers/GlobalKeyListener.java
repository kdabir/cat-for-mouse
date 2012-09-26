package org.c4m.ui.support.helpers;

import com.apple.eawt.Application;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.NativeInputEvent;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import javax.swing.*;

public class GlobalKeyListener {

    public static void registerGlobalKeyListener() {
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void setupGlobalShortcut(final JFrame frame) {
        if (!GlobalScreen.isNativeHookRegistered()) return;

        GlobalScreen.getInstance().addNativeKeyListener(new NativeKeyListener() {
            @Override
            public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {

                if (nativeKeyEvent.getModifiers() == NativeInputEvent.ALT_MASK && NativeKeyEvent.BUTTON2_MASK == nativeKeyEvent.getKeyCode()) {
                    frame.setVisible(true);
                    frame.setAlwaysOnTop(true);
                    Application.getApplication().requestForeground(true);
                }
            }

            @Override
            public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
            }

            @Override
            public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {
            }

        });
    }
}
