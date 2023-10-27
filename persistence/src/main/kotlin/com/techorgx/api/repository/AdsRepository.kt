package com.techorgx.api.repository

import com.techorgx.api.model.Ad
import com.techorgx.api.util.AdStatus

interface AdsRepository {
    fun <S : Ad?> save(entity: S): S

    fun findById(id: String): Ad?

    fun updateAdStatus(
        id: String,
        status: AdStatus,
    )

    fun getAdsByUser(id: String): List<Ad>
}
