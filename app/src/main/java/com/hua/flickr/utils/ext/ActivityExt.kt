package com.hua.flickr.utils.ext

import android.app.Activity
import android.content.Intent

fun Activity.share(text: String) {
    val shareIntent = Intent(Intent.ACTION_SEND)
    shareIntent.setType("text/plain")
    shareIntent.putExtra(Intent.EXTRA_TEXT, text)
    val chooserTitle = "Share via"
    val chooser = Intent.createChooser(shareIntent, chooserTitle)

    if (shareIntent.resolveActivity(packageManager) != null) {
        startActivity(chooser)
    }
}