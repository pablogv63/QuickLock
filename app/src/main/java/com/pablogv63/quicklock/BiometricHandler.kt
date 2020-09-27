package com.pablogv63.quicklock

import androidx.biometric.BiometricManager
import java.util.concurrent.Executor

class BiometricHandler (val executor: Executor, val biometricManager: BiometricManager) {

    public fun authenticate() {
        when (biometricManager.canAuthenticate()) {
            
        }
    }

}