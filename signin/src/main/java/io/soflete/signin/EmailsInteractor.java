package io.soflete.signin;

import java.util.List;

/**
 * Created by leandro on 20/08/16.
 */
public interface EmailsInteractor {
    void execute(Listener listener);

    interface Listener {
        void onEmailAddressesLoaded(List<String> emails);
    }
}
