package jp.samples.github.view;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import org.parceler.Parcels;

import javax.inject.Inject;

import dagger.Lazy;
import jp.samples.github.App;
import jp.samples.github.repository.GithubApiService;
import jp.samples.github.R;
import jp.samples.github.databinding.RepositoryActivityBinding;
import jp.samples.github.model.Repository;
import jp.samples.github.viewmodel.RepositoryViewModel;

public class RepositoryActivity extends AppCompatActivity {

    private static final String EXTRA_REPOSITORY = "EXTRA_REPOSITORY";

    @Inject
    Lazy<GithubApiService> githubService;

    private RepositoryViewModel viewModel;

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
        viewModel = new RepositoryViewModel(this, githubService.get(), repository);
        binding.setViewModel(viewModel);

        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setTitle(repository.name);
    }

    @Override
    protected void onPause() {
        super.onPause();
        viewModel.onPause();
    }
}
