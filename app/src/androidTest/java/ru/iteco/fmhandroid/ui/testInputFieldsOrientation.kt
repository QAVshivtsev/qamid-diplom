package ru.iteco.fmhandroid.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
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
class testInputFieldsOrientation {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(AppActivity::class.java)

    @Test
    fun testInputFieldsOrientation() {
        // Ожидание загрузки экрана
        Thread.sleep(4000)

        // Проверка видимости полей ввода логина и пароля
        checkFieldsVisibility()

        // Смена ориентации устройства
        changeOrientation()

        // Повторная проверка видимости полей ввода после смены ориентации
        checkFieldsVisibility()
    }

    private fun checkFieldsVisibility() {
        // Проверка видимости поля ввода логина
        onView(withId(R.id.login_text_input_layout))
            .check(matches(isDisplayed()))

        // Проверка видимости поля ввода пароля
        onView(withId(R.id.password_text_input_layout))
            .check(matches(isDisplayed()))
    }

    private fun changeOrientation() {
        mActivityScenarioRule.scenario.onActivity { activity ->
            val currentOrientation = activity.resources.configuration.orientation
            val newOrientation = if (currentOrientation == android.content.res.Configuration.ORIENTATION_PORTRAIT) {
                android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            } else {
                android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
            activity.requestedOrientation = newOrientation
        }
    }

}
