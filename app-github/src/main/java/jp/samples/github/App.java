package jp.samples.github;

import android.app.Application;
import android.content.Context;

import rx.Scheduler;
import rx.schedulers.Schedulers;

public class App extends Application {

    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }

    public GithubService getGithubService() {
        return GithubServiceHolder.instance;
    }
    private static class GithubServiceHolder {
        private static final GithubService instance = GithubService.Factory.create();
    }

    public Scheduler defaultSubscribeScheduler() {
        return DefaultSubscribeSchedulerHolder.instance;
    }
    private static class DefaultSubscribeSchedulerHolder {
        private static final Scheduler instance = Schedulers.io();
    }

}
