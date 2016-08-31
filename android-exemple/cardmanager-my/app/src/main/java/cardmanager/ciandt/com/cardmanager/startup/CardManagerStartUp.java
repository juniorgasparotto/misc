package cardmanager.ciandt.com.cardmanager.startup;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import cardmanager.ciandt.com.cardmanager.data.model.Payment;
import cardmanager.ciandt.com.cardmanager.data.model.User;
import cardmanager.ciandt.com.cardmanager.infrastructure.OperationError;
import cardmanager.ciandt.com.cardmanager.infrastructure.OperationListener;
import cardmanager.ciandt.com.cardmanager.infrastructure.Utils;
import cardmanager.ciandt.com.cardmanager.manager.PaymentManager;
import cardmanager.ciandt.com.cardmanager.manager.UserManager;
import cardmanager.ciandt.com.cardmanager.presentation.main.MainActivity;
import cardmanager.ciandt.com.cardmanager.presentation.payment.SchedulePaymentNotify;

public class CardManagerStartUp extends BroadcastReceiver {
    private Context mContext;
    private final OperationListener<ArrayList<Payment>> mGetPaymentsCallBack = new OperationListener<ArrayList<Payment>>() {

        @Override
        public void onPreExecute() {

        }

        @Override
        public void onSuccess(ArrayList<Payment> payments) {
            CardManagerStartUp.this.setPayments(payments);
        }

        @Override
        public void onError(OperationError error) {
            if (error.code == OperationError.ERROR_CODE_SERVER_WITH_MESSAGE) {

            } else {

            }
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onPostExecute() {

        }
    };
    
    public void onReceive(Context context, Intent intent) {
        this.mContext = context;
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            PaymentManager manager = new PaymentManager(context);
            manager.doGetPayments(null, mGetPaymentsCallBack);
        }
    }

    public void setPayments(ArrayList<Payment> payments) {
        for (Payment payment : payments) {
            SchedulePaymentNotify.setAlarm(this.mContext, payment.date.getTime());
        }
    }
}