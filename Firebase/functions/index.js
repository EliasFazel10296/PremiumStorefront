const functions = require('firebase-functions');
const admin = require('firebase-admin');

admin.initializeApp({
    credential: admin.credential.applicationDefault(),
});

const firestore = admin.firestore();

const XMLHttpRequest = require("xmlhttprequest").XMLHttpRequest;

const runtimeOptions = {
    timeoutSeconds: 512,
}

/* Start - Triggering Updates On Clients */
exports.triggerBackgroundUpdatingProcessApplications = functions.runWith(runtimeOptions).https.onRequest((req, res) => {

    var message = {

        android: {
            ttl: (3600 * 1000) * (24), // 1 Hour in Milliseconds

            priority: 'high',
        },

        data: {
            UpdateDataKey: 'ApplicationsData',
        },
        topic: 'PremiumStorefront'
    };

    admin.messaging().send(message)
        .then((response) => {

            res.status(200).send('Done!');

        })
        .catch((error) => {

            res.status(200).send('Error: ' + error);

        });

});

exports.triggerBackgroundUpdatingProcessGames = functions.runWith(runtimeOptions).https.onRequest((req, res) => {

    var message = {

        android: {
            ttl: (3600 * 1000) * (24), // 1 Hour in Milliseconds

            priority: 'high',
        },

        data: {
            UpdateDataKey: 'GamesData',
        },
        topic: 'PremiumStorefront'
    };

    admin.messaging().send(message)
        .then((response) => {

            res.status(200).send('Done!');

        })
        .catch((error) => {

            res.status(200).send('Error: ' + error);

        });

});

exports.triggerBackgroundUpdatingProcessBooks = functions.runWith(runtimeOptions).https.onRequest((req, res) => {

    var message = {

        android: {
            ttl: (3600 * 1000) * (24), // 1 Hour in Milliseconds

            priority: 'high',
        },

        data: {
            UpdateDataKey: 'BooksData',
        },
        topic: 'PremiumStorefront'
    };

    admin.messaging().send(message)
        .then((response) => {

            res.status(200).send('Done!');

        })
        .catch((error) => {

            res.status(200).send('Error: ' + error);

        });

});

exports.triggerBackgroundUpdatingProcessMovies = functions.runWith(runtimeOptions).https.onRequest((req, res) => {

    var message = {

        android: {
            ttl: (3600 * 1000) * (24), // 1 Hour in Milliseconds

            priority: 'high',
        },

        data: {
            UpdateDataKey: 'MoviesData',
        },
        topic: 'PremiumStorefront'
    };

    admin.messaging().send(message)
        .then((response) => {

            res.status(200).send('Done!');

        })
        .catch((error) => {

            res.status(200).send('Error: ' + error);

        });

});
/* End - Triggering Updates On Clients */

/* Start - Transfering Applications/Games for Popup Shortcuts */
exports.transferQuickAccessProducts = functions.runWith(runtimeOptions).https.onRequest((req, res) => {

    var featuredApplicationsEndpoint = 'https://geeksempire.co/wp-json/wc/v3/products?consumer_key=ck_e469d717bd778da4fb9ec24881ee589d9b202662&consumer_secret=cs_ac53c1b36d1a85e36a362855d83af93f0d377686'
        + '&category=836';

    var xmlHttpRequest = new XMLHttpRequest();
    xmlHttpRequest.open('GET', featuredApplicationsEndpoint, true);
    xmlHttpRequest.setRequestHeader('accept', 'application/json');
    xmlHttpRequest.setRequestHeader('Content-Type', 'application/json');
    xmlHttpRequest.onreadystatechange = function () {
        if (this.readyState == 4) {

        } else {

        }
    };
    xmlHttpRequest.onprogress = function () {

    };
    xmlHttpRequest.onload = function () {

        var jsonArrayParserResponse = JSON.parse(xmlHttpRequest.responseText);

        var featuredContent = [];

        jsonArrayParserResponse.forEach((jsonObject, index) => {

            const IdKey = "id"
            const NameKey = "name";

            const CategoriesKey = "categories";

            var productId = jsonObject[IdKey];

            /* Start - Primary Category */
            var productCategories = jsonObject[CategoriesKey];

            var productCategoryName = "All";
            var productCategoryId = 15;

            productCategories.forEach((productCategory) => {

                var textCheckpoint = (productCategory)[NameKey].split(" ")[0];

                if (textCheckpoint != "All" && textCheckpoint != "Quick" && textCheckpoint != "Unique") {

                    productCategoryName = productCategory[NameKey].split(" ")[0];
                    productCategoryId = productCategory[IdKey];

                }

            });
            /* End - Primary Category */

            featuredContent[index] = {
                'ProductId': productId,
                'ProductCategory': productCategoryName,
            };

        });

        /* Start - Document * With Even Directory */
        var firestoreDirectory = '/' + 'PremiumStorefront'
            + '/' + 'Products'
            + '/' + 'Android'
            + '/' + 'QuickAccess';

        firestore.doc(firestoreDirectory).set({
            ProductsIds: featuredContent,
        });
        /* End - Document * With Even Directory */

    };
    xmlHttpRequest.send();

});
/* End - Transfering Applications/Games for Popup Shortcuts */

