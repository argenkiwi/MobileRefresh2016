package io.soflete.mobilerefresh2016;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by leandro on 22/06/16.
 */
@Module
public class FragmentActivityModule {
    private final FragmentActivity activity;

    public FragmentActivityModule(FragmentActivity activity) {
        this.activity = activity;
    }

    @Provides
    public Context provideContext() {
        return activity;
    }

    @Provides
    public LoaderManager provideLoaderManager() {
        return activity.getSupportLoaderManager();
    }
}
