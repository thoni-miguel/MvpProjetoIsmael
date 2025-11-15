package com.thoni.mvpprojetoismael.data.repository

import com.thoni.mvpprojetoismael.data.local.entity.EpiTypeEntity
import com.thoni.mvpprojetoismael.domain.model.EpiType

fun EpiTypeEntity.toDomain(): EpiType = EpiType(
    id = id,
    name = name,
    monthsValidity = monthsValidity,
    synced = synced,
    updatedAt = updatedAt
)

fun EpiType.toEntity(): EpiTypeEntity = EpiTypeEntity(
    id = id,
    name = name,
    monthsValidity = monthsValidity,
    synced = synced,
    updatedAt = updatedAt
)
