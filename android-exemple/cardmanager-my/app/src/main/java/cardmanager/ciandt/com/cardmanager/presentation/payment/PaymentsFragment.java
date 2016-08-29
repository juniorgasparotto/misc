package cardmanager.ciandt.com.cardmanager.presentation.payment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import cardmanager.ciandt.com.cardmanager.R;
import cardmanager.ciandt.com.cardmanager.data.model.Payment;
import cardmanager.ciandt.com.cardmanager.data.model.User;
import cardmanager.ciandt.com.cardmanager.infrastructure.Utils;
import cardmanager.ciandt.com.cardmanager.presentation.main.MainActivity;

public class PaymentsFragment extends Fragment implements PaymentsContract.View {
    private PaymentsContract.Presenter mPresenter;
    private User mUser;
    private ListView mList;
    private PaymentsAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_payments_pending, container, false);
        new PaymentsPresenter(this.getContext(), this);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);

        //get the drawable
//        Drawable myFabSrc = getResources().getDrawable(android.R.drawable.ic_input_add);
//        //copy it in a new one
//        Drawable willBeWhite = myFabSrc.getConstantState().newDrawable();
//        //set the color filter, you can use also Mode.SRC_ATOP
//        willBeWhite.mutate().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
//        //set it to your fab button initialized before
//        fab.setImageDrawable(willBeWhite);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.openAddPayment();
            }
        });


        // bind elements
        mList = (ListView)rootView.findViewById(R.id.schedule_listview);

        // load
        mPresenter.loadLoggedUser();


        return rootView;
    }

    @Override
    public void setUser(User user) {
        this.mUser = user;
        mPresenter.loadPayments(user);
    }

    @Override
    public void setPayments(ArrayList<Payment> payments) {
        if (payments != null) {
            mAdapter = new PaymentsAdapter(this.getContext(), R.layout.fragment_payments_pending_item, payments);
            mAdapter.removeListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Payment payment = (Payment) v.getTag();
                    mPresenter.removePayment(mUser, payment);
                }
            };

            mList.setAdapter(mAdapter);
        }
    }

    @Override
    public void openAddPayment() {
        Intent intent = new Intent(getContext(), SchedulePaymentActivity.class);
        startActivity(intent);
    }

    @Override
    public void removePayment(Payment payment) {
//        for(int i = 0; i < mList.getChildCount(); i++)
//            if (mList.getItemAtPosition(i).)
        mAdapter.remove(payment);
    }

    @Override
    public void refresh(Payment payments) {
        mPresenter.loadLoggedUser();
    }

    @Override
    public void setPresenter(PaymentsContract.Presenter presenter) {
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
        Utils.showDialogError(this.getContext(), message);
    }

    @Override
    public void showDefaultDialogError(String message) {
        Utils.showDefaultDialogError(this.getContext(), message);
    }
}