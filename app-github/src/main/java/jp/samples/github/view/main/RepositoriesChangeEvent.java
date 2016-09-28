package jp.samples.github.view.main;

import java.util.List;

import jp.samples.github.api.model.Repository;

public class RepositoriesChangeEvent {
    public final List<Repository> repositories;
    public RepositoriesChangeEvent(List<Repository> repositories) {
        this.repositories = repositories;
    }
}
