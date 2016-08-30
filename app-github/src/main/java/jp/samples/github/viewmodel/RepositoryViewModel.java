package jp.samples.github.viewmodel;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.trello.rxlifecycle.LifecycleProvider;
import com.trello.rxlifecycle.android.ActivityEvent;

import jp.samples.github.R;
import jp.samples.github.model.Repository;
import jp.samples.github.repository.GithubApiService;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RepositoryViewModel {

    private static final String TAG = RepositoryViewModel.class.getSimpleName();

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        Picasso.with(view.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.placeholder)
                .into(view);
    }

    private final Context context;
    private final LifecycleProvider<ActivityEvent> lifecycleProvider;
    private final GithubApiService githubService;
    private final Repository repository;

    public final ObservableField<String> ownerName;
    public final ObservableField<String> ownerEmail;
    public final ObservableField<String> ownerLocation;
    public final ObservableInt ownerEmailVisibility;
    public final ObservableInt ownerLocationVisibility;
    public final ObservableInt ownerLayoutVisibility;

    public RepositoryViewModel(Context context,
                               LifecycleProvider<ActivityEvent> lifecycleProvider,
                               GithubApiService githubService,
                               Repository repository) {
        this.context = context;
        this.lifecycleProvider = lifecycleProvider;
        this.githubService = githubService;
        this.repository = repository;

        this.ownerName = new ObservableField<>();
        this.ownerEmail = new ObservableField<>();
        this.ownerLocation = new ObservableField<>();
        this.ownerLayoutVisibility = new ObservableInt(View.INVISIBLE);
        this.ownerEmailVisibility = new ObservableInt(View.VISIBLE);
        this.ownerLocationVisibility = new ObservableInt(View.VISIBLE);

        loadFullUser(repository.owner.url);
    }

    public String getDescription() {
        return repository.description;
    }

    public String getHomepage() {
        return repository.homepage;
    }

    public int getHomepageVisibility() {
        return repository.hasHomepage() ? View.VISIBLE : View.GONE;
    }

    public String getLanguage() {
        return context.getString(R.string.text_language, repository.language);
    }

    public int getLanguageVisibility() {
        return repository.hasLanguage() ? View.VISIBLE : View.GONE;
    }

    public int getForkVisibility() {
        return repository.isFork() ? View.VISIBLE : View.GONE;
    }

    public String getOwnerAvatarUrl() {
        return repository.owner.avatarUrl;
    }

    private void loadFullUser(String url) {
        githubService.userFromUrl(url)
                .compose(lifecycleProvider.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {
                    Log.i(TAG, "Full user data loaded " + user);
                    ownerName.set(user.name);
                    ownerEmail.set(user.email);
                    ownerLocation.set(user.location);
                    ownerEmailVisibility.set(user.hasEmail() ? View.VISIBLE : View.GONE);
                    ownerLocationVisibility.set(user.hasLocation() ? View.VISIBLE : View.GONE);
                    ownerLayoutVisibility.set(View.VISIBLE);
                });
    }

}
