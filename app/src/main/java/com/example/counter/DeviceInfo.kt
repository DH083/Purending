package com.example.counter

import android.content.Context
import android.content.pm.PackageInfo
import android.os.Build
import android.provider.Settings

class DeviceInfo(val context: Context) {
    fun getDeviceId(): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    fun getDeviceModel(): String {
        return Build.MODEL
    }

    fun getDeviceOs(): String {
        return Build.VERSION.RELEASE.toString()
    }

    fun getAppVersion(): String {
        val info: PackageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        return info.versionName
    }
}