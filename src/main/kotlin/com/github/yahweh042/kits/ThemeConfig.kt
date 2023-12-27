package com.github.yahweh042.kits


data class ThemeConfigSettings(
    var items: MutableMap<String, String> = mutableMapOf(),
    var themeJsons: MutableMap<String, String> = mutableMapOf(),
    var selectedThemeName: String? = null
)
