package jp.samples.github.view.main;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.inputmethod.InputMethodManager;

import java.util.List;

import icepick.Icepick;
import icepick.State;
import jp.samples.github.R;
import jp.samples.github.api.model.Repository;
import jp.samples.github.bundler.ListBundler;
import jp.samples.github.databinding.MainActivityBinding;
import jp.samples.github.event.RepositoriesChangeEvent;
import jp.samples.github.view.ViewModelActivity;

public class MainActivity extends ViewModelActivity {

    MainActivityBinding binding;

    MainViewModel viewModel;

    @State(ListBundler.class)
    List<Repository> repositories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Icepick.restoreInstanceState(this, savedInstanceState);

        viewModel = new MainViewModel(component);
        Icepick.restoreInstanceState(viewModel, savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        binding.setViewModel(viewModel);

        eventBus.subscribe(this, RepositoriesChangeEvent.class, this::subscribe);

        setSupportActionBar(binding.toolbar);

        setupRecyclerView(binding.reposRecyclerView);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
        Icepick.saveInstanceState(viewModel, outState);
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        MainAdapter adapter = new MainAdapter(component);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setupAdapterRepositories(adapter);
    }

    private void setupAdapterRepositories(MainAdapter adapter) {
        if (repositories != null) {
            adapter.setRepositories(repositories);
            adapter.notifyDataSetChanged();
        }
    }

    private void subscribe(RepositoriesChangeEvent event) {
        this.repositories = event.repositories;
        MainAdapter adapter = (MainAdapter) binding.reposRecyclerView.getAdapter();
        setupAdapterRepositories(adapter);
        hideSoftKeybord();
    }

    private void hideSoftKeybord() {
        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(binding.editTextUsername.getWindowToken(), 0);
    }

}
