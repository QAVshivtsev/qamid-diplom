package ru.iteco.fmhandroid.ui;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static TestData.DataHelper.waitForElement;

import android.view.View;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import TestData.DataHelper;
import TestPageObjects.AuthorizationPage;
import TestPageObjects.CustomBar;
import TestPageObjects.MainPage;
import io.qameta.allure.android.rules.ScreenshotRule;
import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.junit4.DisplayName;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class AuthorizationPageTest {

    // Правило для запуска основной активности приложения
    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    // Правило для создания скриншотов в случае сбоя теста
    @Rule
    public ScreenshotRule screenshotRule = new ScreenshotRule(ScreenshotRule.Mode.FAILURE,
            String.valueOf(System.currentTimeMillis()));

    // Декоративное представление для проверки всплывающих сообщений
    private View authDecorView;

    // Объекты страниц для взаимодействия с элементами интерфейса
    AuthorizationPage authPage = new AuthorizationPage();
    MainPage mainPage = new MainPage();
    CustomBar CustomBar = new CustomBar();

    @Before
    public void beforeSession() {
        try {
            // Проверка, что страница авторизации видима
            authPage.checkAuthPageVisible();
        } catch (Exception notFound) {
            System.out.println(notFound.getMessage());
            // Если страница авторизации не видима, выполняется выход из аккаунта
            CustomBar.logOutWithHeaderBtn();
            authPage.checkAuthPageVisible();
        }
    }

    @After
    public void afterSession() {
        try {
            // Выход из аккаунта после каждого теста
            CustomBar.logOutWithHeaderBtn();
        } catch (Exception generalException) {
            System.out.println(generalException.getMessage());
        }
    }

    @Test
    @DisplayName("Проверка успешного входа с корректными данными")
    public void testSuccessfulLogin() {
        DataHelper testUser = new DataHelper();
        authPage.enterLogin(testUser.getHardcodeUser().getTestLogin());
        authPage.enterPassword(testUser.getHardcodeUser().getTestPassword());
        authPage.pressSignIn();
        waitForElement(withText(mainPage.getNewsAdapterTitleText()), 4000);
    }

    @Test
    @DisplayName("Проверка появления ошибки при вводе некорректных данных")
    public void testFailedLogin() {
        DataHelper testUser = new DataHelper();
        authPage.enterLogin(testUser.getErrorUser().getTestLogin());
        authPage.enterPassword(testUser.getErrorUser().getTestPassword());
        authPage.pressSignIn();
        String credsErrorText = authPage.getLoginErrorToastText();
        ActivityScenario<AppActivity> scenario = mActivityScenarioRule.getScenario();
        scenario.onActivity(activity -> authDecorView = activity.getWindow().getDecorView());
        onView(withText(credsErrorText)).inRoot(withDecorView(not(authDecorView)))
                .check(matches(isDisplayed()));
    }



    @Test
    @DisplayName("Проверка отображения сообщений об ошибках при пустых полях")
    public void testEmptyFieldsErrorMessage() {
        String emptyFields = "";
        authPage.enterLogin(emptyFields);
        authPage.enterPassword(emptyFields);
        authPage.pressSignIn();
        onView(withText(authPage.getEmptyLoginToastText())).inRoot(withDecorView(Matchers.not(authDecorView)))
                .check(matches(isDisplayed()));
    }
}