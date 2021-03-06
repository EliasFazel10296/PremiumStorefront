/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 8/9/21, 2:13 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.Extensions

import android.animation.Animator
import android.animation.ValueAnimator
import android.app.ActivityOptions
import android.content.Intent
import android.content.res.ColorStateList
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemeType
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontForApplicationsConfigurations.UserInterface.StorefrontApplications
import co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontForGamesConfigurations.UserInterface.StorefrontGames
import co.geeksempire.premium.storefront.databinding.SectionsSwitcherLayoutBinding
import net.geeksempire.balloon.optionsmenu.library.Utils.dpToInteger

fun storefrontSectionSwitcher(context: AppCompatActivity, rootView: ViewGroup, sectionsSwitcherLayoutBinding: SectionsSwitcherLayoutBinding, themeType: Boolean) {

    when (context) {
        is StorefrontApplications -> {

            applicationsSectionSwitcherDesign(context, sectionsSwitcherLayoutBinding, themeType)

            sectionsSwitcherLayoutBinding.gamesSectionView.setOnClickListener {

                val valueAnimatorGames = ValueAnimator.ofInt(dpToInteger(context, 57), sectionsSwitcherLayoutBinding.applicationsSectionView.width)
                valueAnimatorGames.duration = 333
                valueAnimatorGames.startDelay = 333
                valueAnimatorGames.addUpdateListener { animator ->

                    val animatorValue = animator.animatedValue as Int

                    sectionsSwitcherLayoutBinding.gamesSectionView.layoutParams.width = animatorValue
                    sectionsSwitcherLayoutBinding.gamesSectionView.requestLayout()

                }
                valueAnimatorGames.addListener(object : Animator.AnimatorListener {

                    override fun onAnimationStart(animation: Animator) {

                        gamesSectionSwitcherDesign(context, sectionsSwitcherLayoutBinding, themeType)

                    }

                    override fun onAnimationEnd(animation: Animator) {

                        val activityOptions = ActivityOptions.makeCustomAnimation(context, R.anim.fade_in, 0)

                        val switchIntent = Intent(context, StorefrontGames::class.java).apply {

                        }

                        context.startActivity(switchIntent, activityOptions.toBundle())

                    }

                    override fun onAnimationCancel(animation: Animator) {

                    }

                    override fun onAnimationRepeat(animation: Animator) {

                    }

                })

                val valueAnimatorApplications = ValueAnimator.ofInt(sectionsSwitcherLayoutBinding.applicationsSectionView.width, dpToInteger(context, 57))
                valueAnimatorApplications.duration = 333
                valueAnimatorApplications.startDelay = 333
                valueAnimatorApplications.addUpdateListener { animator ->

                    val animatorValue = animator.animatedValue as Int

                    sectionsSwitcherLayoutBinding.applicationsSectionView.layoutParams.width = animatorValue
                    sectionsSwitcherLayoutBinding.applicationsSectionView.requestLayout()

                }
                valueAnimatorApplications.addListener(object : Animator.AnimatorListener {

                    override fun onAnimationStart(animation: Animator) {

                    }

                    override fun onAnimationEnd(animation: Animator) {

                        sectionsSwitcherLayoutBinding.applicationsSectionView.text = ""

                        valueAnimatorGames.start()

                    }

                    override fun onAnimationCancel(animation: Animator) {

                    }

                    override fun onAnimationRepeat(animation: Animator) {

                    }

                })
                valueAnimatorApplications.start()

            }

            sectionsSwitcherLayoutBinding.moviesSectionView.setOnClickListener {

                startMoviesSwitching(context, rootView, sectionsSwitcherLayoutBinding, themeType)

            }

        }
        is StorefrontGames -> {

            gamesSectionSwitcherDesign(context, sectionsSwitcherLayoutBinding, themeType)

            sectionsSwitcherLayoutBinding.applicationsSectionView.setOnClickListener {

                val valueAnimatorGames = ValueAnimator.ofInt(dpToInteger(context, 57), sectionsSwitcherLayoutBinding.gamesSectionView.width)
                valueAnimatorGames.duration = 333
                valueAnimatorGames.startDelay = 333
                valueAnimatorGames.addUpdateListener { animator ->

                    val animatorValue = animator.animatedValue as Int

                    sectionsSwitcherLayoutBinding.applicationsSectionView.layoutParams.width = animatorValue
                    sectionsSwitcherLayoutBinding.applicationsSectionView.requestLayout()

                }
                valueAnimatorGames.addListener(object : Animator.AnimatorListener {

                    override fun onAnimationStart(animation: Animator) {

                        applicationsSectionSwitcherDesign(context, sectionsSwitcherLayoutBinding, themeType)

                    }

                    override fun onAnimationEnd(animation: Animator) {

                        val activityOptions = ActivityOptions.makeCustomAnimation(context, R.anim.fade_in, 0)

                        val switchIntent = Intent(context, StorefrontApplications::class.java).apply {

                        }

                        context.startActivity(switchIntent, activityOptions.toBundle())

                    }

                    override fun onAnimationCancel(animation: Animator) {

                    }

                    override fun onAnimationRepeat(animation: Animator) {

                    }

                })

                val valueAnimatorApplications = ValueAnimator.ofInt(sectionsSwitcherLayoutBinding.gamesSectionView.width, dpToInteger(context, 57))
                valueAnimatorApplications.duration = 333
                valueAnimatorApplications.startDelay = 333
                valueAnimatorApplications.addUpdateListener { animator ->

                    val animatorValue = animator.animatedValue as Int

                    sectionsSwitcherLayoutBinding.gamesSectionView.layoutParams.width = animatorValue
                    sectionsSwitcherLayoutBinding.gamesSectionView.requestLayout()

                }
                valueAnimatorApplications.addListener(object : Animator.AnimatorListener {

                    override fun onAnimationStart(animation: Animator) {

                    }

                    override fun onAnimationEnd(animation: Animator) {

                        sectionsSwitcherLayoutBinding.gamesSectionView.text = ""

                        valueAnimatorGames.start()

                    }

                    override fun onAnimationCancel(animation: Animator) {

                    }

                    override fun onAnimationRepeat(animation: Animator) {

                    }

                })
                valueAnimatorApplications.start()

            }

            sectionsSwitcherLayoutBinding.moviesSectionView.setOnClickListener {

                startMoviesSwitching(context, rootView, sectionsSwitcherLayoutBinding, themeType)

            }

        }
        else -> {

        }
    }

}

