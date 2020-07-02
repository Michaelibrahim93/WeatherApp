package com.test.basemodule.utils;

import android.util.Log;

import com.test.basemodule.BuildConfig;

/**
 * Created by Michael on 11/27/17.
 */


public class LogUtils {

    private final static String LOG_TAG = "LogUtils";

    public static void here() {
        if (isDebug()) {
            Log.d(LOG_TAG, getFullMessage(null, ""));
        }
    }

    public static void here(Class<?> callingClass) {
        if (isDebug()) {
            Log.d(LOG_TAG, getFullMessage(callingClass, ""));
        }
    }

    public static void d(String message) {
        if (isDebug()) {
            Log.d(LOG_TAG, getFullMessage(null, message));
        }
    }

    public static void d(String tag, String message) {
        if (isDebug()) {
            Log.d(tag, getFullMessage(null, message));
        }
    }

    public static void d(String message, Throwable t) {
        if (isDebug()) {
            Log.d(LOG_TAG, getFullMessage(null, message), t);
        }
    }

    public static void wtf(String message) {
        if (isDebug()) {
            Log.wtf(LOG_TAG, getFullMessage(null, message));
        }
    }

    public static void wtf(String tag, String message) {
        if (isDebug()) {
            Log.wtf(tag, getFullMessage(null, message));
        }
    }

    public static void i(String message) {
        if (isDebug()) {
            Log.i(LOG_TAG, getFullMessage(null, message));
        }
    }

    public static void i(String tag, String message) {
        if (isDebug()) {
            Log.i(tag, getFullMessage(null, message));
        }
    }

    public static void i(Class<?> callingClass, String message) {
        if (isDebug()) {
            Log.i(LOG_TAG, getFullMessage(callingClass, message));
        }
    }

    public static void w(String message) {
        if (isDebug()) {
            Log.w(LOG_TAG, getFullMessage(null, message));
        }
    }

    public static void w(String tag, String message) {
        if (isDebug()) {
            Log.w(tag, getFullMessage(null, message));
        }
    }

    public static void w(String message, Throwable t) {
        if (isDebug()) {
            Log.w(LOG_TAG, getFullMessage(null, message), t);
        }
    }

    public static void e(String message) {
        if (isDebug()) {
            Log.e(LOG_TAG, getFullMessage(null, message));
        }
    }

    public static void e(Class<?> callingClass, String message) {
        if (isDebug()) {
            Log.e(LOG_TAG, getFullMessage(callingClass, message));
        }
    }

    public static void e(Class<?> callingClass, Throwable t) {
        if (isDebug()) {
            Log.e(LOG_TAG, getFullMessage(callingClass, ""), t);
        }
    }

    public static void e(String message, Throwable t) {
        if (isDebug()) {
            Log.e(LOG_TAG, getFullMessage(null, message), t);
        }
    }

    public static void e(String tag, String message) {
        if (isDebug()) {
            Log.e(tag, getFullMessage(null, message));
        }
    }

    public static void e(String tag, String message, Throwable exception) {
        if (isDebug()) {
            Log.e(tag, getFullMessage(null, message), exception);
        }
    }

    public static void v(String message) {
        if (isDebug()) {
            Log.v(LOG_TAG, getFullMessage(null, message));
        }
    }

    public static void v(String tag, String message) {
        if (isDebug()) {
            Log.v(tag, getFullMessage(null, message));
        }
    }

    private static String getFullMessage(Class<?> callingClass, String message) {
        int lineNumber = Thread.currentThread().getStackTrace()[4].getLineNumber();
        String methodName = Thread.currentThread().getStackTrace()[4].getMethodName();
        String[] classPackage = Thread.currentThread().getStackTrace()[4].getClassName().split("\\.");
        String className = callingClass != null ? callingClass.getSimpleName() :
                (classPackage.length > 0 ? classPackage[classPackage.length - 1] : Thread.currentThread().getStackTrace()[4].getClassName());

        return className + "." + methodName + ":" + lineNumber + (message != null && message.length() > 0 ? " - " + message : "");
    }

    private static boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    public static void logToFile(String tag, String msg) {

    }
}
