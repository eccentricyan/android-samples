package jp.samples.github.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.view.View;

import jp.samples.github.R;
import jp.samples.github.model.Repository;
import jp.samples.github.view.RepositoryActivity;

public class MainItemViewModel extends BaseObservable {

    private final Context context;
    private Repository repository;

    public MainItemViewModel(Context context, Repository repository) {
        this.context = context;
        this.repository = repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
        super.notifyChange();
    }

    public void onItemClick(View view) {
        context.startActivity(RepositoryActivity.newIntent(context, repository));
    }

    public String getName() {
        return repository.name;
    }

    public String getDescription() {
        return repository.description;
    }

    public String getStarts() {
        return context.getString(R.string.text_stars, repository.stars);
    }

    public String getWatchers() {
        return context.getString(R.string.text_watchers, repository.watchers);
    }

    public String getForks() {
        return context.getString(R.string.text_forks, repository.forks);
    }

}
