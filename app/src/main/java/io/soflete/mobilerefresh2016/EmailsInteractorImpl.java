package io.soflete.mobilerefresh2016;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import java.util.ArrayList;
import java.util.List;

import dagger.Lazy;
import io.soflete.signin.EmailsInteractor;

/**
 * Created by leandro on 22/06/16.
 */
public class EmailsInteractorImpl implements EmailsInteractor,
        LoaderManager.LoaderCallbacks<Cursor> {

    final int ADDRESS = 0;

    private final Lazy<Loader<Cursor>> lazyLoader;
    private final LoaderManager loaderManager;
    private Listener listener;

    public EmailsInteractorImpl(Lazy<Loader<Cursor>> lazyLoader, LoaderManager loaderManager) {
        this.lazyLoader = lazyLoader;
        this.loaderManager = loaderManager;
    }

    public void execute(Listener listener) {
        this.listener = listener;
        loaderManager.initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return lazyLoader.get();
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        List<String> emails = new ArrayList<>();
        data.moveToFirst();
        while (!data.isAfterLast()) {
            emails.add(data.getString(ADDRESS));
            data.moveToNext();
        }

        if (listener != null) {
            listener.onEmailAddressesLoaded(emails);
            listener = null;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
