/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.settings.fuelgauge.batterytip;

import android.content.Context;
import android.provider.Settings;
import android.support.annotation.VisibleForTesting;
import android.util.KeyValueListParser;
import android.util.Log;

/**
 * Class to store the policy for battery tips, which comes from
 * {@link Settings.Global}
 */
public class BatteryTipPolicy {
    public static final String TAG = "BatteryTipPolicy";

    private static final String KEY_BATTERY_TIP_ENABLED = "battery_tip_enabled";
    private static final String KEY_SUMMARY_ENABLED = "summary_enabled";
    private static final String KEY_BATTERY_SAVER_TIP_ENABLED = "battery_saver_tip_enabled";
    private static final String KEY_HIGH_USAGE_ENABLED = "high_usage_enabled";
    private static final String KEY_HIGH_USAGE_APP_COUNT = "high_usage_app_count";
    private static final String KEY_APP_RESTRICTION_ENABLED = "app_restriction_enabled";
    private static final String KEY_REDUCED_BATTERY_ENABLED = "reduced_battery_enabled";
    private static final String KEY_REDUCED_BATTERY_PERCENT = "reduced_battery_percent";
    private static final String KEY_LOW_BATTERY_ENABLED = "low_battery_enabled";
    private static final String KEY_LOW_BATTERY_HOUR = "low_battery_hour";

    /**
     * {@code true} if general battery tip is enabled
     *
     * @see Settings.Global#BATTERY_TIP_CONSTANTS
     * @see #KEY_BATTERY_TIP_ENABLED
     */
    public final boolean batteryTipEnabled;

    /**
     * {@code true} if summary tip is enabled
     *
     * @see Settings.Global#BATTERY_TIP_CONSTANTS
     * @see #KEY_SUMMARY_ENABLED
     */
    public final boolean summaryEnabled;

    /**
     * {@code true} if battery saver tip is enabled
     *
     * @see Settings.Global#BATTERY_TIP_CONSTANTS
     * @see #KEY_BATTERY_SAVER_TIP_ENABLED
     */
    public final boolean batterySaverTipEnabled;

    /**
     * {@code true} if high usage tip is enabled
     *
     * @see Settings.Global#BATTERY_TIP_CONSTANTS
     * @see #KEY_HIGH_USAGE_ENABLED
     */
    public final boolean highUsageEnabled;

    /**
     * The maximum number of apps shown in high usage
     *
     * @see Settings.Global#BATTERY_TIP_CONSTANTS
     * @see #KEY_HIGH_USAGE_APP_COUNT
     */
    public final int highUsageAppCount;

    /**
     * {@code true} if app restriction tip is enabled
     *
     * @see Settings.Global#BATTERY_TIP_CONSTANTS
     * @see #KEY_APP_RESTRICTION_ENABLED
     */
    public final boolean appRestrictionEnabled;

    /**
     * {@code true} if reduced battery tip is enabled
     *
     * @see Settings.Global#BATTERY_TIP_CONSTANTS
     * @see #KEY_REDUCED_BATTERY_ENABLED
     */
    public final boolean reducedBatteryEnabled;

    /**
     * The percentage of reduced battery to trigger the tip(e.g. 50%)
     *
     * @see Settings.Global#BATTERY_TIP_CONSTANTS
     * @see #KEY_REDUCED_BATTERY_PERCENT
     */
    public final int reducedBatteryPercent;

    /**
     * {@code true} if low battery tip is enabled
     *
     * @see Settings.Global#BATTERY_TIP_CONSTANTS
     * @see #KEY_LOW_BATTERY_ENABLED
     */
    public final boolean lowBatteryEnabled;

    /**
     * Remaining battery hour to trigger the tip(e.g. 16 hours)
     *
     * @see Settings.Global#BATTERY_TIP_CONSTANTS
     * @see #KEY_LOW_BATTERY_HOUR
     */
    public final int lowBatteryHour;

    private final KeyValueListParser mParser;

    public BatteryTipPolicy(Context context) {
        this(context, new KeyValueListParser(','));
    }

    @VisibleForTesting
    BatteryTipPolicy(Context context, KeyValueListParser parser) {
        mParser = parser;
        final String value = Settings.Global.getString(context.getContentResolver(),
                Settings.Global.BATTERY_TIP_CONSTANTS);

        try {
            mParser.setString(value);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "Bad battery tip constants");
        }

        batteryTipEnabled = mParser.getBoolean(KEY_BATTERY_TIP_ENABLED, true);
        summaryEnabled = mParser.getBoolean(KEY_SUMMARY_ENABLED, true);
        batterySaverTipEnabled = mParser.getBoolean(KEY_BATTERY_SAVER_TIP_ENABLED, true);
        highUsageEnabled = mParser.getBoolean(KEY_HIGH_USAGE_ENABLED, true);
        highUsageAppCount = mParser.getInt(KEY_HIGH_USAGE_APP_COUNT, 3);
        appRestrictionEnabled = mParser.getBoolean(KEY_APP_RESTRICTION_ENABLED, true);
        reducedBatteryEnabled = mParser.getBoolean(KEY_REDUCED_BATTERY_ENABLED, true);
        reducedBatteryPercent = mParser.getInt(KEY_REDUCED_BATTERY_PERCENT, 50);
        lowBatteryEnabled = mParser.getBoolean(KEY_LOW_BATTERY_ENABLED, true);
        lowBatteryHour = mParser.getInt(KEY_LOW_BATTERY_HOUR, 16);
    }

}