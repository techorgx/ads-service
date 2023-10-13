package com.project.ads.model

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable

@DynamoDBTable(tableName = "Ads")
data class Ad(
    @DynamoDBHashKey
    var id: String = "",
    var title: String = "",
    var description: String = "",
    var price: Double = 0.0,
    var owner: String = "",
    var status: String = "",
)
