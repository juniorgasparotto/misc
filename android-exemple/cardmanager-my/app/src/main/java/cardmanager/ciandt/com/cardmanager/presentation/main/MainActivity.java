package cardmanager.ciandt.com.cardmanager.presentation.main;


import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import cardmanager.ciandt.com.cardmanager.R;
import cardmanager.ciandt.com.cardmanager.data.model.Payment;
import cardmanager.ciandt.com.cardmanager.data.model.User;
import cardmanager.ciandt.com.cardmanager.infrastructure.Utils;
import cardmanager.ciandt.com.cardmanager.presentation.about.AboutFragment;
import cardmanager.ciandt.com.cardmanager.presentation.card.CardFragment;
import cardmanager.ciandt.com.cardmanager.presentation.extracts.ExtractsFragment;
import cardmanager.ciandt.com.cardmanager.presentation.login.LoginActivity;
import cardmanager.ciandt.com.cardmanager.presentation.mycards.MyCardsFragment;
import cardmanager.ciandt.com.cardmanager.presentation.payment.PaymentsFragment;
import cardmanager.ciandt.com.cardmanager.presentation.payment.SchedulePaymentNotify;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements MainContract.View {
    private MainContract.Presenter mPresenter;
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private CircleImageView mMenuImageProfile;
    private TextView mMenuUserName;
    private User mUserLogged;
    private LinearLayout mMenuHeader;
    private ActionBar mActionBar;
    private AlertDialog mDialogPaymentsOverDue;

    private Fragment mCurrentFragment;

    private static final String TAG_MY_CARDS_FRAGMENT = "MY_CARDS";
    private static final String TAG_EXTRACTS_FRAGMENT = "EXTRACTS";
    private static final String TAG_PAYMENT_SCHEDULE_FRAGMENT = "PAYMENT_SCHEDULE";
    private static final String TAG_ABOUT_FRAGMENT = "ABOUT";
    private static final String TAG_HOME = "CARD";
    public static final String NOTIFICATION_REQUEST_CODE_OPEN_PAY_PENDING = "0";

    public static boolean isVisible = false;
    public static MainActivity current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // default
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            //Restore the fragment's instance
            mCurrentFragment = getSupportFragmentManager().getFragment(savedInstanceState, "mCurrentFragment");
            updateDisplay(mCurrentFragment, mCurrentFragment.getTag());
        }

        // bindings
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);

        View header = mNavigationView.getHeaderView(0);
        mMenuImageProfile = (CircleImageView)header.findViewById(R.id.profile_image);
        mMenuUserName = (TextView)header.findViewById(R.id.user_name);
        mMenuHeader = (LinearLayout) header.findViewById(R.id.menu_header);

        // set toolbar
        setSupportActionBar(mToolbar);

        mActionBar = getSupportActionBar();

        // configure icon menu
        //mActionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        //mActionBar.setDisplayHomeAsUpEnabled(true);

        // create presenter
        new MainPresenter(this, this);

        // start app configurations
        mPresenter.configureAppToUser();
        MainActivity.current = this;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Save the fragment's instance
        getSupportFragmentManager().putFragment(outState, "mCurrentFragment", mCurrentFragment);
    }

    @Override
    public void configureAppToUser(User user) {
        this.mUserLogged = user;
        configureMenu();
        homeOrMantainLastFragment(false);
        //openPaymentsPendingDialogIfNofificationClick();
    }

    public void startDialogNotificationForPaymentsOverDue() {
        mPresenter.startDialogNotificationForPaymentsOverDue(null);
    }

    @Override
    public void updateDialogNotificationForPaymentsOverDue(final ArrayList<Payment> payments) {
        if (mDialogPaymentsOverDue != null)
            mDialogPaymentsOverDue.dismiss();

        if (payments.size() == 1) {
            if (DateUtils.isToday(payments.get(0).date.getTime())) {
                String message = this.getString(R.string.schedule_payment_dialog_over_due_title);
                message = String.format(message, payments.get(0).name);

                AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
                builderSingle.setMessage(message);

                //dlgAlert.setPositiveButton(R.string.ok_label, null);

                builderSingle.setCancelable(false);

                builderSingle.setPositiveButton(
                        R.string.ok_label,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mPresenter.removePaymentsOverDue(payments);
                                dialog.dismiss();
                            }
                        });

                this.mDialogPaymentsOverDue = builderSingle.create();
                this.mDialogPaymentsOverDue.show();
            }
        }
        else if (payments.size() > 1) {
            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
            boolean hasItem = false;
            for (Payment payment : payments) {
                if (DateUtils.isToday(payment.date.getTime())) {
                    arrayAdapter.add(payment.name);
                    hasItem = true;
                }
            }

            if (hasItem) {
                AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
                builderSingle.setTitle(R.string.schedule_payment_dialog_over_due_more_one_title);
                builderSingle.setCancelable(false);
                builderSingle.setPositiveButton(
                        R.string.ok_label,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mPresenter.removePaymentsOverDue(payments);
                                dialog.dismiss();
                            }
                        });

                builderSingle.setAdapter(arrayAdapter, null);
                this.mDialogPaymentsOverDue = builderSingle.show();
            }
        }
    }

    @Override
    public void logout() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

