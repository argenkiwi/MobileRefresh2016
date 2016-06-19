package io.soflete.mobilerefresh2016;

/**
 * Created by leandro on 19/06/16.
 */
public interface LoginView {
    void clearErrors();

    void finish();

    void focusOnEmail();

    void focusOnPassword();

    void hideProgress();

    void showEmailInvalidError();

    void showEmailRequiredError();

    void showIncorrectPasswordError();

    void showPasswordTooShortError();

    void showProgress();
}
