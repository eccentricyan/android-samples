package jp.samples.github.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.inputmethod.InputMethodManager;

import javax.inject.Inject;

import dagger.Lazy;
import jp.samples.github.App;
import jp.samples.github.repository.GithubApiService;
import jp.samples.github.R;
import jp.samples.github.databinding.MainActivityBinding;
import jp.samples.github.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {

    @Inject
    Lazy<GithubApiService> githubService;

    private MainActivityBinding binding;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        App.getAppComponent(this).inject(this);

        binding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        MainViewModel.ViewModelListener viewModelListener = repositories -> {
            MainAdapter adapter = (MainAdapter) binding.reposRecyclerView.getAdapter();
            adapter.setRepositories(repositories);
            adapter.notifyDataSetChanged();
            hideSoftKeybord();
        };
        viewModel = new MainViewModel(this, githubService.get(), viewModelListener);
        binding.setViewModel(viewModel);

        setSupportActionBar(binding.toolbar);
        setupRecyclerView(binding.reposRecyclerView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        viewModel.onPause();
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        MainAdapter adapter = new MainAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void hideSoftKeybord() {
        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(binding.editTextUsername.getWindowToken(), 0);
    }

}
