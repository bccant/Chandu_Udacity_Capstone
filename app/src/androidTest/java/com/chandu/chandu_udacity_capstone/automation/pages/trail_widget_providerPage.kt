package com.chandu.chandu_udacity_capstone.automation.pages

class TrailWidgetProviderPage : BasePage() {

    private val appWidgetIngredientImage by lazy { find("appwidget_ingredient") }
    private val appWidgetLabel by lazy { find("appwidget_text") }

    /**
     * Verifies that the ingredient image is displayed on the widget.
     * @return TrailWidgetProviderPage
     */
    fun verifyIngredientImageDisplayed(): TrailWidgetProviderPage {
        appWidgetIngredientImage.isDisplayed
        return this
    }

    /**
     * Retrieves the text currently displayed on the widget label.
     * @return String
     */
    fun getWidgetLabelText(): String {
        return appWidgetLabel.text
    }

    /**
     * Verifies the widget label text matches expected string.
     * @param expectedText The expected text to verify.
     * @return TrailWidgetProviderPage
     */
    fun verifyWidgetLabelText(expectedText: String): TrailWidgetProviderPage {
        assert(appWidgetLabel.text == expectedText)
        return this
    }
}