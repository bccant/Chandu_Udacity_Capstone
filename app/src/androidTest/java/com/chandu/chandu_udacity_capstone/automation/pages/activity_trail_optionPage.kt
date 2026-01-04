package com.chandu.chandu_udacity_capstone.automation.pages

class ActivityTrailOptionPage : BasePage() {

    private val forecastRecyclerView by lazy { find("recyclerview_forecast") }
    private val loadingIndicator by lazy { find("pb_loading_indicator") }
    private val errorMessageDisplay by lazy { find("tv_error_message_display") }

    fun isForecastListVisible(): Boolean {
        return forecastRecyclerView.isDisplayed
    }

    fun isLoadingIndicatorVisible(): Boolean {
        return loadingIndicator.isDisplayed
    }

    fun getErrorMessage(): String {
        return errorMessageDisplay.text
    }

    fun verifyErrorMessageIsDisplayed(): ActivityTrailOptionPage {
        assert(errorMessageDisplay.isDisplayed)
        return this
    }

    fun scrollToTop(): ActivityTrailOptionPage {
        // Logic for scrolling the RecyclerView would go here
        return this
    }
}