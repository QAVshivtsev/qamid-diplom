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
import org.hamcrest.Matchers.`is`
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.iteco.fmhandroid.R

@LargeTest
@RunWith(AndroidJUnit4::class)
class testFailedLogin {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(AppActivity::class.java)

    @Test
    fun testFailedLogin() {
        // Ожидание загрузки экрана
        Thread.sleep(4000)

        // 1. Проверка авторизации с некоректными данными.
        // Почему: Критически важная функция для доступа пользователя к приложению.
        login()

        // Проверка, что кнопка "Sign in" отображается после разлогинивания
        checkSignInButtonVisibility()
    }

    private fun login() {
        // Клик на поле ввода логина
        clickOnLoginField()

        // Ввод текста логина
        enterLoginText("login")

        // Клик на поле ввода пароля
        clickOnPasswordField()

        // Ввод текста пароля
        enterPasswordText("password")

        // Клик на кнопку входа
        clickSignInButton()
    }

    private fun clickOnLoginField() {
        val loginInputField = onView(
            allOf(
                withId(R.id.login_text_input_layout),
                isDisplayed()
            )
        )
        loginInputField.perform(click())
    }

    private fun enterLoginText(login: String) {
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

    private fun clickOnPasswordField() {
        val passwordInputField = onView(
            allOf(
                withId(R.id.password_text_input_layout),
                isDisplayed()
            )
        )
        passwordInputField.perform(click())
    }

    private fun enterPasswordText(password: String) {
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
        val materialButton = onView(
            allOf(
                withId(R.id.enter_button), withText("Войти"), withContentDescription("Сохранить"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.RelativeLayout")),
                        1
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())
    }

    private fun checkSignInButtonVisibility() {
        val signInButton = onView(
            allOf(
                withId(R.id.enter_button), withText("Войти"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.RelativeLayout::class.java))),
                isDisplayed()
            )
        )
        signInButton.check(matches(isDisplayed()))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Дочерний элемент на позиции $position в родителе ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
