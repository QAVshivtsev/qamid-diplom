//Шапка приложения
package ru.iteco.fmhandroid.ui

import androidx.test.espresso.Espresso.onView
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
class testHeaderElementsOrientation {

    @get:Rule
    val activityRule = ActivityScenarioRule(AppActivity::class.java)

    @Test
    fun testHeaderElementsOrientation() {
        Thread.sleep(4000)

        // Список элементов для проверки
        val elements = listOf(
            R.id.main_menu_image_button to "Главное меню",
            R.id.trademark_image_view to null,
            R.id.our_mission_image_button to "Наша Миссия",
            R.id.authorization_image_button to "Авторизация"
        )

        // Проверка отображения элементов до смены ориентации
        checkElementsDisplayed(elements)

        // Смена ориентации устройства
        activityRule.scenario.onActivity { activity ->
            activity.requestedOrientation =
                android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        // Ожидание завершения смены ориентации
        Thread.sleep(2000)

        // Проверка отображения элементов после смены ориентации
        checkElementsDisplayed(elements)
    }

    // Функция для проверки отображения элементов
    private fun checkElementsDisplayed(elements: List<Pair<Int, String?>>) {
        elements.forEach { (id, contentDescription) ->
            onView(
                allOf(
                    withId(id),
                    contentDescription?.let { withContentDescription(it) } ?: isDisplayed(),
                    withParent(
                        allOf(
                            withId(R.id.container_custom_app_bar_include_on_fragment_main),
                            withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java))
                        )
                    ),
                    isDisplayed()
                )

            ).check(matches(isDisplayed()))
        }
    }
}