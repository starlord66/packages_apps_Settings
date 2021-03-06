/*
 * Copyright (C) 2020 The Android Open Source Project
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

package com.android.settings.sim.smartForwarding;

import static com.android.settings.sim.smartForwarding.SmartForwardingUtils.TAG;

import android.telephony.CallForwardingInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

public class DisableSmartForwardingTask implements Runnable {
    private final TelephonyManager tm;
    private final boolean[] callWaitingStatus;
    private final CallForwardingInfo[] callForwardingInfo;

    public DisableSmartForwardingTask(TelephonyManager tm,
            boolean[] callWaitingStatus, CallForwardingInfo[] callForwardingInfo) {
        this.tm = tm;
        this.callWaitingStatus = callWaitingStatus;
        this.callForwardingInfo = callForwardingInfo;
    }

    @Override
    public void run() {
        for (int i = 0; i < tm.getActiveModemCount(); i++) {
            if (callWaitingStatus != null) {
                Log.d(TAG, "Restore call waiting to " + callWaitingStatus[i]);
                tm.setCallWaitingEnabled(callWaitingStatus[i], null, null);
            }

            if (callForwardingInfo != null
                    && callForwardingInfo[i] != null
                    && callForwardingInfo[i].getTimeoutSeconds() > 0) {
                Log.d(TAG, "Restore call waiting to " + callForwardingInfo);
                tm.setCallForwarding(callForwardingInfo[i], null, null);
            }
        }
    }
}
