package sample.mvvm.viewmodel;

import android.view.View;

import sample.github.model.Repository;
import sample.mvvm.R;
import sample.mvvm.di.ActivityComponent;
import sample.mvvm.view.DetailActivity;

public class ListItemViewModel extends ViewModel {

    private Repository repository;

    public ListItemViewModel(ActivityComponent component, Repository repository) {
        super(component);
        this.repository = repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
        super.notifyChange();
    }

    public void onItemClick(View view) {
        context.startActivity(DetailActivity.newIntent(context, repository));
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