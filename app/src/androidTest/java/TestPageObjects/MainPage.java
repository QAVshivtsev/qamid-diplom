package TestPageObjects;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static TestData.DataHelper.waitForElement;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;

/**
 * Класс MainPage представляет главную страницу и предоставляет методы для взаимодействия с элементами этой страницы.
 */
public class MainPage {

    private final String newsAdapterTitleText = "News";
    private final int headerLogoutBtnId = R.id.authorization_image_button;
    private final int allNewsBtn = R.id.all_news_text_view;

    public String getNewsAdapterTitleText() {
        return newsAdapterTitleText;
    }

    /**
     * Проверяет видимость главной страницы по тексту заголовка адаптера новостей.
     */
    public void checkMainPageVisible() {
        Allure.step("Проверка видимости главной страницы по тексту заголовка адаптера новостей");
        waitForElement(withText(newsAdapterTitleText), 10000);
        onView(withText(newsAdapterTitleText)).check(matches(isDisplayed()));
    }

    /**
     * Проверяет видимость кнопки выхода на главной странице.
     */
    public void checkLogoutBtnVisible() {
        Allure.step("Проверка видимости кнопки выхода на главной странице");
        waitForElement(withId(headerLogoutBtnId), 5000);
    }

    /**
     * Переходит на страницу новостей с помощью кнопки "Все новости".
     */
    public void goToNewsPageWithAllNewsBtn() {
        Allure.step("Переход на страницу новостей с помощью кнопки 'Все новости'");
        waitForElement(withId(allNewsBtn), 1500);
        onView(withId(allNewsBtn)).perform(click());
    }

}
