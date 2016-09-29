package sample.mvvm.di;

import android.content.Context;

import com.trello.rxlifecycle.LifecycleProvider;
import com.trello.rxlifecycle.android.ActivityEvent;

import dagger.Module;
import dagger.Provides;
import sample.mvvm.view.ViewModelActivity;

@Module
public class ActivityModule {

    private ViewModelActivity activity;

    public ActivityModule(ViewModelActivity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    public Context activityContext() {
        return this.activity;
    }

    @Provides
    @ActivityScope
    public LifecycleProvider<ActivityEvent> lifecycleProvider() {
        return activity;
    }

}
