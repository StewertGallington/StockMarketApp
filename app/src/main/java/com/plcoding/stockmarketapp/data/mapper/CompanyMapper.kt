package com.plcoding.stockmarketapp.data.mapper

import com.plcoding.stockmarketapp.data.local.CompanyListingEntity
import com.plcoding.stockmarketapp.data.remote.dto.CompanyInfoDto
import com.plcoding.stockmarketapp.domain.model.CompanyInfo
import com.plcoding.stockmarketapp.domain.model.CompanyListing
import java.util.UUID

fun CompanyListingEntity.toCompanyListing() : CompanyListing {
    return CompanyListing(
        symbol = symbol,
        name = name,
        exchange = exchange,
        assetType = assetType,
        ipoDate = ipoDate,
        delistingDate = delistingDate,
        status = status
    )
}

fun CompanyListing.toCompanyListingEntity() : CompanyListingEntity {
    return CompanyListingEntity(
        id = 0,
        symbol = symbol,
        name = name,
        exchange = exchange,
        assetType = assetType,
        ipoDate = ipoDate,
        delistingDate = delistingDate,
        status = status
    )
}

fun CompanyInfoDto.toCompanyInfo() : CompanyInfo {
    return CompanyInfo(
        symbol = symbol ?: "",
        description = description ?: "",
        name = name ?: "",
        country = country ?: "",
        industry = industry ?: ""
    )
}