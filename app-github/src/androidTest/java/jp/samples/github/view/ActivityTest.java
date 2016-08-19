package jp.samples.github.view;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;

import jp.samples.github.App;
import jp.samples.github.di.AppComponent;
import jp.samples.github.di.DaggerAppComponent;
import jp.samples.github.di.TestModule;
import jp.samples.github.repository.TestGithubApiService;

public abstract class ActivityTest<T extends Activity> {

    @Rule
    public ActivityTestRule<T> activityTestRule
            = new ActivityTestRule<>(activityClass(), true, false);

    protected TestGithubApiService githubApiService;

    protected abstract Class<T> activityClass();

    @Before
    public void setup() {
        setupComponent();
        launchActivity();
    }

    private void setupComponent() {
        this.githubApiService = new TestGithubApiService();
        AppComponent component = DaggerAppComponent
                .builder()
                .appModule(new TestModule(githubApiService))
                .build();
        getApplication().setAppComponent(component);
    }

    protected void launchActivity() {
        activityTestRule.launchActivity(new Intent());
    }

    protected App getApplication() {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        return (App) instrumentation.getTargetContext().getApplicationContext();
    }

}
