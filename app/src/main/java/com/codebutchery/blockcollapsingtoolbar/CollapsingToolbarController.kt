package com.codebutchery.blockcollapsingtoolbar

import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.view.View

class CollapsingToolbarController(val appBarLayout: AppBarLayout) {
    private var isExpanded = true
    private val behavior = AppBarLayoutBehavior()
    private var onStateChangedListener:(Boolean) -> Unit = {}

    init {
        appBarLayout.addOnOffsetChangedListener(
            AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
                isExpanded = verticalOffset == 0
                onStateChangedListener(isExpanded)
            })

        val params = appBarLayout.layoutParams as CoordinatorLayout.LayoutParams
        params.behavior = behavior
        appBarLayout.layoutParams = params
    }

    fun expandAndCollapseByDraggingToolbar(enabled:Boolean) {
        behavior.canDrag = enabled
    }

    fun expandAndCollapseByDraggingContent(enabled:Boolean) {
        behavior.acceptsNestedScroll = enabled
    }

    fun toggleExpandedState() {
        appBarLayout.setExpanded(!isExpanded, true)
    }

    fun onExpandAndCollapse(onStateChangedListener: (Boolean) -> Unit) {
        this.onStateChangedListener = onStateChangedListener
    }
}

private class AppBarLayoutBehavior : AppBarLayout.Behavior() {
    var canDrag = true
    var acceptsNestedScroll = true

    init {
        setDragCallback(object : AppBarLayout.Behavior.DragCallback() {
            override fun canDrag(appBarLayout: AppBarLayout): Boolean {
                // Do not allow dragging down/up to expand/collapse the layout
                return canDrag
            }
        })
    }

    override fun onStartNestedScroll(parent: CoordinatorLayout,
                                     child: AppBarLayout,
                                     directTargetChild: View,
                                     target: View,
                                     nestedScrollAxes: Int,
                                     type: Int): Boolean {
        // Refuse any nested scroll event
        return acceptsNestedScroll
    }
}