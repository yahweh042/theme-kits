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
import javax.swing.ListModel

class OneConfigurable : SearchableConfigurable {

    private val state = ThemeKitsState.getInstance().state
    private val originItems = state.themeJsons

    private lateinit var mainPanel: JPanel
    private lateinit var leftPanel: JPanel
    private lateinit var themeNameList: JBList<String>

    override fun createComponent(): JComponent {
        mainPanel = JPanel(BorderLayout(5, 5))
        initLeftPanel()
        val document = EditorFactory.getInstance().createDocument("")
        val editor = EditorFactory.getInstance().createEditor(document)
        (editor as EditorEx).highlighter =
            EditorHighlighterFactory.getInstance().createEditorHighlighter(null, "demo.theme.json");
        editor.settings.isIndentGuidesShown = true
        mainPanel.add(editor.component, BorderLayout.CENTER)
        return mainPanel
    }

    private fun initLeftPanel() {
        leftPanel = JPanel(BorderLayout())
        leftPanel.setBorder(CustomLineBorder(1, 1, 1, 1))
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
        leftPanel.add(toolbar.component, BorderLayout.NORTH)
        initThemeNameList()
        mainPanel.add(leftPanel, BorderLayout.WEST)
    }

    private fun initThemeNameList() {
        val listItems = getListItems()
        themeNameList = JBList(listItems)
        themeNameList.selectedIndex = listItems.indexOf(state.selectedThemeName)
        leftPanel.add(themeNameList, BorderLayout.CENTER)
    }

    private fun refreshUI() {
        themeNameList.model = JBList.createDefaultListModel(getListItems())
    }

    private fun getListItems() = originItems.keys.toList()

    override fun isModified(): Boolean {
        return false
    }

    override fun apply() {
    }

    override fun getDisplayName() = "One"

    override fun getId() = "One"
}