fun applicationsSectionSwitcherDesign(context: AppCompatActivity, sectionsSwitcherLayoutBinding: SectionsSwitcherLayoutBinding, themeType: Boolean) {

    sectionsSwitcherLayoutBinding.applicationsSectionView.apply {

        (layoutParams as ConstraintLayout.LayoutParams).width = 0
        requestLayout()

        text = contentDescription
        setTextColor(context.getColor(R.color.white))
        iconTint = ColorStateList.valueOf(context.getColor(R.color.white))
        iconSize = dpToInteger(context, 29)
        iconPadding = dpToInteger(context, 7)
        rippleColor = ColorStateList.valueOf(context.getColor(R.color.transparent))
        backgroundTintList = ColorStateList.valueOf(context.getColor(R.color.applicationsSectionColor))

        (layoutParams as ConstraintLayout.LayoutParams).matchConstraintPercentWidth = 0.51f
        requestLayout()


    }

    sectionsSwitcherLayoutBinding.gamesSectionView.apply {

        text = ""
        iconTint = ColorStateList.valueOf(context.getColor(R.color.gamesSectionColor))
        iconSize = dpToInteger(context, 25)
        iconPadding = 0
        rippleColor = ColorStateList.valueOf(context.getColor(R.color.gamesSectionColor))
        backgroundTintList = when (themeType) {
            ThemeType.ThemeLight -> {

                ColorStateList.valueOf(context.getColor(R.color.premiumLight))

            }
            ThemeType.ThemeDark -> {

                ColorStateList.valueOf(context.getColor(R.color.premiumDark))

            }
            else -> ColorStateList.valueOf(context.getColor(R.color.premiumLight))
        }

        layoutParams.width = dpToInteger(context, 57)

        requestLayout()

        clearFocus()

    }

    sectionsSwitcherLayoutBinding.moviesSectionView.apply {

        text = ""
        iconTint = ColorStateList.valueOf(context.getColor(R.color.moviesSectionColor))
        iconSize = dpToInteger(context, 25)
        iconPadding = 0
        rippleColor = ColorStateList.valueOf(context.getColor(R.color.moviesSectionColor))
        backgroundTintList = when (themeType) {
            ThemeType.ThemeLight -> {

                ColorStateList.valueOf(context.getColor(R.color.premiumLight))

            }
            ThemeType.ThemeDark -> {

                ColorStateList.valueOf(context.getColor(R.color.premiumDark))

            }
            else -> ColorStateList.valueOf(context.getColor(R.color.premiumLight))
        }

        layoutParams.width = dpToInteger(context, 57)

        requestLayout()

        clearFocus()

    }

}

