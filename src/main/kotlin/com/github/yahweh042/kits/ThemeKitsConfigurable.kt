package com.github.yahweh042.kits

import com.github.yahweh042.kits.components.ConfigComboBox
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.EditorFactory
import com.intellij.openapi.editor.event.DocumentEvent
import com.intellij.openapi.editor.event.DocumentListener
import com.intellij.openapi.options.ConfigurationException
import com.intellij.openapi.options.SearchableConfigurable
import org.jetbrains.plugins.notebooks.visualization.addEditorDocumentListener
import java.awt.BorderLayout
import javax.swing.JComponent
import javax.swing.JPanel


class ThemeKitsConfigurable : SearchableConfigurable {

    private val themeKitsState = ThemeKitsState.getInstance().state
    private lateinit var mEditor: Editor
    private lateinit var mPanel: JPanel

    init {
        val editorFactory = EditorFactory.getInstance()
        val document =
            editorFactory.createDocument(themeKitsState.configs[themeKitsState.selectedConfig]?.content ?: "")
        mEditor = editorFactory.createEditor(document)
        mEditor.addEditorDocumentListener(object : DocumentListener {
            override fun documentChanged(event: DocumentEvent) {
                themeKitsState.configs[themeKitsState.selectedConfig]?.content = event.document.text
            }
        })
    }

    override fun getDisplayName(): String {
        return "ThemeKits"
    }

    override fun getId(): String {
        return "ThemeKits"
    }

    override fun createComponent(): JComponent {
        val panel = JPanel(BorderLayout())
        panel.add(
            ConfigComboBox(themeKitsState.configs.keys, themeKitsState.selectedConfig).getPanel(),
            BorderLayout.NORTH
        )
        panel.add(mEditor.component, BorderLayout.CENTER)
        return panel
    }

    override fun isModified(): Boolean {
        return true
    }

    @Throws(ConfigurationException::class)
    override fun apply() {
        themeKitsState.configs[themeKitsState.selectedConfig]?.let {
            ThemeUtils.changeThemeConfig(it)
        }
    }


}
