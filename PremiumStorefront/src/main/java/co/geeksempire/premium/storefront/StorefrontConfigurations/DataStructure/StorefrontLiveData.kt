/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 11/12/21, 6:54 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.StorefrontConfigurations.DataStructure

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.json.JSONArray
import org.json.JSONObject

class StorefrontLiveData : ViewModel() {

    /**
     * Firebase
     **/

    val allContentItems: MutableLiveData<ArrayList<DocumentSnapshot>> by lazy {
        MutableLiveData<ArrayList<DocumentSnapshot>>()
    }

    val featuredContentItems: MutableLiveData<ArrayList<DocumentSnapshot>> by lazy {
        MutableLiveData<ArrayList<DocumentSnapshot>>()
    }

    val categoriesItems: MutableLiveData<ArrayList<StorefrontCategoriesData>> by lazy {
        MutableLiveData<ArrayList<StorefrontCategoriesData>>()
    }

    val allFilteredContentItemData: MutableLiveData<Pair<ArrayList<DocumentSnapshot>, Boolean>> by lazy {
        MutableLiveData<Pair<ArrayList<DocumentSnapshot>, Boolean>>()
    }

    /**
     * Wordpress
     **/

    val featuredContentItemDataWordpress: MutableLiveData<ArrayList<StorefrontContentsData>> by lazy {
        MutableLiveData<ArrayList<StorefrontContentsData>>()

    }

    val allContentItemDataWordpress: MutableLiveData<ArrayList<StorefrontContentsData>> by lazy {
        MutableLiveData<ArrayList<StorefrontContentsData>>()
    }

    val allContentMoreItemDataWordpress: MutableLiveData<ArrayList<StorefrontContentsData>> by lazy {
        MutableLiveData<ArrayList<StorefrontContentsData>>()
    }

    val presentMoreItemDataWordpress: MutableLiveData<StorefrontContentsData> by lazy {
        MutableLiveData<StorefrontContentsData>()
    }

    val allFilteredContentItemDataWordpress: MutableLiveData<Pair<ArrayList<StorefrontContentsData>, Boolean>> by lazy {
        MutableLiveData<Pair<ArrayList<StorefrontContentsData>, Boolean>>()
    }

    val newContentItemDataWordpress: MutableLiveData<ArrayList<StorefrontContentsData>> by lazy {
        MutableLiveData<ArrayList<StorefrontContentsData>>()
    }

    val oldContentItemDataWordpress: MutableLiveData<ArrayList<StorefrontContentsData>> by lazy {
        MutableLiveData<ArrayList<StorefrontContentsData>>()
    }

    /**
     * Firebase
     **/
    fun processCategoryData(documentSnapshot: DocumentSnapshot) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

        val categoriesDocumentSnapshots = ArrayList<StorefrontCategoriesData>()

        documentSnapshot.toObject(CategoriesIds::class.java)!!.CategoriesIds?.forEach { documentMap ->

            val categoryId = documentMap[CategoryDataKey.CategoryId].toString().toInt()

            val categoryName = documentMap[CategoryDataKey.CategoryName].toString()

            categoriesDocumentSnapshots.add(StorefrontCategoriesData(
                categoryId = categoryId ,
                categoryName = categoryName,
                categoryIconLink = documentMap[CategoryDataKey.CategoryIconLink].toString(),
                productCount = documentMap[CategoryDataKey.ProductCount].toString().toInt()
            ))

            Log.d(this@StorefrontLiveData.javaClass.simpleName, "Category Id: ${categoryId} | Category Name: ${categoryName}")

        }

        val moviesDocumentSnapshotsSorted = categoriesDocumentSnapshots.sortedByDescending {

            it.productCount
        }

        categoriesDocumentSnapshots.clear()
        categoriesDocumentSnapshots.addAll(moviesDocumentSnapshotsSorted)

