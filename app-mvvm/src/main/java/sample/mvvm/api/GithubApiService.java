package sample.mvvm.api;

import java.util.List;

import sample.mvvm.api.model.Repository;
import sample.mvvm.api.model.User;
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
