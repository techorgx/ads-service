package com.project.ads.grpc

import com.project.ads.api.v1.AdsApiGrpcKt
import com.project.ads.api.v1.CreateAdRequest
import com.project.ads.api.v1.CreateAdResponse
import com.project.ads.api.v1.GetAdRequest
import com.project.ads.api.v1.GetAdResponse
import com.project.ads.api.v1.UpdateAdStatusRequest
import com.project.ads.api.v1.UpdateAdStatusResponse
import com.project.ads.service.AdsService
import com.project.ads.util.formatPayload
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.lognet.springboot.grpc.GRpcService

@GRpcService
class AdsApi(
    private val adsService: AdsService,
) : AdsApiGrpcKt.AdsApiCoroutineImplBase() {
    override suspend fun createAd(request: CreateAdRequest): CreateAdResponse {
        logger.info("Received request on createAd: ${formatPayload(request)}")
        return adsService.createAd(request).also {
            logger.info("createAd responded with: ${formatPayload(it)}}")
        }
    }

    override suspend fun getAd(request: GetAdRequest): GetAdResponse {
        logger.info("Received request on getAd: ${formatPayload(request)}")
        return adsService.getAd(request).also {
            logger.info("getAd responded with: ${formatPayload(it)}")
        }
    }

    override suspend fun updateAdStatus(request: UpdateAdStatusRequest): UpdateAdStatusResponse {
        logger.info("Received request on updateAdStatus: ${formatPayload(request)}")
        return adsService.updateAdStatus(request).also {
            logger.info("updateAdStatus responded with: ${formatPayload(it)}")
        }
    }

    private companion object {
        val logger: Logger = LogManager.getLogger(AdsApi::class.qualifiedName)
    }
}