/* Start - Transfering Featured Products */
exports.transferFeaturedApplicationsData = functions.runWith(runtimeOptions).https.onRequest((req, res) => {

    var numberOfPage = req.query.numberOfPage;

    if (numberOfPage == null) {
        numberOfPage = 1;
    }

    var featuredApplicationsEndpoint = 'https://geeksempire.co/wp-json/wc/v3/products?consumer_key=ck_e469d717bd778da4fb9ec24881ee589d9b202662&consumer_secret=cs_ac53c1b36d1a85e36a362855d83af93f0d377686'
        + '&page=' + numberOfPage
        + '&per_page=100'
        + '&category=80'
        + '&featured=true'

    var xmlHttpRequest = new XMLHttpRequest();
    xmlHttpRequest.open('GET', featuredApplicationsEndpoint, true);
    xmlHttpRequest.setRequestHeader('accept', 'application/json');
    xmlHttpRequest.setRequestHeader('Content-Type', 'application/json');
    xmlHttpRequest.onreadystatechange = function () {
        if (this.readyState == 4) {

        } else {

        }
    };
    xmlHttpRequest.onprogress = function () {

    };
    xmlHttpRequest.onload = function () {

        var jsonArrayParserResponse = JSON.parse(xmlHttpRequest.responseText);

        var featuredContent = [];

        jsonArrayParserResponse.forEach((jsonObject, index) => {

            const IdKey = "id"
            const NameKey = "name";

            const CategoriesKey = "categories";

            var productId = jsonObject[IdKey];

            /* Start - Primary Category */
            var productCategories = jsonObject[CategoriesKey];

            var productCategoryName = "All";
            var productCategoryId = 15;

            productCategories.forEach((productCategory) => {

                var textCheckpoint = (productCategory)[NameKey].split(" ")[0];

                if (textCheckpoint != "All" && textCheckpoint != "Quick" && textCheckpoint != "Unique") {

                    productCategoryName = productCategory[NameKey].split(" ")[0];
                    productCategoryId = productCategory[IdKey];

                }

            });
            /* End - Primary Category */

            featuredContent[index] = {
                'ProductId': productId,
                'ProductCategory': productCategoryName,
            };

        });

        /* Start - Document * With Even Directory */
        var firestoreDirectory = '/' + 'PremiumStorefront'
            + '/' + 'Products'
            + '/' + 'Android'
            + '/' + 'Applications'
            + '/' + 'Featured'
            + '/' + 'Content';

        firestore.doc(firestoreDirectory).set({
            ProductsIds: featuredContent,
        });
        /* End - Document * With Even Directory */

    };
    xmlHttpRequest.send();

});

exports.transferFeaturedGamesData = functions.runWith(runtimeOptions).https.onRequest((req, res) => {

    var numberOfPage = req.query.numberOfPage;

    if (numberOfPage == null) {
        numberOfPage = 1;
    }

    var featuredApplicationsEndpoint = 'https://geeksempire.co/wp-json/wc/v3/products?consumer_key=ck_e469d717bd778da4fb9ec24881ee589d9b202662&consumer_secret=cs_ac53c1b36d1a85e36a362855d83af93f0d377686'
        + '&page=' + numberOfPage
        + '&per_page=100'
        + '&category=546'
        + '&featured=true'

    var xmlHttpRequest = new XMLHttpRequest();
    xmlHttpRequest.open('GET', featuredApplicationsEndpoint, true);
    xmlHttpRequest.setRequestHeader('accept', 'application/json');
    xmlHttpRequest.setRequestHeader('Content-Type', 'application/json');
    xmlHttpRequest.onreadystatechange = function () {
        if (this.readyState == 4) {

        } else {

        }
    };
    xmlHttpRequest.onprogress = function () {

    };
    xmlHttpRequest.onload = function () {

        var jsonArrayParserResponse = JSON.parse(xmlHttpRequest.responseText);

        var featuredContent = [];

        jsonArrayParserResponse.forEach((jsonObject, index) => {

            const IdKey = "id"
            const NameKey = "name";

            const CategoriesKey = "categories";

            var productId = jsonObject[IdKey];

            /* Start - Primary Category */
            var productCategories = jsonObject[CategoriesKey];

            var productCategoryName = "All";
            var productCategoryId = 15;

            productCategories.forEach((productCategory) => {

                var textCheckpoint = (productCategory)[NameKey].split(" ")[0];

                if (textCheckpoint != "All" && textCheckpoint != "Quick" && textCheckpoint != "Unique") {

                    productCategoryName = productCategory[NameKey].split(" ")[0];
                    productCategoryId = productCategory[IdKey];

                }

            });
            /* End - Primary Category */

            featuredContent[index] = {
                'ProductId': productId,
                'ProductCategory': productCategoryName,
            };

        });

        /* Start - Document * With Even Directory */
        var firestoreDirectory = '/' + 'PremiumStorefront'
            + '/' + 'Products'
            + '/' + 'Android'
            + '/' + 'Games'
            + '/' + 'Featured'
            + '/' + 'Content';

        firestore.doc(firestoreDirectory).set({
            ProductsIds: featuredContent,
        });
        /* End - Document * With Even Directory */

    };
    xmlHttpRequest.send();

});


