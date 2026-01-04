package com.chandu.chandu_udacity_capstone.automation.pages

class TrailListPage : BasePage() {

    private val trailListItem by lazy { find("trail_list") }

    fun clickTrailListItem(): TrailListPage {
        trailListItem.click()
        return this
    }

    fun getTrailListItemText(): String {
        return trailListItem.text
    }
}