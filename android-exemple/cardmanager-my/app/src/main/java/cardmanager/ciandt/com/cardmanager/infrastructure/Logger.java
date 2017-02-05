package cardmanager.ciandt.com.cardmanager.infrastructure;

import android.text.TextUtils;
import android.util.Log;

import cardmanager.ciandt.com.cardmanager.BuildConfig;

/**
 * This class concentrates all t
 */
public abstract class Logger {

    private static final String DEFAULT_TAG = "CardManagerMy";

    private static boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    /**
     * Send a verbose log message.
     *
     * @param tag Used to identify the source of a log message. It usually identifies the class or
     *            activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void verbose(String tag, String msg) {
        if (isDebug() && !TextUtils.isEmpty(msg)) {
            Log.v(tag, msg);
        }
    }

    /**
     * Send a verbose log message and log the exception.
     *
     * @param msg The message you would like logged.
     */
    public static void verbose(String msg) {
        if (isDebug() && !TextUtils.isEmpty(msg)) {
            Log.v(DEFAULT_TAG, msg);
        }
    }

    /**
     * Send a debug log message.
     *
     * @param tag Used to identify the source of a log message. It usually identifies the class or
     *            activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void debug(String tag, String msg) {
        if (isDebug() && !TextUtils.isEmpty(msg)) {
            Log.d(tag, msg);
        }
    }

    /**
     * Send a debug log message and log the exception.
     *
     * @param msg The message you would like logged.
     */
    public static void debug(String msg) {
        if (isDebug() && !TextUtils.isEmpty(msg)) {
            Log.d(DEFAULT_TAG, msg);
        }
    }

    /**
     * Send an info log message.
     *
     * @param tag Used to identify the source of a log message. It usually identifies the class or
     *            activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void info(String tag, String msg) {
        if (isDebug() && !TextUtils.isEmpty(msg)) {
            Log.i(tag, msg);
        }
    }

    /**
     * Send a info log message and log the exception.
     *
     * @param msg The message you would like logged.
     */
    public static void info(String msg) {
        if (isDebug() && !TextUtils.isEmpty(msg)) {
            Log.i(DEFAULT_TAG, msg);
        }
    }

    /**
     * Send an warn log message.
     *
     * @param tag Used to identify the source of a log message. It usually identifies the class or
     *            activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void warn(String tag, String msg) {
        if (isDebug() && !TextUtils.isEmpty(msg)) {
            Log.w(tag, msg);
        }
    }

    /**
     * Send an warn log message.
     *
     * @param msg The message you would like logged.
     */
    public static void warn(String msg) {
        if (isDebug() && !TextUtils.isEmpty(msg)) {
            Log.w(DEFAULT_TAG, msg);
        }
    }

    /**
     * Send an error log message.
     *
     * @param tag Used to identify the source of a log message. It usually identifies the class or
     *            activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void error(String tag, String msg) {
        if (isDebug() && !TextUtils.isEmpty(msg)) {
            Log.e(tag, msg);
        }
    }

    /**
     * Send a error log message and log the exception.
     *
     * @param msg The message you would like logged.
     */
    public static void error(String msg) {
        if (isDebug() && !TextUtils.isEmpty(msg)) {
            Log.e(DEFAULT_TAG, msg);
        }
    }

    /**
     * Send a error log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param tr  An exception to log
     */
    public static void error(String msg, Throwable tr) {
        if (isDebug() && !TextUtils.isEmpty(msg)) {
            Log.e(DEFAULT_TAG, msg, tr);
        }
    }

    /**
     * Send a error log message and log the exception.
     *
     * @param tag Used to identify the source of a log message. It usually identifies the class or
     *            activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr  An exception to log
     */
    public static void error(String tag, String msg, Throwable tr) {
        if (isDebug() && !TextUtils.isEmpty(msg)) {
            Log.e(tag, msg, tr);
        }
    }
}