        categoriesItems.postValue(categoriesDocumentSnapshots)

    }

    fun processAllContent(documentSnapshots: ArrayList<List<DocumentSnapshot>>) {

        val allContent = ArrayList<DocumentSnapshot>()

        documentSnapshots.forEachIndexed { index, documentsList ->

            documentsList.forEachIndexed { index, documentSnapshot ->

                allContent.add(documentSnapshot)

            }

        }

        allContentItems.postValue(allContent)

    }

    /**
     * Wordpress
     **/
    fun processAllContentWordpress(allContentJsonArray: JSONArray) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {
        Log.d(this@StorefrontLiveData.javaClass.simpleName, "Process All Content")

        val storefrontAllContents = ArrayList<StorefrontContentsData>()

        for (indexContent in 0 until allContentJsonArray.length()) {

            val featuredContentJsonObject: JSONObject = allContentJsonArray[indexContent] as JSONObject

            /* Start - Images */
            val featuredContentImages: JSONArray = featuredContentJsonObject[ProductsContentKey.ImagesKey] as JSONArray

            val productIcon = (featuredContentImages[0] as JSONObject).getString(ProductsContentKey.ImageSourceKey)
            val productCover: String? = try {
                (featuredContentImages[2] as JSONObject).getString(ProductsContentKey.ImageSourceKey)
            } catch (e: Exception) {
                null
            }
            /* End - Images */

            /* Start - Primary Category */
            val productCategories = featuredContentJsonObject.getJSONArray(ProductsContentKey.CategoriesKey)

            var productCategory = (productCategories[productCategories.length() - 1] as JSONObject)

            var productCategoryName = "All Products"
            var productCategoryId = 15

            for (indexCategory in 0 until productCategories.length()) {

                val textCheckpoint: String = (productCategories[indexCategory] as JSONObject).getString(ProductsContentKey.NameKey).split(" ")[0]

                if (textCheckpoint != "All" && textCheckpoint != "Quick" && textCheckpoint != "Unique") {

                    productCategory = (productCategories[indexCategory] as JSONObject)

                    productCategoryName = productCategory.getString(ProductsContentKey.NameKey)
                    productCategoryId = productCategory.getInt(ProductsContentKey.IdKey)

                }

            }
            /* End - Primary Category */

            /* Start - Attributes */
            val featuredContentAttributes: JSONArray = featuredContentJsonObject[ProductsContentKey.AttributesKey] as JSONArray

            val attributesMap = HashMap<String, String?>()

            for (indexAttribute in 0 until featuredContentAttributes.length()) {

                val attributesJsonObject: JSONObject = featuredContentAttributes[indexAttribute] as JSONObject

                attributesMap[attributesJsonObject.getString(ProductsContentKey.NameKey)] = attributesJsonObject.getJSONArray(ProductsContentKey.AttributeOptionsKey)[0].toString()

            }
            /* End - Attributes */

            storefrontAllContents.add(StorefrontContentsData(
                productName = featuredContentJsonObject.getString(ProductsContentKey.NameKey),
                productDescription = featuredContentJsonObject.getString(ProductsContentKey.DescriptionKey),
                productSummary = featuredContentJsonObject.getString(ProductsContentKey.SummaryKey),
                productCategoryName = productCategoryName,
                productCategoryId = productCategoryId,
                productPrice = featuredContentJsonObject.getString(ProductsContentKey.RegularPriceKey),
                productSalePrice = featuredContentJsonObject.getString(ProductsContentKey.SalePriceKey),
                productIconLink = productIcon,
                productCoverLink = productCover,
                productAttributes = attributesMap
            ))

            Log.d(this@StorefrontLiveData.javaClass.simpleName, "All Products: ${featuredContentJsonObject.getString(ProductsContentKey.NameKey)}")
        }

        val storefrontAllContentsSorted = storefrontAllContents.sortedByDescending {

            it.productPrice
        }

        storefrontAllContents.clear()
        storefrontAllContents.addAll(storefrontAllContentsSorted)

        allContentItemDataWordpress.postValue(storefrontAllContents)

    }

    fun processAllContentMoreWordpress(allContentJsonArray: JSONArray) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {
        Log.d(this@StorefrontLiveData.javaClass.simpleName, "Process All Content And Add Them To Memory")

        val storefrontAllContents = ArrayList<StorefrontContentsData>()

        for (indexContent in 0 until allContentJsonArray.length()) {

            val featuredContentJsonObject: JSONObject = allContentJsonArray[indexContent] as JSONObject

            /* Start - Images */
            val featuredContentImages: JSONArray = featuredContentJsonObject[ProductsContentKey.ImagesKey] as JSONArray

            val productIcon = (featuredContentImages[0] as JSONObject).getString(ProductsContentKey.ImageSourceKey)
            val productCover = try {
                (featuredContentImages[2] as JSONObject).getString(ProductsContentKey.ImageSourceKey)
            } catch (e: Exception) {
                null
            }
            /* End - Images */

            /* Start - Primary Category */
            val productCategories = featuredContentJsonObject.getJSONArray(ProductsContentKey.CategoriesKey)

            var productCategory = (productCategories[productCategories.length() - 1] as JSONObject)

            var productCategoryName = "All Products"
            var productCategoryId = 15

            for (indexCategory in 0 until productCategories.length()) {

                val textCheckpoint: String = (productCategories[indexCategory] as JSONObject).getString(ProductsContentKey.NameKey).split(" ")[0]

                if (textCheckpoint != "All" && textCheckpoint != "Quick" && textCheckpoint != "Unique") {

                    productCategory = (productCategories[indexCategory] as JSONObject)

                    productCategoryName = productCategory.getString(ProductsContentKey.NameKey)
                    productCategoryId = productCategory.getInt(ProductsContentKey.IdKey)

                }

            }
            /* End - Primary Category */

            /* Start - Attributes */
            val featuredContentAttributes: JSONArray = featuredContentJsonObject[ProductsContentKey.AttributesKey] as JSONArray

            val attributesMap = HashMap<String, String?>()

            for (indexAttribute in 0 until featuredContentAttributes.length()) {

                val attributesJsonObject: JSONObject = featuredContentAttributes[indexAttribute] as JSONObject

                attributesMap[attributesJsonObject.getString(ProductsContentKey.NameKey)] = attributesJsonObject.getJSONArray(ProductsContentKey.AttributeOptionsKey)[0].toString()

            }
            /* End - Attributes */

            storefrontAllContents.add(StorefrontContentsData(
                productName = featuredContentJsonObject.getString(ProductsContentKey.NameKey),
                productDescription = featuredContentJsonObject.getString(ProductsContentKey.DescriptionKey),
                productSummary = featuredContentJsonObject.getString(ProductsContentKey.SummaryKey),
                productCategoryName = productCategoryName,
                productCategoryId = productCategoryId,
                productPrice = featuredContentJsonObject.getString(ProductsContentKey.RegularPriceKey),
                productSalePrice = featuredContentJsonObject.getString(ProductsContentKey.SalePriceKey),
                productIconLink = productIcon,
                productCoverLink = productCover,
                productAttributes = attributesMap
            ))

            Log.d(this@StorefrontLiveData.javaClass.simpleName, "All Products: ${featuredContentJsonObject.getString(ProductsContentKey.NameKey)}")
        }

        val storefrontAllContentsSorted = storefrontAllContents.sortedByDescending {

            it.productPrice
        }

        storefrontAllContents.clear()
        storefrontAllContents.addAll(storefrontAllContentsSorted)

        allContentMoreItemDataWordpress.postValue(storefrontAllContents)

    }

    fun processAllContentOfflineWordpress(allContentJsonArray: JSONArray) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {
        Log.d(this@StorefrontLiveData.javaClass.simpleName, "Process All Content")

        val initialJsonArray = JSONArray()

        val targetIndex = if (allContentJsonArray.length() >= 19) {

            19

        } else {

            allContentJsonArray.length()

        }

        for (indexContent in 0 until targetIndex) {

            initialJsonArray.put(allContentJsonArray[indexContent])

            allContentJsonArray.remove(indexContent)

        }

        processAllContentWordpress(initialJsonArray)

        processAllContentMoreWordpress(allContentJsonArray)

    }

    fun loadMoreDataIntoPresenterWordpress(allData: ArrayList<StorefrontContentsData>, currentAvailableData: ArrayList<StorefrontContentsData>) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

        val moreProducts: ArrayList<StorefrontContentsData> = ArrayList<StorefrontContentsData>()

        val startIndex = currentAvailableData.size
        val endIndex = if (allData.size < (currentAvailableData.size + 19)) {
            currentAvailableData.size + (allData.size - currentAvailableData.size)
        } else {
            currentAvailableData.size + 19
        }

        allData.subList(startIndex, endIndex).forEach {

            moreProducts.add(it)

            presentMoreItemDataWordpress.postValue(it)

            delay(531)

        }

    }

    fun processFeaturedContentWordpress(featuredContentJsonArray: JSONArray) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {
        Log.d(this@StorefrontLiveData.javaClass.simpleName, "Process Featured Content")

        val storefrontFeaturedContents = ArrayList<StorefrontContentsData>()

        for (indexFeaturedContent in 0 until featuredContentJsonArray.length()) {

            val featuredContentJsonObject: JSONObject = featuredContentJsonArray[indexFeaturedContent] as JSONObject

            /* Start - Images */
            val featuredContentImages: JSONArray = featuredContentJsonObject[ProductsContentKey.ImagesKey] as JSONArray

            val productIcon = (featuredContentImages[0] as JSONObject).getString(ProductsContentKey.ImageSourceKey)
            val productCover: String? = try {
                (featuredContentImages[2] as JSONObject).getString(ProductsContentKey.ImageSourceKey)
            } catch (e: Exception) {
                null
            }
            /* End - Images */

            /* Start - Primary Category */
            val productCategories = featuredContentJsonObject.getJSONArray(ProductsContentKey.CategoriesKey)

            var productCategory = (productCategories[productCategories.length() - 1] as JSONObject)

            var productCategoryName = "All Products"
            var productCategoryId = 15

            for (indexCategory in 0 until productCategories.length()) {

                val textCheckpoint: String = (productCategories[indexCategory] as JSONObject).getString(ProductsContentKey.NameKey).split(" ")[0]

                if (textCheckpoint != "All" && textCheckpoint != "Quick" && textCheckpoint != "Unique") {

                    productCategory = (productCategories[indexCategory] as JSONObject)

                    productCategoryName = productCategory.getString(ProductsContentKey.NameKey)
                    productCategoryId = productCategory.getInt(ProductsContentKey.IdKey)

                }

            }
            /* End - Primary Category */

            /* Start - Attributes */
            val featuredContentAttributes: JSONArray = featuredContentJsonObject[ProductsContentKey.AttributesKey] as JSONArray

            val attributesMap = HashMap<String, String?>()

            for (indexAttribute in 0 until featuredContentAttributes.length()) {

                val attributesJsonObject: JSONObject = featuredContentAttributes[indexAttribute] as JSONObject

                attributesMap[attributesJsonObject.getString(ProductsContentKey.NameKey)] = attributesJsonObject.getJSONArray(ProductsContentKey.AttributeOptionsKey)[0].toString()

            }
            /* End - Attributes */

            storefrontFeaturedContents.add(StorefrontContentsData(
                productName = featuredContentJsonObject.getString(ProductsContentKey.NameKey),
                productDescription = featuredContentJsonObject.getString(ProductsContentKey.DescriptionKey),
                productSummary = featuredContentJsonObject.getString(ProductsContentKey.SummaryKey),
                productCategoryName = productCategoryName,
                productCategoryId = productCategoryId,
                productPrice = featuredContentJsonObject.getString(ProductsContentKey.RegularPriceKey),
                productSalePrice = featuredContentJsonObject.getString(ProductsContentKey.SalePriceKey),
                productIconLink = productIcon,
                productCoverLink = productCover,
                productAttributes = attributesMap
            ))

            Log.d(this@StorefrontLiveData.javaClass.simpleName, "Featured Product: ${featuredContentJsonObject.getString(ProductsContentKey.NameKey)}")
        }

        val storefrontFeaturedContentsSorted = storefrontFeaturedContents.sortedByDescending {

            it.productAttributes[ProductsContentKey.AttributesRatingKey]
        }

        storefrontFeaturedContents.clear()
        storefrontFeaturedContents.addAll(storefrontFeaturedContentsSorted)

        featuredContentItemDataWordpress.postValue(storefrontFeaturedContents)

    }

    fun processNewContentWordpress(allContentJsonArray: JSONArray) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {
        Log.d(this@StorefrontLiveData.javaClass.simpleName, "Process All Content")

        val storefrontAllContents = ArrayList<StorefrontContentsData>()

        for (indexContent in 0 until allContentJsonArray.length()) {

            val featuredContentJsonObject: JSONObject = allContentJsonArray[indexContent] as JSONObject

            /* Start - Images */
            val featuredContentImages: JSONArray = featuredContentJsonObject[ProductsContentKey.ImagesKey] as JSONArray

            val productIcon = (featuredContentImages[0] as JSONObject).getString(ProductsContentKey.ImageSourceKey)
            val productCover: String? = try {
                (featuredContentImages[2] as JSONObject).getString(ProductsContentKey.ImageSourceKey)
            } catch (e: Exception) {
                null
            }
            /* End - Images */

            /* Start - Primary Category */
            val productCategories = featuredContentJsonObject.getJSONArray(ProductsContentKey.CategoriesKey)

            var productCategory = (productCategories[productCategories.length() - 1] as JSONObject)

            var productCategoryName = "All Products"
            var productCategoryId = 15

            for (indexCategory in 0 until productCategories.length()) {

                val textCheckpoint: String = (productCategories[indexCategory] as JSONObject).getString(ProductsContentKey.NameKey).split(" ")[0]

                if (textCheckpoint != "All" && textCheckpoint != "Quick" && textCheckpoint != "Unique") {

                    productCategory = (productCategories[indexCategory] as JSONObject)

                    productCategoryName = productCategory.getString(ProductsContentKey.NameKey)
                    productCategoryId = productCategory.getInt(ProductsContentKey.IdKey)

                }

            }
            /* End - Primary Category */

            /* Start - Attributes */
            val featuredContentAttributes: JSONArray = featuredContentJsonObject[ProductsContentKey.AttributesKey] as JSONArray

            val attributesMap = HashMap<String, String?>()

            for (indexAttribute in 0 until featuredContentAttributes.length()) {

                val attributesJsonObject: JSONObject = featuredContentAttributes[indexAttribute] as JSONObject

                attributesMap[attributesJsonObject.getString(ProductsContentKey.NameKey)] = attributesJsonObject.getJSONArray(ProductsContentKey.AttributeOptionsKey)[0].toString()

            }
            /* End - Attributes */

            storefrontAllContents.add(StorefrontContentsData(
                productName = featuredContentJsonObject.getString(ProductsContentKey.NameKey),
                productDescription = featuredContentJsonObject.getString(ProductsContentKey.DescriptionKey),
                productSummary = featuredContentJsonObject.getString(ProductsContentKey.SummaryKey),
                productCategoryName = productCategoryName,
                productCategoryId = productCategoryId,
                productPrice = featuredContentJsonObject.getString(ProductsContentKey.RegularPriceKey),
                productSalePrice = featuredContentJsonObject.getString(ProductsContentKey.SalePriceKey),
                productIconLink = productIcon,
                productCoverLink = productCover,
                productAttributes = attributesMap
            ))

            Log.d(this@StorefrontLiveData.javaClass.simpleName, "All Products: ${featuredContentJsonObject.getString(ProductsContentKey.NameKey)}")
        }

        val storefrontAllContentsSorted = storefrontAllContents.sortedByDescending {

            it.productPrice
        }

        storefrontAllContents.clear()
        storefrontAllContents.addAll(storefrontAllContentsSorted)

        newContentItemDataWordpress.postValue(storefrontAllContents)

    }

    fun processOldContentWordpress(allContentJsonArray: JSONArray) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {
        Log.d(this@StorefrontLiveData.javaClass.simpleName, "Process All Content")

        val storefrontAllContents = ArrayList<StorefrontContentsData>()

        for (indexContent in 0 until allContentJsonArray.length()) {

            val featuredContentJsonObject: JSONObject = allContentJsonArray[indexContent] as JSONObject

            /* Start - Images */
            val featuredContentImages: JSONArray = featuredContentJsonObject[ProductsContentKey.ImagesKey] as JSONArray

            val productIcon = (featuredContentImages[0] as JSONObject).getString(ProductsContentKey.ImageSourceKey)
            val productCover: String? = try {
                (featuredContentImages[2] as JSONObject).getString(ProductsContentKey.ImageSourceKey)
            } catch (e: Exception) {
                null
            }
            /* End - Images */

            /* Start - Primary Category */
            val productCategories = featuredContentJsonObject.getJSONArray(ProductsContentKey.CategoriesKey)

            var productCategory = (productCategories[productCategories.length() - 1] as JSONObject).getString(ProductsContentKey.NameKey)
            var productCategoryId = 15

            for (indexCategory in 0 until productCategories.length()) {

                val textCheckpoint: String = (productCategories[indexCategory] as JSONObject).getString(ProductsContentKey.NameKey).split(" ")[0]

                if (textCheckpoint != "All" && textCheckpoint != "Quick" && textCheckpoint != "Unique") {

                    productCategory = (productCategories[indexCategory] as JSONObject).getString(ProductsContentKey.NameKey)

                    productCategoryId = try {

                            (productCategories[indexCategory] as JSONObject).getInt(ProductsContentKey.IdKey)

                    } catch (e: Exception) {
                        e.printStackTrace()

                        15
                    }

                }

            }
            /* End - Primary Category */

            /* Start - Attributes */
            val featuredContentAttributes: JSONArray = featuredContentJsonObject[ProductsContentKey.AttributesKey] as JSONArray

            val attributesMap = HashMap<String, String?>()

            for (indexAttribute in 0 until featuredContentAttributes.length()) {

                val attributesJsonObject: JSONObject = featuredContentAttributes[indexAttribute] as JSONObject

                attributesMap[attributesJsonObject.getString(ProductsContentKey.NameKey)] = attributesJsonObject.getJSONArray(ProductsContentKey.AttributeOptionsKey)[0].toString()

            }
            /* End - Attributes */

            storefrontAllContents.add(StorefrontContentsData(
                productName = featuredContentJsonObject.getString(ProductsContentKey.NameKey),
                productDescription = featuredContentJsonObject.getString(ProductsContentKey.DescriptionKey),
                productSummary = featuredContentJsonObject.getString(ProductsContentKey.SummaryKey),
                productCategoryName = productCategory,
                productCategoryId = productCategoryId,
                productPrice = featuredContentJsonObject.getString(ProductsContentKey.RegularPriceKey),
                productSalePrice = featuredContentJsonObject.getString(ProductsContentKey.SalePriceKey),
                productIconLink = productIcon,
                productCoverLink = productCover,
                productAttributes = attributesMap
            ))

            Log.d(this@StorefrontLiveData.javaClass.simpleName, "All Products: ${featuredContentJsonObject.getString(ProductsContentKey.NameKey)}")
        }

        val storefrontAllContentsSorted = storefrontAllContents.sortedByDescending {

            it.productPrice
        }

        storefrontAllContents.clear()
        storefrontAllContents.addAll(storefrontAllContentsSorted)

        oldContentItemDataWordpress.postValue(storefrontAllContents)

    }

    fun processCategoriesList(allContentJsonArray: JSONArray) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

        val storefrontCategoriesData = ArrayList<StorefrontCategoriesData>()

        for (indexContent in 0 until allContentJsonArray.length()) {

            val categoryJsonObject: JSONObject = allContentJsonArray[indexContent] as JSONObject

            val categoryId = categoryJsonObject.getInt(ProductsContentKey.IdKey)

            val categoryName = categoryJsonObject.getString(ProductsContentKey.NameKey)

            val categoryImageData = categoryJsonObject[ProductsContentKey.ImageKey]

            val categoryIconLink = (categoryImageData as JSONObject).getString(ProductsContentKey.ImageSourceKey)

            storefrontCategoriesData.add(StorefrontCategoriesData(
                categoryId = categoryId,
                categoryName = categoryName,
                categoryIconLink = categoryIconLink,
                productCount = 0
            ))

            Log.d(this@StorefrontLiveData.javaClass.simpleName, "Category Id: ${categoryId} | Category Name: ${categoryName}")
        }

        categoriesItems.postValue(storefrontCategoriesData)

    }

}