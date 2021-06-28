/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/28/21, 6:18 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.Adapters.FeaturedContent.Adapter

import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemeType
import co.geeksempire.premium.storefront.ProductsDetailsConfigurations.Extensions.openProductsDetails
import co.geeksempire.premium.storefront.ProductsDetailsConfigurations.UserInterface.ProductDetailsFragment
import co.geeksempire.premium.storefront.R
import co.geeksempire.premium.storefront.StorefrontConfigurations.Adapters.FeaturedContent.ViewHolder.FeaturedContentViewHolder
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.ProductsContentKey
import co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure.StorefrontContentsData
import co.geeksempire.premium.storefront.Utils.Data.openPlayStoreToInstall
import co.geeksempire.premium.storefront.Utils.UI.Colors.extractDominantColor
import co.geeksempire.premium.storefront.Utils.UI.Colors.extractVibrantColor
import co.geeksempire.premium.storefront.Utils.UI.Views.Fragment.FragmentInterface
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import net.geeksempire.balloon.optionsmenu.library.Utils.dpToInteger

class FeaturedContentAdapter(private val context: AppCompatActivity,
                             private val contentDetailsContainer: FragmentContainerView, private val productDetailsFragment: ProductDetailsFragment,
                             private val fragmentInterface: FragmentInterface) : RecyclerView.Adapter<FeaturedContentViewHolder>() {

    var themeType: Boolean = ThemeType.ThemeLight

    val storefrontContents: ArrayList<StorefrontContentsData> = ArrayList<StorefrontContentsData>()

    override fun getItemCount() : Int {

        return storefrontContents.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) : FeaturedContentViewHolder {

        return FeaturedContentViewHolder(LayoutInflater.from(context).inflate(R.layout.storefront_featured_content_item, viewGroup, false))
    }

    override fun onBindViewHolder(featuredContentViewHolder: FeaturedContentViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(featuredContentViewHolder, position, payloads)

        when (themeType) {
            ThemeType.ThemeLight -> {

                featuredContentViewHolder.productNameTextView.setTextColor(context.getColor(R.color.black))
                featuredContentViewHolder.productNameBlur.setOverlayColor(context.getColor(R.color.light_transparent_high))

            }
            ThemeType.ThemeDark -> {

                featuredContentViewHolder.productNameTextView.setTextColor(context.getColor(R.color.white))
                featuredContentViewHolder.productNameBlur.setOverlayColor(context.getColor(R.color.dark_transparent_high))

            }
            else -> {

                featuredContentViewHolder.productNameTextView.setTextColor(context.getColor(R.color.black))
                featuredContentViewHolder.productNameBlur.setOverlayColor(context.getColor(R.color.light_transparent_high))

            }
        }

    }

    override fun onBindViewHolder(featuredContentViewHolder: FeaturedContentViewHolder, position: Int) {

        featuredContentViewHolder.productNameTextView.text = Html.fromHtml(storefrontContents[position].productName, Html.FROM_HTML_MODE_COMPACT)

        featuredContentViewHolder.productCurrentRateView.text = storefrontContents[position].productAttributes[ProductsContentKey.AttributesRatingKey]

        featuredContentViewHolder.productNameTextView.bringToFront()

        //Product Icon Image
        Glide.with(context)
            .asDrawable()
            .load(storefrontContents[position].productIconLink)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .override(256, 256)
            .transform(CircleCrop())
            .listener(object : RequestListener<Drawable> {

                override fun onLoadFailed(glideException: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean { return true }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                    resource?.let {

                        context.runOnUiThread {

                            val vibrantColor = extractVibrantColor(context, resource)
                            val dominantColor = extractDominantColor(context, resource)

                            featuredContentViewHolder.installView.setBackgroundColor(vibrantColor)

                            featuredContentViewHolder.productCurrentRateView.setTextColor(vibrantColor)
                            featuredContentViewHolder.productCurrentRateView.setShadowLayer(featuredContentViewHolder.productCurrentRateView.shadowRadius, featuredContentViewHolder.productCurrentRateView.shadowDx, featuredContentViewHolder.productCurrentRateView.shadowDy, vibrantColor)

                            featuredContentViewHolder.productIconImageView.setImageDrawable(resource)

                            val gradientFeaturedBackground = GradientDrawable(GradientDrawable.Orientation.TL_BR, intArrayOf(vibrantColor, dominantColor))
                            gradientFeaturedBackground.cornerRadius = dpToInteger(context, 11).toFloat()

                            featuredContentViewHolder.backgroundCoverImageView.background = gradientFeaturedBackground

                        }

                    }

                    return true
                }

            })
            .submit()

        //Product Cover Image
        Glide.with(context)
            .asDrawable()
            .load(storefrontContents[position].productCoverLink?:context.getString(R.string.choicePremiumStorefront))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .override(512, 250)
            .into(featuredContentViewHolder.backgroundCoverImageView)

        featuredContentViewHolder.rootView.setOnClickListener {

            openProductsDetails(context = context, fragmentInterface = fragmentInterface,
                contentDetailsContainer = contentDetailsContainer, productDetailsFragment = productDetailsFragment,
                storefrontContents = storefrontContents[position])

        }

        featuredContentViewHolder.rootView.setOnLongClickListener {

            openPlayStoreToInstall(context = context,
                aPackageName = (storefrontContents[position].productAttributes[ProductsContentKey.AttributesPackageNameKey].toString()),
                applicationName = storefrontContents[position].productName,
                applicationSummary = storefrontContents[position].productSummary)

            true
        }

        featuredContentViewHolder.installView.setOnClickListener {

            openPlayStoreToInstall(context = context,
                aPackageName = (storefrontContents[position].productAttributes[ProductsContentKey.AttributesPackageNameKey].toString()),
                applicationName = storefrontContents[position].productName,
                applicationSummary = storefrontContents[position].productSummary)

        }

        featuredContentViewHolder.productNameTextView.setOnClickListener {

        }

    }

}