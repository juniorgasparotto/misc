package cardmanager.ciandt.com.cardmanager.infrastructure;

import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cardmanager.ciandt.com.cardmanager.R;
import cardmanager.ciandt.com.cardmanager.data.repository.sharedpreferences.SharedPreferencesRepository;
import cardmanager.ciandt.com.cardmanager.data.repository.webapi.WebApiRepository;

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
}
