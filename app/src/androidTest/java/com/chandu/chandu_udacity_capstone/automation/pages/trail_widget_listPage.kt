package com.chandu.chandu_udacity_capstone.automation.pages

import io.appium.java_client.MobileElement

class TrailWidgetListPage : BasePage() {

    private val hikeTitleLabel by lazy { find("id/hike_title") }
    private val trailList by lazy { find("id/trail_list_view") }
    private val emptyStateLayout by lazy { find("id/widget_empty") }
    private val emptyStateIllustration by lazy { find("id/trail_widget_desc") }
    private val emptyStateMessage by lazy { find("id/appwidget_text") }

    /**
     * Retrieves the text displayed in the hike title header.
     * @return String The title of the hike widget.
     */
    fun getHikeTitleText(): String {
        return hikeTitleLabel.text
    }

    /**
     * Checks if the trail list container is currently displayed.
     * @return Boolean true if displayed.
     */
    fun isTrailListVisible(): Boolean {
        return trailList.isDisplayed
    }

    /**
     * Checks if the empty state layout is visible when no trails are available.
     * @return Boolean true if empty state is active.
     */
    fun isEmptyStateActive(): Boolean {
        return emptyStateLayout.isDisplayed
    }

    /**
     * Retrieves the informative message from the empty state view.
     * @return String The empty state message.
     */
    fun getEmptyStateMessageText(): String {
        return emptyStateMessage.text
    }

    /**
     * Example of a fluent action to verify widget state.
     * @return TrailWidgetListPage
     */
    fun verifyWidgetLoaded(): TrailWidgetListPage {
        hikeTitleLabel.isDisplayed
        return this
    }
}