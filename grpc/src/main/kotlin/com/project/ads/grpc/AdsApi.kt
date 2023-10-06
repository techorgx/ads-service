package com.project.ads.grpc

import com.project.ads.api.v1.AdsApiGrpcKt
import com.project.ads.api.v1.CreateAdRequest
import com.project.ads.api.v1.CreateAdResponse
import com.project.ads.api.v1.GetAdRequest
import com.project.ads.api.v1.GetAdResponse
import org.lognet.springboot.grpc.GRpcService

@GRpcService
class AdsApi : AdsApiGrpcKt.AdsApiCoroutineImplBase() {
    override suspend fun createAd(request: CreateAdRequest): CreateAdResponse {
        return super.createAd(request)
    }

    override suspend fun getAd(request: GetAdRequest): GetAdResponse {
        return super.getAd(request)
    }
}
