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
class TestAppLogoDisplay {

    @get:Rule
    var mActivityScenarioRule = ActivityScenarioRule(AppActivity::class.java)

    @Test
    fun testAppLogoDisplay() {
        Thread.sleep(4000)
        // Проверка отображения логотипа приложения
        val logoImageView = onView(
            allOf(
                withId(R.id.trademark_image_view),
                withParent(
                    allOf(
                        withId(R.id.container_custom_app_bar_include_on_fragment_main),
                        withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java))
                    )
                ),
                isDisplayed()
            )
        )

        // Убедиться, что логотип отображается на экране
        logoImageView.check(matches(isDisplayed()))
    }
}
