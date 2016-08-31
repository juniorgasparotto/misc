package cardmanager.ciandt.com.cardmanager.presentation.payment;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import cardmanager.ciandt.com.cardmanager.R;
import cardmanager.ciandt.com.cardmanager.presentation.main.MainActivity;

public class SchedulePaymentNotify extends BroadcastReceiver {
    private final int NOTIFICATION_ID = 1;
    private static int ALARM_ID;

    public void onReceive(Context context, Intent intent) {
        this.notify(context);
    }

    private void notify(Context context) {
        if (!MainActivity.isVisible) {
            if (!this.existAlarm(context)) {
                Intent resultIntent =  new Intent(context, MainActivity.class);
                resultIntent.putExtra(MainActivity.NOTIFICATION_REQUEST_CODE_OPEN_PAY_PENDING, "");
                resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

                TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
                stackBuilder.addParentStack(MainActivity.class);
                stackBuilder.addNextIntent(resultIntent);

                PendingIntent resultPendingIntent =
                        stackBuilder.getPendingIntent(
                                0,
                                PendingIntent.FLAG_UPDATE_CURRENT
                        );
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(context)
                                .setSmallIcon(R.drawable.ico_agendar)
                                .setContentTitle(context.getString(R.string.app_name))
                                .setContentText(context.getString(R.string.schedule_payment_notification_message));
                mBuilder.setContentIntent(resultPendingIntent);
                mBuilder.setAutoCancel(true);
                NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

                Notification notification = mBuilder.build();
                notification.flags |= Notification.FLAG_NO_CLEAR;
                mNotificationManager.notify(NOTIFICATION_ID, notification);
            }
        }
        else {
            MainActivity.current.startDialogNotificationForPaymentsOverDue();
        }
    }

//    public static final void setAlarms(Context context)
//    {
//        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
//        Intent intent = new Intent(context, SchedulePaymentNotify.class);
//        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

//        // Set the alarm to start at 8:30 a.m.
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.set(Calendar.HOUR_OF_DAY, 11);
//        calendar.set(Calendar.MINUTE, 24);

        // setRepeating() lets you specify a precise custom interval--in this case,
        // 20 minutes.
        //alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 20000, alarmIntent);
//        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 3000, alarmIntent);
//    }

    public static final void setAlarm(Context context, long triggerAtMillis)
    {
        if (triggerAtMillis >= System.currentTimeMillis()) {
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, SchedulePaymentNotify.class);
            PendingIntent alarmIntent = PendingIntent.getBroadcast(context, ALARM_ID++, intent, 0);
            alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, alarmIntent);
        }
    }

    // **** Não funciona ****
    public boolean existAlarm(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MainActivity.NOTIFICATION_REQUEST_CODE_OPEN_PAY_PENDING, "");
        PendingIntent test = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_NO_CREATE);
        return test != null;
    }

}