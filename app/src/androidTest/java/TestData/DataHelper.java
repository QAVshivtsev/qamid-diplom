package TestData;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;

import static org.apache.commons.lang3.StringUtils.capitalize;
import static java.lang.System.currentTimeMillis;

import android.app.Activity;
import android.view.View;
import com.github.javafaker.Faker;

import java.util.Locale;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.PerformException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.util.HumanReadables;
import androidx.test.espresso.util.TreeIterables;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import androidx.test.runner.lifecycle.Stage;


import org.hamcrest.Matcher;

import java.util.Collection;
import java.util.Random;
import java.util.concurrent.TimeoutException;

public class DataHelper {

    /**
     * Класс TestUser представляет тестового пользователя с логином и паролем.
     */
    public static class TestUser {
        private final String testLogin;
        private final String testPassword;

        public TestUser(String testLogin, String testPassword) {
            this.testLogin = testLogin;
            this.testPassword = testPassword;
        }

        public String getTestLogin() {
            return testLogin;
        }

        public String getTestPassword() {
            return testPassword;
        }
    }


    /**
     * Возвращает тестового пользователя с заранее заданными логином и паролем.
     *
     * @return объект TestUser с заранее заданными логином и паролем.
     */
    public TestUser getHardcodeUser() {
        return new TestUser("login2", "password2");
    }

    /**
     * Возвращает тестового пользователя с ошибочными логином и паролем.
     *
     * @return объект TestUser с ошибочными логином и паролем.
     */
    public TestUser getErrorUser() {
        return new TestUser("loginError", "passwordError");
    }

    /**
     * Ожидает появления элемента на экране в течение заданного времени.
     *
     * @param elViewMatcher Matcher для элемента, который нужно дождаться.
     * @param waitTime      Время ожидания в миллисекундах.
     * @return ViewAction для ожидания элемента.
     */
    public static ViewAction waitUntilElementDisplayed(final Matcher<View> elViewMatcher, final long waitTime) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "Ожидание появления элемента " + waitTime + " миллисекунд";
            }

            @Override
            public void perform(final UiController uiController, final View view) {
                uiController.loopMainThreadUntilIdle();
                final long startTime = currentTimeMillis();
                final long endTime = startTime + waitTime;
                final Matcher<View> viewMatcher = elViewMatcher;
                final Matcher<View> matchDisplayed = isDisplayed();

                do {
                    for (View child : TreeIterables.breadthFirstViewTraversal(view)) {
                        try {
                            if (viewMatcher.matches(child) && matchDisplayed.matches(child)) {
                                return;
                            }
                        } catch (NoMatchingViewException viewNotFound) {
                        }
                        uiController.loopMainThreadForAtLeast(100);
                    }
                } while (currentTimeMillis() < endTime);

                throw new PerformException.Builder()
                        .withActionDescription(this.getDescription())
                        .withViewDescription(HumanReadables.describe(view))
                        .withCause(new TimeoutException())
                        .build();
            }
        };
    }
    /**
     * Ожидает появления элемента на экране в течение заданного времени.
     *
     * @param itemMatcher Matcher для элемента, который нужно дождаться.
     * @param waitMlsec   Время ожидания в миллисекундах.
     */
    public static void waitForElement(Matcher<View> itemMatcher, long waitMlsec) {
        onView(isRoot()).perform(waitUntilElementDisplayed(itemMatcher, waitMlsec));
    }

    /**
     * Возвращает текущую активность.
     *
     * @return текущая активность.
     */
    public Activity getCurrentActivity() {
        final Activity[] currentActivity = new Activity[1];
        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> {
            Collection<Activity> allActivities = ActivityLifecycleMonitorRegistry.getInstance()
                    .getActivitiesInStage(Stage.RESUMED);
            if (!allActivities.isEmpty()) {
                currentActivity[0] = allActivities.iterator().next();
            }
        });
        return currentActivity[0];
    }

    /**
     * Создает случайную категорию новости.
     *
     * @return случайная категория новости.
     */
    public static String createRandomNewsCategory() {
        String[] categories = {
                "Объявление",
                "День рождения",
                "Зарплата",
                "Профсоюз",
                "Праздник",
                "Массаж",
                "Благодарность",
                "Нужна помощь"
        };

        Random randIndex = new Random();
        int pickIndex = randIndex.nextInt(categories.length);
        return categories[pickIndex];
    }
    /**
     * Создает случайный заголовок новости.
     *
     * @return случайный заголовок новости.
     */
    public static String createRandomNewsTitle() {
        Faker faker = new Faker(new Locale("en"));
        String[] bugTypes = {"Functional Bug", "Performance Bug", "Security Bug", "Usability Bug", "Compatibility Bug"};
        String[] testingMethods = {"Manual Testing", "Automated Testing", "Exploratory Testing", "Regression Testing", "Load Testing"};
        String[] bugTrackingTools = {"JIRA", "Bugzilla", "Trello", "Redmine", "Mantis"};

        return capitalize(faker.company().bs()) + " involves " +
                bugTypes[faker.random().nextInt(0, bugTypes.length - 1)] + " discovered during " +
                testingMethods[faker.random().nextInt(0, testingMethods.length - 1)] + " and tracked using " +
                bugTrackingTools[faker.random().nextInt(0, bugTrackingTools.length - 1)] + ".";
    }
    /**
     * Создает случайное описание новости.
     *
     * @return случайное описание новости.
     */
    public static String createRandomNewsDescription() {
        Faker faker = new Faker(new Locale("en"));
        String[] testTypes = {"Unit Test", "Integration Test", "System Test", "Acceptance Test", "Performance Test"};
        String[] testTools = {"JUnit", "Selenium", "TestNG", "Cucumber", "JMeter"};
        String[] testScenarios = {"Login Scenario", "Registration Scenario", "Checkout Scenario", "Search Scenario", "Profile Update Scenario"};

        return capitalize(faker.company().bs()) + " involves " +
                faker.random().nextInt(0, testTypes.length - 1) + " for " +
                testScenarios[faker.random().nextInt(0, testScenarios.length - 1)] + " using " +
                testTools[faker.random().nextInt(0, testTools.length - 1)] + ".";
    }
}