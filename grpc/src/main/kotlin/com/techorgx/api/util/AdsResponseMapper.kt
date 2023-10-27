package com.techorgx.api.util

import com.techorgx.ads.api.v1.GetAdsByUserResponse
import com.techorgx.api.model.Ad
import org.springframework.stereotype.Component
import com.techorgx.ads.api.v1.Ad as AdResponse

@Component
class AdsResponseMapper {
    fun mapToAds(ads: List<Ad>): GetAdsByUserResponse {
        val responseBuilder = GetAdsByUserResponse.newBuilder()
        val adResponseList = mutableListOf<AdResponse>()
        ads.forEach {
            adResponseList.add(
                AdResponse.newBuilder()
                    .setUsername(it.username)
                    .setId(it.id)
                    .setStatus(getEnumValue(it.status))
                    .setDescription(it.description)
                    .setPrice(it.price.toString())
                    .setTitle(it.title)
                    .build(),
            )
        }
        responseBuilder.addAllAds(adResponseList)
        return responseBuilder.build()
    }
}
