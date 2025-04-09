package ru.iteco.fmhandroid.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
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
class testNewsSorting {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(AppActivity::class.java)

    @Test
    fun testNewsSorting() {
        Thread.sleep(4000)

        // Клик на кнопку главного меню
        clickMainMenuButton()

        // Клик на элемент "Новости"
        clickNewsMenuItem()

        // Клик на кнопку сортировки новостей
        clickSortNewsButton()
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

    private fun clickSortNewsButton() {
        onView(
            allOf(
                withId(R.id.sort_news_material_button),
                withContentDescription("Кнопка сортировки списка новостей"),
                isDisplayed()
            )
        ).perform(click())
    }

}
