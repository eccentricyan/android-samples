package jp.samples.github.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.inputmethod.InputMethodManager;

import jp.samples.github.R;
import jp.samples.github.databinding.MainActivityBinding;
import jp.samples.github.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private MainActivityBinding binding;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        MainViewModel.ViewModelListener viewModelListener = repositories -> {
            MainAdapter adapter = (MainAdapter) binding.reposRecyclerView.getAdapter();
            adapter.setRepositories(repositories);
            adapter.notifyDataSetChanged();
            hideSoftKeybord();
        };
        viewModel = new MainViewModel(this, viewModelListener);
        binding.setViewModel(viewModel);
        setSupportActionBar(binding.toolbar);
        setupRecyclerView(binding.reposRecyclerView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel.onDestroy();
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
