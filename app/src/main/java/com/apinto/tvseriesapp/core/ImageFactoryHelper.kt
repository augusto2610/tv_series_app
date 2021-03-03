package com.apinto.tvseriesapp.core

import com.apinto.tvseriesapp.model.ImageConfiguration

/**
 * This class is a helper that build the right url based on the size.
 * The components of the URL are retrieved for /configuration endpoint so this helper creates
 * the url with the right components.
 */
class ImageFactoryHelper {

    private var imageConfiguration: ImageConfiguration? = null

    fun setImageConfig(config: ImageConfiguration) {
        imageConfiguration = config
    }

    fun getDefaultSizeBackdropPath(imagePath: String): String? {
        val size = (imageConfiguration?.backdropSizes?.let {
            (it.find {size -> size == "w780" }) ?: it[0]
        })

        return "${imageConfiguration?.secureBaseUrl}$size$imagePath"
    }

    fun getDefaultSizePosterPath(imagePath: String): String? {
        val size = (imageConfiguration?.posterSizes?.let {
            (it.find {size -> size == "w500" }) ?: it[0]
        })

        return "${imageConfiguration?.secureBaseUrl}$size$imagePath"
    }

    fun getOriginalSizePath(imagePath: String): String? {
        val size = (imageConfiguration?.posterSizes?.let {
            it.last()
        })

        return "${imageConfiguration?.secureBaseUrl}$size$imagePath"
    }

}