//    private void openPaymentsPendingDialogIfNofificationClick() {
//        Intent intent = getIntent();
//        Bundle bundle = intent.getExtras();
//        if (bundle != null && bundle.containsKey(NOTIFICATION_REQUEST_CODE_OPEN_PAY_PENDING))
//        {
//            this.startDialogNotificationForPaymentsOverDue();
//        }
//    }

    private void configureMenu() {
        Glide.with(this).load(Utils.buildImagesUriRepository(this, this.mUserLogged.profileImagePath)).into(mMenuImageProfile);
        mMenuUserName.setText(this.mUserLogged.name);

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();

                switch (menuItem.getItemId()) {
                    case R.id.menu_my_cards:
                        mPresenter.openMyCards();
                        break;

                    case R.id.menu_extract:
                        mPresenter.openExtracts();
                        break;

                    case R.id.menu_scheduling_payment:
                        mPresenter.openPaymentsPending();
                        break;

                    case R.id.menu_about:
                        mPresenter.openAbout();
                        break;
                    case R.id.menu_logout:
                        mPresenter.logout();
                        break;
                }
                return true;
            }
        });

        mMenuHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeOrMantainLastFragment(true);
                mDrawerLayout.closeDrawers();
            }
        });
    }

    @Override
    public void homeOrMantainLastFragment(boolean forceHome) {
        if (mCurrentFragment != null && !forceHome) {
            if (mCurrentFragment.getTag() == TAG_HOME) {
                mPresenter.openHome();
            } else if (mCurrentFragment.getTag() == TAG_MY_CARDS_FRAGMENT) {
                mPresenter.openMyCards();
            } else if (mCurrentFragment.getTag() == TAG_EXTRACTS_FRAGMENT) {
                mPresenter.openExtracts();
            } else if (mCurrentFragment.getTag() == TAG_PAYMENT_SCHEDULE_FRAGMENT) {
                mPresenter.openPaymentsPending();
            } else if (mCurrentFragment.getTag() == TAG_ABOUT_FRAGMENT) {
                mPresenter.openAbout();
            }
        }
        else {
            mPresenter.openHome();
        }
    }

    @Override
    public void openHome() {
        if (mCurrentFragment == null || mCurrentFragment.getTag() != TAG_HOME) {
            mCurrentFragment = new CardFragment();
            updateDisplay(mCurrentFragment, TAG_HOME);
        }

        mActionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        mActionBar.setDisplayHomeAsUpEnabled(true);

        // uncheck all menu item
        for (int i = 0; i < mNavigationView.getMenu().size(); i++)
            mNavigationView.getMenu().getItem(i).setChecked(false);

        mToolbar.setTitle(R.string.title_activity_main);
    }

    @Override
    public void openMyCards() {
        if (mCurrentFragment == null || mCurrentFragment.getTag() != TAG_MY_CARDS_FRAGMENT) {
            mCurrentFragment = new MyCardsFragment();
            updateDisplay(mCurrentFragment, TAG_MY_CARDS_FRAGMENT);
        }

        mActionBar.setHomeAsUpIndicator(null);
        mActionBar.setDisplayHomeAsUpEnabled(true);

        mToolbar.setTitle(R.string.title_activity_my_cards);
        mNavigationView.setCheckedItem(R.id.menu_my_cards);
    }

    @Override
    public void openExtracts() {
        if (mCurrentFragment == null || mCurrentFragment.getTag() != TAG_EXTRACTS_FRAGMENT) {
            mCurrentFragment = new ExtractsFragment();
            updateDisplay(mCurrentFragment, TAG_EXTRACTS_FRAGMENT);

        }

        mActionBar.setHomeAsUpIndicator(null);
        mActionBar.setDisplayHomeAsUpEnabled(true);

        mToolbar.setTitle(R.string.title_activity_extracts);
        mNavigationView.setCheckedItem(R.id.menu_extract);
    }

    @Override
    public void openPaymentsPending() {
        if (mCurrentFragment == null || mCurrentFragment.getTag() != TAG_PAYMENT_SCHEDULE_FRAGMENT) {
            mCurrentFragment = new PaymentsFragment();
            updateDisplay(mCurrentFragment, TAG_PAYMENT_SCHEDULE_FRAGMENT);
        }

        mActionBar.setHomeAsUpIndicator(null);
        mActionBar.setDisplayHomeAsUpEnabled(true);

        mToolbar.setTitle(R.string.title_activity_payment_schedule);
        mNavigationView.setCheckedItem(R.id.menu_scheduling_payment);
    }

    @Override
    public void openAbout() {
        if (mCurrentFragment == null || mCurrentFragment.getTag() != TAG_ABOUT_FRAGMENT) {
            mCurrentFragment = new AboutFragment();
            updateDisplay(mCurrentFragment, TAG_ABOUT_FRAGMENT);
        }

        mActionBar.setHomeAsUpIndicator(null);
        mActionBar.setDisplayHomeAsUpEnabled(true);

        mToolbar.setTitle(R.string.title_activity_about);
        mNavigationView.setCheckedItem(R.id.menu_about);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home: {
                if (isHome())
                    mDrawerLayout.openDrawer(GravityCompat.START);
                else
                    openHome();
                return true;
            }
            case R.id.action_settings: {
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!isHome())
            this.openHome();
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showDialogError(String message) {
        Utils.showDialogError(this, message);
    }

    @Override
    public void showDefaultDialogError(String message) {
        Utils.showDefaultDialogError(this, message);
    }

    private void updateDisplay(Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment, tag).commit();
    }

    private boolean isHome() {
        if (mCurrentFragment == null || mCurrentFragment.getTag() == TAG_HOME)
            return true;

        return false;
    }

}
