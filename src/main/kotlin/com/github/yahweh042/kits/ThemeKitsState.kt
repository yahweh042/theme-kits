package com.github.yahweh042.kits

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

@State(name = "ThemeKitsState", storages = [Storage("theme_kits_config.xml")])
class ThemeKitsState : PersistentStateComponent<ThemeConfigSettings> {

    private val themeConfigSettings = ThemeConfigSettings()

    override fun getState(): ThemeConfigSettings {
        return themeConfigSettings
    }

    override fun loadState(state: ThemeConfigSettings) {
        XmlSerializerUtil.copyBean(state, themeConfigSettings)
    }

    companion object {

        @JvmStatic
        fun getInstance(): ThemeKitsState =
            ApplicationManager.getApplication().getService(ThemeKitsState::class.java)

    }
}
