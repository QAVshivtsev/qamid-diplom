package ru.iteco.fmhandroid.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Matchers.allOf
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.iteco.fmhandroid.R

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestLogoutButtonFunctionality {

    @get:Rule
    var mActivityScenarioRule = ActivityScenarioRule(AppActivity::class.java)

    @Test
    fun testLogoutButtonFunctionality() {
        // Пауза в 4 секунды перед началом теста
        Thread.sleep(4000)

        // Открыть меню авторизации
        openAuthorizationMenu()

        // Выбрать опцию "Выйти"
        selectLogoutOption()

        // Проверить, что кнопка "Войти" отображается после выхода
        checkSignInButtonDisplayed()
    }

    private fun openAuthorizationMenu() {
        val authorizationButton = onView(
            allOf(
                withId(R.id.authorization_image_button),
                withContentDescription("Авторизация"),
                isDisplayed()
            )
        )
        authorizationButton.perform(click())
    }

    private fun selectLogoutOption() {
        val logoutOption = onView(
            allOf(
                withId(android.R.id.title),
                withText("Выйти"),
                isDisplayed()
            )
        )
        logoutOption.perform(click())
    }

    private fun checkSignInButtonDisplayed() {
        val signInButton = onView(
            allOf(
                withId(R.id.enter_button),
                withText("ВОЙТИ"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.RelativeLayout::class.java))),
                isDisplayed()
            )
        )
        signInButton.check(matches(isDisplayed()))
    }
}
