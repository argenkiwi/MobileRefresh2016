package io.soflete.mobilerefresh2016;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import io.soflete.signin.SignInPresenter;
import io.soflete.signin.SignInView;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * Created by leandro on 20/08/16.
 */
public class LoginFragment extends Fragment implements SignInView {

    private static final int REQUEST_READ_CONTACTS = 0;

    @Inject
    SignInPresenter loginPresenter;

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mLoginFormView;
    private View mProgressView;

    @Override
    public void addEmailsToAutoComplete(List<String> emails) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_dropdown_item_1line, emails);

        mEmailView.setAdapter(adapter);
    }

    @Override
    public void clearErrors() {
        mEmailView.setError(null);
        mPasswordView.setError(null);
    }

    @Override
    public void finish() {
        getActivity().finish();
    }

    @Override
    public void focusOnEmail() {
        mEmailView.requestFocus();
    }

    @Override
    public void focusOnPassword() {
        mPasswordView.requestFocus();
    }

    @Override
    public void hideProgress() {
        showProgress(false);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return true;
        if (getActivity().checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED)
            return true;
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        return false;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DaggerLoginComponent.builder()
                .fragmentActivityModule(new FragmentActivityModule(getActivity()))
                .loginModule(new LoginModule(this))
                .build().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_login, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        loginPresenter.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        loginPresenter.onStop();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set up the login form.
        mEmailView = (AutoCompleteTextView) view.findViewById(R.id.email);

        mPasswordView = (EditText) view.findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    final String email = mEmailView.getText().toString();
                    final String password = mPasswordView.getText().toString();
                    loginPresenter.onAttemptLogin(email, password);
                    return true;
                }
                return false;
            }
        });

        view.findViewById(R.id.email_sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = mEmailView.getText().toString();
                final String password = mPasswordView.getText().toString();
                loginPresenter.onAttemptLogin(email, password);
            }
        });

        mLoginFormView = view.findViewById(R.id.login_form);
        mProgressView = view.findViewById(R.id.login_progress);
    }

    @Override
    public void populateAutoComplete() {
        if (!mayRequestContacts()) return;
        loginPresenter.onLoadEmailAddresses();
    }

    @Override
    public void showEmailInvalidError() {
        mEmailView.setError(getString(R.string.error_invalid_email));
    }

    @Override
    public void showEmailRequiredError() {
        mEmailView.setError(getString(R.string.error_field_required));
    }

    @Override
    public void showIncorrectPasswordError() {
        mPasswordView.setError(getString(R.string.error_incorrect_password));
    }

    @Override
    public void showPasswordTooShortError() {
        mPasswordView.setError(getString(R.string.error_invalid_password));
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void showProgress() {
        showProgress(true);
    }
}
