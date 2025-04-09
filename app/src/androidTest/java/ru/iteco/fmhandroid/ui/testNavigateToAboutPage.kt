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
class TestNavigateToAboutPage {

    @get:Rule
    var mActivityScenarioRule = ActivityScenarioRule(AppActivity::class.java)

    @Test
    fun testNavigateToAboutPage() {
        // Пауза в 4 секунды перед началом теста
        Thread.sleep(4000)

        // Открыть главное меню
        openMainMenu()

        // Перейти на страницу "О приложении"
        navigateToAboutPage()

        // Проверить отображение информации о компании
        checkCompanyInfoDisplayed()

        // Проверить отображение версии приложения
        checkVersionInfoDisplayed()
    }

    private fun openMainMenu() {
        val mainMenuButton = onView(
            allOf(
                withId(R.id.main_menu_image_button),
                withContentDescription("Главное меню"),
                isDisplayed()
            )
        )
        mainMenuButton.perform(click())
    }

    private fun navigateToAboutPage() {
        val aboutMenuItem = onView(
            allOf(
                withId(android.R.id.title),
                withText("О приложении"),
                isDisplayed()
            )
        )
        aboutMenuItem.perform(click())
    }

    private fun checkCompanyInfoDisplayed() {
        val companyInfoTextView = onView(
            allOf(
                withId(R.id.about_company_info_label_text_view),
                withText("© Айтеко, 2022"),
                isDisplayed()
            )
        )
        companyInfoTextView.check(matches(withText("© Айтеко, 2022")))
    }

    private fun checkVersionInfoDisplayed() {
        val versionInfoTextView = onView(
            allOf(
                withId(R.id.about_version_title_text_view),
                withText("Версия:"),
                isDisplayed()
            )
        )
        versionInfoTextView.check(matches(withText("Версия:")))
    }
}
