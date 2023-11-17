package com.github.yahweh042.kits.components

import com.intellij.json.JsonLanguage
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.EditorFactory
import com.intellij.openapi.editor.event.DocumentEvent
import com.intellij.openapi.editor.event.DocumentListener
import org.jetbrains.plugins.notebooks.visualization.addEditorDocumentListener
import java.awt.BorderLayout
import javax.swing.JPanel

class EditorPanel {

    private var mEditor: Editor
    private var mPanel: JPanel = JPanel(BorderLayout())

    init {
        val editorFactory = EditorFactory.getInstance()
        val document = editorFactory.createDocument("")
        mEditor = editorFactory.createEditor(document)
        mEditor.settings.setLanguageSupplier {
            JsonLanguage.INSTANCE
        }
        mEditor.addEditorDocumentListener(object : DocumentListener {
            override fun documentChanged(event: DocumentEvent) {
                // file?.changeConfig(mEditor.document.text)
            }
        })
        mPanel.add(mEditor.component)
    }

    fun getPanel() = mPanel

}
