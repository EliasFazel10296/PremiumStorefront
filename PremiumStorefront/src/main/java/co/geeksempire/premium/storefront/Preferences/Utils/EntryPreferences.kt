/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 7/30/21, 11:23 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Preferences.Utils

import androidx.datastore.preferences.core.stringPreferencesKey
import co.geeksempire.premium.storefront.Database.Preferences.PreferencesIO
import kotlinx.coroutines.flow.Flow

class EntryPreferences (private val preferencesIO: PreferencesIO) {

    companion object {
        const val EntryStorefrontApplications = "EntryStorefrontApplications"
        const val EntryStorefrontGames = "EntryStorefrontGames"
        const val EntryStorefrontMovies = "EntryStorefrontMovies"
    }

    suspend fun entryType(inputValue: String) {

        preferencesIO.savePreferences(stringPreferencesKey(EntryStorefrontApplications), inputValue)

    }

    fun entryType(): Flow<String> {

        return preferencesIO.readPreferencesString(stringPreferencesKey(EntryStorefrontApplications), EntryPreferences.EntryStorefrontApplications)
    }

}