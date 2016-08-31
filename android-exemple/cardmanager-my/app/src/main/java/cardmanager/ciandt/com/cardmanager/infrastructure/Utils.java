package cardmanager.ciandt.com.cardmanager.infrastructure;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cardmanager.ciandt.com.cardmanager.R;
import cardmanager.ciandt.com.cardmanager.data.repository.webapi.WebApiRepository;
import cardmanager.ciandt.com.cardmanager.presentation.main.MainActivity;
import cardmanager.ciandt.com.cardmanager.presentation.payment.SchedulePaymentNotify;

/**
 * Created by root on 12/08/16.
 */

public final class Utils {
    public static final WebApiRepository getWebApiRepository(Context context) {
        String url = context.getString(R.string.settings_webapi_url);
        WebApiRepository repository = new WebApiRepository(url);
        return repository;
    }

    public static final Uri buildImagesUriRepository(Context context, String path) {
        return Uri.parse(context.getString(R.string.settings_images_uri_repository) + path);
    }

    public static final boolean isOnline(Context context) {
        boolean isOnline = false;
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo netInfo = connectivity.getActiveNetworkInfo();

            // Se não existe nenhum tipo de conexão retorna false
            if (netInfo != null) {
                int netType = netInfo.getType();

                // Verifica se a conexão é do tipo WiFi ou Mobile e
                // retorna true se estiver conectado ou false em
                // caso contrário
                if (netType == ConnectivityManager.TYPE_WIFI || netType == ConnectivityManager.TYPE_MOBILE)
                    isOnline = netInfo.isConnected();
            }
        }

        return isOnline;
    }

    public static String getInternetAccessError(Context context) {
        if (!isOnline(context))
        {
            //if (!Utils.checkPermission(context, "android.permission.ACCESS_NETWORK_STATE"))
            //    return R.string.error_can_not_verify_internet_permission;
            if (!Utils.checkPermission(context, "android.permission.INTERNET"))
                return context.getString(R.string.error_without_internet_permission);

            return context.getString(R.string.error_is_not_online);
        }

        return null;
    }

    public static final boolean checkPermission(Context context, String permission)
    {
        // String permission = "android.permission.WRITE_EXTERNAL_STORAGE";
        int res = context.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    public static final ArrayList<View> getAllChildren(View v) {

        if (!(v instanceof ViewGroup)) {
            ArrayList<View> viewArrayList = new ArrayList<View>();
            viewArrayList.add(v);
            return viewArrayList;
        }

        ArrayList<View> result = new ArrayList<View>();

        ViewGroup vg = (ViewGroup) v;
        for (int i = 0; i < vg.getChildCount(); i++) {

            View child = vg.getChildAt(i);

            ArrayList<View> viewArrayList = new ArrayList<View>();
            viewArrayList.add(v);
            viewArrayList.addAll(getAllChildren(child));

            result.addAll(viewArrayList);
        }
        return result;
    }

    public static final void createLinkToCheckBox(CheckBox targetTextView,
                                  String completeString,
                                  String partToClick,
                                  ClickableSpan clickableAction) {

        SpannableString spannableString = new SpannableString(completeString);

        // make sure the String is exist, if it doesn't exist
        // it will throw IndexOutOfBoundException
        int startPosition = completeString.indexOf(partToClick);
        int endPosition = completeString.lastIndexOf(partToClick) + partToClick.length();

        spannableString.setSpan(
                clickableAction,
                startPosition,
                endPosition,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        targetTextView.setText(spannableString);
        targetTextView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public static void showDialogError(Context context, String message) {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(context);
        dlgAlert.setMessage(message);
        dlgAlert.setTitle(R.string.error_title);
        dlgAlert.setPositiveButton(R.string.ok_label, null);
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }

    public static void showDefaultDialogError(Context context, String message) {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(context);
        dlgAlert.setMessage(context.getString(R.string.error_unexpected) + " - " + message);
        dlgAlert.setTitle(R.string.error_title);
        dlgAlert.setPositiveButton(R.string.ok_label, null);
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }

    public static String formatPrice(Context context, String value) {
        String format = context.getString(R.string.format_coin);
        return format + " " + value;
    }

    public static String formatDate(Context context, Date date, int formatResId) {
        SimpleDateFormat dt1 = new SimpleDateFormat(context.getString(formatResId));
        return dt1.format(date);
    }

    public static Date tryParseDate(Context context, String date, int formatResId) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(context.getString(formatResId));
        Date myDate = null;
        try {
            myDate = dateFormat.parse(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return myDate;
    }
//
//    public static final void notify(Context context, String extraKey, String extraValue, String notificationTitle, String notificationMessage) {
//        Intent resultIntent = new Intent(context, MainActivity.class);
//        resultIntent.putExtra(extraKey, extraValue);
//        //resultIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//        // Sets the Activity to start in a new, empty task
//        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
//        stackBuilder.addParentStack(MainActivity.class);
//        stackBuilder.addNextIntent(resultIntent);
//
//        PendingIntent resultPendingIntent =
//                stackBuilder.getPendingIntent(
//                        0,
//                        PendingIntent.FLAG_UPDATE_CURRENT
//                );
//        NotificationCompat.Builder mBuilder =
//                new NotificationCompat.Builder(context)
//                        .setSmallIcon(R.drawable.ic_menu)
//                        .setContentTitle(notificationTitle)
//                        .setContentText(notificationMessage);
//        mBuilder.setContentIntent(resultPendingIntent);
//        mBuilder.setAutoCancel(true);
//        NotificationManager mNotificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
//        mNotificationManager.notify(1, mBuilder.build());
//    }
//
//    public static final void setAlarm(Context context)
//    {
//        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
//        Intent intent = new Intent(context, SchedulePaymentNotify.class);
//        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        // Set the alarm to start at 8:30 a.m.
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.set(Calendar.HOUR_OF_DAY, 11);
//        calendar.set(Calendar.MINUTE, 24);
//
//        // setRepeating() lets you specify a precise custom interval--in this case,
//        // 20 minutes.
//        //alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 20000, alarmIntent);
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 3000, 2000, alarmIntent);
//    }
}
