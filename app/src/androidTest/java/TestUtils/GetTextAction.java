package TestUtils;

import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import org.hamcrest.Matcher;

/**
 * Класс GetTextAction представляет действие для получения текста из TextView.
 */
public class GetTextAction implements ViewAction {

    private String text;

    @Override
    public Matcher<View> getConstraints() {
        return isAssignableFrom(TextView.class);
    }

    @Override
    public String getDescription() {
        return "Получение текста";
    }

    @Override
    public void perform(UiController uiController, View view) {
        TextView textView = (TextView) view;
        text = textView.getText().toString();
    }

    @Nullable
    public String getText() {
        return text;
    }
}
