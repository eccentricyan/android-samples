package jp.samples.github.view;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import jp.samples.github.R;
import jp.samples.github.databinding.MainItemBinding;
import jp.samples.github.model.Repository;
import jp.samples.github.viewmodel.MainItemViewModel;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private List<Repository> repositories;

    public MainAdapter() {
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
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(repositories.get(position));
    }

    @Override
    public int getItemCount() {
        return repositories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final MainItemBinding binding;

        public ViewHolder(MainItemBinding binding) {
            super(binding.cardView);
            this.binding = binding;
        }
        void bind(Repository repository) {
            if (binding.getViewModel() == null) {
                binding.setViewModel(new MainItemViewModel(itemView.getContext(), repository));
            } else {
                binding.getViewModel().setRepository(repository);
            }
        }
    }

}
