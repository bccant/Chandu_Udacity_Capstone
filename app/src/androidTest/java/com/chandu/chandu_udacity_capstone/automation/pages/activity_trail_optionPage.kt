package com.chandu.chandu_udacity_capstone.automation.pages

class TrailOptionPage : BasePage() {

    private val forecastRecyclerView by lazy { find("id/recyclerview_forecast") }
    private val loadingIndicator by lazy { find("id/pb_loading_indicator") }
    private val errorMessageTextView by lazy { find("id/tv_error_message_display") }

    fun verifyForecastRecyclerViewIsDisplayed(): TrailOptionPage {
        assert(forecastRecyclerView.isDisplayed)
        return this
    }

    fun verifyLoadingIndicatorIsDisplayed(): TrailOptionPage {
        assert(loadingIndicator.isDisplayed)
        return this
    }

    fun verifyErrorMessageIsDisplayed(): TrailOptionPage {
        assert(errorMessageTextView.isDisplayed)
        return this
    }

    fun getErrorMessageText(): String {
        return errorMessageTextView.text
    }

    fun isDataLoaded(): Boolean {
        return forecastRecyclerView.isDisplayed && !loadingIndicator.isDisplayed
    }
}