package dev.gabrielhenrique.tiratime.core.extensions

import android.app.Activity
import android.content.res.Configuration

fun Activity.isSystemInDarkTheme(): Boolean {
    return (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
}