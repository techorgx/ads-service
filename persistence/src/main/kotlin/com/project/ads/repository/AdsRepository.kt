package com.project.ads.repository

import com.project.ads.model.Ad
import com.project.ads.util.AdStatus

interface AdsRepository {
    fun <S : Ad?> save(entity: S): S

    fun findById(id: String): Ad?

    fun updateAdStatus(
        id: String,
        status: AdStatus,
    )
}
