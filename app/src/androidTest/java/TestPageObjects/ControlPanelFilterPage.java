package TestPageObjects;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static TestData.DataHelper.waitForElement;

import android.view.View;

import org.hamcrest.Matchers;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;

/**
 * Класс ControlPanelFilterPage представляет страницу фильтрации панели управления и предоставляет методы для взаимодействия с элементами этой страницы.
 */
public class ControlPanelFilterPage {

    private View controlPanelFilterPageDecorView;
    private final int filterPageTitle = R.id.filter_news_title_text_view;
    private final int catgrTextInputField = R.id.news_item_category_text_auto_complete_text_view;
    private final int applyFilterBtn = R.id.filter_button;

    /**
     * Проверяет видимость страницы фильтрации по заголовку.
     */
    public void checkPageVisibleByTitle() {
        Allure.step("Проверка видимости страницы фильтрации по заголовку");
        waitForElement(withId(filterPageTitle), 1000);
        onView(withId(filterPageTitle)).check(matches(isDisplayed()));
    }

    /**
     * Нажимает кнопку "Фильтр" для применения фильтров.
     */
    public void clickFilterBtn() {
        Allure.step("Нажатие кнопки 'Фильтр' для применения фильтров");
        waitForElement(withId(applyFilterBtn), 1000);
        onView(withId(applyFilterBtn)).perform(click());
    }
    public void selectNewsCategoryFromDropdown(String catgrToPick) {
        Allure.step("Ввод категории новости с помощью выпадающего списка");
        waitForElement(withId(catgrTextInputField), 1000);
        onView(withId(catgrTextInputField)).perform(click());
        closeSoftKeyboard();
        onView(withText(catgrToPick)).inRoot(withDecorView(Matchers.not(controlPanelFilterPageDecorView)))
                .check(matches(isDisplayed())).perform(click());
    }
}
