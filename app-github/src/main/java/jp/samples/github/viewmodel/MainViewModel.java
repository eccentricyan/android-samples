package jp.samples.github.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.trello.rxlifecycle.LifecycleProvider;
import com.trello.rxlifecycle.android.ActivityEvent;

import java.util.List;

import jp.samples.github.R;
import jp.samples.github.event.RxEventBus;
import jp.samples.github.model.Repository;
import jp.samples.github.repository.GithubApiService;
import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();

    public interface ViewModelListener {
        void onRepositoriesChanged(List<Repository> repositories);
    }

    public class RepositoriesChangeEvent {
        public final List<Repository> repositories;
        public RepositoriesChangeEvent(List<Repository> repositories) {
            this.repositories = repositories;
        }
    }

    private final Context context;
    private final LifecycleProvider<ActivityEvent> lifecycleProvider;
    private final GithubApiService githubService;
    private final RxEventBus eventBus;

    public final ObservableInt searchButtonVisibility;
    public final ObservableInt progressVisibility;
    public final ObservableInt recyclerViewVisibility;
    public final ObservableInt infoMessageVisibility;
    public final ObservableField<String> infoMessage;

    private String editTextUsernameValue;

    public MainViewModel(Context context,
                         LifecycleProvider<ActivityEvent> lifecycleProvider,
                         GithubApiService githubService,
                         RxEventBus eventBus) {

        this.context = context;
        this.lifecycleProvider = lifecycleProvider;
        this.githubService = githubService;
        this.eventBus = eventBus;

        this.searchButtonVisibility = new ObservableInt(View.GONE);
        this.progressVisibility = new ObservableInt(View.INVISIBLE);
        this.recyclerViewVisibility = new ObservableInt(View.INVISIBLE);
        this.infoMessageVisibility = new ObservableInt(View.VISIBLE);
        this.infoMessage = new ObservableField<>(context.getString(R.string.default_info_message));
    }

    public void onClickSearch(View view) {
        loadGithubRepos(editTextUsernameValue);
    }

    public boolean onSearchAction(TextView view, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            String username = view.getText().toString();
            if (username.length() > 0) {
                loadGithubRepos(username);
                return true;
            }
        }
        return false;
    }

    public TextWatcher getUsernameEditTextWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editTextUsernameValue = charSequence.toString();
                searchButtonVisibility.set(charSequence.length() > 0 ? View.VISIBLE : View.GONE);
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
    }

    private void loadGithubRepos(String username) {
        progressVisibility.set(View.VISIBLE);
        recyclerViewVisibility.set(View.INVISIBLE);
        infoMessageVisibility.set(View.INVISIBLE);

        githubService.publicRepositories(username)
                .compose(lifecycleProvider.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> progressVisibility.set(View.INVISIBLE))
                .subscribe(repositories -> {
                    Log.i(TAG, "Repose loaded " + repositories);
                    eventBus.post(new RepositoriesChangeEvent(repositories));
                    if (repositories.isEmpty()) {
                        infoMessage.set(context.getString(R.string.text_empty_repos));
                        infoMessageVisibility.set(View.VISIBLE);
                    } else {
                        recyclerViewVisibility.set(View.VISIBLE);
                    }
                }, e -> {
                    Log.e(TAG, "Error loading GitHub repos ", e);
                    if (e instanceof HttpException && ((HttpException) e).code() == 404) {
                        infoMessage.set(context.getString(R.string.error_username_not_found));
                    } else {
                        infoMessage.set(context.getString(R.string.error_loading_repos));
                    }
                    infoMessageVisibility.set(View.VISIBLE);
                });
    }

}
