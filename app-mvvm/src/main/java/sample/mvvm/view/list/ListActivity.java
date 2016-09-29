package sample.mvvm.view.list;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.inputmethod.InputMethodManager;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import icepick.Icepick;
import icepick.State;
import sample.mvvm.R;
import sample.mvvm.api.model.Repository;
import sample.mvvm.bundler.ListBundler;
import sample.mvvm.databinding.ListActivityBinding;
import sample.mvvm.view.ViewModelActivity;

public class ListActivity extends ViewModelActivity {

    ListActivityBinding binding;

    ListViewModel viewModel;

    @State(ListBundler.class)
    List<Repository> repositories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Icepick.restoreInstanceState(this, savedInstanceState);
        viewModel = new ListViewModel(component);
        Icepick.restoreInstanceState(viewModel, savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.list_activity);
        binding.setViewModel(viewModel);

        eventBus.register(this);

        setupRecyclerView(binding.reposRecyclerView);

        setSupportActionBar(binding.toolbar);
    }

    @Override
    protected void onDestroy() {
        eventBus.unregister(this);
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
        Icepick.saveInstanceState(viewModel, outState);
    }

    @Subscribe
    public void subscribe(RepositoriesChangeEvent event) {
        this.repositories = event.repositories;
        ListAdapter adapter = (ListAdapter) binding.reposRecyclerView.getAdapter();
        setupAdapterRepositories(adapter);
        hideSoftKeybord();
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        ListAdapter adapter = new ListAdapter(component);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setupAdapterRepositories(adapter);
    }

    private void setupAdapterRepositories(ListAdapter adapter) {
        if (repositories != null) {
            adapter.setRepositories(repositories);
            adapter.notifyDataSetChanged();
        }
    }

    private void hideSoftKeybord() {
        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(binding.editTextUsername.getWindowToken(), 0);
    }

}
