package jp.samples.github.view.main;

import android.view.View;

import jp.samples.github.R;
import jp.samples.github.api.model.Repository;
import jp.samples.github.di.ActivityComponent;
import jp.samples.github.view.ViewModel;
import jp.samples.github.view.repository.RepositoryActivity;

public class MainItemViewModel extends ViewModel {

    private Repository repository;

    MainItemViewModel(ActivityComponent component, Repository repository) {
        super(component);
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
