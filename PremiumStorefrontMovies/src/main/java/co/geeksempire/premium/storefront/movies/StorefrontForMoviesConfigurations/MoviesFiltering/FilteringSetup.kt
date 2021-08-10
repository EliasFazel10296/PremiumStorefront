/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/10/21, 1:22 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.MoviesFiltering

import android.animation.Animator
import android.content.res.ColorStateList
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.OvershootInterpolator
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemeType
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.Utils.UI.Animations.AnimationListener
import co.geeksempire.premium.storefront.Utils.UI.Animations.CircularRevealAnimation
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.MoviesFiltering.FilterAdapter.FilterOptionsAdapter
import co.geeksempire.premium.storefront.movies.databinding.MoviesFilteringLayoutBinding
import co.geeksempire.premium.storefront.movies.databinding.MoviesSortingLayoutBinding
import com.google.firebase.firestore.DocumentSnapshot
import kotlin.math.absoluteValue

fun moviesFilteringSetup(context: AppCompatActivity,
                         moviesSortingLayoutBinding: MoviesSortingLayoutBinding,
                         moviesFilteringLayoutBinding: MoviesFilteringLayoutBinding,
                         rightActionView: ImageView,
                         middleActionView: ImageView,
                         leftActionView: ImageView,
                         filterOptionsAdapter: FilterOptionsAdapter,
                         storefrontAllUnfilteredContents: ArrayList<DocumentSnapshot>,
                         themeType: Boolean = ThemeType.ThemeLight) {

    when (themeType) {
        ThemeType.ThemeLight -> {

            moviesFilteringLayoutBinding.popupBlurryBackground.setOverlayColor(context.getColor(R.color.premiumLightTransparent))

            moviesFilteringLayoutBinding.filtersContainerView.background = context.getDrawable(R.drawable.filtering_container_layer_light)

            moviesFilteringLayoutBinding.filterSelectedView.background = context.getDrawable(R.drawable.filtering_selected_container_layer_light)

        }
        ThemeType.ThemeDark -> {

            moviesFilteringLayoutBinding.popupBlurryBackground.setOverlayColor(context.getColor(R.color.premiumDarkTransparent))

            moviesFilteringLayoutBinding.filtersContainerView.background = context.getDrawable(R.drawable.filtering_container_layer_dark)

            moviesFilteringLayoutBinding.filterSelectedView.background = context.getDrawable(R.drawable.filtering_selected_container_layer_dark)

        }
    }

    if (moviesSortingLayoutBinding.root.isShown) {

        moviesSortingLayoutBinding.root.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_out))
        moviesSortingLayoutBinding.root.visibility = View.GONE

        leftActionView.background = context.getDrawable(R.drawable.action_center_glowing)

        leftActionView.imageTintList = when (themeType) {
            ThemeType.ThemeLight -> {

                ColorStateList.valueOf(context.getColor(R.color.default_color_dark))

            }
            ThemeType.ThemeDark -> {

                ColorStateList.valueOf(context.getColor(R.color.default_color_bright))

            }
            else -> ColorStateList.valueOf(context.getColor(R.color.default_color_dark))
        }

        middleActionView.background = context.getDrawable(R.drawable.action_center_glowing)

        middleActionView.imageTintList = when (themeType) {
            ThemeType.ThemeLight -> {

                ColorStateList.valueOf(context.getColor(R.color.default_color_dark))

            }
            ThemeType.ThemeDark -> {

                ColorStateList.valueOf(context.getColor(R.color.default_color_bright))

            }
            else -> ColorStateList.valueOf(context.getColor(R.color.default_color_dark))
        }

    }

    if (moviesFilteringLayoutBinding.root.isShown) {

        rightActionView.background = null

        moviesFilteringLayoutBinding.root.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_out))
        moviesFilteringLayoutBinding.root.visibility = View.GONE

        rightActionView.imageTintList = when (themeType) {
            ThemeType.ThemeLight -> {

                ColorStateList.valueOf(context.getColor(R.color.default_color_dark))

            }
            ThemeType.ThemeDark -> {

                ColorStateList.valueOf(context.getColor(R.color.default_color_bright))

            }
            else -> ColorStateList.valueOf(context.getColor(R.color.default_color_dark))
        }

    } else {

        rightActionView.background = context.getDrawable(R.drawable.action_center_glowing)

        rightActionView.imageTintList = ColorStateList.valueOf(context.getColor(R.color.default_color_game_dark))

        val animationListener = object : AnimationListener {

            override fun animationFinished() {
                super.animationFinished()



            }

        }

        val circularRevealAnimation = CircularRevealAnimation (animationListener)
        circularRevealAnimation.startForView(context = context, rootView = moviesFilteringLayoutBinding.root,
            xPosition = ((rightActionView.x) + (rightActionView.width/2)).toInt(),
            yPosition = ((rightActionView.y) + (rightActionView.height/2)).toInt())

    }

    moviesFilteringLayoutBinding.root.post {

        if (filterOptionsAdapter.filterOptionsData.isEmpty()) {

            //

        }

        moviesFilteringLayoutBinding.filterCountryView.setOnClickListener {

            moviesFilteringLayoutBinding.filterSelectedView.animate()
                .translationYBy(-(moviesFilteringLayoutBinding.filterCompatibilitiesView.y - moviesFilteringLayoutBinding.filterCountryView.y))
                .apply {
                    interpolator = OvershootInterpolator()
                    duration = 531
                }.setListener(object : Animator.AnimatorListener {

                    override fun onAnimationStart(animation: Animator?) {}

                    override fun onAnimationEnd(animation: Animator?) {

//                        filterByCountriesDataProcess(context,
//                            storefrontAllUnfilteredContents,
//                            moviesFilteringLayoutBinding,
//                            filterOptionsAdapter)

                    }

                    override fun onAnimationCancel(animation: Animator?) {}

                    override fun onAnimationRepeat(animation: Animator?) {}

                }).start()

            moviesFilteringLayoutBinding.filterCountryView.setTextColor(context.getColor(R.color.white))
            moviesFilteringLayoutBinding.filterCompatibilitiesView.setTextColor(context.getColor(R.color.default_color_bright))

        }

        moviesFilteringLayoutBinding.filterCompatibilitiesView.setOnClickListener {

            moviesFilteringLayoutBinding.filterSelectedView.animate()
                .translationYBy((moviesFilteringLayoutBinding.filterCountryView.y - moviesFilteringLayoutBinding.filterCompatibilitiesView.y).absoluteValue)
                .apply {
                    interpolator = OvershootInterpolator()
                    duration = 531
                }.setListener(object : Animator.AnimatorListener {

                    override fun onAnimationStart(animation: Animator?) {}

                    override fun onAnimationEnd(animation: Animator?) {

//                        filterByCompatibilitiesDataProcess(context,
//                            storefrontAllUnfilteredContents,
//                            moviesFilteringLayoutBinding,
//                            filterOptionsAdapter)

                    }

                    override fun onAnimationCancel(animation: Animator?) {}

                    override fun onAnimationRepeat(animation: Animator?) {}

                }).start()

            moviesFilteringLayoutBinding.filterCountryView.setTextColor(context.getColor(R.color.default_color_bright))
            moviesFilteringLayoutBinding.filterCompatibilitiesView.setTextColor(context.getColor(R.color.white))

        }

    }

}