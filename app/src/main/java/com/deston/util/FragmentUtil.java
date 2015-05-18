package com.deston.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class FragmentUtil {
    public static void replace(FragmentManager fragmentManager, int contentID, Fragment fragment) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(contentID, fragment);
        ft.commit();
    }
}
