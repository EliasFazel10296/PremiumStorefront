/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 12/18/21, 5:21 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.FavoriteProductsConfigurations.Extensions

import android.app.ActivityOptions
import android.content.Intent
import co.geeksempire.premium.storefront.AccountManager.UserInterface.AccountInformation
import co.geeksempire.premium.storefront.FavoriteProductsConfigurations.IO.FavoriteProductQueryInterface
import co.geeksempire.premium.storefront.Preferences.Utils.EntryPreferences
import co.geeksempire.premium.storefront.ProductsDetailsConfigurations.UserInterface.ProductDetailsFragment
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.Utils.Notifications.SnackbarActionHandlerInterface
import co.geeksempire.premium.storefront.Utils.Notifications.SnackbarBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

fun ProductDetailsFragment.startFavoriteProcess(productId: String, productName: String, productDescription: String, productIcon: String,  favoriteType: String = EntryPreferences.EntryStorefrontApplications) {

    if (Firebase.auth.currentUser != null) {

        favoritedProcess.isProductFavorited(Firebase.auth.currentUser!!.uid, Firebase.auth.currentUser!!.email!!, productId,
            object : FavoriteProductQueryInterface {

                override fun favoriteProduct(isProductFavorited: Boolean) {
                    super.favoriteProduct(isProductFavorited)

                    if (isProductFavorited) {

                        if (networkCheckpoint.networkConnection()) {

                            favoritedProcess.remove(userUniqueIdentifier = Firebase.auth.currentUser!!.uid, userEmailAddress = Firebase.auth.currentUser!!.uid , productId)

                            productDetailsLayoutBinding.favoriteView.setImageDrawable(requireContext().getDrawable(R.drawable.favorite_icon))

                        } else {

                            SnackbarBuilder(requireContext()).show (
                                rootView = productDetailsLayoutBinding.rootViewFragment,
                                messageText= getString(R.string.noNetworkConnection),
                                messageDuration = Snackbar.LENGTH_INDEFINITE,
                                actionButtonText = R.string.retryText,
                                snackbarActionHandlerInterface = object :
                                    SnackbarActionHandlerInterface {

                                    override fun onActionButtonClicked(snackbar: Snackbar) {
                                        super.onActionButtonClicked(snackbar)

                                        if (networkCheckpoint.networkConnection()) {

                                            favoritedProcess.remove(userUniqueIdentifier = Firebase.auth.currentUser!!.uid, userEmailAddress = Firebase.auth.currentUser!!.uid, productId)

                                            productDetailsLayoutBinding.favoriteView.setImageDrawable(requireContext().getDrawable(R.drawable.favorite_icon))

                                            snackbar.dismiss()

                                        } else {



                                        }

                                    }

                                }
                            )

                        }

                    } else {

                        if (networkCheckpoint.networkConnection()) {

                            favoritedProcess.add(userUniqueIdentifier = Firebase.auth.currentUser!!.uid,
                                userEmailAddress = Firebase.auth.currentUser!!.email!!,
                                productIdToFavorite = productId,
                                productName = productName,
                                productDescription = productDescription,
                                productIcon = productIcon,
                                productType = favoriteType)

                            productDetailsLayoutBinding.favoriteView.setImageDrawable(requireContext().getDrawable(R.drawable.favorited_icon))

                        } else {

                            SnackbarBuilder(requireContext()).show (
                                rootView = productDetailsLayoutBinding.rootViewFragment,
                                messageText= getString(R.string.noNetworkConnection),
                                messageDuration = Snackbar.LENGTH_INDEFINITE,
                                actionButtonText = R.string.retryText,
                                snackbarActionHandlerInterface = object :
                                    SnackbarActionHandlerInterface {

                                    override fun onActionButtonClicked(snackbar: Snackbar) {
                                        super.onActionButtonClicked(snackbar)

                                        if (networkCheckpoint.networkConnection()) {

                                            favoritedProcess.add(userUniqueIdentifier = Firebase.auth.currentUser!!.uid,
                                                userEmailAddress = Firebase.auth.currentUser!!.email!!,
                                                productIdToFavorite = productId,
                                                productName = productName,
                                                productDescription = productDescription,
                                                productIcon = productIcon,
                                                productType = favoriteType)

                                            productDetailsLayoutBinding.favoriteView.setImageDrawable(requireContext().getDrawable(R.drawable.favorited_icon))

                                            snackbar.dismiss()

                                        } else {



                                        }

                                    }

                                }
                            )

                        }

                    }

                }

            })


    } else {

        startActivity(Intent(requireContext(), AccountInformation::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK),
            ActivityOptions.makeCustomAnimation(requireContext(), R.anim.slide_in_right, 0).toBundle())

    }

}