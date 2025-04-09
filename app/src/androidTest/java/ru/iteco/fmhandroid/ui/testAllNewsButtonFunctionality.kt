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
class TestAllNewsButtonFunctionality {

    @get:Rule
    var mActivityScenarioRule = ActivityScenarioRule(AppActivity::class.java)

    @Test
    fun testAllNewsButtonFunctionality() {
        Thread.sleep(4000)
        // Найти и кликнуть на кнопку "Все новости"
        val allNewsButton = onView(
            allOf(
                withId(R.id.all_news_text_view),
                withText("Все новости"),
                isDisplayed()
            )
        )
        allNewsButton.perform(click())

        // Проверить, что кнопка редактирования новостей отображается
        val editNewsButton = onView(
            allOf(
                withId(R.id.edit_news_material_button),
                withParent(withParent(withId(R.id.container_list_news_include))),
                isDisplayed()
            )
        )
        editNewsButton.check(matches(isDisplayed()))
    }

}
