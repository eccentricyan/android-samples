package jp.samples.github;

import android.app.Application;
import android.content.Context;

import jp.samples.github.di.AppComponent;
import jp.samples.github.di.DaggerAppComponent;

public class App extends Application {

    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.create();
    }

    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }

    public static AppComponent getAppComponent(Context context) {
        return get(context).component;
    }

}
