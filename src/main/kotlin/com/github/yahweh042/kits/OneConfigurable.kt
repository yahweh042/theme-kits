package com.github.yahweh042.kits

import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.editor.EditorFactory
import com.intellij.openapi.editor.ex.EditorEx
import com.intellij.openapi.editor.highlighter.EditorHighlighterFactory
import com.intellij.openapi.options.SearchableConfigurable
import com.intellij.ui.border.CustomLineBorder
import com.intellij.ui.components.JBList
import java.awt.BorderLayout
import javax.swing.JComponent
import javax.swing.JPanel

class OneConfigurable : SearchableConfigurable {
    override fun createComponent(): JComponent {
        val panel = JPanel(BorderLayout(5, 5))
        panel.add(getLeftPanel(), BorderLayout.WEST)
        val document = EditorFactory.getInstance().createDocument("")
        val editor = EditorFactory.getInstance().createEditor(document)
        (editor as EditorEx).highlighter =
            EditorHighlighterFactory.getInstance().createEditorHighlighter(null, "demo.theme.json");
        editor.settings.isIndentGuidesShown = true
        panel.add(editor.component, BorderLayout.CENTER)
        return panel
    }

    private fun getLeftPanel(): JPanel {
        val panel = JPanel(BorderLayout())
        panel.setBorder(CustomLineBorder(1, 1, 1, 1))
        val actionGroup = DefaultActionGroup()
        actionGroup.add(object : AnAction(AllIcons.General.Add) {
            override fun actionPerformed(e: AnActionEvent) {
                TODO("Not yet implemented")
            }
        })
        actionGroup.add(object : AnAction(AllIcons.General.Remove) {
            override fun actionPerformed(e: AnActionEvent) {
                TODO("Not yet implemented")
            }
        })
        val toolbar = ActionManager.getInstance().createActionToolbar("Toolbar", actionGroup, true)
        panel.add(toolbar.component, BorderLayout.NORTH)
        val jbList = JBList("internal.theme.json", "material.theme.json")
        panel.add(jbList, BorderLayout.CENTER)
        return panel
    }

    override fun isModified(): Boolean {
        return false
    }

    override fun apply() {
        println("apply")
    }

    override fun getDisplayName() = "One"

    override fun getId() = "One"
}
