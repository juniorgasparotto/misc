package cardmanager.ciandt.com.cardmanager.presentation.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import cardmanager.ciandt.com.cardmanager.R;
import cardmanager.ciandt.com.cardmanager.presentation.login.LoginActivity;

public class SplashActivity extends AppCompatActivity implements SplashContract.View {
    /** Duration of wait **/
    private SplashContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new SplashPresenter(this);
    }

    @Override
    public void setPresenter(SplashContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(IntentCompat.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.stop();
    }
}
