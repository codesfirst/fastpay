package com.codesfirst.fastpay.data
import android.util.Patterns
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.pixplicity.easyprefs.library.Prefs
import java.net.MalformedURLException
import java.net.URISyntaxException
import java.net.URL

object VarGlobal {
    var THEMELIGTH: String = "LIGHT"
    var THEMEDARK: String = "DARK"
}

data class Message(
        @SerializedName("message_successful_payment") var message_successful_payment: String? = "The payment was successful",
        @SerializedName("message_unsuccessful_payment") var message_unsuccessful_payment: String? = "The payment was unsuccessful",
        @SerializedName("progress_message_please_wait") var progress_message_please_wait: String? = "Please wait…",
        @SerializedName("progress_message_checkout_id") var progress_message_checkout_id: String? = "Getting the checkout id…",
        @SerializedName("progress_message_checkout_info") var progress_message_checkout_info: String? = "Getting the checkout info…",
        @SerializedName("progress_message_payment_status") var progress_message_payment_status: String? = "Getting the payment status…",
        @SerializedName("progress_message_processing_payment") var progress_message_processing_payment: String? = "Processing payment…",
        @SerializedName("error_message") var error_message: String? = "An error occurred",
        @SerializedName("error_empty_fields") var error_empty_fields: String? = "The fields must not be empty"
)

data class Settings(
        @SerializedName("theme") var theme: String = "Light",
        @SerializedName("theme_customize") var theme_customize: String? = "",
        @SerializedName("storing_payment_data") var storing_payment_data: String? = "prompt",
        @SerializedName("display_total_amount") var display_total_amount: Boolean = false,
        @SerializedName("skipping_cvv") var skipping_cvv: String? = "never",
        @SerializedName("device_authentication") var device_authentication: String? = "never",
        @SerializedName("language") var language: String? = "en",
        @SerializedName("display_installment") var display_installment: Boolean = false,
        @SerializedName("arr_display_installment") var arr_display_installment: List<Int> = listOf(1,2,3)
)

data class Config(
        @SerializedName("message") var message: Message = Message(),
        @SerializedName("setting") var setting: Settings = Settings()
)

class ConfigJson {

    lateinit var arr_display_installment:List<Int>

    fun LoadJson(json: String?){
        json?.let {
                var dataJson = Config()
                try {
                    if(it.isNotEmpty()) {
                        dataJson = Gson().fromJson(it, Config::class.java)
                    }
                }catch (e: Exception){
                    print(e)
                }
                setConfig(dataJson)
        }?: run {
            var dataJson = Config()
            setConfig(dataJson)
        }
    }
    

    fun getPrefsConfig(key: String): String = Prefs.getString(key, "")

    fun getPrefsIntValueConf(key: String): Int = Prefs.getInt(key, -1)

    fun getPrefsBoolValueConf(key: String): Boolean = Prefs.getBoolean(key, false)

    fun getLanguage(key: String): String = Language.getLanguage(key)

    fun setConfig(dataJson: Config){
        //Configuraciones
        dataJson.setting.theme.let { PrefSingleton.setPrefs("theme", it) }
        dataJson.setting.theme_customize?.let { PrefSingleton.setPrefs("theme_customize", it) }
        dataJson.setting.storing_payment_data?.let { PrefSingleton.setPrefs("storing_payment_data", it) }
        dataJson.setting.display_total_amount?.let { PrefSingleton.setPrefs("display_total_amount", it) }
        dataJson.setting.skipping_cvv?.let { PrefSingleton.setPrefs("skipping_cvv", it) }
        dataJson.setting.device_authentication?.let { PrefSingleton.setPrefs("device_authentication", it) }
        dataJson.setting.language?.let { PrefSingleton.setPrefs("language", it) }
        dataJson.setting.display_installment.let { PrefSingleton.setPrefs("display_installment", it) }
        arr_display_installment = dataJson.setting.arr_display_installment

        //Mensajes
        dataJson.message.message_successful_payment?.let { PrefSingleton.setPrefs("message_successful_payment", it) }
        dataJson.message.message_unsuccessful_payment?.let { PrefSingleton.setPrefs("message_unsuccessful_payment", it) }
        dataJson.message.progress_message_please_wait?.let { PrefSingleton.setPrefs("progress_message_please_wait", it) }
        dataJson.message.progress_message_checkout_info?.let { PrefSingleton.setPrefs("progress_message_checkout_info", it) }
        dataJson.message.progress_message_processing_payment?.let { PrefSingleton.setPrefs("progress_message_processing_payment", it) }
        dataJson.message.error_message?.let { PrefSingleton.setPrefs("error_message", it) }
        dataJson.message.error_empty_fields?.let { PrefSingleton.setPrefs("error_empty_fields", it) }
        dataJson.message.progress_message_checkout_id?.let { PrefSingleton.setPrefs("progress_message_checkout_id", it) }
        dataJson.message.progress_message_payment_status?.let { PrefSingleton.setPrefs("progress_message_payment_status", it) }
        PrefSingleton.setPrefs("isLoad", true)
    }

    fun loadJsonClient(jsonClient: String?):Boolean{
        jsonClient?.let {
            try {
                if(it.isNotEmpty()) {
                    Checkout.checkoutObject = Gson().fromJson(it, CheckoutObject::class.java)
                    return true
                }
            }catch (e: Exception){
                print(e)
            }
        }
        return false
    }

    fun validateUrl(url: String?):Boolean{
        url?.let {
            try {
                URL(it).toURI()
                return true
            } catch (exception: URISyntaxException) {
                return false
            } catch (exception: MalformedURLException) {
                return false
            }
        }
        return false
    }

    fun validateUrlPatterns(url: String?):Boolean{
        url?.let {
            return Patterns.WEB_URL.matcher(it).matches()
        }
        return false
    }
}