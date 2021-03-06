/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 12/22/21, 8:17 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.Actions.Operation

import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.view.isInvisible
import co.geeksempire.premium.storefront.CategoriesDetailsConfigurations.UserInterface.CategoryDetails
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemeType
import co.geeksempire.premium.storefront.DevelopersConfigurations.UserInterface.DeveloperIntroductionPage
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.StorefrontConfigurations.ContentFiltering.filteringSetup
import co.geeksempire.premium.storefront.StorefrontConfigurations.ContentSearching.SearchingSetup
import co.geeksempire.premium.storefront.StorefrontConfigurations.ContentSorting.sortingSetup
import co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontForApplicationsConfigurations.UserInterface.StorefrontApplications
import co.geeksempire.premium.storefront.StorefrontConfigurations.StorefrontForGamesConfigurations.UserInterface.StorefrontGames
import co.geeksempire.premium.storefront.Utils.Data.openPlayStoreToInstallApplications
import co.geeksempire.premium.storefront.Utils.Data.shareApplication

class ActionCenterOperations {

    /*
     * Applications
     */
    fun setupForApplicationsStorefront(context: StorefrontApplications, themeType: Boolean = ThemeType.ThemeLight) {

        val searchingSetup = SearchingSetup(context)

        context.storefrontLayoutBinding.leftActionView.setOnClickListener {

            sortingSetup(context = context, filterAllContent = context.filterAllContent,
                sortingInclude = context.storefrontLayoutBinding.sortingInclude, filteringInclude = context.storefrontLayoutBinding.filteringInclude,
                rightActionView = context.storefrontLayoutBinding.rightActionView, middleActionView =  context.storefrontLayoutBinding.middleActionView, leftActionView = context.storefrontLayoutBinding.leftActionView, context.allContentAdapter,
                themeType = themeType)

        }

        context.storefrontLayoutBinding.middleActionView.setOnClickListener {

            if (context.storefrontLayoutBinding.searchRevertView.isShown
                && context.storefrontLayoutBinding.searchAdvancedView.isShown) {

                context.storefrontLayoutBinding.searchRevertView.apply {
                    visibility = View.INVISIBLE
                    startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_out))
                }

                context.storefrontLayoutBinding.searchAdvancedView.apply {
                    visibility = View.INVISIBLE
                    startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_out))
                }

            }

            searchingSetup.searchingSetup(context.filterAllContent,
                context.storefrontLayoutBinding.textInputSearchView,
                context.storefrontLayoutBinding.searchView,
                context.storefrontLayoutBinding.rightActionView,
                context.storefrontLayoutBinding.middleActionView,
                context.storefrontLayoutBinding.leftActionView,
                context.storefrontAllUnfilteredContents,
                themeType)

        }

        context.storefrontLayoutBinding.rightActionView.setOnClickListener {

            filteringSetup(context = context,
                sortingInclude = context.storefrontLayoutBinding.sortingInclude, filteringInclude = context.storefrontLayoutBinding.filteringInclude,
                rightActionView = context.storefrontLayoutBinding.rightActionView, middleActionView =  context.storefrontLayoutBinding.middleActionView, leftActionView = context.storefrontLayoutBinding.leftActionView, filterOptionsAdapter = context.filterOptionsAdapter,
                storefrontAllUnfilteredContents = context.storefrontAllUnfilteredContents,
                themeType = themeType)

        }

    }

    fun setupForApplicationsDetails(context: StorefrontApplications, applicationPackageName: String, applicationName: String, applicationSummary: String) {

        /* Share */
        context.storefrontLayoutBinding.leftActionView.setOnClickListener {

            shareApplication(context = context,
                aPackageName = applicationPackageName,
                applicationName = applicationName,
                applicationSummary = applicationSummary)

        }

        /* Install */
        context.storefrontLayoutBinding.middleActionView.setOnClickListener {

            openPlayStoreToInstallApplications(context = context,
                aPackageName = applicationPackageName,
                applicationName = applicationName,
                applicationSummary = applicationSummary)

        }

        /* Rate */
        context.storefrontLayoutBinding.rightActionView.setOnClickListener {

            openPlayStoreToInstallApplications(context = context,
                aPackageName = applicationPackageName,
                applicationName = applicationName,
                applicationSummary = applicationSummary)

        }

    }

    /*
     * Games
     */
    fun setupForGamesStorefront(context: StorefrontGames, themeType: Boolean = ThemeType.ThemeLight) {

        val searchingSetup = SearchingSetup(context)

        context.storefrontLayoutBinding.leftActionView.setOnClickListener {

            sortingSetup(context = context, filterAllContent = context.filterAllContent,
                context.storefrontLayoutBinding.sortingInclude, filteringInclude = context.storefrontLayoutBinding.filteringInclude,
                rightActionView = context.storefrontLayoutBinding.rightActionView, middleActionView =  context.storefrontLayoutBinding.middleActionView, leftActionView = context.storefrontLayoutBinding.leftActionView, context.allContentAdapter,
                themeType = themeType)

        }

        context.storefrontLayoutBinding.middleActionView.setOnClickListener {

            if (context.storefrontLayoutBinding.searchRevertView.isShown
                && context.storefrontLayoutBinding.searchAdvancedView.isShown) {

                context.storefrontLayoutBinding.searchRevertView.apply {
                    visibility = View.INVISIBLE
                    startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_out))
                }

                context.storefrontLayoutBinding.searchAdvancedView.apply {
                    visibility = View.INVISIBLE
                    startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_out))
                }

            }

            searchingSetup.searchingSetup(context.filterAllContent,
                context.storefrontLayoutBinding.textInputSearchView,
                context.storefrontLayoutBinding.searchView,
                context.storefrontLayoutBinding.rightActionView,
                context.storefrontLayoutBinding.middleActionView,
                context.storefrontLayoutBinding.leftActionView,
                context.storefrontAllUnfilteredContents,
                themeType)

        }

        context.storefrontLayoutBinding.rightActionView.setOnClickListener {

            filteringSetup(context = context,
                sortingInclude = context.storefrontLayoutBinding.sortingInclude, filteringInclude = context.storefrontLayoutBinding.filteringInclude,
                rightActionView = context.storefrontLayoutBinding.rightActionView, middleActionView =  context.storefrontLayoutBinding.middleActionView, leftActionView = context.storefrontLayoutBinding.leftActionView, filterOptionsAdapter = context.filterOptionsAdapter,
                storefrontAllUnfilteredContents = context.storefrontAllUnfilteredContents,
                themeType = themeType)

        }

    }

    fun setupForGamesDetails(context: StorefrontGames, applicationPackageName: String, applicationName: String, applicationSummary: String) {

        /* Share */
        context.storefrontLayoutBinding.leftActionView.setOnClickListener {

            shareApplication(context = context,
                aPackageName = applicationPackageName,
                applicationName = applicationName,
                applicationSummary = applicationSummary)

        }

        /* Install */
        context.storefrontLayoutBinding.middleActionView.setOnClickListener {

            openPlayStoreToInstallApplications(context = context,
                aPackageName = applicationPackageName,
                applicationName = applicationName,
                applicationSummary = applicationSummary)

        }

        /* Rate */
        context.storefrontLayoutBinding.rightActionView.setOnClickListener {

            openPlayStoreToInstallApplications(context = context,
                aPackageName = applicationPackageName,
                applicationName = applicationName,
                applicationSummary = applicationSummary)

        }

    }

    /*
     * Categories
     */
    fun setupCategoryVisibility(context: CategoryDetails) {

        if (context.categoryDetailsLayoutBinding.actionCenterView.isShown) {

            context.categoryDetailsLayoutBinding.actionCenterView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_out))
            context.categoryDetailsLayoutBinding.actionCenterView.visibility = View.INVISIBLE

            context.categoryDetailsLayoutBinding.actionCenterBlurView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_out))
            context.categoryDetailsLayoutBinding.actionCenterBlurView.visibility = View.INVISIBLE

            context.categoryDetailsLayoutBinding.leftActionView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_out))
            context.categoryDetailsLayoutBinding.leftActionView.visibility = View.INVISIBLE

            context.categoryDetailsLayoutBinding.middleActionView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_out))
            context.categoryDetailsLayoutBinding.middleActionView.visibility = View.INVISIBLE

            context.categoryDetailsLayoutBinding.rightActionView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_out))
            context.categoryDetailsLayoutBinding.rightActionView.visibility = View.INVISIBLE

        } else if (context.categoryDetailsLayoutBinding.actionCenterView.isInvisible) {

            context.categoryDetailsLayoutBinding.actionCenterView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in))
            context.categoryDetailsLayoutBinding.actionCenterView.visibility = View.VISIBLE

            context.categoryDetailsLayoutBinding.actionCenterBlurView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in))
            context.categoryDetailsLayoutBinding.actionCenterBlurView.visibility = View.VISIBLE

            context.categoryDetailsLayoutBinding.leftActionView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in))
            context.categoryDetailsLayoutBinding.leftActionView.visibility = View.VISIBLE

            context.categoryDetailsLayoutBinding.middleActionView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in))
            context.categoryDetailsLayoutBinding.middleActionView.visibility = View.VISIBLE

            context.categoryDetailsLayoutBinding.rightActionView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in))
            context.categoryDetailsLayoutBinding.rightActionView.visibility = View.VISIBLE

        }

    }

    fun setupForCategoryDetails(context: CategoryDetails, applicationPackageName: String, applicationName: String, applicationSummary: String) {

        /* Share */
        context.categoryDetailsLayoutBinding.leftActionView.setOnClickListener {

            shareApplication(context = context,
                aPackageName = applicationPackageName,
                applicationName = applicationName,
                applicationSummary = applicationSummary)

        }

        /* Install */
        context.categoryDetailsLayoutBinding.middleActionView.setOnClickListener {

            openPlayStoreToInstallApplications(context = context,
                aPackageName = applicationPackageName,
                applicationName = applicationName,
                applicationSummary = applicationSummary)

        }

        /* Rate */
        context.categoryDetailsLayoutBinding.rightActionView.setOnClickListener {

            openPlayStoreToInstallApplications(context = context,
                aPackageName = applicationPackageName,
                applicationName = applicationName,
                applicationSummary = applicationSummary)

        }

    }

    /*
     * Developer
     */
    fun setupDeveloperVisibility(context: DeveloperIntroductionPage) {

        if (context.developerIntroductionLayoutBinding.actionCenterView.isShown) {

            context.developerIntroductionLayoutBinding.actionCenterView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_out))
            context.developerIntroductionLayoutBinding.actionCenterView.visibility = View.INVISIBLE

            context.developerIntroductionLayoutBinding.actionCenterBlurView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_out))
            context.developerIntroductionLayoutBinding.actionCenterBlurView.visibility = View.INVISIBLE

            context.developerIntroductionLayoutBinding.leftActionView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_out))
            context.developerIntroductionLayoutBinding.leftActionView.visibility = View.INVISIBLE

            context.developerIntroductionLayoutBinding.middleActionView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_out))
            context.developerIntroductionLayoutBinding.middleActionView.visibility = View.INVISIBLE

            context.developerIntroductionLayoutBinding.rightActionView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_out))
            context.developerIntroductionLayoutBinding.rightActionView.visibility = View.INVISIBLE

        } else if (context.developerIntroductionLayoutBinding.actionCenterView.isInvisible) {

            context.developerIntroductionLayoutBinding.actionCenterView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in))
            context.developerIntroductionLayoutBinding.actionCenterView.visibility = View.VISIBLE

            context.developerIntroductionLayoutBinding.actionCenterBlurView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in))
            context.developerIntroductionLayoutBinding.actionCenterBlurView.visibility = View.VISIBLE

            context.developerIntroductionLayoutBinding.leftActionView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in))
            context.developerIntroductionLayoutBinding.leftActionView.visibility = View.VISIBLE

            context.developerIntroductionLayoutBinding.middleActionView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in))
            context.developerIntroductionLayoutBinding.middleActionView.visibility = View.VISIBLE

            context.developerIntroductionLayoutBinding.rightActionView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in))
            context.developerIntroductionLayoutBinding.rightActionView.visibility = View.VISIBLE

        }

    }

    fun setupForDeveloperDetails(context: DeveloperIntroductionPage, applicationPackageName: String, applicationName: String, applicationSummary: String) {

        /* Share */
        context.developerIntroductionLayoutBinding.leftActionView.setOnClickListener {

            shareApplication(context = context,
                aPackageName = applicationPackageName,
                applicationName = applicationName,
                applicationSummary = applicationSummary)

        }

        /* Install */
        context.developerIntroductionLayoutBinding.middleActionView.setOnClickListener {

            openPlayStoreToInstallApplications(context = context,
                aPackageName = applicationPackageName,
                applicationName = applicationName,
                applicationSummary = applicationSummary)

        }

        /* Rate */
        context.developerIntroductionLayoutBinding.rightActionView.setOnClickListener {

            openPlayStoreToInstallApplications(context = context,
                aPackageName = applicationPackageName,
                applicationName = applicationName,
                applicationSummary = applicationSummary)

        }

    }

}