package jp.samples.github.viewmodel;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import jp.samples.github.App;
import jp.samples.github.GithubService;
import jp.samples.github.R;
import jp.samples.github.SubscriptionUtil;
import jp.samples.github.model.Repository;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class RepositoryViewModel implements ViewModel {

    private static final String TAG = RepositoryViewModel.class.getSimpleName();

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        Picasso.with(view.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.placeholder)
                .into(view);
    }

    private Repository repository;
    private Context context;
    private Subscription subscription;

    public ObservableField<String> ownerName;
    public ObservableField<String> ownerEmail;
    public ObservableField<String> ownerLocation;
    public ObservableInt ownerEmailVisibility;
    public ObservableInt ownerLocationVisibility;
    public ObservableInt ownerLayoutVisibility;

    public RepositoryViewModel(Context context, Repository repository) {
        this.context = context;
        this.repository = repository;

        this.ownerName = new ObservableField<>();
        this.ownerEmail = new ObservableField<>();
        this.ownerLocation = new ObservableField<>();
        this.ownerLayoutVisibility = new ObservableInt(View.INVISIBLE);
        this.ownerEmailVisibility = new ObservableInt(View.VISIBLE);
        this.ownerLocationVisibility = new ObservableInt(View.VISIBLE);

        loadFullUser(repository.owner.url);
    }

    @Override
    public void onDestroy() {
        this.context = null;
        SubscriptionUtil.unsubscribe(subscription);
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
        App app = App.get(context);
        GithubService githubService = app.getGithubService();
        subscription = githubService.userFromUrl(url)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(app.defaultSubscribeScheduler())
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
