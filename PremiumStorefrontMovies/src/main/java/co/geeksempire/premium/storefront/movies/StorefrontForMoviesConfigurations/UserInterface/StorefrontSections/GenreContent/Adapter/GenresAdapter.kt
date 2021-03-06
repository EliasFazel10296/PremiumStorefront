/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 11/10/21, 2:42 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.UserInterface.StorefrontSections.GenreContent.Adapter

import android.app.ActivityOptions
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.premium.storefront.CategoriesDetailsConfigurations.DataStructure.CategoriesDataKeys
import co.geeksempire.premium.storefront.Database.Preferences.Theme.ThemeType
import co.geeksempire.premium.storefront.databinding.StorefrontCategoryItemBinding
import co.geeksempire.premium.storefront.movies.GenreDetailsConfigurations.UserInterface.GenreDetails
import co.geeksempire.premium.storefront.movies.R
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.DataStructure.StorefrontGenresData
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.MoviesFiltering.Filter.FilterAllMovies
import co.geeksempire.premium.storefront.movies.StorefrontForMoviesConfigurations.UserInterface.StorefrontSections.GenreContent.ViewHolder.GenresViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.firebase.firestore.DocumentSnapshot
import net.geeksempire.balloon.optionsmenu.library.BalloonItemsAction
import net.geeksempire.balloon.optionsmenu.library.BalloonOptionsMenu
import net.geeksempire.balloon.optionsmenu.library.OptionDataItems
import net.geeksempire.balloon.optionsmenu.library.TitleTextViewCustomization

