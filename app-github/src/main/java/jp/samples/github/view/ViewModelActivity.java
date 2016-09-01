package jp.samples.github.view;

import android.os.Bundle;
import android.support.annotation.CallSuper;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

import jp.samples.github.App;
import jp.samples.github.di.ActivityComponent;
import jp.samples.github.di.ActivityModule;
import jp.samples.github.event.RxEventBus;

public class ViewModelActivity extends RxAppCompatActivity {

    protected ActivityComponent component;

    @Inject
    protected RxEventBus eventBus;

    @CallSuper
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.component = App.getComponent(this).newActivityComponent(new ActivityModule(this));
        this.component.inject(this);
    }

}


