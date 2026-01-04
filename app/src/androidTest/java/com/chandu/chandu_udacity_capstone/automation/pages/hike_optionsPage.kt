package com.chandu.chandu_udacity_capstone.automation.pages

class HikeOptionsPage : BasePage() {

    private val hikeOptionName by lazy { find("id/hike_option_name") }
    private val hikeOptionDistance by lazy { find("id/hike_option_distance") }
    private val hikeOptionDifficulty by lazy { find("id/hike_option_difficulty") }

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
}