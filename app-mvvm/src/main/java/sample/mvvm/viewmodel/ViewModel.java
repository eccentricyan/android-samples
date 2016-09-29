package sample.mvvm.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;

import com.trello.rxlifecycle.LifecycleProvider;
import com.trello.rxlifecycle.android.ActivityEvent;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import sample.github.GithubApiService;
import sample.mvvm.di.ActivityComponent;

public class ViewModel extends BaseObservable {

    @Inject
    protected Context context;

    @Inject
    protected LifecycleProvider<ActivityEvent> lifecycleProvider;

    @Inject
    protected GithubApiService githubService;

    @Inject
    protected EventBus eventBus;

    public ViewModel(ActivityComponent component) {
        component.inject(this);
    }

}
