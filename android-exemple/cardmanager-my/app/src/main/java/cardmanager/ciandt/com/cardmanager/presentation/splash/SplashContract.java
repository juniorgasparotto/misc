package cardmanager.ciandt.com.cardmanager.presentation.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import cardmanager.ciandt.com.cardmanager.R;

public interface SplashContract {
    interface View {
        void setPresenter(Presenter presenter);
        void openLoginActivity();
    }

    interface Presenter {
        void start();
        void stop();
    }
}
