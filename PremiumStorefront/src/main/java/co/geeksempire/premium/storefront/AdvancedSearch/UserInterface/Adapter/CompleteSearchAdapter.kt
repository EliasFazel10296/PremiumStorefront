/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 12/17/21, 3:28 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.AdvancedSearch.UserInterface.Adapter

import android.content.res.ColorStateList
import android.text.Html
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.AdvancedSearch.DataStructure.CompleteSearchContent
import co.geeksempire.premium.storefront.AdvancedSearch.UserInterface.CompleteSearch
import co.geeksempire.premium.storefront.AdvancedSearch.UserInterface.ViewHolder.CompleteSearchViewHolder
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemeType
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.ProductsContentKey
import co.geeksempire.premium.storefront.StorefrontConfigurations.NetworkEndpoints.GeneralEndpoints
import co.geeksempire.premium.storefront.Utils.Data.openPlayStoreToInstallApplications
import co.geeksempire.premium.storefront.Utils.Data.openPlayStoreToWatchMovie
import co.geeksempire.premium.storefront.Utils.UI.Colors.setColorAlpha
import co.geeksempire.premium.storefront.databinding.CompleteSearchLayoutItemBinding
import com.bumptech.glide.Glide

class CompleteSearchAdapter (private val context: CompleteSearch, private val themeType: Boolean) : RecyclerView.Adapter<CompleteSearchViewHolder>() {

    val completeSearchResultsItems: ArrayList<CompleteSearchContent> = ArrayList<CompleteSearchContent>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CompleteSearchViewHolder {

        return CompleteSearchViewHolder(CompleteSearchLayoutItemBinding.inflate(context.layoutInflater, viewGroup, false))
    }

    override fun getItemCount(): Int {

        return completeSearchResultsItems.size
    }

    override fun onBindViewHolder(completeSearchViewHolder: CompleteSearchViewHolder, position: Int) {

        when (themeType) {
            ThemeType.ThemeLight -> {

                completeSearchViewHolder.productTitle.setTextColor(context.getColor(R.color.dark))

            }
            ThemeType.ThemeDark -> {

                completeSearchViewHolder.productTitle.setTextColor(context.getColor(R.color.light))

            }
            else -> {}
        }

        when (completeSearchResultsItems[position].searchResultType) {
            GeneralEndpoints.QueryType.ApplicationsQuery -> {

                completeSearchViewHolder.blurryBackground.setOverlayColor(context.getColor(R.color.applicationsSectionTransparentColor))
                completeSearchViewHolder.blurryBackground.setSecondOverlayColor(setColorAlpha(context.getColor(R.color.applicationsSectionColor), 113f))

                completeSearchViewHolder.searchQueryType.imageTintList = ColorStateList.valueOf(context.getColor(R.color.applicationsSectionColor))
                completeSearchViewHolder.searchQueryType.setImageDrawable(context.getDrawable(R.drawable.applications_icon))
                completeSearchViewHolder.searchQueryType.contentDescription = context.getString(R.string.applicationsSectionText)

            }
            GeneralEndpoints.QueryType.GamesQuery -> {

                completeSearchViewHolder.blurryBackground.setOverlayColor(context.getColor(R.color.gamesSectionTransparentColor))
                completeSearchViewHolder.blurryBackground.setSecondOverlayColor(setColorAlpha(context.getColor(R.color.gamesSectionColor), 113f))

                completeSearchViewHolder.searchQueryType.imageTintList = ColorStateList.valueOf(context.getColor(R.color.gamesSectionColor))
                completeSearchViewHolder.searchQueryType.setImageDrawable(context.getDrawable(R.drawable.games_icon))
                completeSearchViewHolder.searchQueryType.contentDescription = context.getString(R.string.gamesSectionText)

            }
            GeneralEndpoints.QueryType.MoviesQuery -> {

                completeSearchViewHolder.blurryBackground.setOverlayColor(context.getColor(R.color.moviesSectionTransparentColor))
                completeSearchViewHolder.blurryBackground.setSecondOverlayColor(setColorAlpha(context.getColor(R.color.moviesSectionColor), 113f))

                completeSearchViewHolder.searchQueryType.imageTintList = ColorStateList.valueOf(context.getColor(R.color.moviesSectionColor))
                completeSearchViewHolder.searchQueryType.setImageDrawable(context.getDrawable(R.drawable.movies_icon))
                completeSearchViewHolder.searchQueryType.contentDescription = context.getString(R.string.moviesSectionText)

            }
        }

        completeSearchViewHolder.productTitle.text = Html.fromHtml(completeSearchResultsItems[position].productName, Html.FROM_HTML_MODE_COMPACT)

        Glide.with(context)
            .load(completeSearchResultsItems[position].productIconLink)
            .into(completeSearchViewHolder.productIcon)

        completeSearchViewHolder.rootViewItem.setOnClickListener {

            when (completeSearchResultsItems[position].searchResultType) {
                GeneralEndpoints.QueryType.ApplicationsQuery -> {

                    openPlayStoreToInstallApplications(context = context,
                        aPackageName = (completeSearchResultsItems[position].productAttributes[ProductsContentKey.AttributesPackageNameKey].toString()),
                        applicationName = completeSearchResultsItems[position].productName,
                        applicationSummary = completeSearchResultsItems[position].productSummary)

                }
                GeneralEndpoints.QueryType.GamesQuery -> {

                    openPlayStoreToInstallApplications(context = context,
                        aPackageName = (completeSearchResultsItems[position].productAttributes[ProductsContentKey.AttributesPackageNameKey].toString()),
                        applicationName = completeSearchResultsItems[position].productName,
                        applicationSummary = completeSearchResultsItems[position].productSummary)

                }
                GeneralEndpoints.QueryType.MoviesQuery -> {

                    openPlayStoreToWatchMovie(context = context,
                        movieId = (completeSearchResultsItems[position].productAttributes["Movie Id"].toString()),
                        movieName = completeSearchResultsItems[position].productName,
                        movieSummary = completeSearchResultsItems[position].productSummary)

                }
            }

        }
        completeSearchViewHolder.searchQueryType.setOnLongClickListener { view ->

            Toast.makeText(context, view.contentDescription.toString(), Toast.LENGTH_LONG).show()

            true
        }


    }

}