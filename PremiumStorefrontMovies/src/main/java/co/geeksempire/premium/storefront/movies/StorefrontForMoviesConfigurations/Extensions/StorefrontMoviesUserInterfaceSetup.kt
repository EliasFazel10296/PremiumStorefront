/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/20/21, 12:56 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.Extensions

import android.content.res.ColorStateList
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.os.Build
import android.view.View
import android.view.WindowInsetsController
import androidx.appcompat.widget.AppCompatButton
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemeType
import co.geeksempire.premium.storefront.movies.R
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.UserInterface.StorefrontMovies

fun StorefrontMovies.setupStorefrontMoviesUserInterface(themeType: Boolean) {

    featuredMoviesAdapter.apply {

        this.themeType = themeType

        if (this.featuredMoviesData.isNotEmpty()) {

            notifyItemRangeChanged(0, featuredMoviesAdapter.itemCount)

        }

    }

    newMoviesAdapter.apply {

        this.themeType = themeType

        if (storefrontMoviesContents.isNotEmpty()) {

            notifyItemRangeChanged(0, newMoviesAdapter.itemCount)

        }

    }

    allMoviesAdapter.apply {

        this.themeType = themeType

        if (storefrontMoviesContents.isNotEmpty()) {

            notifyItemRangeChanged(0, allMoviesAdapter.itemCount)

        }

    }

    when (themeType) {
        ThemeType.ThemeLight -> {

            prepareActionCenterUserInterface.let { centerUserInterface ->

                centerUserInterface.design(ThemeType.ThemeLight)

                centerUserInterface.setupIconsForStorefront(ThemeType.ThemeLight)

            }

            window.statusBarColor = getColor(R.color.premiumLight)
            window.navigationBarColor = getColor(R.color.premiumLight)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

                window.insetsController?.setSystemBarsAppearance(
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)

                window.insetsController?.setSystemBarsAppearance(
                    WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS,
                    WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS)

            } else {

                @Suppress("DEPRECATION")
                window.decorView.systemUiVisibility = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                } else {
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                }

            }

            storefrontMoviesLayoutBinding.rootView.setBackgroundColor(getColor(R.color.premiumLight))

            storefrontMoviesLayoutBinding.brandingBackground.imageTintList = ColorStateList.valueOf(getColor(R.color.dark))

            /* Start - Add Shadow To Content Background */
            val backgroundShadowRadius = floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f)

            backgroundShadowRadius[0] = (29).toFloat()//topLeftCorner
            backgroundShadowRadius[1] = (29).toFloat()//topLeftCorner

            backgroundShadowRadius[2] = (13).toFloat()//topRightCorner
            backgroundShadowRadius[3] = (13).toFloat()//topRightCorner

            backgroundShadowRadius[4] = (13).toFloat()//bottomRightCorner
            backgroundShadowRadius[5] = (13).toFloat()//bottomRightCorner

            backgroundShadowRadius[6] = (29).toFloat()//bottomLeftCorner
            backgroundShadowRadius[7] = (29).toFloat()//bottomLeftCorner

            val shapeShadow: ShapeDrawable = ShapeDrawable(RoundRectShape(backgroundShadowRadius, null, null))
            shapeShadow.paint.apply {
                color = getColor(R.color.dark)

                setShadowLayer(31f, 0f, 0f, getColor(R.color.dark_transparent_high))
            }

            val shadowLayer = getDrawable(R.drawable.storefront_content_background_light_movie) as LayerDrawable

            shadowLayer.setDrawableByLayerId(R.id.temporaryBackground, shapeShadow)

            storefrontMoviesLayoutBinding.allContentBackground.setLayerType(AppCompatButton.LAYER_TYPE_SOFTWARE, shapeShadow.paint)
            storefrontMoviesLayoutBinding.allContentBackground.background = (shadowLayer)
            /* End - Add Shadow To Content Background */

            storefrontMoviesLayoutBinding.dividerTopImageView.setImageDrawable(getDrawable(R.drawable.diamond_solid_icon_light_movie))
            storefrontMoviesLayoutBinding.dividerTopImageView.background = getDrawable(co.geeksempire.premium.storefront.movies.R.drawable.featured_content_divider_light_movie)

            storefrontMoviesLayoutBinding.dividerNewContentImageView.setImageDrawable(getDrawable(R.drawable.diamond_solid_icon_light_movie))
            storefrontMoviesLayoutBinding.dividerNewContentImageView.background = getDrawable(co.geeksempire.premium.storefront.movies.R.drawable.new_content_divider_light_movie)

            storefrontMoviesLayoutBinding.profileView.background = getDrawable(R.drawable.profile_icon_light_movie)
            storefrontMoviesLayoutBinding.preferencesView.setImageDrawable(getDrawable(R.drawable.preferences_icon_light_movie))
            storefrontMoviesLayoutBinding.favoritesView.background = getDrawable(R.drawable.squircle_background_light_movie)

            storefrontMoviesLayoutBinding.genreIndicatorTextView.setTextColor(getColor(R.color.dark))

            storefrontMoviesLayoutBinding.newMovieBlurryBackground.setSecondOverlayColor(getColor(R.color.premiumLightTransparent))

        }
        ThemeType.ThemeDark -> {

            prepareActionCenterUserInterface.let { centerUserInterface ->

                centerUserInterface.design(ThemeType.ThemeDark)

                centerUserInterface.setupIconsForStorefront(ThemeType.ThemeDark)

            }

            window.statusBarColor = getColor(R.color.premiumDark)
            window.navigationBarColor = getColor(R.color.premiumDark)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

                window.insetsController?.setSystemBarsAppearance(0, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
                window.insetsController?.setSystemBarsAppearance(0, WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS)

            } else {

                @Suppress("DEPRECATION")
                window.decorView.systemUiVisibility = 0

            }

            storefrontMoviesLayoutBinding.rootView.setBackgroundColor(getColor(R.color.premiumDark))

            storefrontMoviesLayoutBinding.brandingBackground.imageTintList = ColorStateList.valueOf(getColor(R.color.light))

            /* Start - Add Shadow To Content Background */
            val backgroundShadowRadius = floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f)

            backgroundShadowRadius[0] = (29).toFloat()//topLeftCorner
            backgroundShadowRadius[1] = (29).toFloat()//topLeftCorner

            backgroundShadowRadius[2] = (13).toFloat()//topRightCorner
            backgroundShadowRadius[3] = (13).toFloat()//topRightCorner

            backgroundShadowRadius[4] = (13).toFloat()//bottomRightCorner
            backgroundShadowRadius[5] = (13).toFloat()//bottomRightCorner

            backgroundShadowRadius[6] = (29).toFloat()//bottomLeftCorner
            backgroundShadowRadius[7] = (29).toFloat()//bottomLeftCorner

            val shapeShadow: ShapeDrawable = ShapeDrawable(RoundRectShape(backgroundShadowRadius, null, null))
            shapeShadow.paint.apply {
                color = getColor(R.color.black)

                setShadowLayer(31f, 0f, 0f, getColor(R.color.black_transparent))
            }

            val shadowLayer = getDrawable(R.drawable.storefront_content_background_dark_movie) as LayerDrawable

            shadowLayer.setDrawableByLayerId(R.id.temporaryBackground, shapeShadow)

            storefrontMoviesLayoutBinding.allContentBackground.setLayerType(AppCompatButton.LAYER_TYPE_SOFTWARE, shapeShadow.paint)
            storefrontMoviesLayoutBinding.allContentBackground.background = (shadowLayer)
            /* End - Add Shadow To Content Background */

            storefrontMoviesLayoutBinding.dividerTopImageView.setImageDrawable(getDrawable(R.drawable.diamond_solid_icon_dark_movie))
            storefrontMoviesLayoutBinding.dividerTopImageView.background = getDrawable(co.geeksempire.premium.storefront.movies.R.drawable.featured_content_divider_dark_movie)

            storefrontMoviesLayoutBinding.dividerNewContentImageView.setImageDrawable(getDrawable(R.drawable.diamond_solid_icon_dark_movie))
            storefrontMoviesLayoutBinding.dividerNewContentImageView.background = getDrawable(co.geeksempire.premium.storefront.movies.R.drawable.new_content_divider_dark_movie)

            storefrontMoviesLayoutBinding.profileView.background = getDrawable(R.drawable.profile_icon_dark_movie)
            storefrontMoviesLayoutBinding.preferencesView.setImageDrawable(getDrawable(R.drawable.preferences_icon_dark_movie))
            storefrontMoviesLayoutBinding.favoritesView.background = getDrawable(R.drawable.squircle_background_dark_movie)

            storefrontMoviesLayoutBinding.genreIndicatorTextView.setTextColor(getColor(R.color.light))

            storefrontMoviesLayoutBinding.newMovieBlurryBackground.setSecondOverlayColor(getColor(R.color.premiumDarkTransparent))

        }
        else -> {}
    }

}