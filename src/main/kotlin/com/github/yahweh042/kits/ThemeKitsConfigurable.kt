package com.github.yahweh042.kits

import com.intellij.ide.ui.LafManager
import com.intellij.ide.ui.UITheme
import com.intellij.openapi.actionSystem.ActionToolbarPosition
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.options.SearchableConfigurable
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.util.Pair
import com.intellij.ui.ToolbarDecorator
import com.intellij.ui.components.JBTextField
import com.intellij.ui.table.JBTable
import com.intellij.util.ui.UI
import com.intellij.util.ui.UIUtil
import java.awt.*
import java.io.Serial
import java.util.*
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JTable
import javax.swing.UIManager
import javax.swing.border.EmptyBorder
import javax.swing.table.DefaultTableCellRenderer
import javax.swing.table.DefaultTableModel


class ThemeKitsConfigurable : SearchableConfigurable {

    private val themeKitsState = ThemeKitsState.getInstance().state

    private lateinit var mTable: JBTable
    private lateinit var mSearchField: JBTextField
    private lateinit var mOriginValues: MutableMap<String, String>
    private var isModified = false

    override fun getDisplayName(): String {
        return "ThemeKits"
    }

    override fun getId(): String {
        return "ThemeKits"
    }

    private fun getUIDefaultsData(): Array<Array<Any>> {
        val data = Array(mOriginValues.size) {
            arrayOf<Any>(0, 1)
        }
        var i = 0;
        mOriginValues.forEach { (k, v) ->
            val parseValue = UITheme.parseValue(k, v)
            data[i][0] = k
            data[i][1] = parseValue
            i++
        }
        return data
    }

    private fun createTableModel(): DefaultTableModel {
        return object : DefaultTableModel(getUIDefaultsData(), arrayOf("Name", "Value")) {
            @Serial
            private val serialVersionUID: Long = 4410430065367899038L

            override fun isCellEditable(row: Int, column: Int): Boolean {
                if (column != 1) return false
                val value = (getValueAt(row, column) as Pair<*, *>).second
                return isEditable(value)
            }
        }
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

        val columns = Vector<String>(2)
        columns.add("Name")
        columns.add("Value")

        mTable = object : JBTable(createTableModel()) {

        }

        mTable.setDefaultRenderer(Any::class.java, object : DefaultTableCellRenderer() {
            override fun getTableCellRendererComponent(
                table: JTable,
                value: Any,
                isSelected: Boolean,
                hasFocus: Boolean,
                row: Int,
                column: Int
            ): Component {
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column)
            }
        })

        val centerPanel = ToolbarDecorator.createDecorator(mTable)
            .setToolbarPosition(ActionToolbarPosition.BOTTOM)
            .setAddAction {
                addNewValue()
            }
            .setRemoveAction {
                val selectedRow = mTable.selectedRow
                if (selectedRow == 1) {
                    return@setRemoveAction
                }
                val first = (mTable.getValueAt(selectedRow, 0) as Pair<*, *>).first
                mOriginValues.remove(first)
                isModified = true
            }
            .createPanel()

        panel.add(centerPanel, BorderLayout.CENTER)
        return panel
    }

    private fun addNewValue() {
        ApplicationManager.getApplication().invokeLater(object : DialogWrapper(mTable, true) {
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
                        mTable.setModel(createTableModel())
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
