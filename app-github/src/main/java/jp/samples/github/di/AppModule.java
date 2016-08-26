package jp.samples.github.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import jp.samples.github.event.RxEventBus;
import jp.samples.github.repository.GithubApiInterceptor;
import jp.samples.github.repository.GithubApiService;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

    @Provides
    @Singleton
    public OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(new GithubApiInterceptor())
                .build();
    }

    @Provides
    public Gson gsonBuilder() {
        return new GsonBuilder().create();
    }

    @Provides
    @Singleton
    public Retrofit githubRetrofit(OkHttpClient okHttpClient, Gson gson) {
        return new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public GithubApiService githubApiService(Retrofit retrofit) {
        return retrofit.create(GithubApiService.class);
    }

    @Provides
    @Singleton
    public RxEventBus rxEventBus() {
        return new RxEventBus();
    }

}
