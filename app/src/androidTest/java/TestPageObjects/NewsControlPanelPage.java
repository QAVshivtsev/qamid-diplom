package TestPageObjects;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.hasSibling;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;
import static TestData.DataHelper.waitForElement;

import android.app.Activity;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.PerformException;
import androidx.test.espresso.contrib.RecyclerViewActions;

import com.forkingcode.espresso.contrib.DescendantViewActions;

import org.hamcrest.Matchers;

import java.util.HashMap;

import TestData.DataHelper;
import TestUtils.GetTextAction;
import TestUtils.RecyclerCustomMatcher;
import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;

/**
 * Класс NewsControlPanelPage представляет панель управления новостями и предоставляет методы для взаимодействия с элементами этой панели.
 */
public class NewsControlPanelPage {

    private View newsControlPanelDecorView;
    private final int addNewsBtn = R.id.add_news_image_view;
    private final String newsCtrlPanelText = "Control panel";
    private final int allNewsCardsBlockList = R.id.news_list_recycler_view;
    private final int delNewsCardBtn = R.id.delete_news_item_image_view;
    private final int newsCardTitle = R.id.news_item_title_text_view;
    private final int sortNewsHeaderBtn = R.id.sort_news_material_button;
    private final int filterNewsHeaderBtn = R.id.filter_news_material_button;
    private final int newsItemCardTitle = R.id.news_item_title_text_view;
    private final int newsCardDescr = R.id.news_item_description_text_view;
    private final int refreshPageBtn = R.id.control_panel_news_retry_material_button;
    private final String okBtnText = "OK";

    /**
     * Возвращает ID блока всех карточек новостей.
     *
     * @return ID блока всех карточек новостей.
     */
    public int getAllNewsCardsBlockList() {
        return allNewsCardsBlockList;
    }

    /**
     * Нажимает кнопку "Добавить новость" для открытия страницы создания новости.
     */
    public void clickAddNewsBtn() {
        Allure.step("Нажатие кнопки 'Добавить новость' для открытия страницы создания новости");
        waitForElement(withId(addNewsBtn), 1000);
        onView(withId(addNewsBtn)).perform(click());
    }

    /**
     * Проверяет видимость панели управления новостями по тексту заголовка.
     *
     * @param timeToWait время ожидания в миллисекундах.
     */
    public void checkNewsControlPageVisible(int timeToWait) {
        Allure.step("Проверка видимости панели управления новостями по тексту заголовка");
        waitForElement(withText(newsCtrlPanelText), timeToWait);
    }

    /**
     * Проверяет видимость списка новостей на странице.
     */
    public void checkAllNewsRecyclerListVisible() {
        Allure.step("Проверка видимости списка новостей на странице");
        waitForElement(withId(allNewsCardsBlockList), 1000);
        onView(withId(allNewsCardsBlockList)).check(matches(isDisplayed()));
    }

