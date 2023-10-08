package com.project.ads.grpc

import com.project.ads.api.v1.AdsApiGrpcKt
import com.project.ads.api.v1.CreateAdRequest
import com.project.ads.api.v1.CreateAdResponse
import com.project.ads.api.v1.GetAdRequest
import com.project.ads.api.v1.GetAdResponse
import com.project.ads.api.v1.UpdateAdStatusRequest
import com.project.ads.api.v1.UpdateAdStatusResponse
import com.project.ads.service.AdsService
import org.lognet.springboot.grpc.GRpcService

@GRpcService
class AdsApi(
    private val adsService: AdsService,
) : AdsApiGrpcKt.AdsApiCoroutineImplBase() {
    override suspend fun createAd(request: CreateAdRequest): CreateAdResponse {
        return adsService.createAd(request)
    }

    override suspend fun getAd(request: GetAdRequest): GetAdResponse {
        return adsService.getAd(request)
    }

    override suspend fun updateAdStatus(request: UpdateAdStatusRequest): UpdateAdStatusResponse {
        return adsService.updateAdStatus(request)
    }
}
