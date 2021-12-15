/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 12/15/21, 6:25 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.AdvancedSearch.UserInterface

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import co.geeksempire.premium.storefront.AdvancedSearch.DataStructure.CompleteSearchLiveData
import co.geeksempire.premium.storefront.AdvancedSearch.Extensions.setupCompleteSearchUserInterface
import co.geeksempire.premium.storefront.AdvancedSearch.NetworkOperations.SearchAllProducts
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemePreferences
import co.geeksempire.premium.storefront.databinding.CompleteSearchLayoutBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CompleteSearch : AppCompatActivity() {

    val themePreferences: ThemePreferences by lazy {
        ThemePreferences(this@CompleteSearch)
    }

    val completeSearchLiveData: CompleteSearchLiveData by lazy {
        ViewModelProvider(this@CompleteSearch)[CompleteSearchLiveData::class.java]
    }

    val searchAllProducts: SearchAllProducts by lazy {
        SearchAllProducts(this@CompleteSearch)
    }

    lateinit var completeSearchLayoutBinding: CompleteSearchLayoutBinding

    companion object {
        const val SearchQuery: String = "SearchQuery"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        completeSearchLayoutBinding = CompleteSearchLayoutBinding.inflate(layoutInflater)
        setContentView(completeSearchLayoutBinding.root)

        if (intent.hasExtra(CompleteSearch.SearchQuery)) {

            val searchQuery = intent.getStringExtra(CompleteSearch.SearchQuery)

            if (searchQuery != null) {

                lifecycleScope.launch {

                    themePreferences.checkThemeLightDark().collect {

                        setupCompleteSearchUserInterface(it)

                    }

                }

                completeSearchLiveData.applicationsSearchResults.observe(this@CompleteSearch, {



                })

                completeSearchLiveData.gamesSearchResults.observe(this@CompleteSearch, {



                })

                completeSearchLiveData.moviesSearchResults.observe(this@CompleteSearch, {



                })

                searchAllProducts.startApplicationsSearch(searchQuery)

                searchAllProducts.startGamesSearch(searchQuery)

                searchAllProducts.startMoviesSearch(searchQuery)

            } else {

                this@CompleteSearch.finish()

            }

        }

        completeSearchLayoutBinding.cancelIconCompleteSearch.setOnClickListener {

            this@CompleteSearch.finish()

        }

    }

    override fun onPause() {
        super.onPause()
    }

}