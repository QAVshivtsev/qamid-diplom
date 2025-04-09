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
class TestLogoutDropdownDisplay {

    @get:Rule
    var mActivityScenarioRule = ActivityScenarioRule(AppActivity::class.java)

    @Test
    fun testLogoutDropdownDisplay() {
        Thread.sleep(4000)
        // Открыть выпадающее меню авторизации
        openAuthorizationDropdown()

        // Проверить отображение опции "Выйти"
        checkLogoutOptionDisplayed()
    }

    private fun openAuthorizationDropdown() {
        val authorizationButton = onView(
            allOf(
                withId(R.id.authorization_image_button),
                withContentDescription("Авторизация"),
                isDisplayed()
            )
        )
        authorizationButton.perform(click())
    }

    private fun checkLogoutOptionDisplayed() {
        val logoutOption = onView(
            allOf(
                withId(android.R.id.title),
                withText("Выйти"),
                isDisplayed()
            )
        )
        logoutOption.check(matches(withText("Выйти")))
    }
}
