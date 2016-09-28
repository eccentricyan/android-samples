package jp.samples.github.view.main;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import jp.samples.github.R;
import jp.samples.github.api.model.Repository;
import jp.samples.github.databinding.MainItemBinding;
import jp.samples.github.di.ActivityComponent;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private ActivityComponent component;
    private List<Repository> repositories;

    public MainAdapter(ActivityComponent component) {
        this.component = component;
        this.repositories = Collections.emptyList();
    }

    public void setRepositories(List<Repository> repositories) {
        this.repositories = repositories;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MainItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.main_item,
                parent,
                false
        );
        return new ViewHolder(binding, component);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(repositories.get(position));
    }

    @Override
    public int getItemCount() {
        return repositories.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final MainItemBinding binding;
        final ActivityComponent component;

        ViewHolder(MainItemBinding binding, ActivityComponent component) {
            super(binding.getRoot());
            this.binding = binding;
            this.component = component;
        }
        void bind(Repository repository) {
            if (binding.getViewModel() == null) {
                binding.setViewModel(new MainItemViewModel(component, repository));
            } else {
                binding.getViewModel().setRepository(repository);
            }
        }
    }

}
