package com.chandu.chandu_udacity_capstone.automation.pages

class MainPage : BasePage() {

    private val recyclerViewStatesList by lazy { find("id/recyclerview_stateslist") }
    private val statesErrorMessageDisplay by lazy { find("id/states_error_message_display") }
    private val statesLoadingIndicator by lazy { find("id/states_loading_indicator") }
    private val mainLoadingIndicator by lazy { find("id/main_loading_indicator") }
    private val staticSpinner by lazy { find("id/static_spinner") }
    private val fabSearch by lazy { find("id/fab") }

    fun clickSearch(): MainPage {
        fabSearch.click()
        return this
    }

    fun isStatesListVisible(): Boolean {
        return recyclerViewStatesList.isDisplayed
    }

    fun getErrorMessage(): String {
        return statesErrorMessageDisplay.text
    }

    fun isMainLoadingVisible(): Boolean {
        return mainLoadingIndicator.isDisplayed
    }

    fun isStatesLoadingVisible(): Boolean {
        return statesLoadingIndicator.isDisplayed
    }

    fun openSpinner(): MainPage {
        staticSpinner.click()
        return this
    }
}