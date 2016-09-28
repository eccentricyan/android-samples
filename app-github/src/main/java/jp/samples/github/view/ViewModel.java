package jp.samples.github.view;

import android.content.Context;
import android.databinding.BaseObservable;

import com.trello.rxlifecycle.LifecycleProvider;
import com.trello.rxlifecycle.android.ActivityEvent;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import jp.samples.github.api.GithubApiService;
import jp.samples.github.di.ActivityComponent;

public class ViewModel extends BaseObservable {

    @Inject
    protected Context context;

    @Inject
    protected GithubApiService githubService;

    @Inject
    protected EventBus eventBus;

    @Inject
    protected LifecycleProvider<ActivityEvent> lifecycleProvider;

    public ViewModel(ActivityComponent component) {
        component.inject(this);
    }

}
