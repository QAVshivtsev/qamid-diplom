package TestPageObjects;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
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
 * Класс CreatingNewsPage представляет страницу создания новости и предоставляет методы для взаимодействия с элементами этой страницы.
 */
public class CreatingNewsPage {

    private View createNewsDecorView;
    private final int catgrTextField = R.id.news_item_category_text_auto_complete_text_view;
    private final int titleTextField = R.id.news_item_title_text_input_edit_text;
    private final int publDateInputField = R.id.news_item_publish_date_text_input_edit_text;
    private final int publTimeInputField = R.id.news_item_publish_time_text_input_layout;
    private final int descrTextField = R.id.news_item_description_text_input_edit_text;
    private final int saveBtn = R.id.save_button;
    private final String pageHeaderTitleText = "Creating";
    private final String toastConfirmBtnText = "OK";

    /**
     * Проверяет видимость страницы создания новости по тексту заголовка.
     */
    public void checkPageVisibleByHeaderTitle() {
        Allure.step("Проверка видимости страницы создания новости по тексту заголовка");
        waitForElement(withText(pageHeaderTitleText), 1000);
    }

    /**
     * Вводит категорию новости с помощью клавиатуры.
     *
     * @param newsCategory категория новости для ввода.
     */
    public void enterNewsCategoryWithKeyboard(String newsCategory) {
        Allure.step("Ввод категории новости с помощью клавиатуры (не список)");
        waitForElement(withId(catgrTextField), 1000);
        onView(withId(catgrTextField)).perform(click()).perform(replaceText(newsCategory));
        closeSoftKeyboard();
    }

    /**
     * Вводит заголовок новости с помощью клавиатуры.
     *
     * @param newsTitle заголовок новости для ввода.
     */
    public void enterNewsTitle(String newsTitle) {
        Allure.step("Ввод заголовка новости с помощью клавиатуры");
        waitForElement(withId(titleTextField), 1000);
        onView(withId(titleTextField)).perform(click()).perform(replaceText(newsTitle));
        closeSoftKeyboard();
    }


    /**
     * Вводит дату публикации новости с помощью виджета календаря.
     */
    public void insertNewsPublDateByWidgetNoOffset() {
        Allure.step("Ввод даты публикации новости с помощью виджета календаря (yay)");
        waitForElement(withId(publDateInputField), 1000);
        onView(withId(publDateInputField)).perform(click());
        onView(withText(toastConfirmBtnText)).inRoot(withDecorView(Matchers.not(createNewsDecorView)))
                .check(matches(isDisplayed())).perform(click());
    }

    /**
     * Вводит время публикации новости с помощью виджета часов.
     */
    public void insertNewsPublTimeByWidgetNoOffset() {
        Allure.step("Ввод времени публикации новости с помощью виджета часов (yay)");
        waitForElement(withId(publTimeInputField), 1000);
        onView(withId(publTimeInputField)).perform(click());
        onView(withText(toastConfirmBtnText)).inRoot(withDecorView(Matchers.not(createNewsDecorView)))
                .check(matches(isDisplayed())).perform(click());
    }



    /**
     * Вводит описание новости с помощью клавиатуры.
     *
     * @param newsDescr описание новости для ввода.
     */
    public void enterNewsDescription(String newsDescr) {
        Allure.step("Ввод описания новости с помощью клавиатуры");
        waitForElement(withId(descrTextField), 1000);
        onView(withId(descrTextField)).perform(click()).perform(replaceText(newsDescr));
        closeSoftKeyboard();
    }

    /**
     * Нажимает кнопку "Сохранить" для подтверждения создания новости.
     */
    public void pressSaveToCreateNews() {
        Allure.step("Подтверждение создания новости нажатием кнопки 'Сохранить'");
        waitForElement(withId(saveBtn), 1000);
        onView(withId(saveBtn)).perform(click());
    }
}
