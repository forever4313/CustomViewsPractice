package com.k_beta.exercise.customviewspractice.utils;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created by dongkai on 2015/12/24.
 */
public class StringUtils {

    private static DecimalFormat dfs = null;

    public static DecimalFormat formatFloat(String pattern) {
        if (dfs == null) {
            dfs = new DecimalFormat();
        }
        dfs.setRoundingMode(RoundingMode.FLOOR);
        dfs.applyPattern(pattern);
        return dfs;
    }
}
