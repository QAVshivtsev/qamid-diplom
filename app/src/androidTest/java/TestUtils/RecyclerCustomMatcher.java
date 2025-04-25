package TestUtils;

import static androidx.core.util.Preconditions.checkNotNull;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.matcher.BoundedMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Класс RecyclerCustomMatcher предоставляет пользовательские сопоставители для работы с элементами RecyclerView.
 */
public class RecyclerCustomMatcher {

    /**
     * Создает сопоставитель для элемента на указанной позиции в RecyclerView.
     *
     * @param position позиция элемента.
     * @param itemMatcher сопоставитель для элемента.
     * @return сопоставитель для элемента на указанной позиции.
     */
    public static Matcher<View> atPosition(final int position, @NonNull final Matcher<View> itemMatcher) {
        checkNotNull(itemMatcher);
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has item at position " + position + ": ");
                itemMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(final RecyclerView view) {
                RecyclerView.ViewHolder viewHolder = view.findViewHolderForAdapterPosition(position);
                if (viewHolder == null) {
                    return false;
                }
                return itemMatcher.matches(viewHolder.itemView);
            }
        };
    }

    /**
     * Создает сопоставитель для элемента с указанным индексом.
     *
     * @param matcher сопоставитель для элемента.
     * @param index индекс элемента.
     * @return сопоставитель для элемента с указанным индексом.
     */
    public static Matcher<View> withIndex(final Matcher<View> matcher, final int index) {
        return new TypeSafeMatcher<View>() {
            int currentIndex = 0;

            @Override
            public void describeTo(Description description) {
                description.appendText("with index: ");
                description.appendValue(index);
                matcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                return matcher.matches(view) && currentIndex++ == index;
            }
        };
    }
}
