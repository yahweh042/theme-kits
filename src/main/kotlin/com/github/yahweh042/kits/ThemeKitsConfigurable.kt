package com.github.yahweh042.kits

import com.github.yahweh042.kits.components.ConfigComboBox
import com.github.yahweh042.kits.components.EditorPanel
import com.intellij.openapi.options.ConfigurationException
import com.intellij.openapi.options.SearchableConfigurable
import java.awt.BorderLayout
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JSeparator


class ThemeKitsConfigurable : SearchableConfigurable {

    val themeKitsState = ThemeKitsState.getInstance().state
    override fun getDisplayName(): String {
        return "ThemeKits"
    }

    override fun getId(): String {
        return "ThemeKits"
    }

    override fun createComponent(): JComponent {
        val panel = JPanel(BorderLayout())
        panel.add(ConfigComboBox(themeKitsState.configs.keys).getPanel())
        panel.add(JSeparator())
        return panel
    }

    override fun isModified(): Boolean {
        return false
    }

    @Throws(ConfigurationException::class)
    override fun apply() {
    }


}
