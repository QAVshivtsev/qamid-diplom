package ru.iteco.fmhandroid.ui;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import TestData.DataHelper;
import TestPageObjects.AuthorizationPage;
import TestPageObjects.CustomBar;
import TestPageObjects.MainPage;
import TestPageObjects.OurMissionPage;
import TestUtils.RecyclerCustomMatcher;
import io.qameta.allure.android.rules.ScreenshotRule;
import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.junit4.DisplayName;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class OurMissionPageTest {

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
    OurMissionPage ourMissionPage = new OurMissionPage();
    AuthorizationPage authPage = new AuthorizationPage();
    DataHelper testUser = new DataHelper();
    CustomBar CustomBar = new CustomBar();

    @Before
    public void setUp() {
        try {
            // Проверка, что кнопка выхода видима (пользователь авторизован)
            mainPage.checkLogoutBtnVisible();
        } catch (Exception notLoggedIn) {
            System.out.println(notLoggedIn.getMessage());
            // Если пользователь не авторизован, проверяем видимость страницы авторизации
            authPage.checkAuthPageVisible();
            // Ввод данных для авторизации
            authPage.enterLogin(testUser.getHardcodeUser().getTestLogin());
            authPage.enterPassword(testUser.getHardcodeUser().getTestPassword());
            authPage.pressSignIn();
        }

        try {
            // Проверка видимости заголовка и переход на страницу цитат
            CustomBar.checkHeaderVisibleWithBarTitle();
            CustomBar.goToOurMissionPageWithLogoBtn();
            ourMissionPage.checkPageVisibleByHeader(1000);
        } catch (Exception notOnQuotesPage) {
            System.out.println(notOnQuotesPage.getMessage());
            // Если не на странице цитат, переходим на неё
            CustomBar.goToOurMissionPageWithLogoBtn();
        }
    }

    @Test
    @DisplayName("Проверка перенаправления на страницу цитат")
    public void testNavigateToQuotesPage() {
        ourMissionPage.checkPageVisibleByHeader(1000);
    }

    @Test
    @DisplayName("Проверка видимости списка цитат")
    public void checkingTheVisibilityOfAListOfQuotes() {
        onView(withId(ourMissionPage.getQuoteListRecycler())).check(matches(isDisplayed()));
    }

    @Test
    @DisplayName("Проверка, список цитат содержит необходимое количество элементов")
    public void listOfQuotesRequiredNumberOfElements() {
        ourMissionPage.checkQuoteRecyclerHasAtLeastPos(ourMissionPage.getTotalQuotesInList());
    }

    @Test
    @DisplayName("Проверка, что цитата раскрывается при нажатии на кнопку раскрытия")
    public void expandQuoteOnExpandButton() {
        int cardPos = 0;
        ourMissionPage.toggleQuoteAtPosWithBtn(cardPos);
        onView(withId(ourMissionPage.getQuoteListRecycler()))
                .check(matches(RecyclerCustomMatcher
                        .atPosition(cardPos, hasDescendant(allOf(withId(ourMissionPage.getQuoteDescription()), isDisplayed())))));
    }

    @Test
    @DisplayName("Проверка, что цитата сворачивается при нажатии на кнопку сворачивания")
    public void collapsingQuoteToExpandButton() {
        int cardPos = 7;
        ourMissionPage.toggleQuoteAtPosWithBtn(cardPos);
        ourMissionPage.toggleQuoteAtPosWithBtn(cardPos);
        onView(withId(ourMissionPage.getQuoteListRecycler()))
                .check(matches(RecyclerCustomMatcher
                        .atPosition(cardPos,
                                hasDescendant(allOf(withId(ourMissionPage.getQuoteDescription()), not(isDisplayed()))))));
    }
}
