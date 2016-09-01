package jp.samples.github.api;

import java.util.List;

import jp.samples.github.api.model.Repository;
import jp.samples.github.api.model.User;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;
import rx.Observable;

public interface GithubApiService {

    @GET("users/{username}/repos")
    Observable<List<Repository>> publicRepositories(@Path("username") String username);

    @GET
    Observable<User> userFromUrl(@Url String userUrl);

}
