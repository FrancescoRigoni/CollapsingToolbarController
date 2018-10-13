package com.codebutchery.blockcollapsingtoolbar

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val controller = CollapsingToolbarController(appBarLayout)

        controller.onExpandAndCollapse { expanded ->
            btCollapseExpand.text = if (expanded) "Collapse" else "Expand"
        }

        checkBoxDisableDragToolbar.setOnCheckedChangeListener { _, isChecked ->
            controller.expandAndCollapseByDraggingToolbar(!isChecked)
        }

        checkBoxDisableScrolling.setOnCheckedChangeListener { _, isChecked ->
            controller.expandAndCollapseByDraggingContent(!isChecked)
        }

        btCollapseExpand.setOnClickListener { _ ->
            controller.toggleExpandedState()
        }
    }
}
