package io.soflete.signin;

import java.util.List;

/**
 * Created by leandro on 19/06/16.
 */
public class SignInPresenter implements SignInInteractor.Listener, EmailsInteractor.Listener {
    private final SignInView view;
    private final SignInInteractor signInInteractor;
    private final EmailsInteractor emailsInteractor;

    public SignInPresenter(SignInView view, SignInInteractor signInInteractor,
                           EmailsInteractor emailsInteractor) {
        this.view = view;
        this.signInInteractor = signInInteractor;
        this.emailsInteractor = emailsInteractor;
    }

    public void onAttemptLogin(String email, String password) {
        view.clearErrors();

        boolean cancelled = false;
        if (email == null || email.isEmpty()) {
            view.showEmailRequiredError();
            view.focusOnEmail();
            cancelled = true;
        } else if (!isEmailValid(email)) {
            view.showEmailInvalidError();
            view.focusOnEmail();
            cancelled = true;
        }

        if (password != null && !password.isEmpty() && !isPasswordValid(password)) {
            view.showPasswordTooShortError();
            if (!cancelled) view.focusOnPassword();
            cancelled = true;
        }

        if (!cancelled) {
            view.showProgress();
            signInInteractor.execute(email, password, this);
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    @Override
    public void onCancelled() {
        view.hideProgress();
    }

    @Override
    public void onError() {
        view.hideProgress();
        view.focusOnPassword();
        view.showIncorrectPasswordError();
    }

    public void onStop() {
        signInInteractor.cancel();
    }

    @Override
    public void onSuccess() {
        view.hideProgress();
        view.finish();
    }

    public void onLoadEmailAddresses() {
        emailsInteractor.execute(this);
    }

    @Override
    public void onEmailAddressesLoaded(List<String> emails) {
        view.addEmailsToAutoComplete(emails);
    }

    public void onStart() {
        view.populateAutoComplete();
    }
}
