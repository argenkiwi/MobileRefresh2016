package io.soflete.mobilerefresh2016;

/**
 * Created by leandro on 19/06/16.
 */
public interface LoginView {
    void attemptLogin(String email, String password);

    void clearErrors();

    void focusOnEmail();

    void focusOnPassword();

    void showEmailInvalidError();

    void showEmailRequiredError();

    void showPasswordTooShortError();

    void showProgress();
}
