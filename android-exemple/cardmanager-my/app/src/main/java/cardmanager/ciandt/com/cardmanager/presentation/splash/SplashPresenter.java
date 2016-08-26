package cardmanager.ciandt.com.cardmanager.presentation.splash;

import android.content.Intent;
import android.os.Handler;

import cardmanager.ciandt.com.cardmanager.presentation.login.LoginActivity;

public class SplashPresenter implements SplashContract.Presenter {
    private Handler mHandler;
    private final int SPLASH_DISPLAY_LENGTH = 500;
    private SplashContract.View mView;

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
//            /* Create an Intent that will start the Menu-Activity. */
//            Intent mainIntent = new Intent(this, LoginActivity.class);
//            Splash.this.startActivity(mainIntent);
//            Splash.this.finish();

            mView.openLoginActivity();
        }
    };

    public SplashPresenter(SplashContract.View view) {
        this.mView = view;
        this.mView.setPresenter(this);
        mHandler = new Handler();
    }

    @Override
    public void start() {
        mHandler.postDelayed(mRunnable, SPLASH_DISPLAY_LENGTH);
    }

    @Override
    public void stop() {
        mHandler.removeCallbacks(mRunnable);
    }
}
