package com.chandu.chandu_udacity_capstone.automation.pages

class MainActivityPage : BasePage() {

    private val statesListRecyclerView by lazy { find("recyclerview_stateslist") }
    private val statesErrorMessageDisplay by lazy { find("states_error_message_display") }
    private val statesLoadingIndicator by lazy { find("states_loading_indicator") }
    private val mainLoadingIndicator by lazy { find("main_loading_indicator") }
    private val staticSpinner by lazy { find("static_spinner") }
    private val searchFloatingActionButton by lazy { find("fab") }

    fun isStatesListDisplayed(): Boolean {
        return statesListRecyclerView.isDisplayed
    }

    fun getErrorMessage(): String {
        return statesErrorMessageDisplay.text
    }

    fun isStatesLoading(): Boolean {
        return statesLoadingIndicator.isDisplayed
    }

    fun isMainLoading(): Boolean {
        return mainLoadingIndicator.isDisplayed
    }

    fun clickSearch(): MainActivityPage {
        searchFloatingActionButton.click()
        return this
    }

    fun clickSpinner(): MainActivityPage {
        staticSpinner.click()
        return this
    }

    fun waitForLoadingToFinish(): MainActivityPage {
        // Implementation logic for waiting for mainLoadingIndicator to disappear
        return this
    }
}