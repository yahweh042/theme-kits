package com.github.yahweh042.kits.toolWindow

import com.github.yahweh042.kits.services.MyProjectService
import com.intellij.application.options.colors.FontEditorPreview
import com.intellij.openapi.components.service
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.editor.colors.impl.DefaultColorsScheme
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory
import com.intellij.ui.dsl.builder.panel


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

        fun getContent() = panel {

            // row {
            //     val editorFactory = EditorFactory.getInstance()
            //     val document = editorFactory.createDocument("")
            //     val editor = editorFactory.createEditor(document, toolWindow.project, JsonFileType.INSTANCE, false)
            //     val component = editor.component
            //     scrollCell(component)
            // }

            twoColumnsRow(
                {
                    intTextField()
                },
                {
                    cell(FontEditorPreview({ DefaultColorsScheme() }, false).panel)
                }
            )
        }
    }
}
