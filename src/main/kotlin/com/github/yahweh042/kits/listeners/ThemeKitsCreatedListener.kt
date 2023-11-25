package com.github.yahweh042.kits.listeners

import com.github.yahweh042.kits.ThemeKitsState
import com.intellij.ide.AppLifecycleListener
import com.intellij.ide.ui.LafManager
import com.intellij.ide.ui.UITheme
import com.intellij.openapi.application.ApplicationManager
import javax.swing.UIManager

internal class ThemeKitsCreatedListener : AppLifecycleListener {

    override fun appFrameCreated(commandLineArgs: MutableList<String>) {
        super.appFrameCreated(commandLineArgs)

        val items = ThemeKitsState.getInstance().state.items
        items.forEach {
            val parseValue = UITheme.parseValue(it.key, it.value)
            UIManager.getDefaults().remove(it.key)
            UIManager.put(it.key, parseValue)
        }
        ApplicationManager.getApplication().invokeLater {
            LafManager.getInstance().repaintUI()
        }

    }

}
