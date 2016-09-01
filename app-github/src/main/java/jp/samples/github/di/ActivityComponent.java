package jp.samples.github.di;

import dagger.Subcomponent;
import jp.samples.github.view.ViewModel;
import jp.samples.github.view.ViewModelActivity;

@ActivityScope
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(ViewModelActivity viewModelActivity);

    void inject(ViewModel viewModel);

}
