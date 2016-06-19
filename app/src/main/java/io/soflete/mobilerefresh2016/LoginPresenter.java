package io.soflete.mobilerefresh2016;

/**
 * Created by leandro on 19/06/16.
 */
public class LoginPresenter {
    private final LoginView view;

    public LoginPresenter(LoginView view) {
        this.view = view;
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
            view.attemptLogin(email, password);
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }
}
