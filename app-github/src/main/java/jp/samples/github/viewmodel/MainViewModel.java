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

import java.util.List;

import jp.samples.github.App;
import jp.samples.github.GithubService;
import jp.samples.github.GithubSubscriber;
import jp.samples.github.R;
import jp.samples.github.SubscriptionUtil;
import jp.samples.github.model.Repository;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class MainViewModel implements ViewModel {

    public interface ViewModelListener {
        void onRepositoriesChanged(List<Repository> repositories);
    }

    private static final String TAG = MainViewModel.class.getSimpleName();

    public ObservableInt searchButtonVisibility;
    public ObservableInt progressVisibility;
    public ObservableInt recyclerViewVisibility;
    public ObservableInt infoMessageVisibility;
    public ObservableField<String> infoMessage;

    protected Context context;
    protected Subscription subscription;
    private ViewModelListener viewModelListener;
    private List<Repository> repositories;
    private String editTextUsernameValue;

    public MainViewModel(Context context, ViewModelListener viewModelListener) {
        this.context = context;
        this.viewModelListener = viewModelListener;
        this.progressVisibility = new ObservableInt(View.INVISIBLE);
        this.recyclerViewVisibility = new ObservableInt(View.INVISIBLE);
        this.searchButtonVisibility = new ObservableInt(View.GONE);
        this.infoMessageVisibility = new ObservableInt(View.VISIBLE);
        this.infoMessage = new ObservableField<>(context.getString(R.string.default_info_message));
    }

    @Override
    public void onDestroy() {
        context = null;
        SubscriptionUtil.unsubscribe(subscription);
        subscription = null;
        viewModelListener = null;
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

        SubscriptionUtil.unsubscribe(subscription);

        App app = App.get(context);
        GithubService githubService = app.getGithubService();
        subscription = githubService.publicRepositories(username)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(app.defaultSubscribeScheduler())
                .doAfterTerminate(() -> progressVisibility.set(View.INVISIBLE))
                .subscribe(new GithubSubscriber<List<Repository>>() {
                    @Override
                    public void onCompleted() {
                        if (viewModelListener != null) {
                            viewModelListener.onRepositoriesChanged(repositories);
                        }

                        if (repositories.isEmpty()) {
                            infoMessage.set(context.getString(R.string.text_empty_repos));
                            infoMessageVisibility.set(View.VISIBLE);
                        } else {
                            recyclerViewVisibility.set(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Error loading GitHub repos ", e);
                        if (isHttp404(e)) {
                            infoMessage.set(context.getString(R.string.error_username_not_found));
                        } else {
                            infoMessage.set(context.getString(R.string.error_loading_repos));
                        }
                        infoMessageVisibility.set(View.VISIBLE);
                    }

                    @Override
                    public void onNext(List<Repository> repositories) {
                        Log.i(TAG, "Repose loaded " + repositories);
                        MainViewModel.this.repositories = repositories;
                    }
                });
    }

}
