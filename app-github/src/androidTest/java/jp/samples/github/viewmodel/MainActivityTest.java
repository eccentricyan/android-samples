package jp.samples.github.viewmodel;


import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collections;

import jp.samples.github.R;
import jp.samples.github.view.MainActivity;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.mock.Calls;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.*;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest extends ActivityTest<MainActivity> {

    @Override
    protected Class<MainActivity> activityClass() {
        return MainActivity.class;
    }

    @Override
    public void setup() {
        super.setup();
    }

    @Test
    public void 初期表示() throws Exception {
        onView(withId(R.id.button_search)).check(matches(not(isDisplayed())));
        onView(withId(R.id.progress)).check(matches(not(isDisplayed())));
        onView(withId(R.id.repos_recycler_view)).check(matches(not(isDisplayed())));
        onView(withId(R.id.text_info)).check(matches(isDisplayed()));
        onView(withText(R.string.default_info_message)).check(matches(isDisplayed()));
    }

    @Test
    public void 入力ボタン表示() throws Exception {
        onView(withId(R.id.edit_text_username)).perform(typeText("username"));
        onView(withId(R.id.button_search)).check(matches(isDisplayed()));

        onView(withId(R.id.edit_text_username)).perform(clearText());
        onView(withId(R.id.button_search)).check(matches(not(isDisplayed())));
    }

    @Test
    public void 検索_結果あり() throws Exception {
        onView(withId(R.id.edit_text_username)).perform(typeText("username"));
        onView(withId(R.id.button_search)).perform(click());
        onView(withId(R.id.repos_recycler_view)).check(matches(isDisplayed()));
        onView(withId(R.id.text_info)).check(matches(not(isDisplayed())));
    }

    @Test
    public void 検索_結果なし() throws Exception {
        githubApiService.repositories = Calls.response(Collections.emptyList());

        onView(withId(R.id.edit_text_username)).perform(typeText("username"));
        onView(withId(R.id.button_search)).perform(click());
        onView(withId(R.id.repos_recycler_view)).check(matches(not(isDisplayed())));
        onView(withId(R.id.text_info)).check(matches(isDisplayed()));

        onView(withText(R.string.text_empty_repos)).check(matches(isDisplayed()));
    }

    @Test
    public void 検索_404エラー() throws Exception {
        ResponseBody responseBody = ResponseBody.create(MediaType.parse("text/plain"), "");
        githubApiService.repositories = Calls.response(Response.error(404, responseBody));

        onView(withId(R.id.edit_text_username)).perform(typeText("username"));
        onView(withId(R.id.button_search)).perform(click());
        onView(withId(R.id.repos_recycler_view)).check(matches(not(isDisplayed())));
        onView(withId(R.id.text_info)).check(matches(isDisplayed()));

        onView(withText(R.string.error_username_not_found)).check(matches(isDisplayed()));
    }

    @Test
    public void 検索_500エラー() throws Exception {
        ResponseBody responseBody = ResponseBody.create(MediaType.parse("text/plain"), "");
        githubApiService.repositories = Calls.response(Response.error(500, responseBody));

        onView(withId(R.id.edit_text_username)).perform(typeText("username"));
        onView(withId(R.id.button_search)).perform(click());
        onView(withId(R.id.repos_recycler_view)).check(matches(not(isDisplayed())));
        onView(withId(R.id.text_info)).check(matches(isDisplayed()));

        onView(withText(R.string.error_loading_repos)).check(matches(isDisplayed()));
    }

}
