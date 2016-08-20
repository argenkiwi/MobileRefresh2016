package io.soflete.signin;

/**
 * Created by leandro on 20/08/16.
 */
public interface SignInInteractor {
    void cancel();

    void execute(String email, String password, Listener listener);

    interface Listener {
        void onCancelled();

        void onError();

        void onSuccess();
    }
}
