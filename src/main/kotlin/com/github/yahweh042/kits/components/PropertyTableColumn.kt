package com.github.yahweh042.kits.components

import com.intellij.util.ui.ColumnInfo
import com.intellij.util.ui.UIUtil
import java.awt.Color
import java.awt.Font
import java.awt.Insets
import javax.swing.border.EmptyBorder

class PropertyTableColumn(columnName: String) : ColumnInfo<PropertyItem, String>(columnName) {
    override fun valueOf(item: PropertyItem): String {
        return item.key
    }

    override fun isCellEditable(item: PropertyItem): Boolean {
        val value = item.value
        return value is Color ||
                value is Boolean ||
                value is Int ||
                value is Float ||
                value is EmptyBorder ||
                value is Insets ||
                value is UIUtil.GrayFilter ||
                value is Font
    }

}
