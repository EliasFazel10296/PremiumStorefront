/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 5/1/21 9:47 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.AllContent.ViewHolder

import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.storefront_all_content_item.view.*
import net.geeksempire.blurry.effect.view.RealtimeBlurView

class AllContentViewHolder (rootItemView: View) : RecyclerView.ViewHolder(rootItemView) {
    val rootView: ConstraintLayout = rootItemView.rootViewItem

    val productIconImageView: AppCompatImageView = rootItemView.productIconImageView
    val productNameTextView: TextView = rootItemView.productNameTextView
    val productDescriptionTextView: TextView = rootItemView.productDescriptionTextView
    val productCurrentRateView: TextView = rootItemView.productCurrentRateView

    val productRateBlur: RealtimeBlurView = rootItemView.productRateBlur
}