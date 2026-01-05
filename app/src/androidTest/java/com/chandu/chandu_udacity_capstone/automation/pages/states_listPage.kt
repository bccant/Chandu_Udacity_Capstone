package com.chandu.chandu_udacity_capstone.automation.pages

import io.appium.java_client.MobileElement

class StatesListPage : BasePage() {

    private val cardViewStateContainer by lazy { find("id/cv") }
    private val stateImageButton by lazy { find("id/state_name") }
    private val stateCapitalsTextView by lazy { find("id/state_caps") }

    fun clickStateContainer(): StatesListPage {
        cardViewStateContainer.click()
        return this
    }

    fun clickStateImage(): StatesListPage {
        stateImageButton.click()
        return this
    }

    fun getStateCapitals(): String {
        return stateCapitalsTextView.text
    }

    fun isStateItemVisible(): Boolean {
        return cardViewStateContainer.isDisplayed
    }
}