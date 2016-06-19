package io.soflete.mobilerefresh2016;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

/**
 * Created by leandro on 19/06/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterTest {

    private LoginPresenter presenter;

    @Mock
    private LoginView view;

    @Before
    public void setUp() throws Exception {
        presenter = new LoginPresenter(view);
    }

    @Test
    public void shoudClearErrors(){
        presenter.onAttemptLogin(null, null);
        verify(view).clearErrors();
    }

    @Test
    public void shouldShowEmailRequiredError(){
        presenter.onAttemptLogin("", null);
        verify(view).showEmailRequiredError();
    }

    @Test
    public void shouldShowEmailInvalidError(){
        // TODO Test more scenarios?
        presenter.onAttemptLogin("notanemail", null);
        verify(view).showEmailInvalidError();
    }

    @Test
    public void shouldShowPasswordTooShortError(){
        presenter.onAttemptLogin(null, "1234");
        verify(view).showPasswordTooShortError();
    }

    @Test
    public void shouldFocusOnEmail(){
        presenter.onAttemptLogin("notanemail", "12345");
        verify(view).focusOnEmail();
    }

    @Test
    public void shouldFocusOnPassword(){
        presenter.onAttemptLogin("valid@email.yes", "1234");
        verify(view).focusOnPassword();
    }

    @Test
    public void shouldFocusOnEmailOnMultipleErrors(){
        presenter.onAttemptLogin("notanemail", "1234");
        verify(view).focusOnPassword();
    }

    @Test
    public void shouldShowProgress(){
        presenter.onAttemptLogin("valid@email.yes", "12345");
        verify(view).showProgress();
    }

    @Test
    public void shouldAttemptLogin(){
        final String email = "valid@email.yes";
        final String password = "12345";
        presenter.onAttemptLogin(email, password);

        // FIXME The view should not do this!
        verify(view).attemptLogin(email, password);
    }
}