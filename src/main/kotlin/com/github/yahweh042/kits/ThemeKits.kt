package com.github.yahweh042.kits

import com.intellij.util.ui.JBInsets
import java.awt.Insets

object ThemeKits {

    private fun parseNormalValue(value: String): String? {
        return null
    }

    private fun parseInsets(value: String): Insets {
        val numbers = value.split(",").map { it.toInt() }
        return JBInsets(numbers[0], numbers[1], numbers[2], numbers[3]).asUIResource()
    }

    fun parseValue(key: String, value: String): Any? {
        if (key.endsWith("Insets") || key.endsWith(".insets") || key.endsWith("padding")) {
            return parseInsets(value)
        }
        return null
    }

}
