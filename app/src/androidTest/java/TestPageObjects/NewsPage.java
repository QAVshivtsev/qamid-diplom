package TestPageObjects;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static TestData.DataHelper.waitForElement;

import android.app.Activity;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.contrib.RecyclerViewActions;

import com.forkingcode.espresso.contrib.DescendantViewActions;

import java.util.HashMap;

import TestData.DataHelper;
import TestUtils.GetTextAction;
import TestUtils.RecyclerCustomMatcher;
import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;

/**
 * Класс NewsPage представляет страницу новостей и предоставляет методы для взаимодействия с элементами этой страницы.
 */
public class NewsPage {

    private final int editNewsBtn = R.id.edit_news_material_button;
    private final String newsPageHeaderText = "News";
    private final int allNewsRecyclerList = R.id.news_list_recycler_view;
    private final int toggleNewsCardBtn = R.id.view_news_item_image_view;
    private final int newsCardDescription = R.id.news_item_description_text_view;
    private final int newsItemCard = R.id.news_item_material_card_view;
    private final int newsItemCardTitle = R.id.news_item_title_text_view;
    private final int sortNewsHeaderBtn = R.id.sort_news_material_button;
    private final int filterNewsHeaderBtn = R.id.filter_news_material_button;

    /**
     * Возвращает ID списка всех новостей.
     *
     * @return ID списка всех новостей.
     */
    public int getAllNewsRecyclerList() {
        return allNewsRecyclerList;
    }

    /**
     * Возвращает ID описания карточки новости.
     *
     * @return ID описания карточки новости.
     */
    public int getNewsCardDescription() {
        return newsCardDescription;
    }

    /**
     * Возвращает ID карточки новости.
     *
     * @return ID карточки новости.
     */
    public int getNewsItemCard() {
        return newsItemCard;
    }

    /**
     * Нажимает кнопку редактирования новостей для открытия панели управления новостями.
     */
    public void clickEditNewsBtn() {
        Allure.step("Нажатие кнопки 'Редактировать новости' в заголовке страницы и открытие панели управления новостями");
        waitForElement(withId(editNewsBtn), 3000);
        onView(withId(editNewsBtn)).perform(click());
    }

    /**
     * Проверяет видимость страницы новостей по тексту заголовка.
     */
    public void checkNewsPageVisible() {
        Allure.step("Проверка видимости страницы новостей по тексту заголовка");
        waitForElement(withText(newsPageHeaderText), 3000);
    }

    /**
     * Проверяет, что список новостей содержит хотя бы одну видимую позицию.
     *
     * @param totalPos общее количество позиций для проверки.
     */
    public void checkNewsRecyclerHasAtLeastPos(int totalPos) {
        Allure.step("Проверка, что список новостей содержит хотя бы одну видимую позицию");
        totalPos -= 1;
        waitForElement(withId(allNewsRecyclerList), 1000);
        try {
            onView(withId(allNewsRecyclerList))
                    .check(matches(RecyclerCustomMatcher
                            .atPosition(totalPos, hasDescendant(withId(newsItemCardTitle)))));
        } catch (NoMatchingViewException noCardsInList) {
            System.out.println(noCardsInList.getMessage());
        }
    }

    /**
     * Переключает состояние карточки новости на указанной позиции с помощью кнопки ( ˅ / ˄ ).
     *
     * @param cardPos позиция карточки для переключения.
     */
    public void toggleNewsCardStateWithBtnOnPos(int cardPos) {
        Allure.step("Переключение состояния карточки новости на указанной позиции с помощью кнопки ( ˅ / ˄ )");
        waitForElement(withId(toggleNewsCardBtn), 1000);
        onView(withId(allNewsRecyclerList))
                .perform(RecyclerViewActions.actionOnItemAtPosition(cardPos, DescendantViewActions
                        .performDescendantAction(withId(toggleNewsCardBtn), click())));
    }

    /**
     * Нажимает кнопку фильтрации новостей для открытия страницы фильтрации новостей.
     */
    public void clickFilterBtnToOpenFilterPage() {
        Allure.step("Открытие страницы фильтрации новостей с помощью кнопки 'Фильтр' в заголовке страницы");
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
        RecyclerView newsRecycler = currActivity.findViewById(allNewsRecyclerList);
        RecyclerView.Adapter newsAdapter = newsRecycler.getAdapter();
        int allNewsCount = newsAdapter.getItemCount();
        HashMap<Integer, String> newsCardsMap = new HashMap<>();
        for (int i = 0; i < allNewsCount; i++) {
            GetTextAction action = new GetTextAction();
            onView(withId(allNewsRecyclerList)).perform(scrollToPosition(i));
            onView(withId(allNewsRecyclerList)).perform(RecyclerViewActions.actionOnItemAtPosition(i, DescendantViewActions
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
}
