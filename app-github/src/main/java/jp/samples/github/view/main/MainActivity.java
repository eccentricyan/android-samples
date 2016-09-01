package jp.samples.github.view.main;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.inputmethod.InputMethodManager;

import jp.samples.github.R;
import jp.samples.github.databinding.MainActivityBinding;
import jp.samples.github.event.RepositoriesChangeEvent;
import jp.samples.github.view.ViewModelActivity;

public class MainActivity extends ViewModelActivity {

    private MainActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        eventBus.subscribe(this, RepositoriesChangeEvent.class, this::subscribe);

        binding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        binding.setViewModel(new MainViewModel(component));

        setSupportActionBar(binding.toolbar);
        setupRecyclerView(binding.reposRecyclerView);
    }

    private void subscribe(RepositoriesChangeEvent event) {
        MainAdapter adapter = (MainAdapter) binding.reposRecyclerView.getAdapter();
        adapter.setRepositories(event.repositories);
        adapter.notifyDataSetChanged();
        hideSoftKeybord();
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        MainAdapter adapter = new MainAdapter(component);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void hideSoftKeybord() {
        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(binding.editTextUsername.getWindowToken(), 0);
    }

}
