package io.soflete.mobilerefresh2016;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import io.soflete.signin.LoginPresenter;

import static org.mockito.Mockito.verify;

/**
 * Created by leandro on 19/06/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterTest {

    private LoginPresenter presenter;

    @Mock
    private LoginView view;

    @Mock
    private SignInInteractorImpl logInUseCase;

    @Mock
    private EmailsInteractorImpl getEmailAddressesUseCase;

    @Before
    public void setUp() throws Exception {
        presenter = new LoginPresenter(view, logInUseCase, getEmailAddressesUseCase);
    }

    @Test
    public void shoudClearErrors() {
        presenter.onAttemptLogin(null, null);
        verify(view).clearErrors();
    }

    @Test
    public void shouldAttemptLogin() {
        final String email = "valid@email.yes";
        final String password = "12345";
        presenter.onAttemptLogin(email, password);
        verify(logInUseCase).execute(email, password, presenter);
    }

    @Test
    public void shouldCancelLoginAttempt() {
        presenter.onStop();
        verify(logInUseCase).cancel();
    }

    @Test
    public void shouldFinish() {
        presenter.onSuccess();
        verify(view).finish();
    }

    @Test
    public void shouldFocusOnEmail() {
        presenter.onAttemptLogin("notanemail", "12345");
        verify(view).focusOnEmail();
    }

    @Test
    public void shouldFocusOnEmailOnMultipleErrors() {
        presenter.onAttemptLogin("notanemail", "1234");
        verify(view).focusOnEmail();
    }

    @Test
    public void shouldFocusOnPassword() {
        presenter.onAttemptLogin("valid@email.yes", "1234");
        verify(view).focusOnPassword();
    }

    @Test
    public void shouldFocusOnPasswordOnError() {
        presenter.onError();
        verify(view).focusOnPassword();
    }

    @Test
    public void shouldHideProgressOnCancelled() {
        presenter.onCancelled();
        verify(view).hideProgress();
    }

    @Test
    public void shouldHideProgressOnError() {
        presenter.onError();
        verify(view).hideProgress();
    }

    @Test
    public void shouldHideProgressOnSuccess() {
        presenter.onSuccess();
        verify(view).hideProgress();
    }

    @Test
    public void shouldShowEmailInvalidError() {
        // TODO Test more scenarios?
        presenter.onAttemptLogin("notanemail", null);
        verify(view).showEmailInvalidError();
    }

    @Test
    public void shouldShowEmailRequiredError() {
        presenter.onAttemptLogin("", null);
        verify(view).showEmailRequiredError();
    }

    @Test
    public void shouldShowIncorrectPasswordError() {
        presenter.onError();
        verify(view).showIncorrectPasswordError();
    }

    @Test
    public void shouldShowPasswordTooShortError() {
        presenter.onAttemptLogin(null, "1234");
        verify(view).showPasswordTooShortError();
    }

    @Test
    public void shouldShowProgress() {
        presenter.onAttemptLogin("valid@email.yes", "12345");
        verify(view).showProgress();
    }

    @Test
    public void shouldPopulateAutoComplete() {
        presenter.onStart();
        verify(view).populateAutoComplete();
    }

    @Test
    public void shouldLoadEmailAddresses() {
        presenter.onLoadEmailAddresses();
        verify(getEmailAddressesUseCase).execute(presenter);
    }

    @Test
    public void shouldAddEmailAddresses() {
        final ArrayList<String> emails = new ArrayList<>();
        presenter.onEmailAddressesLoaded(emails);
        verify(view).addEmailsToAutoComplete(emails);
    }
}