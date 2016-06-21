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

/**
 * Created by leandro on 19/06/16.
 */
@Module
public class LoginModule {

    private LoginView view;

    public LoginModule(LoginView view) {
        this.view = view;
    }

    @Provides
    public GetEmailAddressesUseCase provideGetEmailAddressesUseCase(Lazy<Loader<Cursor>> lazyLoader,
                                                                    LoaderManager loaderManager) {
        return new GetEmailAddressesUseCase(lazyLoader, loaderManager);
    }

    @Provides
    public LoginUseCase provideLoginUseCase() {
        return new LoginUseCase();
    }

    @Provides
    public LoginPresenter providePresenter(LoginUseCase loginUseCase,
                                           GetEmailAddressesUseCase getEmailAddressesUseCase) {
        return new LoginPresenter(view, loginUseCase, getEmailAddressesUseCase);
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
}
