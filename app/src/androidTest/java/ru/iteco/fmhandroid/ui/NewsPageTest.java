package ru.iteco.fmhandroid.ui;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSubstring;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

import android.content.Context;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import TestData.DataHelper;
import TestPageObjects.AuthorizationPage;
import TestPageObjects.ControlPanelFilterPage;
import TestPageObjects.CreatingNewsPage;
import TestPageObjects.CustomBar;
import TestPageObjects.MainPage;
import TestPageObjects.NewsControlPanelPage;
import TestPageObjects.NewsFilterPage;
import TestPageObjects.NewsPage;
import TestUtils.RecyclerCustomMatcher;
import io.qameta.allure.android.rules.ScreenshotRule;
import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.junit4.DisplayName;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class NewsPageTest {

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
    NewsControlPanelPage newsControlPanelPage = new NewsControlPanelPage();
    CreatingNewsPage creatingNewsPage = new CreatingNewsPage();
    NewsFilterPage newsFilterPage = new NewsFilterPage();
    AuthorizationPage authPage = new AuthorizationPage();
    DataHelper testUser = new DataHelper();
    CustomBar CustomBar = new CustomBar();
    ControlPanelFilterPage controlPanelFilterPage = new ControlPanelFilterPage();
    CustomBar customBar = new CustomBar();
    private Context myContext;

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
            // Проверка видимости заголовка и переход на страницу новостей
            CustomBar.checkHeaderVisibleWithBarTitle();
            CustomBar.goToNewsPageWithMenuBtn();
            newsPage.checkNewsPageVisible();
        } catch (Exception notOnNewsPage) {
            System.out.println(notOnNewsPage.getMessage());
            // Если не на странице новостей, переходим на неё
            CustomBar.goToNewsPageWithMenuBtn();
        }

        myContext = InstrumentationRegistry.getInstrumentation().getContext();
    }

    private void createNews(String category, String title, String description) {
        // Нажатие кнопки добавления новости
        newsControlPanelPage.clickAddNewsBtn();
        // Проверка, что страница создания новости видима
        creatingNewsPage.checkPageVisibleByHeaderTitle();
        // Ввод данных новости
        creatingNewsPage.enterNewsTitle(title);
        creatingNewsPage.enterNewsCategoryWithKeyboard(category);
        creatingNewsPage.enterNewsDescription(description);
        creatingNewsPage.insertNewsPublDateByWidgetNoOffset();
        creatingNewsPage.insertNewsPublTimeByWidgetNoOffset();
        // Сохранение новости
        creatingNewsPage.pressSaveToCreateNews();
    }

    @Test
    @DisplayName("Проверка видимости новостей на странице новостей")
    public void testNewsDisplayOnNewsPage() {
        newsPage.checkNewsPageVisible();
    }

    @Test
    @DisplayName("Проверка, что карточка новости разворачивается и сворачивается")
    public void testNewsDetailsDisplay() {
        int cardPos = 0;
        newsPage.checkNewsPageVisible();
        newsPage.checkNewsRecyclerHasAtLeastPos(1);
        newsPage.toggleNewsCardStateWithBtnOnPos(cardPos);
        onView(RecyclerCustomMatcher.withIndex(withId(newsPage.getNewsItemCard()), cardPos))
                .check(matches(hasDescendant(allOf(withId(newsPage.getNewsCardDescription()), isDisplayed()))));
        newsPage.toggleNewsCardStateWithBtnOnPos(cardPos);
        onView(RecyclerCustomMatcher.withIndex(withId(newsPage.getNewsItemCard()), cardPos))
                .check(matches(hasDescendant(allOf(withId(newsPage.getNewsCardDescription()), not(isDisplayed())))));
    }

    @Test
    @DisplayName("Проверка, что порядок карточек новостей изменяется после нажатия на кнопку сортировка")
    public void testNewsCardOrderChangesAfterSortButtonClick() {
        newsPage.checkNewsPageVisible();
        newsPage.checkNewsRecyclerHasAtLeastPos(2);
        HashMap<Integer, String> cardMap0 = newsPage.createHashMapForCardsInList();
        newsPage.toggleNewsCardOrderWithSortBtn();
        boolean isListReversed = newsPage.checkIfCardListOrderIsReversed(cardMap0);
        Assert.assertTrue("Проверка, что список отсортировался", isListReversed == true);
    }


    @Test
    @DisplayName("Проверка, что страница фильтрации открывается")
    public void testFilterPageOpensAfterFilterButtonClick() {
        newsPage.checkNewsPageVisible();
        newsPage.clickFilterBtnToOpenFilterPage();
        newsFilterPage.checkPageVisibleByTitle();
    }

    @Test
    @DisplayName("Проверка, что фильтр по категории отображает новости выбранной категории")
    public void testFilterNewsByCategory() {
        newsPage.checkNewsPageVisible();
        newsPage.clickEditNewsBtn();
        newsControlPanelPage.clickAddNewsBtn();

        DataHelper testNewsMassage = new DataHelper();
        String testCategory = testNewsMassage.createRandomNewsCategory();
        String testTitle = testNewsMassage.createRandomNewsTitle();
        String testDescr = testNewsMassage.createRandomNewsDescription();

        creatingNewsPage.checkPageVisibleByHeaderTitle();
        creatingNewsPage.enterNewsCategoryWithKeyboard(testCategory);
        creatingNewsPage.enterNewsTitle(testTitle);
        creatingNewsPage.enterNewsDescription(testDescr);
        creatingNewsPage.insertNewsPublDateByWidgetNoOffset();
        creatingNewsPage.insertNewsPublTimeByWidgetNoOffset();
        creatingNewsPage.pressSaveToCreateNews();

        CustomBar.goToNewsPageWithMenuBtn();
        newsPage.clickFilterBtnToOpenFilterPage();
        newsFilterPage.insertNewsCategoryWithKeyboard(testCategory);
        newsFilterPage.clickApplyFilterBtn();
        onView(withId(newsPage.getAllNewsRecyclerList()))
                .check(matches(hasDescendant(withText(testTitle))));
    }



    @Test
    @DisplayName("Проверка навигации на страницу управления новостями")
    public void testNavigateToNewsManagement() {
        newsPage.clickEditNewsBtn();
        newsControlPanelPage.checkAllNewsRecyclerListVisible();
    }

    @Test
    @DisplayName("Проверка, что новость создается.")
    public void testCreateNews() {
        newsPage.clickEditNewsBtn();
        DataHelper testNewsData = new DataHelper();
        String testTitle = testNewsData.createRandomNewsTitle();
        String testDescr = testNewsData.createRandomNewsDescription();
        String testCategory = testNewsData.createRandomNewsCategory();
        createNews(testCategory, testTitle, testDescr);
        int cardPos = newsControlPanelPage.checkIfNewsListHasDescription(true, testDescr);
        newsControlPanelPage.scrollToCardAtPosition(cardPos);
        onView(withId(newsControlPanelPage.getAllNewsCardsBlockList()))
                .check(matches(RecyclerCustomMatcher
                        .atPosition(cardPos, hasDescendant(withSubstring(testTitle)))));
    }

    @Test
    @DisplayName("Проверка сортировки новостей на странице управления новостями.")
    public void testNewsSortingOnNewsManagementPage() {
        newsPage.clickEditNewsBtn();
        newsControlPanelPage.checkNewsRecyclerHasAtLeastPosition(2);
        HashMap<Integer, String> cardMap0 = newsControlPanelPage.createHashMapForCardsInList();
        newsControlPanelPage.toggleNewsCardOrderWithSortBtn();
        boolean isListReversed = newsControlPanelPage.checkIfCardListOrderIsReversed(cardMap0);
        Assert.assertTrue("Проверка, изменения порядка новостей", isListReversed);
    }

    @Test
    @DisplayName("Проверка, открытия страницы фильтров новостей на странцие управления новостями.")
    public void testOpeningNewsFiltersPageOnNewsManagementPage() {
        newsPage.clickEditNewsBtn();
        newsControlPanelPage.clickFilterNewsBtn();
        controlPanelFilterPage.checkPageVisibleByTitle();
    }

    @Test
    @DisplayName("Проверка, что фильтр отображает новости выбранной категории на странице управления новостями")
    public void testFilterDisplaysSelectedCategoryNewsOnNewsManagementPage() {
        newsPage.clickEditNewsBtn();
        newsControlPanelPage.clickAddNewsBtn();
        DataHelper testNewsData = new DataHelper();
        String testCategory = testNewsData.createRandomNewsCategory();
        String testTitle = testNewsData.createRandomNewsTitle();
        String testDescr = testNewsData.createRandomNewsDescription();
        creatingNewsPage.checkPageVisibleByHeaderTitle();
        creatingNewsPage.enterNewsCategoryWithKeyboard(testCategory);
        creatingNewsPage.enterNewsTitle(testTitle);
        creatingNewsPage.enterNewsDescription(testDescr);
        creatingNewsPage.insertNewsPublDateByWidgetNoOffset();
        creatingNewsPage.insertNewsPublTimeByWidgetNoOffset();
        creatingNewsPage.pressSaveToCreateNews();
        newsControlPanelPage.clickFilterNewsBtn();
        controlPanelFilterPage.selectNewsCategoryFromDropdown(testCategory);
        controlPanelFilterPage.clickFilterBtn();
        newsControlPanelPage.checkAllNewsRecyclerListVisible();
        newsControlPanelPage.checkNewsRecyclerHasAtLeastPosition(1);
        newsControlPanelPage.checkIfNewsListHasTitle(true, testTitle);
    }



    @Test
    @DisplayName("Проверка удаления новости.")
    public void testDeleteNews() {
        newsPage.clickEditNewsBtn();
        DataHelper testNewsData = new DataHelper();
        String testTitle = testNewsData.createRandomNewsTitle();
        String testDescr = testNewsData.createRandomNewsDescription();
        String testCategory = testNewsData.createRandomNewsCategory();
        createNews(testCategory, testTitle, testDescr);
        newsControlPanelPage.clickDeleteOnNewsItemWithMatchingTitle(testTitle);
        newsControlPanelPage.confirmNewsItemDeletion();
        newsControlPanelPage.checkIfNewsListHasTitle(false, testTitle);
    }
}
