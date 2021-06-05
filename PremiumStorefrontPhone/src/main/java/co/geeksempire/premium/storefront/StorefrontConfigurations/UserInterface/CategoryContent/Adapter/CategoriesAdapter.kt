/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/5/21, 4:57 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.CategoryContent.Adapter

import android.content.res.ColorStateList
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.StorefrontConfigurations.ContentFiltering.Filter.FilterAllContent
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.StorefrontCategoriesData
import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.CategoryContent.ViewHolder.CategoriesViewHolder
import co.geeksempire.premium.storefront.StorefrontConfigurations.UserInterface.Storefront
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import net.geeksempire.balloon.optionsmenu.library.BalloonItemsAction
import net.geeksempire.balloon.optionsmenu.library.BalloonOptionsMenu
import net.geeksempire.balloon.optionsmenu.library.OptionDataItems
import net.geeksempire.balloon.optionsmenu.library.TitleTextViewCustomization

class CategoriesAdapter(private val context: Storefront, private val filterAllContent: FilterAllContent) : RecyclerView.Adapter<CategoriesViewHolder>() {

    val storefrontCategories: ArrayList<StorefrontCategoriesData> = ArrayList<StorefrontCategoriesData>()

    var lastPosition: Int = 0

    override fun getItemCount(): Int {

        return storefrontCategories.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CategoriesViewHolder {

        return CategoriesViewHolder(LayoutInflater.from(context).inflate(R.layout.storefront_category_item, viewGroup, false))
    }

    override fun onBindViewHolder(categoriesViewHolder: CategoriesViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(categoriesViewHolder, position, payloads)

        categoriesViewHolder.productIconImageView.background = context.getDrawable(R.drawable.category_background_item)
        categoriesViewHolder.productIconImageView.imageTintList = ColorStateList.valueOf(context.getColor(R.color.dark))

    }

    override fun onBindViewHolder(categoriesViewHolder: CategoriesViewHolder, position: Int) {

        categoriesViewHolder.productIconImageView.background = if (storefrontCategories[position].selectedCategory || position == 0) {

            context.getDrawable(R.drawable.category_background_selected_item)

        } else {

            context.getDrawable(R.drawable.category_background_item)

        }

        categoriesViewHolder.productIconImageView.imageTintList = if (storefrontCategories[position].selectedCategory || position == 0) {

            ColorStateList.valueOf(context.getColor(R.color.light))

        } else {

            ColorStateList.valueOf(context.getColor(R.color.dark))

        }

        categoriesViewHolder.productIconImageView.tag = storefrontCategories[position].categoryId
        categoriesViewHolder.productIconImageView.contentDescription = storefrontCategories[position].categoryName

        Glide.with(context)
                .asDrawable()
                .load(storefrontCategories[position].categoryIconLink)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(categoriesViewHolder.productIconImageView)

        categoriesViewHolder.rootView.setOnClickListener {

            if (context.storefrontAllUnfilteredContents.isNotEmpty()) {

                notifyItemChanged(lastPosition, null)

                if (position == 0) {

                    context.storefrontLiveData.allFilteredContentItemData.postValue(context.storefrontAllUntouchedContents)

                } else {

                    filterAllContent.filterAllContentByCategory(context.storefrontAllUnfilteredContents, storefrontCategories[position].categoryName)

                }

                storefrontCategories[lastPosition].selectedCategory = false
                storefrontCategories[position].selectedCategory = true

                lastPosition = position

                categoriesViewHolder.productIconImageView.background = context.getDrawable(R.drawable.category_background_selected_item)
                categoriesViewHolder.productIconImageView.imageTintList = ColorStateList.valueOf(context.getColor(R.color.light))

                context.storefrontLayoutBinding.categoryIndicatorTextView.text = storefrontCategories[position].categoryName
                context.storefrontLayoutBinding.categoryIndicatorTextView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_out))

            } else {



            }

        }

        categoriesViewHolder.rootView.setOnLongClickListener { view ->

            context.balloonOptionsMenu.also {

                it.initializeBalloonPosition(anchorView = view, horizontalOffset = context.storefrontLayoutBinding.categoriesRecyclerView.width)

                it.setupOptionsItems(
                    menuId = storefrontCategories[position].categoryId.toString(),
                    menuTitle = storefrontCategories[position].categoryName.replace(" Applications", ""),
                    titlesOfItems = arrayListOf<OptionDataItems>(OptionDataItems(storefrontCategories[position].categoryId.toString(), context.getString(R.string.categoryShowAllApplications))),
                    titleTextViewCustomization = TitleTextViewCustomization(textSize = 37f, textColor = context.getColor(R.color.dark), textShadowColor = context.getColor(R.color.dark_transparent_high), textFont = ResourcesCompat.getFont(context, R.font.upcil)?: Typeface.DEFAULT)
                )

                it.setupActionListener(balloonItemsAction = object : BalloonItemsAction {

                    override fun onBalloonItemClickListener(balloonOptionsMenu: BalloonOptionsMenu, balloonOptionsRootView: View, itemView: View, itemTextView: TextView, itemData: OptionDataItems) {

                        context.storefrontLayoutBinding.contentDetailsContainer.visibility = View.VISIBLE

                        context.productDetailsFragment.apply {
                            isShowing = true
                        }

                        context.productDetailsFragment.arguments = Bundle().apply {


                        }

                        context.supportFragmentManager
                            .beginTransaction()
                            .setCustomAnimations(R.anim.slide_from_right, R.anim.fade_out)
                            .replace(R.id.contentDetailsContainer, context.categoryDetailsFragment, "Category Details For ${storefrontCategories[position].categoryId}")
                            .commit()

                        balloonOptionsMenu.removeBalloonOption()

                        Log.d(this@CategoriesAdapter.javaClass.simpleName, itemView.tag.toString())
                    }

                })

            }

            true
        }

    }

}