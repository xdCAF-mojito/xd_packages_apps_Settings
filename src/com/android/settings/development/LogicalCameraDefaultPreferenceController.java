/*
 * Copyright (C) 2018 The Android Open Source Project
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

package com.android.settings.development;

import android.content.Context;
import android.os.SystemProperties;
import androidx.preference.SwitchPreference;
import androidx.preference.Preference;
import android.text.TextUtils;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

public class LogicalCameraDefaultPreferenceController extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener, PreferenceControllerMixin {

    private static final String KEY_LOGICAL_CAMERA_DEFAULT_SWITCH = "logical_camera_default_switch";
    @VisibleForTesting
    static final String BUILD_TYPE = "ro.build.type";
    @VisibleForTesting
    static final String PROPERTY_LOGICAL_CAMERA_DEFAULT = "persist.camera.logical.default";
    @VisibleForTesting
    static final int ENABLED = 1;
    @VisibleForTesting
    static final int DISABLED = 0;
    @VisibleForTesting
    static final String USERDEBUG_BUILD = "userdebug";
    @VisibleForTesting
    static final String ENG_BUILD = "eng";
    @VisibleForTesting
    static final String USER_BUILD = "user";

    public LogicalCameraDefaultPreferenceController(Context context) {
        super(context);
    }

    @Override
    public boolean isAvailable() {
        return mContext.getResources().getBoolean(R.bool.config_show_logical_camera_default);
    }

    @Override
    public String getPreferenceKey() {
        return KEY_LOGICAL_CAMERA_DEFAULT_SWITCH;
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        final boolean isEnabled = (Boolean) newValue;
        String value = Integer.toString(isEnabled ? ENABLED : DISABLED);
        SystemProperties.set(PROPERTY_LOGICAL_CAMERA_DEFAULT, value);
        return true;
    }

    @Override
    public void updateState(Preference preference) {
        final boolean enabled = isLogicalCameraDefault();
        ((SwitchPreference) mPreference).setChecked(enabled);
    }

    @Override
    protected void onDeveloperOptionsSwitchDisabled() {
        super.onDeveloperOptionsSwitchDisabled();
        SystemProperties.set(PROPERTY_LOGICAL_CAMERA_DEFAULT, Integer.toString(DISABLED));
        ((SwitchPreference) mPreference).setChecked(false);
    }

    private boolean isLogicalCameraDefault() {
        final String prop = SystemProperties.get(PROPERTY_LOGICAL_CAMERA_DEFAULT,
                Integer.toString(DISABLED));
        return TextUtils.equals(Integer.toString(ENABLED), prop);
    }

}
