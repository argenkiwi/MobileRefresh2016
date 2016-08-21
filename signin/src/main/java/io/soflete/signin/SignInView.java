package io.soflete.signin;

import java.util.List;

/**
 * Created by leandro on 19/06/16.
 */
public interface SignInView {
    void addEmailsToAutoComplete(List<String> emails);

    void clearErrors();

    void focusOnEmail();

    void focusOnPassword();

    void hideProgress();

    void populateAutoComplete();

    void showEmailInvalidError();

    void showEmailRequiredError();

    void showIncorrectPasswordError();

    void showPasswordTooShortError();

    void showProgress();
}
