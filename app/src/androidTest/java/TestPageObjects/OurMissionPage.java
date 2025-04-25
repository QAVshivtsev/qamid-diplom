package TestPageObjects;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static TestData.DataHelper.waitForElement;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.contrib.RecyclerViewActions;

import com.forkingcode.espresso.contrib.DescendantViewActions;

import TestUtils.RecyclerCustomMatcher;
import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;

/**
 * Класс OurMissionPage представляет страницу "Наша миссия" и предоставляет методы для взаимодействия с элементами этой страницы.
 */
public class OurMissionPage {

    private final int loveIsGumHeader = R.id.our_mission_title_text_view;
    private final int quoteListRecycler = R.id.our_mission_item_list_recycler_view;
    private final int quoteTitle = R.id.our_mission_item_title_text_view;
    private final int totalQuotesInList = 8;
    private final int toggleQuoteBtn = R.id.our_mission_item_open_card_image_button;
    private final int quoteDescription = R.id.our_mission_item_description_text_view;

    /**
     * Возвращает общее количество цитат в списке.
     *
     * @return общее количество цитат в списке.
     */
    public int getTotalQuotesInList() {
        return totalQuotesInList;
    }

    /**
     * Возвращает ID списка цитат.
     *
     * @return ID списка цитат.
     */
    public int getQuoteListRecycler() {
        return quoteListRecycler;
    }

    /**
     * Возвращает ID описания цитаты.
     *
     * @return ID описания цитаты.
     */
    public int getQuoteDescription() {
        return quoteDescription;
    }

    /**
     * Проверяет видимость страницы "Наша миссия" по заголовку.
     *
     * @param timeToWait время ожидания в миллисекундах.
     */
    public void checkPageVisibleByHeader(int timeToWait) {
        Allure.step("Проверка видимости страницы 'Наша миссия' по заголовку");
        waitForElement(withId(loveIsGumHeader), timeToWait);
    }

    /**
     * Проверяет, что список цитат содержит хотя бы одну видимую позицию.
     *
     * @param totalPos общее количество позиций для проверки.
     */
    public void checkQuoteRecyclerHasAtLeastPos(int totalPos) {
        Allure.step("Проверка, что список цитат содержит хотя бы одну видимую позицию");
        totalPos -= 1;
        waitForElement(withId(quoteListRecycler), 1000);
        try {
            onView(withId(quoteListRecycler))
                    .perform(RecyclerViewActions.scrollToPosition(totalPos));
            onView(withId(quoteListRecycler))
                    .check(matches(RecyclerCustomMatcher
                            .atPosition(totalPos, hasDescendant(withId(quoteTitle)))));
        } catch (NoMatchingViewException noCardsInList) {
            System.out.println(noCardsInList.getMessage());
        }
    }

    /**
     * Переключает состояние карточки цитаты на указанной позиции с помощью кнопки ( ˅ / ˄ ).
     *
     * @param quotePos позиция карточки для переключения.
     */
    public void toggleQuoteAtPosWithBtn(int quotePos) {
        Allure.step("Нажатие кнопки ( ˅ / ˄ ) для переключения состояния карточки цитаты");
        waitForElement(withId(toggleQuoteBtn), 1000);
        onView(withId(quoteListRecycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition(quotePos, DescendantViewActions
                        .performDescendantAction(withId(toggleQuoteBtn), click())));
    }
}
