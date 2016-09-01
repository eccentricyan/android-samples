package jp.samples.github.view.repository;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;

import org.parceler.Parcels;

import jp.samples.github.R;
import jp.samples.github.api.model.Repository;
import jp.samples.github.databinding.RepositoryActivityBinding;
import jp.samples.github.view.ViewModelActivity;

public class RepositoryActivity extends ViewModelActivity {

    private static final String EXTRA_REPOSITORY = "EXTRA_REPOSITORY";

    public static Intent newIntent(Context context, Repository repository) {
        Intent intent = new Intent(context, RepositoryActivity.class);
        intent.putExtra(EXTRA_REPOSITORY, Parcels.wrap(repository));
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RepositoryActivityBinding binding =  DataBindingUtil.setContentView(this, R.layout.repository_activity);
        Repository repository = Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_REPOSITORY));
        binding.setViewModel(new RepositoryViewModel(component, repository));

        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setTitle(repository.name);
    }

}
