package TestPageObjects;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static TestData.DataHelper.waitForElement;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;

/**
 * Класс AuthorizationPage представляет страницу авторизации и предоставляет методы для взаимодействия с элементами этой страницы.
 */
public class AuthorizationPage {

    private final String loginHint = "Login";
    private final String passwdHint = "Password";
    private final int signInButton = R.id.enter_button;
    private final String loginErrorToastText = "Something went wrong. Try again later.";
    private final String emptyLoginToastText = "Login and password cannot be empty";
    private final String pageHeaderText = "Authorization";

    /**
     * Возвращает текст сообщения об ошибке авторизации.
     *
     * @return текст сообщения об ошибке авторизации.
     */
    public String getLoginErrorToastText() {
        return loginErrorToastText;
    }

    /**
     * Возвращает текст сообщения об ошибке пустого логина.
     *
     * @return текст сообщения об ошибке пустого логина.
     */
    public String getEmptyLoginToastText() {
        return emptyLoginToastText;
    }

    /**
     * Проверяет видимость страницы авторизации по тексту заголовка.
     */
    public void checkAuthPageVisible() {
        Allure.step("Проверка видимости страницы авторизации по тексту заголовка");
        waitForElement(withText(pageHeaderText), 10000);
        onView(withText(pageHeaderText)).check(matches(isDisplayed()));
    }

    /**
     * Вводит логин с помощью виртуальной клавиатуры.
     *
     * @param login логин для ввода.
     */
    public void enterLogin(String login) {
        Allure.step("Ввод логина с помощью виртуальной клавиатуры");
        waitForElement(withHint(loginHint), 5000);
        onView(withHint(loginHint)).perform(click()).perform(typeText(login));
        closeSoftKeyboard();
    }

    /**
     * Вводит пароль с помощью виртуальной клавиатуры.
     *
     * @param password пароль для ввода.
     */
    public void enterPassword(String password) {
        Allure.step("Ввод пароля с помощью виртуальной клавиатуры");
        waitForElement(withHint(passwdHint), 5000);
        onView(withHint(passwdHint)).perform(click()).perform(typeText(password));
        closeSoftKeyboard();
    }

    /**
     * Нажимает кнопку "Войти".
     */
    public void pressSignIn() {
        Allure.step("Нажатие кнопки 'Войти'");
        waitForElement(withId(signInButton), 5000);
        onView(withId(signInButton)).perform(click());
    }
}
