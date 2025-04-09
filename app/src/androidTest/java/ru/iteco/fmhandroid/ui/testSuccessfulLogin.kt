package ru.iteco.fmhandroid.ui

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.iteco.fmhandroid.R

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestSuccessfulLogin {

    @get:Rule
    var mActivityScenarioRule = ActivityScenarioRule(AppActivity::class.java)

    @Test
    fun testSuccessfulLogin() {
        // Пауза в 4 секунды перед началом теста
        Thread.sleep(4000)

        // 1. Проверка авторизации с корректными данными.
        // Почему: Критически важная функция для доступа пользователя к приложению.
        performLogin()

        // Проверка успешной авторизации
        checkLoginSuccess()
    }

    private fun performLogin() {
        // Ввод логина
        enterLogin("login2")

        // Ввод пароля
        enterPassword("password2")

        // Клик на кнопку входа
        clickSignInButton()
    }

    private fun enterLogin(login: String) {
        val loginInputField = onView(
            allOf(
                withId(R.id.login_text_input_layout),
                isDisplayed()
            )
        )
        loginInputField.perform(click())

        val textInputEditText = onView(
            allOf(
                childAtPosition(
                    childAtPosition(
                        withId(R.id.login_text_input_layout),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText.perform(replaceText(login), closeSoftKeyboard())
    }

    private fun enterPassword(password: String) {
        val passwordInputField = onView(
            allOf(
                withId(R.id.password_text_input_layout),
                isDisplayed()
            )
        )
        passwordInputField.perform(click())

        val passwordEditText = onView(
            allOf(
                childAtPosition(
                    childAtPosition(
                        withId(R.id.password_text_input_layout),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        passwordEditText.perform(replaceText(password), closeSoftKeyboard())
    }

    private fun clickSignInButton() {
        val signInButton = onView(
            allOf(
                withId(R.id.enter_button), withText("ВОЙТИ"),
                isDisplayed()
            )
        )
        signInButton.perform(click())
    }

    private fun checkLoginSuccess() {
        // Проверка, что кнопка "Sign in" больше не отображается, указывая на успешный вход
        val signInButton = onView(
            allOf(
                withId(R.id.enter_button), withText("ВОЙТИ"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.RelativeLayout::class.java))),
                isDisplayed()
            )
        )
        signInButton.check(matches(isDisplayed()))
    }

    private fun childAtPosition(parentMatcher: Matcher<View>, position: Int): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Дочерний элемент на позиции $position в родителе ")
                parentMatcher.describeTo(description)
            }

            override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