fun gamesSectionSwitcherDesign(context: AppCompatActivity, sectionsSwitcherLayoutBinding: SectionsSwitcherLayoutBinding, themeType: Boolean) {

    sectionsSwitcherLayoutBinding.applicationsSectionView.apply {

        text = ""
        iconTint = ColorStateList.valueOf(context.getColor(R.color.applicationsSectionColor))
        iconSize = dpToInteger(context, 25)
        iconPadding = 0
        rippleColor = ColorStateList.valueOf(context.getColor(R.color.applicationsSectionColor))
        backgroundTintList = when (themeType) {
            ThemeType.ThemeLight -> {

                ColorStateList.valueOf(context.getColor(R.color.premiumLight))

            }
            ThemeType.ThemeDark -> {

                ColorStateList.valueOf(context.getColor(R.color.premiumDark))

            }
            else -> ColorStateList.valueOf(context.getColor(R.color.premiumLight))
        }

        layoutParams.width = dpToInteger(context, 57)

        requestLayout()

        clearFocus()

    }

    sectionsSwitcherLayoutBinding.gamesSectionView.apply {

        (layoutParams as ConstraintLayout.LayoutParams).width = 0
        requestLayout()

        text = contentDescription
        setTextColor(context.getColor(R.color.white))
        iconTint = ColorStateList.valueOf(context.getColor(R.color.white))
        iconSize = dpToInteger(context, 29)
        iconPadding = dpToInteger(context, 7)
        rippleColor = ColorStateList.valueOf(context.getColor(R.color.transparent))
        backgroundTintList = ColorStateList.valueOf(context.getColor(R.color.gamesSectionColor))

        (layoutParams as ConstraintLayout.LayoutParams).matchConstraintPercentWidth = 0.51f

        requestLayout()

        clearFocus()

    }

    sectionsSwitcherLayoutBinding.moviesSectionView.apply {

        text = ""
        iconTint = ColorStateList.valueOf(context.getColor(R.color.moviesSectionColor))
        iconSize = dpToInteger(context, 25)
        iconPadding = 0
        rippleColor = ColorStateList.valueOf(context.getColor(R.color.moviesSectionColor))
        backgroundTintList = when (themeType) {
            ThemeType.ThemeLight -> {

                ColorStateList.valueOf(context.getColor(R.color.premiumLight))

            }
            ThemeType.ThemeDark -> {

                ColorStateList.valueOf(context.getColor(R.color.premiumDark))

            }
            else -> ColorStateList.valueOf(context.getColor(R.color.premiumLight))
        }

        layoutParams.width = dpToInteger(context, 57)

        requestLayout()

        clearFocus()

    }

}