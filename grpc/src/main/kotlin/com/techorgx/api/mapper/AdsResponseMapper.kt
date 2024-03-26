package com.techorgx.api.mapper

import com.techorgx.ads.api.v1.GetAdsByUserResponse
import com.techorgx.api.model.Ad
import com.techorgx.api.util.getEnumValue
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
                    .setLocation(it.location)
                    .build(),
            )
        }
        responseBuilder.addAllAds(adResponseList)
        return responseBuilder.build()
    }
}
