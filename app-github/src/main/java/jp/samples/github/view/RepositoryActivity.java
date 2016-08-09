package jp.samples.github.view;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import org.parceler.Parcels;

import jp.samples.github.R;
import jp.samples.github.databinding.RepositoryActivityBinding;
import jp.samples.github.model.Repository;
import jp.samples.github.viewmodel.RepositoryViewModel;

public class RepositoryActivity extends AppCompatActivity {

    private static final String EXTRA_REPOSITORY = "EXTRA_REPOSITORY";

    private RepositoryActivityBinding binding;
    private RepositoryViewModel viewModel;


    public static Intent newIntent(Context context, Repository repository) {
        Intent intent = new Intent(context, RepositoryActivity.class);
        intent.putExtra(EXTRA_REPOSITORY, Parcels.wrap(repository));
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.repository_activity);
        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Repository repository = Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_REPOSITORY));
        viewModel = new RepositoryViewModel(this, repository);
        binding.setViewModel(viewModel);

        setTitle(repository.name);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel.onDestroy();
    }
}
