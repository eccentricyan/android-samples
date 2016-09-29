package sample.mvvm.di;

import dagger.Subcomponent;
import sample.mvvm.view.ViewModelActivity;
import sample.mvvm.view.ViewModel;

@ActivityScope
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(ViewModelActivity viewModelActivity);

    void inject(ViewModel viewModel);

}
