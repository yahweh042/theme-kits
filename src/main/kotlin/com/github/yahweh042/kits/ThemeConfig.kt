package com.github.yahweh042.kits


data class ThemeConfigSettings(
    var selectedConfig: String = "internal",
    val configs: Map<String, ThemeConfig> = hashMapOf(Pair("internal", ThemeConfig("internal", """
        {
            "Button.arc": 5
        }
    """.trimIndent())))
)

data class ThemeConfig(
    var name: String,
    var content: String
)
