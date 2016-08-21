package io.soflete.mobilerefresh2016;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import dagger.Lazy;
import dagger.Module;
import dagger.Provides;
import io.soflete.signin.EmailsInteractor;
import io.soflete.signin.SignInInteractor;
import io.soflete.signin.SignInPresenter;
import io.soflete.signin.SignInRouter;
import io.soflete.signin.SignInView;

/**
 * Created by leandro on 19/06/16.
 */
@Module
public class LoginModule {

    private final SignInView view;
    private final SignInRouter router;

    public LoginModule(SignInView view, SignInRouter router) {
        this.view = view;
        this.router = router;
    }

    @Provides
    public EmailsInteractor provideEmailsInteractor(Lazy<Loader<Cursor>> lazyLoader,
                                                    LoaderManager loaderManager) {
        return new EmailsInteractorImpl(lazyLoader, loaderManager);
    }

    @Provides
    public Loader<Cursor> provideLoader(Context context) {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        return new CursorLoader(context,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Provides
    public SignInPresenter providePresenter(SignInInteractor signInInteractor,
                                            EmailsInteractor emailsInteractor) {
        return new SignInPresenter(view, router, signInInteractor, emailsInteractor);
    }

    @Provides
    public SignInInteractor provideSignInInteractor() {
        return new SignInInteractorImpl();
    }
}
