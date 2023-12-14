package com.github.yahweh042.kits.toolWindow

import com.github.yahweh042.kits.services.MyProjectService
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.components.service
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.editor.EditorFactory
import com.intellij.openapi.editor.ex.EditorEx
import com.intellij.openapi.editor.highlighter.EditorHighlighterFactory
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.border.CustomLineBorder
import com.intellij.ui.components.JBList
import com.intellij.ui.content.ContentFactory
import java.awt.BorderLayout
import javax.swing.JPanel


class ThemeKitsFactory : ToolWindowFactory {

    init {
        thisLogger().warn("Don't forget to remove all non-needed sample code files with their corresponding registration entries in `plugin.xml`.")
    }

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val themeKits = ThemeKits(toolWindow)
        val content = ContentFactory.getInstance().createContent(themeKits.getContent(), null, false)
        toolWindow.contentManager.addContent(content)
    }

    override fun shouldBeAvailable(project: Project) = true

    class ThemeKits(private val toolWindow: ToolWindow) {

        private val service = toolWindow.project.service<MyProjectService>()

        fun getContent(): JPanel {
            val panel = JPanel(BorderLayout(5, 5))
            panel.add(getLeftPanel(), BorderLayout.WEST)
            val document = EditorFactory.getInstance().createDocument("")
            val editor = EditorFactory.getInstance().createEditor(document)
            (editor as EditorEx).highlighter =
                EditorHighlighterFactory.getInstance().createEditorHighlighter(toolWindow.project, "demo.theme.json");
            editor.settings.isIndentGuidesShown = true
            panel.add(editor.component, BorderLayout.CENTER)
            return panel
        }

        fun getLeftPanel(): JPanel {
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
    }
}
