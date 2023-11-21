package com.github.yahweh042.kits

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.EditorFactory
import com.intellij.openapi.options.ConfigurationException
import com.intellij.openapi.options.SearchableConfigurable
import com.intellij.ui.dsl.builder.bindItem
import com.intellij.ui.dsl.builder.panel
import javax.swing.JComponent


class ThemeKitsConfigurable : SearchableConfigurable {

    private val themeKitsState = ThemeKitsState.getInstance().state
    private var mEditor: Editor

    init {
        val editorFactory = EditorFactory.getInstance()
        val document =
            editorFactory.createDocument(themeKitsState.configs[themeKitsState.selectedConfig]?.content ?: "")
        mEditor = editorFactory.createEditor(document)
        // mEditor.addEditorDocumentListener(object : DocumentListener {
        //     override fun documentChanged(event: DocumentEvent) {
        //         themeKitsState.configs[themeKitsState.selectedConfig]?.content = event.document.text
        //     }
        // })
    }

    override fun getDisplayName(): String {
        return "ThemeKits"
    }

    override fun getId(): String {
        return "ThemeKits"
    }

    override fun createComponent(): JComponent {
        // val panel = JPanel(BorderLayout())
        // val configComboBox = ConfigComboBox(themeKitsState.configs.keys, themeKitsState.selectedConfig)
        // configComboBox.setOnSelectedItemChanged {
        //     onSelectedConfigChanged(it)
        // }
        // panel.add(configComboBox.getPanel(), BorderLayout.NORTH)
        // panel.add(mEditor.component, BorderLayout.CENTER)
        return panel {
            row {
                comboBox(themeKitsState.configs.keys).label("Theme config")
                    .bindItem(
                        getter = { themeKitsState.selectedConfig },
                        setter = { themeKitsState.selectedConfig = it ?: "internal" })
                    .applyToComponent {
                        addItemListener {
                            onSelectedConfigChanged(it.item as String)
                        }
                    }

            }
            row {
                cell(mEditor.component)
            }
        }
    }

    private fun onSelectedConfigChanged(selectedConfig: String) {
        themeKitsState.configs[selectedConfig]?.let {
            mEditor.document.setText(it.content)
        }
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
