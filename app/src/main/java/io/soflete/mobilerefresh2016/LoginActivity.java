package io.soflete.mobilerefresh2016;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import io.soflete.signin.SignInFragment;
import io.soflete.signin.SignInInteractor;
import io.soflete.signin.SignInPresenter;
import io.soflete.signin.SignInView;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements SignInFragment.Activity{

    @Inject
    SignInPresenter presenter;

    @Override
    public SignInPresenter getPresenter(SignInView view) {
        return presenter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            final SignInFragment fragment = new SignInFragment();

            DaggerLoginComponent.builder()
                    .fragmentActivityModule(new FragmentActivityModule(this))
                    .loginModule(new LoginModule(fragment))
                    .build().inject(this);

            getSupportFragmentManager().beginTransaction()
                    .add(android.R.id.content, fragment)
                    .commit();
        }
    }
}

