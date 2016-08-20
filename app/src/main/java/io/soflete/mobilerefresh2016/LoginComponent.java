package io.soflete.mobilerefresh2016;

import dagger.Component;
import io.soflete.signin.SignInPresenter;

/**
 * Created by leandro on 19/06/16.
 */
@Component(modules = {
        LoginModule.class,
        FragmentActivityModule.class
})
public interface LoginComponent {
    SignInPresenter getPresenter();
}
