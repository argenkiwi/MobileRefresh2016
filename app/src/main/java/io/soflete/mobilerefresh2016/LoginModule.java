package io.soflete.mobilerefresh2016;

import dagger.Module;
import dagger.Provides;

/**
 * Created by leandro on 19/06/16.
 */
@Module
public class LoginModule {

    private LoginView view;

    public LoginModule(LoginView view) {
        this.view = view;
    }

    @Provides
    public LoginPresenter providePresenter(){
        return new LoginPresenter(view);
    }
}