    /**
     * Нажимает кнопку удаления новости для карточки с заданным заголовком.
     *
     * @param titleToMatch заголовок новости для удаления.
     */
    public void clickDeleteOnNewsItemWithMatchingTitle(String titleToMatch) {
        Allure.step("Нажатие кнопки 'Удалить новость' для карточки с заданным заголовком");

        DataHelper activity = new DataHelper();
        Activity currActivity = activity.getCurrentActivity();
        RecyclerView newsRecycler = currActivity.findViewById(allNewsCardsBlockList);
        RecyclerView.Adapter newsAdapter = newsRecycler.getAdapter();

        int allNewsCount = newsAdapter.getItemCount();
        int matchPos = -1;
        for (int i = 0; i < allNewsCount; i++) {
            GetTextAction action = new GetTextAction();
            onView(withId(allNewsCardsBlockList)).perform(scrollToPosition(i));
            onView(withId(allNewsCardsBlockList))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(i, DescendantViewActions
                            .performDescendantAction(withId(newsCardTitle), action)));
            String cardTitle = action.getText();

            if (cardTitle.equals(titleToMatch)) {
                matchPos = i;
            }
        }
        try {
            onView(withId(allNewsCardsBlockList)).perform(scrollToPosition(matchPos));
            onView(withId(allNewsCardsBlockList)).perform(RecyclerViewActions
                    .actionOnItemAtPosition(matchPos, DescendantViewActions
                            .performDescendantAction(withId(delNewsCardBtn), click())));
        } catch (PerformException noSuchTitle) {
            System.out.println(noSuchTitle.getMessage());
        }
    }

    /**
     * Проверяет наличие карточки новости с заданным заголовком в списке.
     *
     * @param isPositiveCheck флаг для положительной или отрицательной проверки.
     * @param titleToCheck заголовок новости для проверки.
     * @return позиция карточки, если найдена, иначе -1.
     */
    public int checkIfNewsListHasTitle(boolean isPositiveCheck, String titleToCheck) {
        Allure.step("Проверка наличия карточки новости с заданным заголовком в списке");
        DataHelper activity = new DataHelper();
        Activity currActivity = activity.getCurrentActivity();
        RecyclerView newsRecycler = currActivity.findViewById(allNewsCardsBlockList);
        RecyclerView.Adapter newsAdapter = newsRecycler.getAdapter();
        int matchPos = -1;
        int allNewsCount = newsAdapter.getItemCount();
        for (int i = 0; i < allNewsCount; i++) {
            GetTextAction action = new GetTextAction();
            onView(withId(allNewsCardsBlockList))
                    .perform(scrollToPosition(i));
            onView(withId(allNewsCardsBlockList))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(i, DescendantViewActions
                            .performDescendantAction(withId(newsCardTitle), action)));
            String cardTitle = action.getText();
            if (cardTitle.equals(titleToCheck)) {
                matchPos = i;
                break;
            }
        }

        if (isPositiveCheck && (matchPos >= 0)) {
            onView(withId(allNewsCardsBlockList))
                    .perform(RecyclerViewActions.scrollToPosition(matchPos));
            onView(withId(allNewsCardsBlockList))
                    .check(matches(RecyclerCustomMatcher.atPosition(matchPos, hasDescendant(withText(titleToCheck)))));
        } else {
            if (allNewsCount > 0) {
                try {
                    matchPos = 0;
                    onView(withId(allNewsCardsBlockList))
                            .perform(RecyclerViewActions.scrollToPosition(matchPos));
                    onView(withId(allNewsCardsBlockList))
                            .check(matches(RecyclerCustomMatcher.atPosition(matchPos, not(hasDescendant(withText(titleToCheck))))));
                } catch (NoMatchingViewException titleMismatch) {
                    System.out.println(titleMismatch.getMessage());
                }
            } else {
                onView(withId(allNewsCardsBlockList))
                        .check(matches(hasSibling(allOf(withId(refreshPageBtn), isDisplayed()))));
            }
        }
        return matchPos;
    }

    /**
     * Проверяет наличие карточки новости с заданным описанием в списке.
     *
     * @param isPositiveCheck флаг для положительной или отрицательной проверки.
     * @param descrToCheck описание новости для проверки.
     * @return позиция карточки, если найдена, иначе -1.
     */
    public int checkIfNewsListHasDescription(boolean isPositiveCheck, String descrToCheck) {
        Allure.step("Проверка наличия карточки новости с заданным описанием в списке");
        DataHelper activity = new DataHelper();
        Activity currActivity = activity.getCurrentActivity();
        RecyclerView newsRecycler = currActivity.findViewById(allNewsCardsBlockList);
        RecyclerView.Adapter newsAdapter = newsRecycler.getAdapter();
        int matchPos = -1;
        int allNewsCount = newsAdapter.getItemCount();
        for (int i = 0; i < allNewsCount; i++) {
            GetTextAction action = new GetTextAction();
            onView(withId(allNewsCardsBlockList))
                    .perform(scrollToPosition(i))
                    .perform(click());
            onView(withId(allNewsCardsBlockList))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(i, DescendantViewActions
                            .performDescendantAction(withId(newsCardDescr), action)));
            String cardDescr = action.getText();
            if (cardDescr.equals(descrToCheck)) {
                matchPos = i;
                break;
            }
        }

        if (isPositiveCheck && (matchPos >= 0)) {
            onView(withId(allNewsCardsBlockList))
                    .perform(RecyclerViewActions.scrollToPosition(matchPos))
                    .perform(click());
            onView(withId(allNewsCardsBlockList))
                    .check(matches(RecyclerCustomMatcher.atPosition(matchPos, hasDescendant(withText(descrToCheck)))));
        } else {
            if (allNewsCount > 0) {
                try {
                    matchPos = 0;
                    onView(withId(allNewsCardsBlockList))
                            .perform(RecyclerViewActions.scrollToPosition(matchPos))
                            .perform(click());
                    onView(withId(allNewsCardsBlockList))
                            .check(matches(RecyclerCustomMatcher.atPosition(matchPos, not(hasDescendant(withText(descrToCheck))))));
                } catch (NoMatchingViewException titleMismatch) {
                    System.out.println(titleMismatch.getMessage());
                }
            } else {
                onView(withId(allNewsCardsBlockList))
                        .check(matches(hasSibling(allOf(withId(refreshPageBtn), isDisplayed()))));
            }
        }
        return matchPos;
    }

    /**
     * Проверяет, что список новостей содержит хотя бы одну видимую позицию.
     *
     * @param totalPos общее количество позиций для проверки.
     */
    public void checkNewsRecyclerHasAtLeastPosition(int totalPos) {
        Allure.step("Проверка, что список новостей содержит хотя бы одну видимую позицию");
        totalPos -= 1;
        waitForElement(withId(allNewsCardsBlockList), 1000);
        try {
            onView(withId(allNewsCardsBlockList))
                    .check(matches(RecyclerCustomMatcher
                            .atPosition(totalPos, hasDescendant(withId(newsItemCardTitle)))));
        } catch (NoMatchingViewException noCardsInList) {
            System.out.println(noCardsInList.getMessage());
        }
    }

    /**
     * Прокручивает список новостей к карточке на указанной позиции.
     *
     * @param cardPos позиция карточки для прокрутки.
     */
    public void scrollToCardAtPosition(int cardPos) {
        Allure.step("Прокрутка списка новостей к карточке на указанной позиции");
        waitForElement(withId(allNewsCardsBlockList), 1000);
        onView(withId(allNewsCardsBlockList))
                .perform(RecyclerViewActions.scrollToPosition(cardPos));
    }

    /**
     * Нажимает кнопку фильтрации новостей для открытия страницы фильтрации новостей.
     */
    public void clickFilterNewsBtn() {
        Allure.step("Нажатие кнопки 'Фильтр новостей' для открытия страницы фильтрации новостей");
        waitForElement(withId(filterNewsHeaderBtn), 1000);
        onView(withId(filterNewsHeaderBtn)).perform(click());
    }

    /**
     * Переключает порядок карточек новостей с помощью кнопки сортировки.
     */
    public void toggleNewsCardOrderWithSortBtn() {
        Allure.step("Переключение порядка карточек новостей с помощью кнопки сортировки");
        waitForElement(withId(sortNewsHeaderBtn), 1000);
        onView(withId(sortNewsHeaderBtn)).perform(click());
    }

    /**
     * Создает хэш-таблицу структуры <[int] Позиция карточки, [String] Заголовок карточки> для последующих проверок.
     *
     * @return хэш-таблица с позициями и заголовками карточек.
     */
    public HashMap<Integer, String> createHashMapForCardsInList() {
        Allure.step("Создание хэш-таблицы структуры <[int] Позиция карточки, [String] Заголовок карточки> для последующих проверок");

        DataHelper activity = new DataHelper();
        Activity currActivity = activity.getCurrentActivity();
        RecyclerView newsRecycler = currActivity.findViewById(allNewsCardsBlockList);
        RecyclerView.Adapter newsAdapter = newsRecycler.getAdapter();
        int allNewsCount = newsAdapter.getItemCount();
        HashMap<Integer, String> newsCardsMap = new HashMap<>();
        for (int i = 0; i < allNewsCount; i++) {
            GetTextAction action = new GetTextAction();
            onView(withId(allNewsCardsBlockList)).perform(scrollToPosition(i));
            onView(withId(allNewsCardsBlockList)).perform(RecyclerViewActions.actionOnItemAtPosition(i, DescendantViewActions
                    .performDescendantAction(withId(newsItemCardTitle), action)));
            String cardTitle = action.getText();
            newsCardsMap.put(i, cardTitle);
        }
        return newsCardsMap;
    }

    /**
     * Проверяет, что порядок элементов в предоставленной хэш-таблице изменен.
     *
     * @param mapToCheck хэш-таблица для проверки.
     * @return true, если порядок изменен, иначе false.
     */
    public boolean checkIfCardListOrderIsReversed(HashMap<Integer, String> mapToCheck) {
        Allure.step("Проверка, что порядок элементов в предоставленной хэш-таблице изменен");
        boolean isReversed = false;
        try {
            HashMap<Integer, String> refMap = createHashMapForCardsInList();
            int mapSize = mapToCheck.size();
            int matchCount = 0;
            for (int i = 0; i < mapSize; i++) {
                String refTitle = refMap.get(mapSize - i - 1);
                String checkTitle = mapToCheck.get(i);
                if (refTitle.equals(checkTitle)) {
                    matchCount += 1;
                }
            }

            if (matchCount == mapSize) {
                isReversed = true;
            }
        } catch (Exception mapsNonComparable) {
            System.out.println(mapsNonComparable.getMessage());
        }
        return isReversed;
    }

    /**
     * Подтверждает удаление новости нажатием кнопки "OK" во всплывающем окне.
     */
    public void confirmNewsItemDeletion() {
        Allure.step("Подтверждение удаления новости нажатием кнопки 'OK' во всплывающем окне");
        onView(withText(okBtnText)).inRoot(withDecorView(Matchers.not(newsControlPanelDecorView)))
                .check(matches(isDisplayed())).perform(click());
    }
}
