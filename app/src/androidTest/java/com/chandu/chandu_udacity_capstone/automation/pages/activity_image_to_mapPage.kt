package com.chandu.chandu_udacity_capstone.automation.pages

class ImageToMapPage : BasePage() {

    private val mapFragment by lazy { find("id/map") }

    /**
     * Verifies if the Google Map fragment is visible on the screen.
     */
    fun verifyMapDisplayed(): ImageToMapPage {
        assert(mapFragment.isDisplayed)
        return this
    }

    /**
     * Performs a click action on the map fragment.
     */
    fun clickMap(): ImageToMapPage {
        mapFragment.click()
        return this
    }
}