exports.transferFeaturedMoviesData = functions.runWith(runtimeOptions).https.onRequest((req, res) => {

    var numberOfPage = req.query.numberOfPage;

    if (numberOfPage == null) {
        numberOfPage = 1;
    }

    var featuredApplicationsEndpoint = 'https://geeksempire.co/wp-json/wc/v3/products?consumer_key=ck_e469d717bd778da4fb9ec24881ee589d9b202662&consumer_secret=cs_ac53c1b36d1a85e36a362855d83af93f0d377686'
        + '&page=' + numberOfPage
        + '&per_page=100'
        + '&category=982'
        + '&featured=true'

    var xmlHttpRequest = new XMLHttpRequest();
    xmlHttpRequest.open('GET', featuredApplicationsEndpoint, true);
    xmlHttpRequest.setRequestHeader('accept', 'application/json');
    xmlHttpRequest.setRequestHeader('Content-Type', 'application/json');
    xmlHttpRequest.onreadystatechange = function () {
        if (this.readyState == 4) {

        } else {

        }
    };
    xmlHttpRequest.onprogress = function () {

    };
    xmlHttpRequest.onload = function () {

        var jsonArrayParserResponse = JSON.parse(xmlHttpRequest.responseText);

        var featuredContent = [];

        jsonArrayParserResponse.forEach((jsonObject, index) => {

            const IdKey = "id"
            const NameKey = "name";

            const CategoriesKey = "categories";

            var productId = jsonObject[IdKey];

            /* Start - Primary Category */
            var productCategories = jsonObject[CategoriesKey];

            var productCategoryName = "All";
            var productCategoryId = 15;

            productCategories.forEach((productCategory) => {

                var textCheckpoint = (productCategory)[NameKey].split(" ")[0];

                if (textCheckpoint != "All" && textCheckpoint != "Quick" && textCheckpoint != "Unique") {

                    productCategoryName = productCategory[NameKey].split(" ")[0];
                    productCategoryId = productCategory[IdKey];

                }

            });
            /* End - Primary Category */

            featuredContent[index] = {
                'ProductId': productId,
                'MovieGenre': productCategoryName,
            };

        });

        /* Start - Document * With Even Directory */
        var firestoreDirectory = '/' + 'PremiumStorefront'
            + '/' + 'Products'
            + '/' + 'Multimedia'
            + '/' + 'Movies'
            + '/' + 'Featured'
            + '/' + 'Content';

        firestore.doc(firestoreDirectory).set({
            ProductsIds: featuredContent,
        });
        /* End - Document * With Even Directory */

    };
    xmlHttpRequest.send();

});
/* End - Transfering Featured Products */

/* Start - Transfering All Data */
exports.transferApplicationsData = functions.runWith(runtimeOptions).https.onRequest(async (req, res) => {

    var numberOfPage = req.query.numberOfPage;

    if (numberOfPage == null) {
        numberOfPage = 1;
    }

    var applicationsEndpoint = 'https://geeksempire.co/wp-json/wc/v3/products?consumer_key=ck_e469d717bd778da4fb9ec24881ee589d9b202662&consumer_secret=cs_ac53c1b36d1a85e36a362855d83af93f0d377686'
        + '&page=' + numberOfPage
        + '&per_page=100'
        + '&category=80'
        + '&orderby=date'
        + '&order=asc';

    var xmlHttpRequest = new XMLHttpRequest();
    xmlHttpRequest.open('GET', applicationsEndpoint, true);
    xmlHttpRequest.setRequestHeader('accept', 'application/json');
    xmlHttpRequest.setRequestHeader('Content-Type', 'application/json');
    xmlHttpRequest.onreadystatechange = function () {
        if (this.readyState == 4) {

        } else {

        }
    };
    xmlHttpRequest.onprogress = function () {

    };
    xmlHttpRequest.onload = function () {

        var jsonArrayParserResponse = JSON.parse(xmlHttpRequest.responseText);

        jsonArrayParserResponse.forEach((jsonObject) => {

            setProductsApplicationsData(jsonObject);

        });

    };
    xmlHttpRequest.send();

});

