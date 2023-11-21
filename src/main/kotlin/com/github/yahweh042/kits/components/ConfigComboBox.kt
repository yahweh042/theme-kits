package com.github.yahweh042.kits.components

import com.intellij.openapi.ui.ComboBox
import com.intellij.ui.components.JBLabel
import java.awt.FlowLayout
import javax.swing.JPanel

class ConfigComboBox(configNames: Set<String>, defaultSelectedItem: String) {

    private val mPanel: JPanel = JPanel(FlowLayout(FlowLayout.LEFT, 5, 5))
    private var mComboBox: ComboBox<String>
    private var onSelectedItemChanged: ((String) -> Unit)? = null

    init {
        mPanel.add(JBLabel("Theme config:"))
        mComboBox = ComboBox<String>(configNames.toTypedArray())
        mComboBox.selectedItem = defaultSelectedItem
        mComboBox.addItemListener {
            onSelectedItemChanged?.invoke(mComboBox.selectedItem as String)
        }
        mPanel.add(mComboBox)
    }

    fun getPanel() = mPanel

    fun setOnSelectedItemChanged(listener: (String) -> Unit) {
        onSelectedItemChanged = listener
    }
}
