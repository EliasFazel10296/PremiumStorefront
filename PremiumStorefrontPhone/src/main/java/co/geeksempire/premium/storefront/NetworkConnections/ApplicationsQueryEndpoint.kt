/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 6/21/21, 9:46 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.premium.storefront.NetworkConnections

class ApplicationsQueryEndpoint (private val generalEndpoint: GeneralEndpoint) {

    val defaultProductsPerPage = 19
    val defaultNumberOfPage = 1

    fun getAllAndroidApplicationsEndpoint(productPerPage: Int = defaultProductsPerPage, numberOfPage: Int = defaultNumberOfPage) =
        "https://geeksempire.co/wp-json/wc/v3/products?" +
            "consumer_key=${generalEndpoint.consumerKey()}" +
            "&" +
            "consumer_secret=${generalEndpoint.consumerSecret()}" +
            "&" +
            "per_page=${productPerPage}" +
            "&" +
            "page=${numberOfPage}" +
            "&" +
            "category=80" +
            "&" +
            "exclude=2341"

    fun getFeaturedApplicationsEndpoint(productPerPage: Int = defaultProductsPerPage, numberOfPage: Int = defaultNumberOfPage) : String =
        getAllAndroidApplicationsEndpoint(productPerPage, numberOfPage) +
            "&" +
            "featured=true"

    fun getApplicationsSearchEndpoint(productSearchQuery: String = "1", productPerPage: Int = 99, numberOfPage: Int = 1) : String =
        "${getAllAndroidApplicationsEndpoint()}" +
            "&search=$productSearchQuery" +
            "&exclude=2341"

    fun getNewApplicationsEndpoint(numberOfProducts: Int = 3, productPerPage: Int = 99, numberOfPage: Int = 1) : String =
        "${getAllAndroidApplicationsEndpoint()}" +
            "&" +
            "per_page=${numberOfProducts}" +
            "&" +
            "exclude=2341" +
            "&" +
            "orderby=date" +
            "&" +
            "order=desc"

    fun getApplicationsCategoriesEndpoint(numberOfProducts: Int = 99) : String =
        "${generalEndpoint.generalStorefrontEndpoint}" + "products/categories" + "?" +
            "consumer_key=${generalEndpoint.consumerKey()}" +
            "&" +
            "consumer_secret=${generalEndpoint.consumerSecret()}" +
            "&" +
            "exclude=80,66,57,546" + //Add Exclusion of All Other Categories
            "&" +
            "per_page=${numberOfProducts}"

}