class GenresAdapter(private val context: AppCompatActivity,
                    private val filterAllMovies: FilterAllMovies,
                    private val allFilteredContentItemData: MutableLiveData<Pair<ArrayList<DocumentSnapshot>, Boolean>>,
                    private val storefrontAllUnfilteredContents: ArrayList<DocumentSnapshot>,
                    private val storefrontAllUntouchedContents: ArrayList<DocumentSnapshot>,
                    private val genreIndicatorTextView: TextView,
                    private val categoriesRecyclerView: RecyclerView,
                    private val balloonOptionsMenu: BalloonOptionsMenu) : RecyclerView.Adapter<GenresViewHolder>() {

    var themeType: Boolean = ThemeType.ThemeLight

    val storefrontGenres: ArrayList<StorefrontGenresData> = ArrayList<StorefrontGenresData>()

    private var lastPosition: Int = 0

    override fun getItemCount(): Int {

        return storefrontGenres.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): GenresViewHolder {

        return GenresViewHolder(StorefrontCategoryItemBinding.inflate(context.layoutInflater, viewGroup, false))
    }

    override fun onBindViewHolder(genresViewHolder: GenresViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(genresViewHolder, position, payloads)

        when (themeType) {
            ThemeType.ThemeLight -> {

                genresViewHolder.genreIconImageView.background = getDrawable(context, R.drawable.category_background_item_light_movie)
                genresViewHolder.genreIconImageView.imageTintList = ColorStateList.valueOf(context.getColor(R.color.dark))

            }
            ThemeType.ThemeDark -> {

                genresViewHolder.genreIconImageView.background = getDrawable(context, R.drawable.category_background_item_dark_movie)
                genresViewHolder.genreIconImageView.imageTintList = ColorStateList.valueOf(context.getColor(R.color.light))

            }
            else -> {}
        }

        genresViewHolder.genreIconImageView.background = if (storefrontGenres[position].selectedCategory) {

            getDrawable(context, when (themeType) {
                ThemeType.ThemeLight -> {
                    genresViewHolder.genreIconImageView.imageTintList = ColorStateList.valueOf(context.getColor(R.color.light))

                    R.drawable.category_background_item_dark_movie
                }
                ThemeType.ThemeDark -> {
                    genresViewHolder.genreIconImageView.imageTintList = ColorStateList.valueOf(context.getColor(R.color.dark))

                    R.drawable.category_background_item_light_movie
                }
                else -> {
                    genresViewHolder.genreIconImageView.imageTintList = ColorStateList.valueOf(context.getColor(R.color.light))

                    R.drawable.category_background_item_dark_movie
                }
            })

        } else {

            getDrawable(context, when (themeType) {
                ThemeType.ThemeLight -> {
                    genresViewHolder.genreIconImageView.imageTintList = ColorStateList.valueOf(context.getColor(R.color.dark))

                    R.drawable.category_background_item_light_movie
                }
                ThemeType.ThemeDark -> {
                    genresViewHolder.genreIconImageView.imageTintList = ColorStateList.valueOf(context.getColor(R.color.light))

                    R.drawable.category_background_item_dark_movie
                }
                else -> {
                    genresViewHolder.genreIconImageView.imageTintList = ColorStateList.valueOf(context.getColor(R.color.dark))

                    R.drawable.category_background_item_light_movie
                }
            })

        }

    }


    override fun onBindViewHolder(categoriesViewHolder: GenresViewHolder, position: Int) {

        categoriesViewHolder.genreIconImageView.background = if (storefrontGenres[position].selectedCategory) {

            getDrawable(context, when (themeType) {
                ThemeType.ThemeLight -> {
                    R.drawable.category_background_item_dark_movie
                }
                ThemeType.ThemeDark -> {
                    R.drawable.category_background_item_light_movie
                }
                else -> R.drawable.category_background_item_dark_movie
            })

        } else {

            getDrawable(context, when (themeType) {
                ThemeType.ThemeLight -> {
                    R.drawable.category_background_item_light_movie
                }
                ThemeType.ThemeDark -> {
                    R.drawable.category_background_item_dark_movie
                }
                else -> R.drawable.category_background_item_light_movie
            })

        }

        categoriesViewHolder.genreIconImageView.imageTintList = if (storefrontGenres[position].selectedCategory) {

            ColorStateList.valueOf(when (themeType) {
                ThemeType.ThemeLight -> {
                    context.getColor(R.color.light)
                }
                ThemeType.ThemeDark -> {
                    context.getColor(R.color.dark)
                }
                else -> context.getColor(R.color.light)
            })

        } else {

            ColorStateList.valueOf(when (themeType) {
                ThemeType.ThemeLight -> {
                    context.getColor(R.color.dark)
                }
                ThemeType.ThemeDark -> {
                    context.getColor(R.color.light)
                }
                else -> context.getColor(R.color.dark)
            })

        }

        categoriesViewHolder.genreIconImageView.tag = storefrontGenres[position].genreId
        categoriesViewHolder.genreIconImageView.contentDescription = storefrontGenres[position].genreName

        Glide.with(context)
                .asDrawable()
                .load(storefrontGenres[position].genreIconLink)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(categoriesViewHolder.genreIconImageView)

        categoriesViewHolder.rootView.setOnClickListener {

            if (storefrontGenres[position].selectedCategory) {

                allFilteredContentItemData.postValue(Pair(storefrontAllUntouchedContents, true))

                storefrontGenres[position].selectedCategory = false

                notifyItemChanged(position, storefrontGenres[position])

            } else {

                if (storefrontAllUnfilteredContents.isNotEmpty()) {

                    if (position == 0) {

                        allFilteredContentItemData.postValue(Pair(storefrontAllUntouchedContents, true))

                    } else {

                        filterAllMovies.filterAllMoviesByGenre(storefrontAllUnfilteredContents, storefrontGenres[position].genreName)

                    }

                    storefrontGenres[lastPosition].selectedCategory = false
                    storefrontGenres[position].selectedCategory = true

                    notifyItemChanged(lastPosition, storefrontGenres[lastPosition])

                    lastPosition = position

                    when (themeType) {
                        ThemeType.ThemeLight -> {

                            categoriesViewHolder.genreIconImageView.background = context.getDrawable(R.drawable.category_background_item_dark_movie)
                            categoriesViewHolder.genreIconImageView.imageTintList = ColorStateList.valueOf(context.getColor(R.color.light))

                        }
                        ThemeType.ThemeDark -> {

                            categoriesViewHolder.genreIconImageView.background = context.getDrawable(R.drawable.category_background_item_light_movie)
                            categoriesViewHolder.genreIconImageView.imageTintList = ColorStateList.valueOf(context.getColor(R.color.dark))

                        }
                        else -> {

                            categoriesViewHolder.genreIconImageView.background = context.getDrawable(R.drawable.category_background_item_dark_movie)
                            categoriesViewHolder.genreIconImageView.imageTintList = ColorStateList.valueOf(context.getColor(R.color.light))

                        }
                    }

                    genreIndicatorTextView.text = storefrontGenres[position].genreName
                    genreIndicatorTextView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_out_movie))

                } else {



                }

            }

        }

        categoriesViewHolder.rootView.setOnLongClickListener { view ->

            balloonOptionsMenu.also {

                it.initializeBalloonPosition(anchorView = view, horizontalOffset = categoriesRecyclerView.width)

                it.setupOptionsItems(
                    menuId = storefrontGenres[position].genreId.toString(),
                    menuTitle = storefrontGenres[position].genreName,
                    titlesOfItems = arrayListOf<OptionDataItems>(OptionDataItems(storefrontGenres[position].genreId.toString(), context.getString(R.string.categoryShowAllMovies))),
                    titleTextViewCustomization = TitleTextViewCustomization(textSize = 37f, textColor = context.getColor(R.color.dark), textShadowColor = context.getColor(R.color.dark_transparent_high), textFont = ResourcesCompat.getFont(context, R.font.upcil)?: Typeface.DEFAULT)
                )

                it.setupActionListener(balloonItemsAction = object : BalloonItemsAction {

                    override fun onBalloonItemClickListener(balloonOptionsMenu: BalloonOptionsMenu, balloonOptionsRootView: View, itemView: View, itemTextView: TextView, itemData: OptionDataItems) {

                        context.startActivity(Intent(context, GenreDetails::class.java).apply {
                            putExtra(CategoriesDataKeys.CategoryId, storefrontGenres[position].genreId)
                            putExtra(CategoriesDataKeys.CategoryName, storefrontGenres[position].genreName)
                            putExtra(CategoriesDataKeys.CategoryIcon, storefrontGenres[position].genreIconLink)
                        }, ActivityOptions.makeCustomAnimation(context, R.anim.slide_in_right_movie, 0).toBundle())

                        balloonOptionsMenu.removeBalloonOption()

                        Log.d(this@GenresAdapter.javaClass.simpleName, itemView.tag.toString())
                    }

                })

            }

            true
        }

    }

}