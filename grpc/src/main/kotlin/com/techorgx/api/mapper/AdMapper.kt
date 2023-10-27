package com.techorgx.api.mapper

import com.techorgx.ads.api.v1.CreateAdRequest
import com.techorgx.api.model.Ad
import com.techorgx.api.util.getEnumValue
import org.bson.types.ObjectId
import org.springframework.stereotype.Component

@Component
class AdMapper {
    fun mapAd(createAdRequest: CreateAdRequest): Ad {
        return Ad(
            id = ObjectId().toString(),
            description = createAdRequest.description,
            title = createAdRequest.title,
            status = getEnumValue(createAdRequest.status),
            username = createAdRequest.username,
            price = createAdRequest.price,
        )
    }
}