async function setProductsApplicationsData(jsonObject) {

    const IdKey = "id";

    const NameKey = "name";
    const DescriptionKey = "description";
    const SummaryKey = "short_description";

    const RegularPriceKey = "regular_price";
    const SalePriceKey = "sale_price";

    const CategoriesKey = "categories";
    const TagsKey = "tags";

    const ImagesKey = "images";
    const ImageKey = "image";
    const ImageSourceKey = "src";

    const AttributesKey = "attributes";
    const AttributeOptionsKey = "options";

    const AttributesPackageNameKey = "Package Name";

    const AttributesAndroidCompatibilitiesKey = "Android Compatibilies";
    const AttributesContentSafetyRatingKey = "Content Safety Rating";

    const AttributesDeveloperEmailKey = "Developer Email";
    const AttributesDeveloperCountryKey = "Developer Country";
    const AttributesDeveloperStateKey = "Developer State";

    const AttributesDeveloperCityKey = "Developer City";
    const AttributesDeveloperNameKey = "Developer Name";
    const AttributesDeveloperWebsiteKey = "Developer Website";

    const AttributesRatingKey = "Rating";
    const AttributesYoutubeIntroductionKey = "Youtube Introduction";

    const AttributesVerticalArtKey = "Vertical Art";

    var productId = jsonObject[IdKey];

    var uniqueRecommendation = false;

    var applicationName = jsonObject[NameKey];

    var productDescription = jsonObject[DescriptionKey];
    var productSummary = jsonObject[SummaryKey];

    var productPrice = jsonObject[RegularPriceKey];
    var productSalePrice = jsonObject[SalePriceKey];

    /* Start - Images */
    var featuredContentImages = jsonObject[ImagesKey];

    var productIcon = (featuredContentImages[0])[ImageSourceKey];
    var productCover = null;
    try {
        productCover = (featuredContentImages[2])[ImageSourceKey];
    } catch (exception) {
        productCover = null;
    }
    /* End - Images */

    /* Start - Primary Category */
    var productCategories = jsonObject[CategoriesKey];

    var productCategoryName = "All";
    var productCategoryId = 15;

    productCategories.forEach((productCategory) => {

        var textCheckpoint = (productCategory)[NameKey].split(" ")[0];

        if (textCheckpoint != "All" && textCheckpoint != "Quick" && textCheckpoint != "Unique") {

            productCategoryName = productCategory[NameKey].split(" ")[0];
            productCategoryId = productCategory[IdKey];

        }

        if (textCheckpoint == "Unique") {

            uniqueRecommendation = true;

        }

    });
    /* End - Primary Category */

    /* Start - Document * With Even Directory */
    var firestoreDirectory = '/' + 'PremiumStorefront'
        + '/' + 'Products'
        + '/' + 'Android'
        + '/' + 'Applications'
        + '/' + productCategoryName
        + '/' + productId;

    await firestore.doc(firestoreDirectory).set({
        productId: productId,
        productName: applicationName,
        productDescription: productDescription,
        productSummary: productSummary,
        productCategoryName: productCategoryName,
        productCategoryId: productCategoryId,
        productIconLink: productIcon,
        productCoverLink: productCover,
        productPrice: productPrice,
        productSalePrice: productSalePrice,
        productCreatedData: admin.firestore.FieldValue.serverTimestamp(),
        uniqueRecommendation: uniqueRecommendation,
    }).then(result => {


    }).catch(error => {
        console.log(error);
    });
    /* End - Document * With Even Directory */

    /* Start - Attributes */
    var contentAttributes = jsonObject[AttributesKey];

    contentAttributes.forEach((attributesJsonObject) => {

        var attributeName = attributesJsonObject[NameKey];
        var attributeValue = attributesJsonObject[AttributeOptionsKey][0];

        var contentAttribute = {};
        contentAttribute[attributeName] = attributeValue;

        firestore.doc(firestoreDirectory)
            .update(contentAttribute).then(result => {

            }).catch(error => {
                console.log(error);
            });

    });
    /* End - Attributes */

}

