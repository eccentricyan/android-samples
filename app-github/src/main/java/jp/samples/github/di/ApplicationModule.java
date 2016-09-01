package jp.samples.github.di;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import jp.samples.github.App;
import jp.samples.github.api.GithubApiInterceptor;
import jp.samples.github.api.GithubApiService;
import jp.samples.github.event.RxEventBus;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApplicationModule {

    private Context context;

    public ApplicationModule(App app) {
        this.context = app.getApplicationContext();
    }

    @Provides
    @ApplicationScope
    public Context context() {
        return this.context;
    }

    @Provides
    @ApplicationScope
    public RxEventBus rxEventBus() {
        return new RxEventBus();
    }

    @Provides
    @ApplicationScope
    public OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(new GithubApiInterceptor())
                .build();
    }

    @Provides
    @ApplicationScope
    public Gson gsonBuilder() {
        return new GsonBuilder().create();
    }

    @Provides
    @ApplicationScope
    public Retrofit githubRetrofit(OkHttpClient okHttpClient, Gson gson) {
        return new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides
    @ApplicationScope
    public GithubApiService githubApiService(Retrofit retrofit) {
        return retrofit.create(GithubApiService.class);
    }

}
