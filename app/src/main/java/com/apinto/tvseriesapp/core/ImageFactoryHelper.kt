package com.apinto.tvseriesapp.core

import com.apinto.tvseriesapp.model.ImageConfiguration

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

    fun getBiggerSizePosterPath(imagePath: String): String? {
        val size = (imageConfiguration?.posterSizes?.let {
            (it.find {size -> size == "w500" }) ?: it[0]
        })

        return "${imageConfiguration?.secureBaseUrl}$size$imagePath"
    }

}