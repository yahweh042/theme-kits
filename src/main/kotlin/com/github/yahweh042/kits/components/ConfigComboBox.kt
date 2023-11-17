package com.github.yahweh042.kits.components

import com.intellij.openapi.ui.ComboBox
import com.intellij.ui.components.JBLabel
import java.awt.FlowLayout
import javax.swing.JPanel

class ConfigComboBox(configNames: Set<String>, defaultSelectedItem: String) {

    private val mPanel: JPanel = JPanel(FlowLayout(FlowLayout.LEFT, 1, 1))
    private var mComboBox: ComboBox<String>

    init {
        mPanel.add(JBLabel("Theme Config:"))
        mComboBox = ComboBox<String>(configNames.toTypedArray())
        mComboBox.selectedItem = defaultSelectedItem
        mComboBox.addItemListener {

        }
        mPanel.add(mComboBox)
    }

    fun getPanel() = mPanel
}
