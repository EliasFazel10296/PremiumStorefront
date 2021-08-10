/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/10/21, 1:10 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.MoviesFiltering.Filter

import android.util.Log
import androidx.annotation.Keep
import co.geeksempire.premium.storefront.StorefrontConfigurations.ContentFiltering.Filter.SortingOptions
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.DataStructure.MoviesDataStructure
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.DataStructure.MoviesStorefrontLiveData
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async

object SortingOptions {
    const val SortByPrice = "ProductPrice"
    const val SortByRating = "Rating"
}

object FilteringOptions {
    const val FilterByCountry = "FilterByCountry"
    const val FilterByStudio = "FilterByStudio"
}

@Keep
data class FilterOptionsItem(var filterOptionLabel: String, var filterOptionIconLink: String?)

class FilterAllMovies (private val moviesStorefrontLiveData: MoviesStorefrontLiveData) {

    fun filterAllMoviesByGenre(storefrontAllUnfilteredMovies: ArrayList<DocumentSnapshot>,
                               selectedGenre: String) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

        Log.d(this@FilterAllMovies.javaClass.simpleName, "Selected Genre: ${selectedGenre}")

        val filteredMovies = ArrayList<DocumentSnapshot>()

        storefrontAllUnfilteredMovies.forEach {

            it.data?.let { moviesHashMap ->

                val moviesDataStructure = MoviesDataStructure(moviesHashMap)


                if (moviesDataStructure.movieGenres().lowercase().contains(selectedGenre.lowercase())) {

                    filteredMovies.add(it)

                }

            }

        }

        moviesStorefrontLiveData.allFilteredMoviesItemData.postValue(Pair(filteredMovies, false))

    }

    fun sortAllMoviesByInput(storefrontFilteredContents: ArrayList<DocumentSnapshot>,
                              sortInputParameter: String) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

        Log.d(this@FilterAllMovies.javaClass.simpleName, "Sorting Input Data | ${storefrontFilteredContents.size} Products: ${sortInputParameter}")

        if (storefrontFilteredContents.isNotEmpty()) {

            val sortedStorefrontFilteredContents = ArrayList<DocumentSnapshot>()

            val storefrontAllContentsFilter = storefrontFilteredContents.sortedByDescending { documentSnapshot ->

                documentSnapshot.data?.let {

                    val moviesDataStructure = MoviesDataStructure(it)

                    when (sortInputParameter) {
                        SortingOptions.SortByRating -> {

                            moviesDataStructure.movieRating()

                        }
                        SortingOptions.SortByPrice -> {

                            moviesDataStructure.moviePurchasePrice()

                        }
                        else -> moviesDataStructure.movieName()
                    }

                }

            }

            sortedStorefrontFilteredContents.clear()
            sortedStorefrontFilteredContents.addAll(storefrontAllContentsFilter)

            moviesStorefrontLiveData.allFilteredMoviesItemData.postValue(Pair(sortedStorefrontFilteredContents, false))

        }

    }

}