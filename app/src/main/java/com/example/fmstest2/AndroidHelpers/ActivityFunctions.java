package com.example.fmstest2.AndroidHelpers;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import com.example.fmstest2.R;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import Model.Event;
import Model.Person;
import Server.ServerData;

/**
 * This is a static class that contains usefull functions for the application.
 */
public class ActivityFunctions {

    public static Drawable getGenderIcon(Activity activity, String gender) {
        if (gender.equals("m")) {
            return new IconDrawable(activity, Iconify.IconValue.fa_male)
                    .colorRes(R.color.line_red)
                    .sizeDp(40);
        }
        else {
            return new IconDrawable(activity, Iconify.IconValue.fa_female)
                    .colorRes(R.color.pink)
                    .sizeDp(40);
        }
    }



}
