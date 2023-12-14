package com.github.yahweh042.kits.listeners

import com.intellij.ide.AppLifecycleListener
import com.intellij.ide.ui.LafManager
import com.intellij.ide.ui.UITheme
import com.intellij.openapi.application.ApplicationManager
import java.util.function.Function
import javax.swing.UIManager

internal class ThemeKitsCreatedListener : AppLifecycleListener {

    override fun appFrameCreated(commandLineArgs: MutableList<String>) {
        super.appFrameCreated(commandLineArgs)
        ApplicationManager.getApplication().invokeAndWait {
            // val theme = UITheme.loadFromJson(null, "", ClassLoader.getSystemClassLoader(), Function.identity())
            // theme.applyProperties(UIManager.getDefaults())
            // LafManager.getInstance().repaintUI()
        }
    }

}
