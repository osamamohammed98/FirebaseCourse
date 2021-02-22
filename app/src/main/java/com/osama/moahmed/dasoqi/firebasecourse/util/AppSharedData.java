package com.osama.moahmed.dasoqi.firebasecourse.util;

import android.text.TextUtils;
import android.widget.EditText;

public class AppSharedData {


    public static boolean isEmptyEditText(EditText editText) {
        if (TextUtils.isEmpty(editText.getText())) {
            return true;
        }
        return false;
    }

    public static String getTextFromET(EditText editText) {
        return editText.getText().toString();
    }

    public static void setErrorET(EditText editText , String error) {
         editText.setError(error);
    }
}
