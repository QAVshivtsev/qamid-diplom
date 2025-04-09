package ru.iteco.fmhandroid.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.iteco.fmhandroid.R

@LargeTest
@RunWith(AndroidJUnit4::class)
class testNavigateToQuotesPage {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(AppActivity::class.java)

    @Test
    fun testNavigateToQuotesPage() {
        Thread.sleep(4000)

        // Клик на кнопку "Наша Миссия"
        clickOurMissionButton()

        // Проверка отображения текста "Главное - жить любя"
        checkTitleDisplayed("Главное - жить любя")
    }

    private fun clickOurMissionButton() {
        onView(
            allOf(
                withId(R.id.our_mission_image_button),
                withContentDescription("Наша Миссия"),
                isDisplayed()
            )
        ).perform(click())
    }

    private fun checkTitleDisplayed(text: String) {
        onView(
            allOf(
                withId(R.id.our_mission_title_text_view),
                withText(text),
                isDisplayed()
            )
        ).check(matches(isDisplayed()))
    }
}
