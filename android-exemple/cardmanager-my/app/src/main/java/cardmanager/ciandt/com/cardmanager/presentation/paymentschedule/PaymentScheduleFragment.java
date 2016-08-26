package cardmanager.ciandt.com.cardmanager.presentation.paymentschedule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cardmanager.ciandt.com.cardmanager.R;

public class PaymentScheduleFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_payment_schedule, container, false);
        return rootView;
    }

}