package sample.mvvm;

import android.app.Application;
import android.content.Context;
import android.support.annotation.VisibleForTesting;

import sample.mvvm.di.ApplicationComponent;
import sample.mvvm.di.ApplicationModule;
import sample.mvvm.di.DaggerApplicationComponent;

public class App extends Application {

    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        this.component = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public static ApplicationComponent getComponent(Context context) {
        return ((App) context.getApplicationContext()).component;
    }

    @VisibleForTesting
    public void setComponent(ApplicationComponent appComponent) {
        this.component = appComponent;
    }

}
