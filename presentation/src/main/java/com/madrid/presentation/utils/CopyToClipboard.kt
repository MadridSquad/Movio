package com.madrid.presentation.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast

fun copyToClipboard(text: String, context: Context) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("Series Link", text)
    clipboard.setPrimaryClip(clip)
    Toast.makeText(context, "Link copied", Toast.LENGTH_SHORT).show()
}