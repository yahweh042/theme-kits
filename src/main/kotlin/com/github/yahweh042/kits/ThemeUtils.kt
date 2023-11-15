package com.github.yahweh042.kits

import com.intellij.ide.ui.LafManager
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import javax.swing.UIManager

object ThemeUtils {

    fun changeThemeConfig(themeConfig: ThemeConfig) {
        val jsonObject = Json.parseToJsonElement(themeConfig.content).jsonObject
        val defaults = UIManager.getDefaults()
        for (entry in jsonObject.entries) {
            defaults[entry.key] = entry.value
        }
        LafManager.getInstance().updateUI()
    }

}
