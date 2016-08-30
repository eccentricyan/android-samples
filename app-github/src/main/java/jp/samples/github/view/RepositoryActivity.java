package jp.samples.github.view;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import org.parceler.Parcels;

import javax.inject.Inject;

import jp.samples.github.App;
import jp.samples.github.R;
import jp.samples.github.databinding.RepositoryActivityBinding;
import jp.samples.github.model.Repository;
import jp.samples.github.repository.GithubApiService;
import jp.samples.github.viewmodel.RepositoryViewModel;

public class RepositoryActivity extends RxAppCompatActivity {

    private static final String EXTRA_REPOSITORY = "EXTRA_REPOSITORY";

    @Inject
    GithubApiService githubService;

    public static Intent newIntent(Context context, Repository repository) {
        Intent intent = new Intent(context, RepositoryActivity.class);
        intent.putExtra(EXTRA_REPOSITORY, Parcels.wrap(repository));
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        App.getAppComponent(this).inject(this);

        RepositoryActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.repository_activity);
        Repository repository = Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_REPOSITORY));
        binding.setViewModel(new RepositoryViewModel(this, this, githubService, repository));

        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setTitle(repository.name);
    }

}
