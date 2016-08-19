package io.soflete.signin;

/**
 * Created by leandro on 20/08/16.
 */
public interface SignInInteractor {
    interface Listener {
        void onCancelled();

        void onError();

        void onSuccess();
    }
}
