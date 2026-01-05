package com.chandu.chandu_udacity_capstone.automation.pages

import io.appium.java_client.MobileElement

class GoogleLocationSearchPage : BasePage() {

    private val placeAutocompleteFragment by lazy { find("id/place_autocomplete_fragment") }

    /**
     * Clicks on the autocomplete fragment to initiate a location search.
     * @return GoogleLocationSearchPage
     */
    fun clickAutocompleteSearch(): GoogleLocationSearchPage {
        placeAutocompleteFragment.click()
        return this
    }

    /**
     * Enters a location string into the autocomplete search field.
     * @param location The name of the place to search for.
     * @return GoogleLocationSearchPage
     */
    fun enterLocation(location: String): GoogleLocationSearchPage {
        placeAutocompleteFragment.sendKeys(location)
        return this
    }

    /**
     * Verifies if the search fragment is currently displayed.
     * @return Boolean
     */
    fun isSearchFragmentVisible(): Boolean {
        return placeAutocompleteFragment.isDisplayed
    }
}