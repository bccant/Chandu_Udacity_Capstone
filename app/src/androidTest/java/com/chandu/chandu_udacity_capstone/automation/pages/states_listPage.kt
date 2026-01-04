package com.chandu.chandu_udacity_capstone.automation.pages

class StatesListPage : BasePage() {

    private val stateCardView by lazy { find("id/cv") }
    private val stateImageButton by lazy { find("id/state_name") }
    private val stateCapitalsTextView by lazy { find("id/state_caps") }

    /**
     * Clicks on the state card container.
     */
    fun clickStateCard(): StatesListPage {
        stateCardView.click()
        return this
    }

    /**
     * Clicks on the state image button.
     */
    fun clickStateImage(): StatesListPage {
        stateImageButton.click()
        return this
    }

    /**
     * Retrieves the text displayed for the state capitals.
     */
    fun getStateCapitalsText(): String {
        return stateCapitalsTextView.text
    }

    /**
     * Verifies if the state card is currently displayed.
     */
    fun isStateCardVisible(): Boolean {
        return stateCardView.isDisplayed
    }
}