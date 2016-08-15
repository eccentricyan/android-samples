package jp.samples.github.di;

import javax.inject.Singleton;

import dagger.Component;
import jp.samples.github.view.MainActivity;
import jp.samples.github.view.RepositoryActivity;

@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {

    void inject(MainActivity activity);

    void inject(RepositoryActivity activity);

}
