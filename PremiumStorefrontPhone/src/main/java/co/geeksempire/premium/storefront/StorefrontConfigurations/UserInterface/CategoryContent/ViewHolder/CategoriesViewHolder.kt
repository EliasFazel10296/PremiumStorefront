/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/11/21, 8:23 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.CategoryContent.ViewHolder

import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.storefront_category_item.view.*

class CategoriesViewHolder (rootItemView: View) : RecyclerView.ViewHolder(rootItemView) {
    val rootView: ConstraintLayout = rootItemView.rootViewItem

    val categoryIconImageView: ImageView = rootItemView.categoryIconImageView
}