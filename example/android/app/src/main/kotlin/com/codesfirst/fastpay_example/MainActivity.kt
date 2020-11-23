package com.codesfirst.fastpay_example

import android.content.ContextWrapper
import android.os.Bundle
import com.codesfirst.fastpay.FastPayment
import com.pixplicity.easyprefs.library.Prefs
import io.flutter.app.FlutterFragmentActivity

class MainActivity: FlutterFragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(packageName)
                .setUseDefaultSharedPreference(true)
                .build()
        FastPayment.registerWith(registrarFor("com.codesfirst.fastpay.FastPayment"))
    }
}
