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
class TestAboutPageLinksDisplay {

    @get:Rule
    var mActivityScenarioRule = ActivityScenarioRule(AppActivity::class.java)

    @Test
    fun testAboutPageLinksDisplay() {
        // Пауза в 4 секунды перед началом теста
        Thread.sleep(4000)

        // Открыть главное меню
        openMainMenu()

        // Перейти на страницу "О приложении"
        navigateToAboutPage()

        // Проверить отображение ссылки на политику конфиденциальности
        checkPrivacyPolicyLinkDisplayed()

        // Проверить отображение ссылки на условия использования
        checkTermsOfUseLinkDisplayed()
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

    private fun checkPrivacyPolicyLinkDisplayed() {
        val privacyPolicyLink = onView(
            allOf(
                withId(R.id.about_privacy_policy_value_text_view),
                withText("https://vhospice.org/#/privacy-policy"),
                isDisplayed()
            )
        )
        privacyPolicyLink.check(matches(isDisplayed()))
    }

    private fun checkTermsOfUseLinkDisplayed() {
        val termsOfUseLink = onView(
            allOf(
                withId(R.id.about_terms_of_use_value_text_view),
                withText("https://vhospice.org/#/terms-of-use"),
                isDisplayed()
            )
        )
        termsOfUseLink.check(matches(isDisplayed()))
    }
}
