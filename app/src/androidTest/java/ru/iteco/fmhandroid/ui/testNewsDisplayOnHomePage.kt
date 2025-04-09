//11. Проверка отображения новостей на главной странице. Почему: Основной контент, который видят пользователи.
package ru.iteco.fmhandroid.ui


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
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
class testNewsDisplayOnHomePage {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(AppActivity::class.java)

    @Test
    fun testNewsDisplayOnHomePage() {
        Thread.sleep(4000)
        val textView = onView(
            allOf(
                withId(R.id.all_news_text_view), withText("ВСЕ НОВОСТИ"),
                withParent(
                    allOf(
                        withId(R.id.container_list_news_include_on_fragment_main),
                        withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("ВСЕ НОВОСТИ")))

        val textView2 = onView(
            allOf(
                withText("Новости"),
                withParent(withParent(withId(R.id.container_list_news_include_on_fragment_main))),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("Новости")))
    }
}
