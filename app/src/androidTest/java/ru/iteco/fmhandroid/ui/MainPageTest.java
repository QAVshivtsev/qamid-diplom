package ru.iteco.fmhandroid.ui;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import TestData.DataHelper;
import TestPageObjects.AuthorizationPage;
import TestPageObjects.CustomBar;
import TestPageObjects.MainPage;
import TestPageObjects.NewsPage;
import io.qameta.allure.android.rules.ScreenshotRule;
import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.junit4.DisplayName;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class MainPageTest {

    // Правило для запуска основной активности приложения
    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    // Правило для создания скриншотов в случае сбоя теста
    @Rule
    public ScreenshotRule screenshotRule = new ScreenshotRule(ScreenshotRule.Mode.FAILURE,
            String.valueOf(System.currentTimeMillis()));

    // Объекты страниц для взаимодействия с элементами интерфейса
    MainPage mainPage = new MainPage();
    NewsPage newsPage = new NewsPage();
    AuthorizationPage authPage = new AuthorizationPage();
    DataHelper testUser = new DataHelper();
    CustomBar CustomBar = new CustomBar();

    @Before
    public void setUp() {
        try {
            // Проверка, авторизован ли пользователь
            mainPage.checkLogoutBtnVisible();
        } catch (Exception notLoggedIn) {
            System.out.println(notLoggedIn.getMessage());
            // Если не авторизован, выполняется вход
            authPage.checkAuthPageVisible();
            authPage.enterLogin(testUser.getHardcodeUser().getTestLogin());
            authPage.enterPassword(testUser.getHardcodeUser().getTestPassword());
            authPage.pressSignIn();
        }

        try {
            // Проверка, что заголовок видим и содержит правильный текст
            CustomBar.checkHeaderVisibleWithBarTitle();
            // Проверка, что главная страница видима
            mainPage.checkMainPageVisible();
        } catch (Exception notOnMainPage) {
            System.out.println(notOnMainPage.getMessage());
            // Если не на главной странице, переходим туда
            CustomBar.goToMainPageWithMenuBtn();
        }

    }

    @Test
    @DisplayName("Проверка видимости новостей на главной странице")
    public void testNewsPanelDisplay() {
        mainPage.checkMainPageVisible();
    }


    @Test
    @DisplayName("Проверка навигации на страницу новостей по кнопке All News")
     public void testNavigateToNewsPage() {
        mainPage.goToNewsPageWithAllNewsBtn();
        newsPage.checkNewsPageVisible();
 }
}
