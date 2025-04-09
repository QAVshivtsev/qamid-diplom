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
class testNavigateToNewsManagement {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(AppActivity::class.java)

    @Test
    fun testNavigateToNewsManagement() {
        Thread.sleep(4000)

        // Клик на кнопку главного меню
        clickMainMenuButton()

        // Клик на элемент "Новости"
        clickNewsMenuItem()

        // Клик на кнопку управления новостями
        clickEditNewsButton()

        // Проверка отображения кнопки добавления новости
        checkAddNewsButtonDisplayed()
    }

    private fun clickMainMenuButton() {
        onView(
            allOf(
                withId(R.id.main_menu_image_button),
                withContentDescription("Главное меню"),
                isDisplayed()
            )
        ).perform(click())
    }

    private fun clickNewsMenuItem() {
        onView(
            allOf(
                withId(android.R.id.title),
                withText("Новости"),
                isDisplayed()
            )
        ).perform(click())
    }

    private fun clickEditNewsButton() {
        onView(
            allOf(
                withId(R.id.edit_news_material_button),
                isDisplayed()
            )
        ).perform(click())
    }

    private fun checkAddNewsButtonDisplayed() {
        onView(
            allOf(
                withId(R.id.add_news_image_view),
                withContentDescription("Кнопка добавления новости"),
                isDisplayed()
            )
        ).check(matches(isDisplayed()))
    }

}
