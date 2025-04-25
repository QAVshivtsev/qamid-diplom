package TestPageObjects;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSubstring;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static TestData.DataHelper.waitForElement;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;

/**
 * Класс AboutPage представляет страницу "О приложении" и предоставляет методы для взаимодействия с элементами этой страницы.
 */
public class AboutPage {

    private final int aboutHeaderTitleImage = R.id.trademark_image_view;
    private final int headerContainer = R.id.container_custom_app_bar_include_on_fragment_about;
    private final String privacyLinkText = "https://vhospice.org/#/privacy-policy/";
    private final String termsLinkText = "https://vhospice.org/#/terms-of-use";

    /**
     * Возвращает текст ссылки на политику конфиденциальности.
     *
     * @return текст ссылки на политику конфиденциальности.
     */
    public String getPrivacyLinkText() {
        return privacyLinkText;
    }
    /**
     * Возвращает текст ссылки на условия использования.
     *
     * @return текст ссылки на условия использования.
     */
    public String getTermsLinkText() {
        return termsLinkText;
    }
    /**
     * Проверяет видимость страницы "О приложении" по изображению заголовка.
     */
    public void checkPageVisibleByHeader() {
        Allure.step("Проверка видимости 'О приложении' по изображению заголовка");
        waitForElement(withId(aboutHeaderTitleImage), 1000);
        onView(withId(aboutHeaderTitleImage)).check(matches(isDisplayed()));
    }
    /**
     * Проверяет видимость текстового контента на странице "О приложении".
     */
    public void checkAboutPageLinksDisplay() {
        Allure.step("Проверка видимости текстового контента на странице");
        waitForElement(withId(headerContainer), 1000);
        onView(withSubstring(privacyLinkText)).check(matches(isDisplayed()));
        onView(withSubstring(termsLinkText)).check(matches(isDisplayed()));
    }

    /**
     * Нажимает на ссылку "Политика конфиденциальности" для открытия её в браузере.
     */
    public void clickPrivacyPolicyLink() {
        Allure.step("Нажатие на ссылку 'Политика конфиденциальности' для открытия её в браузере");
        waitForElement(withText(privacyLinkText), 1000);
        onView(withText(privacyLinkText)).perform(click());
    }

    /**
     * Нажимает на ссылку "Условия использования" для открытия её в браузере.
     */
    public void clickTermsOfUseLink() {
        Allure.step("Нажатие на ссылку 'Условия использования' для открытия её в браузере");
        waitForElement(withText(termsLinkText), 1000);
        onView(withText(termsLinkText)).perform(click());
    }
}
