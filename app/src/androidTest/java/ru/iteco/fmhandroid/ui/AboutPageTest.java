package ru.iteco.fmhandroid.ui;

import android.content.Intent;

import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import TestData.DataHelper;
import TestPageObjects.AboutPage;
import TestPageObjects.AuthorizationPage;
import TestPageObjects.CustomBar;
import TestPageObjects.MainPage;
import io.qameta.allure.android.rules.ScreenshotRule;
import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.junit4.DisplayName;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class AboutPageTest {

    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    @Rule
    public ScreenshotRule screenshotRule = new ScreenshotRule(ScreenshotRule.Mode.FAILURE,
            String.valueOf(System.currentTimeMillis()));

    // Объекты страниц для взаимодействия с элементами интерфейса
    MainPage mainPage = new MainPage();
    AboutPage aboutPage = new AboutPage();
    AuthorizationPage authPage = new AuthorizationPage();
    DataHelper testUser = new DataHelper();
    CustomBar customBar = new CustomBar();

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
            // Проверка, находимся ли мы на странице "О приложении"
            customBar.checkHeaderVisibleWithBarTitle();
            customBar.goToAboutPageWithMenuBtn();
            aboutPage.checkPageVisibleByHeader();
        } catch (Exception notOnAboutPage) {
            System.out.println(notOnAboutPage.getMessage());
            // Если не на странице "О приложении", переходим туда
            customBar.goToAboutPageWithMenuBtn();
        }
    }

    @Test
    @DisplayName("Проверка навигации на страницу About")
    public void testNavigateToAboutPage() {
        aboutPage.checkPageVisibleByHeader();
    }

    @Test
    @DisplayName("Проверка отображения ссылок")
    public void testAboutPageLinksDisplay() {
        aboutPage.checkAboutPageLinksDisplay();
    }

    @Test
    @DisplayName("Проверка открытия ссылки 'Политика конфиденциальности'")
    public void openinglinkPrivacyPolicy() {
        Intents.init();
        aboutPage.clickPrivacyPolicyLink();
        Intents.intended(IntentMatchers.hasAction(Intent.ACTION_VIEW));
        Intents.intended(IntentMatchers.hasData(aboutPage.getPrivacyLinkText()));
        Intents.release();
    }

    @Test
    @DisplayName("Проверка открытия ссылки 'Условия использования'")
    public void openingTheTermsOfUseLink() {
        Intents.init();
        aboutPage.clickTermsOfUseLink();
        Intents.intended(IntentMatchers.hasAction(Intent.ACTION_VIEW));
        Intents.intended(IntentMatchers.hasData(aboutPage.getTermsLinkText()));
        Intents.release();
    }
}
