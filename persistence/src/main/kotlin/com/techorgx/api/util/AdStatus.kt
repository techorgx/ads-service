package com.techorgx.api.util

import io.grpc.Status
import io.grpc.StatusException

enum class AdStatus {
    LIVE,
    EXPIRED,
    CLOSED,
}

fun getEnumValue(status: String): String {
    try {
        return AdStatus.valueOf(status).toString()
    } catch (e: Exception) {
        throw StatusException(
            Status.INVALID_ARGUMENT.withDescription("Invalid status $status, select from valid statuses ${AdStatus.values().toList()}"),
        )
    }
}