exports.transferGamesData = functions.runWith(runtimeOptions).https.onRequest((req, res) => {

    var numberOfPage = req.query.numberOfPage;

    if (numberOfPage == null) {
        numberOfPage = 1;
    }

    var applicationsEndpoint = 'https://geeksempire.co/wp-json/wc/v3/products?consumer_key=ck_e469d717bd778da4fb9ec24881ee589d9b202662&consumer_secret=cs_ac53c1b36d1a85e36a362855d83af93f0d377686'
        + '&page=' + numberOfPage
        + '&per_page=100'
        + '&category=546'
        + '&orderby=date'
        + '&order=asc';

    var xmlHttpRequest = new XMLHttpRequest();
    xmlHttpRequest.open('GET', applicationsEndpoint, true);
    xmlHttpRequest.setRequestHeader('accept', 'application/json');
    xmlHttpRequest.setRequestHeader('Content-Type', 'application/json');
    xmlHttpRequest.onreadystatechange = function () {
        if (this.readyState == 4) {

        } else {

        }
    };
    xmlHttpRequest.onprogress = function () {

    };
    xmlHttpRequest.onload = function () {

        var jsonArrayParserResponse = JSON.parse(xmlHttpRequest.responseText);

        jsonArrayParserResponse.forEach((jsonObject) => {

            setProductsGamesData(jsonObject);

        });

    };
    xmlHttpRequest.send();

});

async function setProductsGamesData(jsonObject) {

    const IdKey = "id";

    const NameKey = "name";
    const DescriptionKey = "description";
    const SummaryKey = "short_description";

    const RegularPriceKey = "regular_price";
    const SalePriceKey = "sale_price";

    const CategoriesKey = "categories";
    const TagsKey = "tags";

    const ImagesKey = "images";
    const ImageKey = "image";
    const ImageSourceKey = "src";

    const AttributesKey = "attributes";
    const AttributeOptionsKey = "options";

    const AttributesPackageNameKey = "Package Name";

    const AttributesAndroidCompatibilitiesKey = "Android Compatibilies";
    const AttributesContentSafetyRatingKey = "Content Safety Rating";

    const AttributesDeveloperEmailKey = "Developer Email";
    const AttributesDeveloperCountryKey = "Developer Country";
    const AttributesDeveloperStateKey = "Developer State";

    const AttributesDeveloperCityKey = "Developer City";
    const AttributesDeveloperNameKey = "Developer Name";
    const AttributesDeveloperWebsiteKey = "Developer Website";

    const AttributesRatingKey = "Rating";
    const AttributesYoutubeIntroductionKey = "Youtube Introduction";

    const AttributesVerticalArtKey = "Vertical Art";

    var productId = jsonObject[IdKey];

    var uniqueRecommendation = false;

    var applicationName = jsonObject[NameKey];

    var productDescription = jsonObject[DescriptionKey];
    var productSummary = jsonObject[SummaryKey];

    var productPrice = jsonObject[RegularPriceKey];
    var productSalePrice = jsonObject[SalePriceKey];

    /* Start - Images */
    var featuredContentImages = jsonObject[ImagesKey];

    var productIcon = (featuredContentImages[0])[ImageSourceKey];
    var productCover = null;
    try {
        productCover = (featuredContentImages[2])[ImageSourceKey];
    } catch (exception) {
        productCover = null
    }
    /* End - Images */

    /* Start - Primary Category */
    var productCategories = jsonObject[CategoriesKey];

    var productCategoryName = "All";
    var productCategoryId = 15;

    productCategories.forEach((productCategory) => {

        var textCheckpoint = (productCategory)[NameKey].split(" ")[0];

        if (textCheckpoint != "All" && textCheckpoint != "Quick" && textCheckpoint != "Unique") {

            productCategoryName = productCategory[NameKey].split(" ")[0];
            productCategoryId = productCategory[IdKey];

        }

        if (textCheckpoint == "Unique") {

            uniqueRecommendation = true;

        }

    });
    /* End - Primary Category */

    /* Start - Document * With Even Directory */
    var firestoreDirectory = '/' + 'PremiumStorefront'
        + '/' + 'Products'
        + '/' + 'Android'
        + '/' + 'Games'
        + '/' + productCategoryName
        + '/' + productId;

    await firestore.doc(firestoreDirectory).set({
        productId: productId,
        productName: applicationName,
        productDescription: productDescription,
        productSummary: productSummary,
        productCategoryName: productCategoryName,
        productCategoryId: productCategoryId,
        productIconLink: productIcon,
        productCoverLink: productCover,
        productPrice: productPrice,
        productSalePrice: productSalePrice,
        productCreatedData: admin.firestore.FieldValue.serverTimestamp(),
        uniqueRecommendation: uniqueRecommendation,
    }).then(result => {


    }).catch(error => {
        console.log(error);
    });
    /* End - Document * With Even Directory */

    /* Start - Attributes */
    var contentAttributes = jsonObject[AttributesKey];

    contentAttributes.forEach((attributesJsonObject) => {

        var attributeName = attributesJsonObject[NameKey];
        var attributeValue = attributesJsonObject[AttributeOptionsKey][0];

        var contentAttribute = {};
        contentAttribute[attributeName] = attributeValue;

        firestore.doc(firestoreDirectory)
            .update(contentAttribute).then(result => {

            }).catch(error => {
                console.log(error);
            });

    });
    /* End - Attributes */

}

