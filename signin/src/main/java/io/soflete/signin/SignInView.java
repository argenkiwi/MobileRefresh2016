package io.soflete.signin;

import java.util.List;

/**
 * Created by leandro on 19/06/16.
 */
public interface SignInView {
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

    void addEmailsToAutoComplete(List<String> emails);

    void populateAutoComplete();
}
