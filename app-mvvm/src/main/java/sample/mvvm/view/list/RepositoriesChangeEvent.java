package sample.mvvm.view.list;

import java.util.List;

import sample.mvvm.api.model.Repository;

public class RepositoriesChangeEvent {
    public final List<Repository> repositories;
    public RepositoriesChangeEvent(List<Repository> repositories) {
        this.repositories = repositories;
    }
}
