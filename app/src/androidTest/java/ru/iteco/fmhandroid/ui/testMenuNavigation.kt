package ru.iteco.fmhandroid.ui

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
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
class testMenuNavigation {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(AppActivity::class.java)

    @Test
    fun testMenuNavigation() {
        // Задержка для загрузки начального экрана
        Thread.sleep(4000)

        // Открываем главное меню
        val appCompatImageButton = onView(
            allOf(
                withId(R.id.main_menu_image_button), withContentDescription("Главное меню"),
                childAtPosition(
                    allOf(
                        withId(R.id.container_custom_app_bar_include_on_fragment_main),
                        childAtPosition(
                            withClassName(`is`("android.widget.LinearLayout")),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatImageButton.perform(click())

        // Переходим в раздел "О приложении"
        val materialTextView = onView(
            allOf(
                withId(android.R.id.title), withText("О приложении"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialTextView.perform(click())

        // Проверяем, что отображается версия приложения
        val textView = onView(
            allOf(
                withId(R.id.about_version_title_text_view), withText("Версия:"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java))),
                isDisplayed()
            )
        )
        textView.check(matches(isDisplayed()))

        // Возвращаемся назад
        val appCompatImageButton2 = onView(
            allOf(
                withId(R.id.about_back_image_button),
                childAtPosition(
                    allOf(
                        withId(R.id.container_custom_app_bar_include_on_fragment_about),
                        childAtPosition(
                            withClassName(`is`("android.widget.LinearLayout")),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatImageButton2.perform(click())

        // Снова открываем главное меню
        val appCompatImageButton3 = onView(
            allOf(
                withId(R.id.main_menu_image_button), withContentDescription("Главное меню"),
                childAtPosition(
                    allOf(
                        withId(R.id.container_custom_app_bar_include_on_fragment_main),
                        childAtPosition(
                            withClassName(`is`("android.widget.LinearLayout")),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatImageButton3.perform(click())

        // Переходим в раздел "Новости"
        val materialTextView2 = onView(
            allOf(
                withId(android.R.id.title), withText("Новости"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialTextView2.perform(click())

        // Проверяем, что кнопка редактирования новостей отображается
        val compoundButton = onView(
            allOf(
                withId(R.id.edit_news_material_button),
                withParent(withParent(withId(R.id.container_list_news_include))),
                isDisplayed()
            )
        )
        compoundButton.check(matches(isDisplayed()))

        // Возвращаемся назад
        val appCompatImageButton4 = onView(
            allOf(
                withId(R.id.main_menu_image_button), withContentDescription("Главное меню"),
                childAtPosition(
                    allOf(
                        withId(R.id.container_custom_app_bar_include_on_fragment_news_list),
                        childAtPosition(
                            withClassName(`is`("android.widget.LinearLayout")),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatImageButton4.perform(click())

        // Переходим на главную страницу
        val materialTextView3 = onView(
            allOf(
                withId(android.R.id.title), withText("Главная"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialTextView3.perform(click())

        // Проверяем, что кнопка авторизации отображается
        val imageButton = onView(
            allOf(
                withId(R.id.authorization_image_button), withContentDescription("Авторизация"),
                withParent(
                    allOf(
                        withId(R.id.container_custom_app_bar_include_on_fragment_main),
                        withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        imageButton.check(matches(isDisplayed()))
    }

    // Вспомогательная функция для поиска дочернего элемента по позиции
    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent) && view == parent.getChildAt(position)
            }
        }
    }
}
