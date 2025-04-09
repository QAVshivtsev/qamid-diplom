package ru.iteco.fmhandroid.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
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
class testCreateNews {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(AppActivity::class.java)

    @Test
    fun testCreateNews() {
        Thread.sleep(4000)
        // Клик на кнопку главного меню
        clickMainMenuButton()

        // Клик на элемент "Новости"
        clickNewsMenuItem()

        // Клик на кнопку управления новостями
        clickEditNewsButton()

        // Клик на кнопку добавления новости
        clickAddNewsButton()

        // Клик на кнопку раскрывающую список
        clickTextInputEndIcon()

        // Клик на кнопку "Объявление"
        clickAnnouncementButton()

        // Ввод данных новости
        enterNewsDetails()

        // Сохранение новости
        saveNews()
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

    private fun clickAddNewsButton() {
        onView(
            allOf(
                withId(R.id.add_news_image_view),
                withContentDescription("Кнопка добавления новости"),
                isDisplayed()
            )
        ).perform(click())
    }

    private fun clickTextInputEndIcon() {
        onView(
            allOf(
                withId(R.id.text_input_end_icon),
                isDisplayed()
            )
        ).perform(click())
    }
//Не удается настроить клик по кнопке без явной привязки к ID элемента
    private fun clickAnnouncementButton() {
        onView(
            allOf(
                withText("Объявление"),
                isDisplayed()
            )
        ).perform(click())
    }

    private fun enterNewsDetails() {
        // Ввод даты публикации
        onView(
            allOf(
                withId(R.id.news_item_publish_date_text_input_edit_text),
                isDisplayed()
            )
        ).perform(click())

        onView(
            allOf(
                withId(android.R.id.button1),
                withText("ОК"),
                isDisplayed()
            )
        ).perform(scrollTo(), click())

        // Ввод времени публикации
        onView(
            allOf(
                withId(R.id.news_item_publish_time_text_input_edit_text),
                isDisplayed()
            )
        ).perform(click())

        onView(
            allOf(
                withId(android.R.id.button1),
                withText("ОК"),
                isDisplayed()
            )
        ).perform(scrollTo(), click())

        // Ввод описания новости
        onView(
            allOf(
                withId(R.id.news_item_description_text_input_edit_text),
                isDisplayed()
            )
        ).perform(replaceText("test"), closeSoftKeyboard())
    }

    private fun saveNews() {
        onView(
            allOf(
                withId(R.id.save_button),
                withText("Сохранить"),
                isDisplayed()
            )
        ).perform(scrollTo(), click())
    }
}
