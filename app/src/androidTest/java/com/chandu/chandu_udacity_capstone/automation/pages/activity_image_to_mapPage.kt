package com.chandu.chandu_udacity_capstone.automation.pages

class ImageToMapPage : BasePage() {

    private val mapFragment by lazy { find("id/map") }

    /**
     * Verifies if the map fragment is displayed on the screen.
     */
    fun verifyMapIsDisplayed(): ImageToMapPage {
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