exports.transferSpecificMovieData = functions.runWith(runtimeOptions).https.onRequest((req, res) => {

    var productId = req.query.productId;

    if (productId == null) {
        productId = '3889';
    }

    var applicationsEndpoint = 'https://geeksempire.co/wp-json/wc/v3/products/'
        + productId
        + '?consumer_key=ck_e469d717bd778da4fb9ec24881ee589d9b202662&consumer_secret=cs_ac53c1b36d1a85e36a362855d83af93f0d377686';

    var xmlHttpRequest = new XMLHttpRequest();
    xmlHttpRequest.open('GET', applicationsEndpoint, true);
    xmlHttpRequest.setRequestHeader('accept', 'application/json');
    xmlHttpRequest.setRequestHeader('Content-Type', 'application/json');
    xmlHttpRequest.onreadystatechange = function () {
        if (this.readyState == 4) {

        } else {

        }
    };
    xmlHttpRequest.onprogress = function () {

    };
    xmlHttpRequest.onload = function () {

        var jsonObjectParserResponse = JSON.parse(xmlHttpRequest.responseText);

        setProductsMoviesData(jsonObjectParserResponse);

    };
    xmlHttpRequest.send();

});

exports.transferMoviesData = functions.runWith(runtimeOptions).https.onRequest((req, res) => {

    var numberOfPage = req.query.numberOfPage;

    if (numberOfPage == null) {
        numberOfPage = 1;
    }

    var applicationsEndpoint = 'https://geeksempire.co/wp-json/wc/v3/products?consumer_key=ck_e469d717bd778da4fb9ec24881ee589d9b202662&consumer_secret=cs_ac53c1b36d1a85e36a362855d83af93f0d377686'
        + '&page=' + numberOfPage
        + '&per_page=100'
        + '&category=982'
        + '&orderby=date'
        + '&order=asc';

    var xmlHttpRequest = new XMLHttpRequest();
    xmlHttpRequest.open('GET', applicationsEndpoint, true);
    xmlHttpRequest.setRequestHeader('accept', 'application/json');
    xmlHttpRequest.setRequestHeader('Content-Type', 'application/json');
    xmlHttpRequest.onreadystatechange = function () {
        if (this.readyState == 4) {

        } else {

        }
    };
    xmlHttpRequest.onprogress = function () {

    };
    xmlHttpRequest.onload = function () {

        var jsonArrayParserResponse = JSON.parse(xmlHttpRequest.responseText);

        jsonArrayParserResponse.forEach((jsonObject) => {

            setProductsMoviesData(jsonObject);

        });

    };
    xmlHttpRequest.send();

});

