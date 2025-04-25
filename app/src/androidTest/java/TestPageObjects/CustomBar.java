package TestPageObjects;

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
 * Класс CustomBar представляет панель навигации и предоставляет методы для взаимодействия с элементами этой панели.
 */
public class CustomBar {

    private View customAppBarDecorView;
    private final int mainMenuBtn = R.id.main_menu_image_button;
    private final int appTrademark = R.id.trademark_image_view;
    private final int appLogoBtn = R.id.our_mission_image_button;
    private final int headerLogoutBtnId = R.id.authorization_image_button;
    private final String logoutItemText = "Log out";
    private final String menuListItemMainText = "Main";
    private final String menuListItemNewsText = "News";
    private final String menuListItemAboutText = "About";

    /**
     * Проверяет видимость панели навигации с торговой маркой.
     */
    public void checkHeaderVisibleWithBarTitle() {
        Allure.step("Проверка видимости панели навигации с торговой маркой");
        waitForElement(withId(appTrademark), 3000);
        onView(withId(appTrademark)).check(matches(isDisplayed()));
    }
    /**
     * Переходит на главную страницу через кнопку главного меню.
     */
    public void goToMainPageWithMenuBtn() {
        Allure.step("Переход на главную страницу через кнопку главного меню");
        waitForElement(withId(mainMenuBtn), 3000);
        onView(withId(mainMenuBtn)).perform(click());
        onView(withText(menuListItemMainText))
                .inRoot(withDecorView(Matchers.not(customAppBarDecorView)))
                .perform(click());
    }


    /**
     * Переходит на страницу новостей через кнопку главного меню.
     */
    public void goToNewsPageWithMenuBtn() {
        Allure.step("Переход на страницу новостей через кнопку главного меню");
        waitForElement(withId(mainMenuBtn), 3000);
        onView(withId(mainMenuBtn)).perform(click());
        onView(withText(menuListItemNewsText))
                .inRoot(withDecorView(Matchers.not(customAppBarDecorView)))
                .perform(click());
    }

    /**
     * Переходит на страницу "Наша миссия" через кнопку логотипа.
     */
    public void goToOurMissionPageWithLogoBtn() {
        Allure.step("Переход на страницу 'Наша миссия' через кнопку логотипа");
        waitForElement(withId(appLogoBtn), 3000);
        onView(withId(appLogoBtn)).perform(click());
    }

    /**
     * Переходит на страницу "О приложении" через кнопку главного меню.
     */
    public void goToAboutPageWithMenuBtn() {
        Allure.step("Переход на страницу 'О приложении' через кнопку главного меню");
        waitForElement(withId(mainMenuBtn), 3000);
        onView(withId(mainMenuBtn)).perform(click());
        onView(withText(menuListItemAboutText))
                .inRoot(withDecorView(Matchers.not(customAppBarDecorView)))
                .perform(click());
    }


    /**
     * Выходит из приложения через кнопку профиля на главной странице.
     */
    public void logOutWithHeaderBtn() {
        Allure.step("Выход из приложения через кнопку профиля на главной странице");
        waitForElement(withId(headerLogoutBtnId), 1500);
        onView(withId(headerLogoutBtnId)).perform(click());
        onView(withText(logoutItemText)).perform(click());
    }

}
