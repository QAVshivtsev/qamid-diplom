package TestPageObjects;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static TestData.DataHelper.waitForElement;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;

/**
 * Класс NewsFilterPage представляет страницу фильтрации новостей и предоставляет методы для взаимодействия с элементами этой страницы.
 */
public class NewsFilterPage {

    private final int filterPageTitle = R.id.filter_news_title_text_view;
    private final int categoryTextInputField = R.id.news_item_category_text_auto_complete_text_view;
    private final int applyFilterBtn = R.id.filter_button;

    /**
     * Проверяет видимость страницы фильтрации новостей по заголовку.
     */
    public void checkPageVisibleByTitle() {
        Allure.step("Проверка видимости страницы фильтрации новостей по заголовку");
        waitForElement(withId(filterPageTitle), 1000);
        onView(withId(filterPageTitle)).check(matches(isDisplayed()));
    }

    /**
     * Вводит категорию новости в текстовое поле с помощью клавиатуры.
     *
     * @param newsCategory категория новости для ввода.
     */
    public void insertNewsCategoryWithKeyboard(String newsCategory) {
        Allure.step("Ввод категории новости в текстовое поле с помощью клавиатуры");
        waitForElement(withId(categoryTextInputField), 1000);
        onView(withId(categoryTextInputField)).perform(click()).perform(replaceText(newsCategory));
        closeSoftKeyboard();
    }

    /**
     * Нажимает кнопку "Применить фильтр" для применения фильтров.
     */
    public void clickApplyFilterBtn() {
        Allure.step("Нажатие кнопки 'Применить фильтр' для применения фильтров");
        waitForElement(withId(applyFilterBtn), 1000);
        onView(withId(applyFilterBtn)).perform(click());
    }
}
