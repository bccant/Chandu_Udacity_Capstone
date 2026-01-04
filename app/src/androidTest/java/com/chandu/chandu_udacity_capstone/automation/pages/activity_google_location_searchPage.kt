package com.chandu.chandu_udacity_capstone.automation.pages

class GoogleLocationSearchPage : BasePage() {

    private val placeAutocompleteFragment by lazy { find("id/place_autocomplete_fragment") }

    fun clickAutocompleteFragment(): GoogleLocationSearchPage {
        placeAutocompleteFragment.click()
        return this
    }

    fun isAutocompleteVisible(): Boolean {
        return placeAutocompleteFragment.isDisplayed
    }
}