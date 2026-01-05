package com.chandu.chandu_udacity_capstone.automation.pages

class HikeOptionsPage : BasePage() {

    private val hikeOptionName by lazy { find("hike_option_name") }
    private val hikeOptionDistance by lazy { find("hike_option_distance") }
    private val hikeOptionDifficulty by lazy { find("hike_option_difficulty") }

    fun getHikeName(): String {
        return hikeOptionName.text
    }

    fun getHikeDistance(): String {
        return hikeOptionDistance.text
    }

    fun getHikeDifficulty(): String {
        return hikeOptionDifficulty.text
    }

    fun selectHikeOption(): HikeOptionsPage {
        hikeOptionName.click()
        return this
    }

    fun verifyHikeDetailsVisible(): HikeOptionsPage {
        assert(hikeOptionName.isDisplayed)
        assert(hikeOptionDistance.isDisplayed)
        assert(hikeOptionDifficulty.isDisplayed)
        return this
    }
}