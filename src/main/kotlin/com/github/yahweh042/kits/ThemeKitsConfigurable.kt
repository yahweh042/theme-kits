package com.github.yahweh042.kits

import com.github.yahweh042.kits.components.PropertyItem
import com.intellij.ide.ui.LafManager
import com.intellij.ide.ui.UITheme
import com.intellij.openapi.actionSystem.ActionToolbarPosition
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.options.SearchableConfigurable
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.ToolbarDecorator
import com.intellij.ui.components.JBTextField
import com.intellij.ui.table.TableView
import com.intellij.util.ui.ColumnInfo
import com.intellij.util.ui.ListTableModel
import com.intellij.util.ui.UI
import com.intellij.util.ui.UIUtil
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Font
import java.awt.Insets
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.UIManager
import javax.swing.border.EmptyBorder


class ThemeKitsConfigurable : SearchableConfigurable {

    private val themeKitsState = ThemeKitsState.getInstance().state

    private lateinit var mTableView: TableView<PropertyItem>
    private lateinit var mSearchField: JBTextField
    private lateinit var mOriginValues: MutableMap<String, String>
    private var isModified = false
    private lateinit var columns: Array<ColumnInfo<PropertyItem, String>>

    override fun getDisplayName(): String {
        return "ThemeKits"
    }

    override fun getId(): String {
        return "ThemeKits"
    }

    private fun getItems(): List<PropertyItem> {
        return mOriginValues.map { (key, value) -> PropertyItem(key, UITheme.parseValue(key, value)) }
    }

    private fun getListTableModel(): ListTableModel<PropertyItem> {
        return ListTableModel(columns, getItems())
    }

    private fun isEditable(value: Any): Boolean {
        return value is Color ||
                value is Boolean ||
                value is Int ||
                value is Float ||
                value is EmptyBorder ||
                value is Insets ||
                value is UIUtil.GrayFilter ||
                value is Font
    }

    override fun createComponent(): JComponent {
        mOriginValues = mutableMapOf()
        mOriginValues.putAll(themeKitsState.items)
        mSearchField = JBTextField(40)
        val top = UI.PanelFactory.panel(mSearchField).withLabel("Search:").createPanel()
        val panel = JPanel(BorderLayout())
        panel.add(top, BorderLayout.NORTH)


        val propertyKeyColumn = object : ColumnInfo<PropertyItem, String>("Key") {
            override fun valueOf(item: PropertyItem): String {
                return item.key
            }
        }

        val propertyValueColumn = object : ColumnInfo<PropertyItem, String>("Value") {
            override fun valueOf(item: PropertyItem): String {
                return item.value.toString()
            }

            override fun isCellEditable(item: PropertyItem): Boolean {
                val value = item.value
                return isEditable(value)
            }
        }

        columns = arrayOf(propertyKeyColumn, propertyValueColumn)
        mTableView = TableView(getListTableModel())
        val centerPanel = ToolbarDecorator.createDecorator(mTableView)
            .setToolbarPosition(ActionToolbarPosition.BOTTOM)
            .setAddAction {
                addNewValue()
            }
            .setRemoveAction {
                val selectedRows = mTableView.selectedRows
                if (selectedRows == null || selectedRows.isEmpty()) {
                    return@setRemoveAction
                }
                for (selectedRow in selectedRows) {
                    val selectedPropertyItem = mTableView.getRow(selectedRow)
                    mOriginValues.remove(selectedPropertyItem.key)
                }
                mTableView.model = getListTableModel()
                isModified = true
            }
            .createPanel()

        panel.add(centerPanel, BorderLayout.CENTER)
        return panel
    }

    private fun addNewValue() {
        ApplicationManager.getApplication().invokeLater(object : DialogWrapper(mTableView, true) {
            val name = JBTextField(40)
            val value = JBTextField(40)

            init {
                title = "Add New Value"
                init()
            }

            override fun createCenterPanel(): JComponent {
                return UI.PanelFactory.grid()
                    .add(UI.PanelFactory.panel(name).withLabel("Name"))
                    .add(UI.PanelFactory.panel(value).withLabel("Value"))
                    .createPanel()
            }

            override fun getPreferredFocusedComponent(): JComponent {
                return name
            }

            override fun doOKAction() {
                val key = name.getText().trim { it <= ' ' }
                val `val` = value.getText().trim { it <= ' ' }
                if (key.isNotEmpty() && `val`.isNotEmpty()) {
                    val parseValue = UITheme.parseValue(key, `val`) ?: return
                    if (isEditable(parseValue)) {
                        mOriginValues[key] = `val`
                        mTableView.model = getListTableModel()
                        isModified = true
                    }
                }
                super.doOKAction()
            }
        }::show)
    }

    override fun isModified(): Boolean {
        return isModified
    }

    override fun apply() {
        themeKitsState.items.clear()
        themeKitsState.items.putAll(mOriginValues)
        mOriginValues.forEach {
            val parseValue = UITheme.parseValue(it.key, it.value)
            UIManager.getDefaults().remove(it.key)
            UIManager.put(it.key, parseValue)
        }
        ApplicationManager.getApplication().invokeLater {
            LafManager.getInstance().repaintUI()
        }
        isModified = false
    }

}
