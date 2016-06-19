package io.soflete.mobilerefresh2016;

/**
 * Created by leandro on 19/06/16.
 */
public class LoginPresenter implements LoginUseCase.Listener {
    private final LoginView view;
    private final LoginUseCase logInUseCase;

    public LoginPresenter(LoginView view, LoginUseCase logInUseCase) {
        this.view = view;
        this.logInUseCase = logInUseCase;
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
            logInUseCase.execute(email, password, this);
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
        logInUseCase.cancel();
    }

    @Override
    public void onSuccess() {
        view.hideProgress();
        view.finish();
    }
}
