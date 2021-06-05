/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/4/21, 1:31 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.Extensions

import android.view.View
import android.view.animation.OvershootInterpolator
import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.Storefront
import kotlin.math.absoluteValue

fun Storefront.filteringSetup() {

    if (storefrontLayoutBinding.sortingInclude.root.isShown) {
        storefrontLayoutBinding.sortingInclude.root.visibility = View.GONE
    }

    storefrontLayoutBinding.filteringInclude.root.visibility = if (storefrontLayoutBinding.filteringInclude.root.isShown) {
        View.GONE
    } else {
        View.VISIBLE
    }

    val viewTranslateY = (storefrontLayoutBinding.filteringInclude.filterCountryView.y - storefrontLayoutBinding.filteringInclude.filterCompatibilitiesView.y).absoluteValue

    storefrontLayoutBinding.filteringInclude.filterCountryView.setOnClickListener {

        storefrontLayoutBinding.filteringInclude.filterSelectedView.animate()
            .translationY(-viewTranslateY)
            .apply {
                interpolator = OvershootInterpolator()
            }.start()

        /*
        put this in adapter of countries options
        context.filterAllContent.filterAlContentByInput(context.storefrontAllUnfilteredContents, FilteringOptions.FilterByCountry, "Germany")
            .invokeOnCompletion {

            }*/

    }

    storefrontLayoutBinding.filteringInclude.filterCompatibilitiesView.setOnClickListener {

        storefrontLayoutBinding.filteringInclude.filterSelectedView.animate()
            .translationY((storefrontLayoutBinding.filteringInclude.filterCountryView.y - storefrontLayoutBinding.filteringInclude.filterCompatibilitiesView.y).absoluteValue)
            .apply {
                interpolator = OvershootInterpolator()
            }.start()

    }

}