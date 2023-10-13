package com.project.ads.mapper

import com.project.ads.api.v1.CreateAdRequest
import com.project.ads.model.Ad
import org.bson.types.ObjectId
import org.springframework.stereotype.Component

@Component
class AdMapper {
    fun mapAd(createAdRequest: CreateAdRequest): Ad {
        return Ad(
            id = ObjectId().toString(),
            description = createAdRequest.description,
            title = createAdRequest.title,
            status = createAdRequest.status,
            owner = createAdRequest.owner,
            price = createAdRequest.price,
        )
    }
}
