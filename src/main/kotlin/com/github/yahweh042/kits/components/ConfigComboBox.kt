package com.github.yahweh042.kits.components

import com.intellij.openapi.ui.ComboBox
import java.awt.FlowLayout
import java.awt.Label
import javax.swing.JPanel

class ConfigComboBox(configNames: Set<String>) {

    private var mPanel: JPanel
    private var mComboBox: ComboBox<String>

    init {
        mPanel = JPanel(FlowLayout(FlowLayout.LEFT))
        mPanel.add(Label("Theme Config:"))
        mComboBox = ComboBox(configNames.toTypedArray())
        mPanel.add(mComboBox)
    }

    fun getPanel() = mPanel
}
