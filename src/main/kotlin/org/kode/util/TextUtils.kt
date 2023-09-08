package org.kode.util

object TextUtils {

    fun displayFormatText(text: String): String {
        val formattedText = text.replace(Regex("([a-z])([A-Z])"), "$1 $2")
        return formattedText.substring(0, 1).uppercase() + formattedText.substring(1)
    }

}