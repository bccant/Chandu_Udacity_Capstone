package com.chandu.chandu_udacity_capstone.automation.pages

class ActivityTrailMoreDetailsPage : BasePage() {

    private val appBar by lazy { find("id/appBar") }
    private val collapsingToolbar by lazy { find("id/collapsingToolbar") }
    private val hikeImage by lazy { find("id/hike_image") }
    private val cToolbar by lazy { find("id/ctoolbar") }

    fun verifyPageLoaded(): ActivityTrailMoreDetailsPage {
        appBar.isDisplayed
        hikeImage.isDisplayed
        return this
    }

    fun clickHikeImage(): ActivityTrailMoreDetailsPage {
        hikeImage.click()
        return this
    }

    fun getToolbarTitle(): String {
        return cToolbar.text
    }

    fun interactWithToolbar(): ActivityTrailMoreDetailsPage {
        cToolbar.click()
        return this
    }

    fun isCollapsingToolbarVisible(): Boolean {
        return collapsingToolbar.isDisplayed
    }
}