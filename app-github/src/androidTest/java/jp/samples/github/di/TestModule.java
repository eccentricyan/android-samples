package jp.samples.github.di;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import jp.samples.github.repository.GithubApiService;
import jp.samples.github.repository.TestGithubApiService;
import retrofit2.Retrofit;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;

@Module
public class TestModule extends AppModule {

    protected TestGithubApiService githubApiService;

    public TestModule(TestGithubApiService githubApiService) {
        this.githubApiService = githubApiService;
    }

    @Override
    public GithubApiService githubApiService(Retrofit retrofit) {
        NetworkBehavior behavior = NetworkBehavior.create();
        behavior.setDelay(0, TimeUnit.SECONDS);
        behavior.setVariancePercent(100);
        behavior.setFailurePercent(0);

        MockRetrofit mockRetrofit = new MockRetrofit.Builder(retrofit)
                .networkBehavior(behavior)
                .build();

        BehaviorDelegate<GithubApiService> delegate = mockRetrofit.create(GithubApiService.class);
        githubApiService.setDelegate(delegate);

        return githubApiService;
    }

}
