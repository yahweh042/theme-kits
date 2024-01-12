package com.github.yahweh042.kits.listeners

import com.github.yahweh042.kits.ThemeKitsState
import com.intellij.ide.AppLifecycleListener
import com.intellij.ide.ui.LafManager
import com.intellij.ide.ui.UITheme
import com.intellij.openapi.application.ApplicationManager
import org.jdesktop.swingx.plaf.UIManagerExt
import java.util.UUID
import java.util.function.Function
import javax.swing.UIDefaults
import javax.swing.UIManager

internal class ThemeKitsCreatedListener : AppLifecycleListener {

    override fun appFrameCreated(commandLineArgs: MutableList<String>) {
        super.appFrameCreated(commandLineArgs)
        // applyCustomProperty()
        UIManager.addPropertyChangeListener {
            if (it.propertyName == "lookAndFeel") {
                // applyCustomProperty()
            }
        }

        ApplicationManager.getApplication().invokeAndWait {

            val themeContent = """
                
                {
                    "ui": {
                        "Button.arc": 0
                    }
                }
                
            """.trimIndent()


            val uiTheme = UITheme.loadFromJson(
                themeContent.encodeToByteArray(),
                UUID.randomUUID().toString(),
                null,
                Function.identity()
            )

            uiTheme.applyProperties(UIManager.getDefaults())


        }
    }

    private fun applyCustomProperty() {
        ApplicationManager.getApplication().invokeAndWait {
            ThemeKitsState.getInstance().state.items.forEach { (key, value) ->
                val parseValue = UITheme.parseValue(key, value)
                UIManager.getDefaults().remove(key)
                UIManager.put(key, parseValue)
            }
            LafManager.getInstance().repaintUI()
        }
    }

}
