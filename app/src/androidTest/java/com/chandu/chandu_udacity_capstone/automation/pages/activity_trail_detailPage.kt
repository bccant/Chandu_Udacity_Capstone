package com.chandu.chandu_udacity_capstone.automation.pages

class ActivityTrailDetailPage : BasePage() {

    private val hikeNameText by lazy { find("id/hike_name") }
    private val favoriteToggleButton by lazy { find("id/fav_button") }
    private val hikeSummaryText by lazy { find("id/hike_summary") }
    private val hikeConditionText by lazy { find("id/hike_condition") }
    private val hikeRatingsText by lazy { find("id/hike_ratings") }
    private val hikeUrlButton by lazy { find("id/hike_url") }
    private val mapFloatingActionButton by lazy { find("id/map_fab") }

    fun getHikeName(): String {
        return hikeNameText.text
    }

    fun toggleFavorite(): ActivityTrailDetailPage {
        favoriteToggleButton.click()
        return this
    }

    fun getHikeSummary(): String {
        return hikeSummaryText.text
    }

    fun getHikeCondition(): String {
        return hikeConditionText.text
    }

    fun getHikeRatings(): String {
        return hikeRatingsText.text
    }

    fun clickExternalUrl(): ActivityTrailDetailPage {
        hikeUrlButton.click()
        return this
    }

    fun openMapView(): ActivityTrailDetailPage {
        mapFloatingActionButton.click()
        return this
    }

    fun isTrailFavorited(): Boolean {
        return favoriteToggleButton.getAttribute("checked") == "true"
    }
}