async function setProductsMoviesData(jsonObject) {

    const IdKey = "id";

    const NameKey = "name";
    const DescriptionKey = "description";
    const SummaryKey = "short_description";

    const RegularPriceKey = "regular_price";
    const SalePriceKey = "sale_price";

    const CategoriesKey = "categories";
    const TagsKey = "tags";

    const ImagesKey = "images";
    const ImageKey = "image";
    const ImageSourceKey = "src";

    const AttributesKey = "attributes";
    const AttributeOptionsKey = "options";

    var productId = jsonObject[IdKey];

    var uniqueRecommendation = false;

    var applicationName = jsonObject[NameKey];

    var productDescription = jsonObject[DescriptionKey];
    var productSummary = jsonObject[SummaryKey];

    /* Start - Images */
    var featuredContentImages = jsonObject[ImagesKey];

    var productPoster = (featuredContentImages[0])[ImageSourceKey];
    /* End - Images */

    /* Start - Primary Category */
    var productCategories = jsonObject[CategoriesKey];

    var productCategoryName = "All";
    var productCategoryId = 15;

    productCategories.forEach((productCategory) => {

        var textCheckpoint = (productCategory)[NameKey].split(" ")[0];

        if (textCheckpoint != "All" && textCheckpoint != "Quick" && textCheckpoint != "Unique") {

            productCategoryName = productCategory[NameKey].split(" ")[0];
            productCategoryId = productCategory[IdKey];

        }

        if (textCheckpoint == "Unique") {

            uniqueRecommendation = true;

        }

    });
    /* End - Primary Category */

    /* Start - Document * With Even Directory */
    var firestoreDirectory = '/' + 'PremiumStorefront'
        + '/' + 'Products'
        + '/' + 'Multimedia'
        + '/' + 'Movies'
        + '/' + productCategoryName
        + '/' + productId;

    await firestore.doc(firestoreDirectory).set({
        productId: productId,
        productName: applicationName,
        productDescription: productDescription,
        productSummary: productSummary,
        productPoster: productPoster,
        primaryGenre: productCategoryName,
        uniqueRecommendation: uniqueRecommendation,
    }).then(result => {


    }).catch(error => {
        console.log(error);
    });
    /* End - Document * With Even Directory */

    /* Start - Attributes */
    var contentAttributes = jsonObject[AttributesKey];

    contentAttributes.forEach((attributesJsonObject) => {

        var attributeName = attributesJsonObject[NameKey];
        var attributeValue = attributesJsonObject[AttributeOptionsKey];

        var contentAttribute = {};
        contentAttribute[attributeName] = attributeValue.toString();

        firestore.doc(firestoreDirectory)
            .update(contentAttribute).then(result => {

            }).catch(error => {
                console.log(error);
            });

    });
    /* End - Attributes */

}
/* End - Transfering All Data */

/* Start - Transferring Categories Data */
exports.transferApplicationsCategories = functions.runWith(runtimeOptions).https.onRequest((req, res) => {

    var numberOfPage = req.query.numberOfPage;

    if (numberOfPage == null) {
        numberOfPage = 1;
    }

    var applicationsEndpoint = 'https://geeksempire.co/wp-json/wc/v3/products/categories?consumer_key=ck_e469d717bd778da4fb9ec24881ee589d9b202662&consumer_secret=cs_ac53c1b36d1a85e36a362855d83af93f0d377686'
        + '&page=' + numberOfPage
        + '&per_page=100';

    var xmlHttpRequest = new XMLHttpRequest();
    xmlHttpRequest.open('GET', applicationsEndpoint, true);
    xmlHttpRequest.setRequestHeader('accept', 'application/json');
    xmlHttpRequest.setRequestHeader('Content-Type', 'application/json');
    xmlHttpRequest.onreadystatechange = function () {
        if (this.readyState == 4) {

        } else {

        }
    };
    xmlHttpRequest.onprogress = function () {

    };
    xmlHttpRequest.onload = function () {

        var jsonArrayParserResponse = JSON.parse(xmlHttpRequest.responseText);

        var applicationCategoryArray = [];

        jsonArrayParserResponse.forEach((jsonObject, index) => {

            var categoryId = jsonObject['id'].toString();
            var categoryName = jsonObject['name'].toString();
            var categoryIconLink = jsonObject['image']['src'].toString();
            
            var productCount = jsonObject['count'].toString();

            if (categoryName.split(" ")[1] == 'Applications') {

                var applications = {};

                applications['categoryId'] = categoryId;
                applications['categoryName'] = categoryName.split(" ")[0];
                applications['categoryIconLink'] = categoryIconLink;
                applications['productCount'] = productCount;

                applicationCategoryArray[index] = applications;

            }

        });

        var initialEntry = {};

        initialEntry['categoryId'] = '80';
        initialEntry['categoryName'] = 'All';
        initialEntry['categoryIconLink'] = 'https://geeksempire.co/wp-content/uploads/2021/05/main_section_icon.png';
        initialEntry['productCount'] = '666';

        applicationCategoryArray[applicationCategoryArray.length] = initialEntry;

        var firestoreDirectory = '/' + 'PremiumStorefront'
            + '/' + 'Products'
            + '/' + 'Android'
            + '/' + 'Applications';

        firestore.settings({ ignoreUndefinedProperties: true });
        firestore.doc(firestoreDirectory).set({
            CategoriesIds: applicationCategoryArray,
        });

    };
    xmlHttpRequest.send();

});

