/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 5/31/21, 12:32 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Database.Preferences.Theme

import android.content.Context
import co.geeksempire.premium.storefront.Database.Preferences.PreferencesIO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

object ThemeType {
    const val ThemeLight = true
    const val ThemeDark = false
}

class ThemePreferences (context: Context) {

    private val preferencesIO: PreferencesIO = PreferencesIO(context)

    /**
     * Light = True - Dark = False
     **/
    fun checkThemeLightDark() : Boolean {

        return ThemeType.ThemeLight
    }

    /**
     * Light = True - Dark = False
     **/
    fun changeLightDarkTheme(themeValue: Boolean) = CoroutineScope(Dispatchers.IO).async {

        preferencesIO.savePreferences(themeValue)
    }

}