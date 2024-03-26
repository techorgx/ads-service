package com.techorgx.api.service

import com.techorgx.ads.api.v1.CreateAdRequest
import com.techorgx.ads.api.v1.CreateAdResponse
import com.techorgx.ads.api.v1.DeleteAdRequest
import com.techorgx.ads.api.v1.DeleteAdResponse
import com.techorgx.ads.api.v1.GetAdRequest
import com.techorgx.ads.api.v1.GetAdResponse
import com.techorgx.ads.api.v1.GetAdsByLocationRequest
import com.techorgx.ads.api.v1.GetAdsByLocationResponse
import com.techorgx.ads.api.v1.GetAdsByUserRequest
import com.techorgx.ads.api.v1.GetAdsByUserResponse
import com.techorgx.ads.api.v1.UpdateAdStatusRequest
import com.techorgx.ads.api.v1.UpdateAdStatusResponse
import com.techorgx.api.mapper.AdMapper
import com.techorgx.api.repository.AdsRepository
import com.techorgx.api.util.AdStatus
import com.techorgx.api.util.AdsResponseMapper
import com.techorgx.api.util.getEnumValue
import io.grpc.Status
import io.grpc.StatusException
import org.springframework.stereotype.Service

@Service
class AdsService(
    private val adsRepository: AdsRepository,
    private val adMapper: AdMapper,
    private val adsResponseMapper: AdsResponseMapper,
) {
    fun createAd(request: CreateAdRequest): CreateAdResponse {
        val ad = adsRepository.save(adMapper.mapAd(request))
        return CreateAdResponse
            .newBuilder()
            .setId(ad.id)
            .build()
    }

    fun getAd(request: GetAdRequest): GetAdResponse {
        val ad = adsRepository.findById(request.id, request.username)
        ad?.let {
            return GetAdResponse
                .newBuilder()
                .setId(it.id)
                .setDescription(it.description)
                .setPrice(it.price)
                .setStatus(getEnumValue(it.status))
                .setUsername(it.username)
                .setTitle(it.title)
                .build()
        } ?: throw StatusException(Status.NOT_FOUND.withDescription("Ad not found"))
    }

    fun updateAdStatus(request: UpdateAdStatusRequest): UpdateAdStatusResponse {
        adsRepository.updateAdStatus(request.id, AdStatus.valueOf(request.status), request.username)
        return UpdateAdStatusResponse
            .newBuilder()
            .setId(request.id)
            .setUpdatedStatus(getEnumValue(request.status))
            .setUsername(request.username)
            .build()
    }

    fun deleteAd(request: DeleteAdRequest): DeleteAdResponse {
        adsRepository.deleteAd(request.id, request.username)
        return DeleteAdResponse.getDefaultInstance()
    }

    fun getAdsByUser(request: GetAdsByUserRequest): GetAdsByUserResponse {
        val ads = adsRepository.getAdsByUser(request.username)
        return adsResponseMapper.mapToAds(ads)
    }

    fun getAdsByLocation(request: GetAdsByLocationRequest): GetAdsByLocationResponse {
        val location = request.location
        return GetAdsByLocationResponse.getDefaultInstance()
    }
}