exports.transferGamesCategories = functions.runWith(runtimeOptions).https.onRequest((req, res) => {

    var numberOfPage = req.query.numberOfPage;

    if (numberOfPage == null) {
        numberOfPage = 1;
    }

    var applicationsEndpoint = 'https://geeksempire.co/wp-json/wc/v3/products/categories?consumer_key=ck_e469d717bd778da4fb9ec24881ee589d9b202662&consumer_secret=cs_ac53c1b36d1a85e36a362855d83af93f0d377686'
        + '&page=' + numberOfPage
        + '&per_page=100';

    var xmlHttpRequest = new XMLHttpRequest();
    xmlHttpRequest.open('GET', applicationsEndpoint, true);
    xmlHttpRequest.setRequestHeader('accept', 'application/json');
    xmlHttpRequest.setRequestHeader('Content-Type', 'application/json');
    xmlHttpRequest.onreadystatechange = function () {
        if (this.readyState == 4) {

        } else {

        }
    };
    xmlHttpRequest.onprogress = function () {

    };
    xmlHttpRequest.onload = function () {

        var jsonArrayParserResponse = JSON.parse(xmlHttpRequest.responseText);

        var gameCategoryArray = [];

        jsonArrayParserResponse.forEach((jsonObject, index) => {

            var categoryId = jsonObject['id'].toString();
            var categoryName = jsonObject['name'].toString();
            var categoryIconLink = jsonObject['image']['src'].toString();
            
            var productCount = jsonObject['count'].toString();

            if (categoryName.split(" ")[1] == 'Games') {

                var games = {};

                games['categoryId'] = categoryId;
                games['categoryName'] = categoryName.split(" ")[0];
                games['categoryIconLink'] = categoryIconLink;
                games['productCount'] = productCount;

                gameCategoryArray[index] = games;

            }

        });

        var initialEntry = {};

        initialEntry['categoryId'] = '546';
        initialEntry['categoryName'] = 'All';
        initialEntry['categoryIconLink'] = 'https://geeksempire.co/wp-content/uploads/2021/05/main_section_icon.png';
        initialEntry['productCount'] = '666';

        gameCategoryArray[gameCategoryArray.length] = initialEntry;

        var firestoreDirectory = '/' + 'PremiumStorefront'
            + '/' + 'Products'
            + '/' + 'Android'
            + '/' + 'Games';

        firestore.settings({ ignoreUndefinedProperties: true });
        firestore.doc(firestoreDirectory).set({
            CategoriesIds: gameCategoryArray,
        });

    };
    xmlHttpRequest.send();

});

exports.transferMoviesCategories = functions.runWith(runtimeOptions).https.onRequest((req, res) => {

    var numberOfPage = req.query.numberOfPage;

    if (numberOfPage == null) {
        numberOfPage = 1;
    }

    var applicationsEndpoint = 'https://geeksempire.co/wp-json/wc/v3/products/categories?consumer_key=ck_e469d717bd778da4fb9ec24881ee589d9b202662&consumer_secret=cs_ac53c1b36d1a85e36a362855d83af93f0d377686'
        + '&page=' + numberOfPage
        + '&per_page=100';

    var xmlHttpRequest = new XMLHttpRequest();
    xmlHttpRequest.open('GET', applicationsEndpoint, true);
    xmlHttpRequest.setRequestHeader('accept', 'application/json');
    xmlHttpRequest.setRequestHeader('Content-Type', 'application/json');
    xmlHttpRequest.onreadystatechange = function () {
        if (this.readyState == 4) {

        } else {

        }
    };
    xmlHttpRequest.onprogress = function () {

    };
    xmlHttpRequest.onload = function () {

        var jsonArrayParserResponse = JSON.parse(xmlHttpRequest.responseText);

        var moviesCategoryArray = [];
        
        jsonArrayParserResponse.forEach((jsonObject, index) => {

            var categoryId = jsonObject['id'].toString();
            var categoryName = jsonObject['name'].toString();
            var categoryIconLink = jsonObject['image']['src'].toString();

            var productCount = jsonObject['count'].toString();

            if (categoryName.split(" ")[1] == 'Movies') {

                var movies = {};

                movies['genreId'] = categoryId;
                movies['genreName'] = categoryName.split(" ")[0];
                movies['genreIconLink'] = categoryIconLink;
                movies['productCount'] = productCount;

                moviesCategoryArray[index] = movies;

            }

        });
        
        var firestoreDirectory = '/' + 'PremiumStorefront'
            + '/' + 'Products'
            + '/' + 'Multimedia'
            + '/' + 'Movies';

        firestore.settings({ ignoreUndefinedProperties: true });
        firestore.doc(firestoreDirectory).set({
            GenreIds: moviesCategoryArray,
        });

    };
    xmlHttpRequest.send();

});
/* End - Transferring Categories Data */