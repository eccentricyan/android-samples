package jp.samples.github.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.inputmethod.InputMethodManager;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

import jp.samples.github.App;
import jp.samples.github.R;
import jp.samples.github.databinding.MainActivityBinding;
import jp.samples.github.event.RxEventBus;
import jp.samples.github.repository.GithubApiService;
import jp.samples.github.viewmodel.MainViewModel;

public class MainActivity extends RxAppCompatActivity {

    @Inject
    GithubApiService githubService;

    @Inject
    RxEventBus eventBus;

    private MainActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        App.getAppComponent(this).inject(this);

        eventBus.subscribe(this, MainViewModel.RepositoriesChangeEvent.class, this::subscribe);

        binding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        binding.setViewModel(new MainViewModel(this, this, githubService, eventBus));

        setSupportActionBar(binding.toolbar);
        setupRecyclerView(binding.reposRecyclerView);
    }

    private void subscribe(MainViewModel.RepositoriesChangeEvent event) {
        MainAdapter adapter = (MainAdapter) binding.reposRecyclerView.getAdapter();
        adapter.setRepositories(event.repositories);
        adapter.notifyDataSetChanged();
        hideSoftKeybord();
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
