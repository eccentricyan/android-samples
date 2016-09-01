package jp.samples.github.view;

import android.content.Context;
import android.databinding.BaseObservable;

import com.trello.rxlifecycle.LifecycleProvider;
import com.trello.rxlifecycle.android.ActivityEvent;

import javax.inject.Inject;

import jp.samples.github.api.GithubApiService;
import jp.samples.github.di.ActivityComponent;
import jp.samples.github.event.RxEventBus;

public class ViewModel extends BaseObservable {

    @Inject
    protected Context context;

    @Inject
    protected GithubApiService githubService;

    @Inject
    protected RxEventBus eventBus;

    @Inject
    protected LifecycleProvider<ActivityEvent> lifecycleProvider;

    public ViewModel(ActivityComponent component) {
        component.inject(this);
    }

}
