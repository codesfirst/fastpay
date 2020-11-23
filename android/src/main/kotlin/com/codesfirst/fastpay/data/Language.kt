package com.codesfirst.fastpay.data

object Language {
    val English = "en_US"
    val Arabic  = "ar_AR"
    val Basque = "eu_ES"
    val Catalan = "ca_ES"
    val Chinese = "zh_CN"
    val Dutch = "nl_NL"
    val Finnish = "fi_FI"
    val French = "fr_FR"
    val Galician = "gl_ES"
    val German = "de_DE"
    val Italian = "it_IT"
    val Japanese = "ja_JA"
    val Korean = "ko_KR"
    val Norwegian = "nb_NO"
    val Polish = "pl_PL"
    val Portuguese = "pt_PT"
    val Russian = "ru_RU"
    val Serbian = "sr_RS"
    val Spanish = "es_ES"
    val Swedish = "sv_SE"
    val Thai = "th_TH"

    fun getLanguage(key: String?): String {
        key?.let {
            when(key.toLowerCase()){
                English.substring(0..1)  -> return English
                Arabic.substring(0..1) -> return Arabic
                Basque.substring(0..1) -> return Basque
                Catalan.substring(0..1) -> return Catalan
                Chinese.substring(0..1) -> return Chinese
                Dutch.substring(0..1) -> return Dutch
                Finnish.substring(0..1) -> return Finnish
                French.substring(0..1) -> return French
                Galician.substring(0..1) -> return Galician
                German.substring(0..1) -> return German
                Italian.substring(0..1) -> return Italian
                Japanese.substring(0..1) -> return Japanese
                Korean.substring(0..1) -> return Korean
                Norwegian.substring(0..1) -> return Norwegian
                Polish.substring(0..1) -> return Polish
                Portuguese.substring(0..1) -> return Portuguese
                Russian.substring(0..1) -> return Russian
                Serbian.substring(0..1) -> return Serbian
                Spanish.substring(0..1) -> return Spanish
                Swedish.substring(0..1) -> return Swedish
                Thai.substring(0..1) -> return Thai
                else -> return English
            }
        }?: run {
            return English
        }
    }
}