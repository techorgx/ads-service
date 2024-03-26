package com.techorgx.api.repository

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression
import com.techorgx.api.model.Ad
import com.techorgx.api.util.AdStatus
import io.grpc.Status
import io.grpc.StatusException
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.stereotype.Component

@Component
class AdsRepositoryImpl(
    private val dynamoDBMapper: DynamoDBMapper,
) : AdsRepository {
    override fun <S : Ad?> save(entity: S): S {
        try {
            dynamoDBMapper.save(entity)
        } catch (e: Exception) {
            logger.error(e)
            throw StatusException(Status.INTERNAL.withDescription("Ad can not be saved"))
        }
        return entity
    }

    override fun findById(
        id: String,
        username: String,
    ): Ad? {
        val ad = Ad(id = id, username = username)
        try {
            return dynamoDBMapper.load(ad)
        } catch (e: Exception) {
            logger.error(e)
        }
        return null
    }

    override fun updateAdStatus(
        id: String,
        status: AdStatus,
        username: String,
    ) {
        val ad = findById(id = id, username = username)
        ad?.let {
            it.status = status.name
            save(ad)
        } ?: throw StatusException(Status.INTERNAL.withDescription("Ad can not be updated"))
    }

    override fun getAdsByUser(username: String): List<Ad> {
        val query = DynamoDBQueryExpression<Ad>()
        val ad = Ad(username = username)
        query.hashKeyValues = ad
        try {
            return dynamoDBMapper.query(Ad::class.java, query)
        } catch (e: Exception) {
            logger.error(e)
            throw StatusException(Status.INTERNAL.withDescription("Ad can not be fetched"))
        }
    }

    override fun deleteAd(
        id: String,
        username: String,
    ) {
        val ad = Ad(id = id, username = username)
        try {
            dynamoDBMapper.delete(ad)
        } catch (e: Exception) {
            println(e)
        }
    }

    override fun getAdsByLocation(location: String): List<Ad> {
        val query = DynamoDBQueryExpression<Ad>()
        val ad = Ad(location = location)
        println(ad)
        query.hashKeyValues = ad
        try {
            return dynamoDBMapper.query(Ad::class.java, query)
        } catch (e: Exception) {
            logger.error(e)
            throw StatusException(Status.INTERNAL.withDescription("Ads can not be fetched"))
        }
    }

    private companion object {
        val logger: Logger = LogManager.getLogger(AdsRepositoryImpl::class.java)
    }
}
