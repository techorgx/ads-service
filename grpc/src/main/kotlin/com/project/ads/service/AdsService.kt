package com.project.ads.service

import com.project.ads.api.v1.CreateAdRequest
import com.project.ads.api.v1.CreateAdResponse
import com.project.ads.api.v1.GetAdRequest
import com.project.ads.api.v1.GetAdResponse
import org.springframework.stereotype.Service

@Service
class AdsService {
    fun createAd(request: CreateAdRequest): CreateAdResponse {
        return CreateAdResponse.getDefaultInstance()
    }

    fun getAd(request: GetAdRequest): GetAdResponse {
        return GetAdResponse.getDefaultInstance()
    }
}
