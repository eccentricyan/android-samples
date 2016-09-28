package jp.samples.github.view;

import android.os.Bundle;
import android.support.annotation.CallSuper;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import jp.samples.github.App;
import jp.samples.github.di.ActivityComponent;
import jp.samples.github.di.ActivityModule;

public class ViewModelActivity extends RxAppCompatActivity {

    protected ActivityComponent component;

    @Inject
    protected EventBus eventBus;

    @CallSuper
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.component = App.getComponent(this).newActivityComponent(new ActivityModule(this));
        this.component.inject(this);
    }

}


