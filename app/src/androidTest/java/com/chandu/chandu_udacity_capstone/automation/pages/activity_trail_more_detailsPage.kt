package com.chandu.chandu_udacity_capstone.automation.pages

import io.appium.java_client.MobileElement

class TrailMoreDetailsPage : BasePage() {

    private val appBarLayout by lazy { find("appBar") }
    private val collapsingToolbarLayout by lazy { find("collapsingToolbar") }
    private val hikeImageView by lazy { find("hike_image") }
    private val detailToolbar by lazy { find("ctoolbar") }

    fun verifyPageLoaded(): TrailMoreDetailsPage {
        appBarLayout.isDisplayed
        detailToolbar.isDisplayed
        return this
    }

    fun isHikeImageDisplayed(): Boolean {
        return hikeImageView.isDisplayed
    }

    fun clickToolbar(): TrailMoreDetailsPage {
        detailToolbar.click()
        return this
    }

    fun getToolbarTitle(): String {
        return detailToolbar.text
    }

    fun expandCollapsingToolbar(): TrailMoreDetailsPage {
        collapsingToolbarLayout.click()
        return this
    }
}