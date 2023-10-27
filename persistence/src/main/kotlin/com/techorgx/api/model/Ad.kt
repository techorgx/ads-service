package com.techorgx.api.model

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable

@DynamoDBTable(tableName = "Ads")
data class Ad(
    @DynamoDBHashKey
    var id: String = "",
    @DynamoDBRangeKey
    var username: String = "",
    var title: String = "",
    var description: String = "",
    var price: Double = 0.0,
    var status: String = "",
)
