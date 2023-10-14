package com.techorgx.api.service

import com.techorgx.ads.api.v1.CreateAdRequest
import com.techorgx.ads.api.v1.CreateAdResponse
import com.techorgx.ads.api.v1.GetAdRequest
import com.techorgx.ads.api.v1.GetAdResponse
import com.techorgx.ads.api.v1.UpdateAdStatusRequest
import com.techorgx.ads.api.v1.UpdateAdStatusResponse
import com.techorgx.api.mapper.AdMapper
import com.techorgx.api.repository.AdsRepository
import com.techorgx.api.util.AdStatus
import io.grpc.Status
import io.grpc.StatusException
import org.springframework.stereotype.Service

@Service
class AdsService(
    private val adsRepository: AdsRepository,
    private val adMapper: AdMapper,
) {
    fun createAd(request: CreateAdRequest): CreateAdResponse {
        val ad = adsRepository.save(adMapper.mapAd(request))
        return CreateAdResponse
            .newBuilder()
            .setId(ad.id)
            .build()
    }

    fun getAd(request: GetAdRequest): GetAdResponse {
        val ad = adsRepository.findById(request.id)
        ad?.let {
            return GetAdResponse
                .newBuilder()
                .setId(it.id)
                .setDescription(it.description)
                .setOwner(it.owner)
                .setPrice(it.price)
                .setStatus(it.status)
                .build()
        } ?: throw StatusException(Status.NOT_FOUND.withDescription("Ad not found"))
    }

    fun updateAdStatus(request: UpdateAdStatusRequest): UpdateAdStatusResponse {
        adsRepository.updateAdStatus(request.id, AdStatus.valueOf(request.status))
        return UpdateAdStatusResponse
            .newBuilder()
            .setId(request.id)
            .setUpdatedStatus(request.status)
            .build()
    }
}